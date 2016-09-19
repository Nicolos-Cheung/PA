package com.pingan.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pingan.dao.CustomerDao;
import com.pingan.dao.impl.MysqlDaoImpl;
import com.pingan.domain.Ivector;

public class AjexServlet extends HttpServlet {
	
	private CustomerDao dao = new MysqlDaoImpl();
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String telnum = request.getParameter("tel");
		Ivector find = dao.find(telnum);
		
		response.setContentType("text/html;charset = UTF-8");
		if (find==null) {
			response.getWriter().write("<font color='red'>该手机号未注册<font>");
		} else {
			response.getWriter().write("<font color='green'>该手机号已注册<font>");
		}
		
	}

}
