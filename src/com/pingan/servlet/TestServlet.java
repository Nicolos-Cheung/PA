package com.pingan.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class TestServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter writer = response.getWriter();

		System.out.println("Servlet开始时间：" + new Date() + ".");
		AsyncContext ctx = request.startAsync();
		new Thread(new Executor(ctx)).start();
		System.out.println("Servlet结束时间：" + new Date() + ".");

		// response.setContentType("text/plain");
		//
		// PrintWriter out = response.getWriter();
		// boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		// DiskFileItemFactory factory = new DiskFileItemFactory();
		// factory.setSizeThreshold(1024 * 1024); // 设置缓冲区大小为1M
		//
		// // 创建一个文件上传处理器
		// ServletFileUpload upload = new ServletFileUpload(factory);
		// upload.setHeaderEncoding("utf-8");
		// upload.setSizeMax(8 * 1024 * 1024); // 允许文件的最大上传尺寸8M
		// try {
		//
		// List<FileItem> items = upload.parseRequest(request);
		// for (FileItem item : items) {
		// if (item.isFormField()) {
		// // 非文件参数
		// // processFormField(item, outNet); // 处理普通的表单域
		// String name = item.getFieldName();
		// if (name.equals("tel")) {
		//
		//
		//
		// }
		//
		// } else {
		//
		// }
		// }
		// } catch (Exception e) {
		// e.printStackTrace();
		// }

	}

}

class Executor implements Runnable {
	private AsyncContext ctx = null;
	public Executor(AsyncContext ctx) {
		this.ctx = ctx;
	}

	public void run() {
		try {
			// 等待十秒钟，以模拟业务方法的执行
			Thread.sleep(5000);
			PrintWriter out = ctx.getResponse().getWriter();
			System.out.println("业务处理完毕的时间：" + new Date() + ".");
			out.write("xxxxx");
			out.flush();
			out.close();
			ctx.complete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
