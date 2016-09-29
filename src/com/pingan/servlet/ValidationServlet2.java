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

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.pingan.constant.Constant;
import com.pingan.service.MongoDBService;
import com.pingan.service.impl.MongoDBServiceImpl;
import com.pingan.utils.IvectorUtils;
import com.pingan.utils.PublicUtils;

public class ValidationServlet2 extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private File tempFile;
	private MongoDBService service;

	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);

		tempFile = new File(Constant.TEMPPATH);

	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("-------------ValidationServlet run!-------------");
		String telnum = "";

		response.setContentType("text/plain");
		// 向客户端发送响应正文
		PrintWriter outNet = response.getWriter();

		boolean isMultipart = ServletFileUpload.isMultipartContent(request);

		if (isMultipart) {

			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(1024 * 1024); // 设置缓冲区大小为1M
			factory.setRepository(tempFile); // 设置临时目录

			// 创建一个文件上传处理器
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setHeaderEncoding("utf-8");
			upload.setSizeMax(8 * 1024 * 1024); // 允许文件的最大上传尺寸8M

			try {
				List<FileItem> items = upload.parseRequest(request);
				for (FileItem item : items) {
					if (item.isFormField()) {
						// 非文件参数
						// processFormField(item, outNet); // 处理普通的表单域
						String name = item.getFieldName();
						if (name.equals("tel")) {
							telnum = item.getString();
						}

					} else {
						// 文件
						int code = processUploadedFile(item, outNet, telnum); // 处理上传文件

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
	 * @param item
	 * @param outNet
	 * @return 0/1:评分结果：成功 2上传失败 3上传非wav文件 4评分失败 5声纹版本号不一致 6其他错误
	 */
	private int processUploadedFile(FileItem item, PrintWriter outNet,
			String telnum) {

		// 验证用的wav文件名
		String filename = item.getName();

		try {
			long fileSize = item.getSize();
			if (filename.equals("") && fileSize == 0)
				return 2;

			if (!PublicUtils.checkFileType("wav", filename)) {
				return 3;
			}

			File uploadedFile = new File(Constant.VALIDATION_VOICEPATH,
					filename);
			item.write(uploadedFile);

			service = new MongoDBServiceImpl();

			String registerPath = service.QueryIvectorPath(telnum);

			if (!(Constant.IVECTOR_VERSION).equals(service
					.QueryIvectorVersion(telnum))) {

				System.out.println("version number is not consistent");
				return 5;
			}

			// 评分结果
			int score = KaldiTest(registerPath, Constant.VALIDATION_VOICEPATH
					+ "/" + filename, telnum);

			if (score == 4) {
				return 4;
			}

			System.out.println("validation_result=" + score);

			float threshold = 0;
			switch (Constant.SCORE_MODE) {
			case PLDA:
				threshold = Constant.PLDA_THRESHOLD;
				break;

			case DOT:
				threshold = Constant.THRESHOLD;
				break;
			}

			System.out.println("threshold=" + threshold);

			if (score > threshold) {
				System.out.println("验证通过!");
				return 1;
			} else {
				System.out.println("验证失败!");
				return 0;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return 6;

	}

	/**
	 * 
	 * @param register_ivecter_dir
	 *            注册的特征文件的路径
	 * @param test_wav_dir
	 *            验证的wav的文件路径
	 * @return 评分
	 */
	public int KaldiTest(String register_ivecter_dir, String test_wav_dir,
			String telnum) {

		double result = 4;
		List<String> ivecter_list = IvectorUtils.KaldiToIvecter(test_wav_dir,
				Constant.TOOLPATH);

		PublicUtils.deletefile(test_wav_dir);

		String filename = telnum + "_test.ivector";

		if (null == ivecter_list && ivecter_list.size() == 0) {
			return 4;
		}

		IvectorUtils.ListToFile(ivecter_list, Constant.VALIDATION_VOICEPATH,
				filename);
		String filepath = Constant.VALIDATION_VOICEPATH + "/" + filename;

		switch (Constant.SCORE_MODE) {
		case PLDA:
			result = IvectorUtils.KaldiPLDAscore(register_ivecter_dir,
					filepath, Constant.TOOLPATH);
			break;

		case DOT:
			result = IvectorUtils.Kaldiscore(register_ivecter_dir, filepath,
					Constant.TOOLPATH);
			break;
		}
		if (!Constant.IS_RETAIN_VALIDATION_VOICE) {
			PublicUtils.deletefile(filepath);
		}
		return (int) result;
	}
}
