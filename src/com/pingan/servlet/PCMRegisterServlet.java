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

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.pingan.constant.Constant;
import com.pingan.dao.CustomerDao;
import com.pingan.dao.impl.MongoDbDaoImpl;
import com.pingan.dao.impl.MysqlDaoImpl;
import com.pingan.domain.Ivector;
import com.pingan.domain.IvectorV;
import com.pingan.domain.PCMResponseObject;
import com.pingan.service.MongoDBService;
import com.pingan.service.impl.MongoDBServiceImpl;
import com.pingan.utils.IvectorUtils;
import com.pingan.utils.PCMUtil;
import com.pingan.utils.PublicUtils;

/**
 * 处理register请求的servlet
 * 
 * @author ning
 */
public class PCMRegisterServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private MongoDBService service;
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
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String customer_id = ""; // 注册手机号

		response.setContentType("text/plain");
		// 向客户端发送响应正文
		PrintWriter outNet = response.getWriter();

		boolean isMultipart = ServletFileUpload.isMultipartContent(request);

		System.out.println("PCMRegisterServlet Run!");

		if (isMultipart) {
			System.out.println("isMutipart=======true");

			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(8 * 1024); // 设置缓冲区大小为8K
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

						if (name.equals("customer_id")) {
							customer_id = item.getString();
							System.out.println("customer_id:" + customer_id);
						}

					} else {
						// 文件
						String uploadpath = Constant.VOICEPATH;

						File pcmfile = processUploadedFile(item, customer_id,
								uploadpath); // 处理上传文件

						String wavfilePath = Constant.VOICEPATH + "/"
								+ customer_id + ".wav";
						
						File wavfile = new File(wavfilePath);

//						PublicUtils.rawToWave(pcmfile, wavfile);
						PCMUtil.convertAudioFiles(pcmfile.getAbsolutePath(), wavfilePath);
						
						System.out.println(wavfilePath);
						
						List<String> ivectorList = IvectorUtils.KaldiToIvecter(
								wavfilePath, Constant.TOOLPATH);
						
						System.out.println("----"+ivectorList.size());
						

//						PublicUtils.deletefile(pcmfile);
//						PublicUtils.deletefile(wavfile);

						String ivectorStr = IvectorUtils
								.ListToString(ivectorList);

						PCMResponseObject po = new PCMResponseObject(
								customer_id, ivectorStr);

						outNet.print((JSONObject.fromObject(po)).toString());

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
	 * @param customer_id
	 * @param uploadpath  文件上传的目录
	 * @return 0成功 1上传非wav文件
	 */
	private File processUploadedFile(FileItem item, String customer_id,
			String uploadpath) {

		String filename = item.getName();

		try {
			long fileSize = item.getSize();

			if (filename.equals("") && fileSize == 0) {
				System.out.println("File upload failed!");
				return null;
			}

			String[] split = filename.split("\\.");
			String filetype = split[split.length - 1];
			if (!filetype.equals("pcm")) {
				System.out.println("Not a PCM file!");
				return null;
			}

			// int firstdircode = customer_id.hashCode() & 0xf;
			// int secondircode = (customer_id.hashCode() >> 4) & 0xf;
			//
			// System.out.println("filename:====" + filename);
			//
			// String voicehashpath = Constant.VOICEPATH + "/" + firstdircode
			// + "/" + secondircode;

			PublicUtils.mkDir(uploadpath);
			File uploadedFile = new File(uploadpath, filename);
			item.write(uploadedFile);
			
			System.out.println("PCM_path"+uploadedFile.getAbsolutePath());

			return uploadedFile;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

}
