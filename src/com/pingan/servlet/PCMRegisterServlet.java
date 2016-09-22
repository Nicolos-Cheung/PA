package com.pingan.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.pingan.constant.Constant;
import com.pingan.domain.PCMObject;
import com.pingan.domain.PCMResponseObject;
import com.pingan.service.PCMMongoDBService;
import com.pingan.service.impl.PCMMongoDBServiceImpl;
import com.pingan.utils.IvectorUtils;
import com.pingan.utils.PCMUtil;
import com.pingan.utils.PublicUtils;
import com.sun.medialib.mlib.Constants;

/**
 * 处理register请求的servlet
 * 
 * @author ning
 */
public class PCMRegisterServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private PCMMongoDBService service = new PCMMongoDBServiceImpl();
	private File tempFile;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		initDir();
		tempFile = new File(Constant.TEMPPATH);

	}

	private void initDir() {
		PublicUtils.mkDir(Constant.FILEPATH);
		PublicUtils.mkDir(Constant.PCMVOICEPATH);
		PublicUtils.mkDir(Constant.TEMPPATH);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String user_id = ""; // 注册用户ID 唯一标识

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

						if (name.equals("user_id")) {
							user_id = item.getString();
							System.out.println("user_id:" + user_id);
						}

					} else {

						int firstdircode = user_id.hashCode() & 0xf;
						int secondircode = (user_id.hashCode() >> 4) & 0xf;
						String hashuploadpath = Constant.PCMVOICEPATH + "/"
								+ firstdircode + "/" + secondircode;

						File pcmfile = processUploadedFile(item, 
								hashuploadpath); // 处理上传文件

						String wavfilePath = hashuploadpath + "/" + user_id
								+ ".wav";

						File wavfile = new File(wavfilePath);

						// PublicUtils.rawToWave(pcmfile, wavfile);
						PCMUtil.convertAudioFiles(pcmfile.getAbsolutePath(),
								wavfilePath);

						System.out.println(wavfilePath);

						List<String> ivectorList = IvectorUtils.KaldiToIvecter(
								wavfilePath, Constant.TOOLPATH);

						String ivectorStr = IvectorUtils
								.ListToString(ivectorList);
						
						if (Constant.IS_RETAIN_PCM_DATA) {

							PCMObject pcmo = new PCMObject(user_id, ivectorStr,
									wavfilePath, Constant.IVECTOR_VERSION, "");
							service.register(pcmo);
							
						}else{
							PublicUtils.deletefile(pcmfile);
							PublicUtils.deletefile(wavfile);
						}

				
						PCMResponseObject po = new PCMResponseObject(user_id,
								ivectorStr);

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
	 * @param uploadpath
	 *            文件上传的目录
	 * @return 0成功 1上传非wav文件
	 */
	private File processUploadedFile(FileItem item, 
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

			PublicUtils.mkDir(uploadpath);
			File uploadedFile = new File(uploadpath, filename);
			item.write(uploadedFile);
			System.out.println("PCM_path" + uploadedFile.getAbsolutePath());

			return uploadedFile;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

}
