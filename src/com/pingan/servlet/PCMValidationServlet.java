package com.pingan.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
import com.pingan.utils.PCMUtil;
import com.pingan.utils.PublicUtils;

public class PCMValidationServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private File tempFile;
	private MongoDBService service;

	@Override
	public void init(ServletConfig config) throws ServletException {

		super.init(config);
		tempFile = new File(Constant.TEMPPATH);

	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out
				.println("-------------PCMValidationServlet run!-------------");
		String user_id = "";
		String regivectorPath = "";
		String pcmpath = "";

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
			List<String> filepathlist = new ArrayList<String>();
			try {
				List<FileItem> items = upload.parseRequest(request);
				for (FileItem item : items) {
					if (item.isFormField()) {
						// 非文件参数
						// processFormField(item, outNet); // 处理普通的表单域
						String name = item.getFieldName();
						if (name.equals("user_id")) {
							user_id = item.getString();
						}

					} else {
						// 文件
						if (item.getFieldName().equals("ivector")) {
							regivectorPath = processUploadedFile(item,
									Constant.VALIDATION_VOICEPATH);
							System.out.println("ivectorPath"+regivectorPath);
						} else if (item.getFieldName().equals("pcm")) {
							pcmpath = processUploadedFile(item,
									Constant.VALIDATION_VOICEPATH);
							System.out.println("pcmpath"+pcmpath);
						}

					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			// pcm转wav
			File wavfile = new File(Constant.VALIDATION_VOICEPATH, user_id + ".wav");

			System.out.println("WAVFILE:" + wavfile.getAbsolutePath());
			
			System.out.println(pcmpath);
			PCMUtil.convertAudioFiles(pcmpath, wavfile.getAbsolutePath());
			
			
//			注册：
			List<String> ivectorList = IvectorUtils.KaldiToIvecter(wavfile.getAbsolutePath(), Constant.TOOLPATH);
			
			//特征文件写入本地
			String testivectorpath  = IvectorUtils.ListToFile_Return_FilePath(ivectorList, Constant.VALIDATION_VOICEPATH, user_id+".ivector");
			
			// 验证操作
			double score = KaldiTest(regivectorPath, testivectorpath);
			

			outNet.println(user_id + ":" + score);
			outNet.close();
			
//			PublicUtils.deletefile(Constant.VALIDATION_VOICEPATH+".ivector");
//			PublicUtils.deletefile(pcmpath);
//			PublicUtils.deletefile(wavfile);
			
		}

	}

	/**
	 * 
	 * @param item
	 * @param fileRootPath
	 *            存放路径的分目录
	 * @return filepath 返回文件的路径
	 */
	private String processUploadedFile(FileItem item, String fileRootPath) {

		// 获取文件名
		String filename = item.getName();

		try {
			
			File uploadedFile = new File(fileRootPath, filename);
			
			item.write(uploadedFile);
			
			return fileRootPath + "/" + filename;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 
	 * @param register_ivecter_dir
	 *            注册的特征文件的路径
	 * @param test_ivector_dir
	 *            验证的wav的文件路径
	 * @return 评分
	 */
	public double KaldiTest(String register_ivecter_dir, String test_ivector_dir) {

		double result = -999;

		switch (Constant.SCORE_MODE) {
		case PLDA:
			result = IvectorUtils.KaldiPLDAscore(register_ivecter_dir,
					test_ivector_dir, Constant.TOOLPATH);
			break;

		case DOT:
			result = IvectorUtils.Kaldiscore(register_ivecter_dir,
					test_ivector_dir, Constant.TOOLPATH);
			break;
		}
		System.out.println("result="+result);
		return result;
	}
}
