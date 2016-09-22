package com.pingan.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.pingan.constant.Constant;
import com.pingan.dao.CustomerDao;
import com.pingan.dao.impl.MongoDbDaoImpl;
import com.pingan.dao.impl.MysqlDaoImpl;
import com.pingan.domain.Ivector;
import com.pingan.utils.IvectorUtils;
import com.pingan.utils.PublicUtils;

/**
 * 处理register请求的servlet
 * 
 * @author ning
 */
// @WebFilter(urlPatterns = "/demo", asyncSupported = true,initParams = {
// @WebInitParam(name = "filePath", value = "register"),
// @WebInitParam(name = "tempFilePath", value = "registertemp"),
// @WebInitParam(name = "Ivector", value = "ivector") })
public class RegisterServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private CustomerDao service;
	private File tempFile;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		initDir();
		tempFile = new File(Constant.TEMPPATH);

	}

	private void initDir() {
		PublicUtils.mkDir(Constant.FILEPATH);
		PublicUtils.mkDir(Constant.VOICEPATH);
		PublicUtils.mkDir(Constant.TEMPPATH);
		PublicUtils.mkDir(Constant.VOICEPATH);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String telnum = ""; // 注册手机号

		response.setContentType("text/plain");
		// 向客户端发送响应正文
		PrintWriter outNet = response.getWriter();

		boolean isMultipart = ServletFileUpload.isMultipartContent(request);

		System.out.println("servlet------");

		if (isMultipart) {
			System.out.println("isMutipart=======true");


			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(6 * 1024); // 设置缓冲区大小为6K
			factory.setRepository(tempFile); // 设置临时目录

			// 创建一个文件上传处理器
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setHeaderEncoding("utf-8");
			upload.setSizeMax(10 * 1024 * 1024); // 允许文件的最大上传尺寸10M

			try {

				List<FileItem> items = upload.parseRequest(request);
				for (FileItem item : items) {
					if (item.isFormField()) {
						// 非文件参数
						// processFormField(item, outNet); // 处理普通的表单域

						String name = item.getFieldName();

						if (name.equals("tel")) {
							telnum = item.getString();
							System.out.println("tel:" + telnum);

						}

					} else {
						// 文件
						int code = processUploadedFile(item, outNet, telnum); // 处理上传文件

						System.out.println("upload isFile----------" + code);
						outNet.println(code);
					}

				}
				outNet.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 处理文件上传
	 * 
	 * @param item
	 * @param outNet
	 * @param dir
	 * @return 0成功    1上传非wav文件      2文件上传失败     3注册失败
	 */
	private int processUploadedFile(FileItem item, PrintWriter outNet,
			String telnum) {
		String filename = item.getName();

		try {
			long fileSize = item.getSize();
			if (filename.equals("") && fileSize == 0)
				return 2;
			String[] split = filename.split("\\.");
			String filetype = split[split.length - 1];
			if (!filetype.equals("wav")) {
				return 1;
			}

			System.out.println("filename:====" + filename);
			File uploadedFile = new File(Constant.VOICEPATH, filename);
			item.write(uploadedFile);

			int dircode = telnum.hashCode() & 0xf;
			String ivectorHashPath = Constant.IVECTOR_PATH + "/" + dircode;

			PublicUtils.mkDir(ivectorHashPath);
			
			boolean isOk = KaldiRegister(Constant.VOICEPATH, ivectorHashPath,
					filename, telnum);

			if (isOk) {
				Ivector c = new Ivector(telnum, ivectorHashPath + "/" + telnum
						+ ".ivector");
				switch (Constant.WHICH_DATABASE) {
				case MYSQL:
					service = new MysqlDaoImpl();
					break;
				case MONGODB:
					service = new MongoDbDaoImpl();
					break;
				}
				if (service.find(c.getTelnum()) != null) {
					service.update(c);
				} else {
					service.register(c);
				}
				return 0;
			} else {
				return 3;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 2;
	}

	/**
	 * 注册声纹
	 * 
	 * @param register_dir
	 *            wav路径
	 * @param inputpath
	 *            特征文件输出的路径
	 * @return
	 */
	public boolean KaldiRegister(String register_dir, String inputpath,
			String filename, String telnum) {

		// xxx/register/filename.wav
		String filepath = register_dir + "/" + filename;

		List<String> ivecter_list = IvectorUtils.KaldiToIvecter(filepath,
				Constant.TOOLPATH);

		// 删除注册用的声纹文件.
		PublicUtils.deletefile(filepath);

		boolean isok = IvectorUtils.ListToFile(ivecter_list, inputpath, telnum
				+ ".ivector");
		return isok;
	}

}
