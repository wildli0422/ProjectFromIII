package com.emp.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.emp.model.EmpService;
import com.emp.model.EmpVO;


@WebServlet("/BackEndLogin")
public class BackEndLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    EmpService empService = new EmpService();

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html; charset=UTF-8");
	    String account = req.getParameter("account");
	    String password = req.getParameter("password");
	    
	    List<String> errorMsgs = new ArrayList<String>();
		req.setAttribute("errorMsgs", errorMsgs);

		try {

			try {
				account = req.getParameter("account").trim();
				if (account == null || account.length() == 0) {
					account="";
					errorMsgs.add("請輸入帳號");
				}
			} catch (Exception e) {
				errorMsgs.add("Mail(Account) Error");
			}

			try {
				password = req.getParameter("password").trim();
				if (password == null || password.length() == 0) {
					errorMsgs.add("請輸入密碼");
				}
			} catch (Exception e) {
				errorMsgs.add("password Error");
			}
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req
						.getRequestDispatcher("/backEnd/loginPage.jsp");
				failureView.forward(req, resp);
				return;
			}
			empService.getEmpPassword(account);
			if (empService.getEmpPassword(account) != null) {
				
				if (empService.getEmpPassword(account).equals(password)) {
					System.out.println("已比對");
					EmpVO empVO=empService.getOneEmp(empService.getEmpNo(account));		

					HttpSession session = req.getSession();
					session.setAttribute("loginEmp", empVO); 										
				
					resp.sendRedirect(req.getContextPath()+"/backEnd/index.jsp");

				} else {
					errorMsgs.add("帳號或密碼錯誤");
				}
			} else {
				errorMsgs.add("帳號或密碼錯誤");
			}


		} catch (Exception e) {
			errorMsgs.add("帳號或密碼錯誤");
		}
		if (!errorMsgs.isEmpty()) {
			RequestDispatcher failureView = req
					.getRequestDispatcher("/backEnd/loginPage.jsp");
			failureView.forward(req, resp);
			return;
		}
	    
    
	}

}
