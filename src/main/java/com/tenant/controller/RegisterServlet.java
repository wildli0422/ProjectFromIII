package com.tenant.controller;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tenant.model.TenantService;
import com.tenant.model.TenantVO;




@WebServlet("/registerTen.do")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html; charset=UTF-8");
		List<String> errorMsgs = new LinkedList<String>();

		req.setAttribute("errorMsgs", errorMsgs);

		try {

			String tenantMail = req.getParameter("tenantMail").trim();
			String tenantPassword = req.getParameter("tenantPassword").trim();
			String tenantName = req.getParameter("tenantName").trim();
			String tenantPhone = req.getParameter("tenantPhone").trim();

			if (tenantPassword == null || tenantPassword.length() == 0) {
				errorMsgs.add("請輸入密碼");
			}
			if (tenantName == null || tenantName.length() == 0) {
				errorMsgs.add("請輸入姓名");
			}
			if (tenantPhone == null || tenantPhone.length() == 0) {
				errorMsgs.add("請輸入電話");
			}

			TenantVO tenantVO = new TenantVO();
			tenantVO.setTenantMail(tenantMail);
			tenantVO.setTenantPassword(tenantPassword);
			tenantVO.setTenantName(tenantName);
			tenantVO.setTenantPhone(tenantPhone);

			if (!errorMsgs.isEmpty()) {
				req.setAttribute("empVO", tenantVO); // 含有輸入格式錯誤的empVO物件,也存入req
				RequestDispatcher failureView = req
						.getRequestDispatcher("/HomePage.jsp");
				failureView.forward(req, resp);
				return;
			}
			
			TenantService tenantSvc = new TenantService();
			tenantVO = tenantSvc.addTenant(tenantMail, tenantPassword, tenantName, null, null, tenantPhone, null);
			HttpSession session = req.getSession();
			session.setAttribute("tenantVO", tenantVO);
			RequestDispatcher successView = req.getRequestDispatcher("/HomePage.jsp"); 

			successView.forward(req, resp);				
			
			
		} catch (Exception e) {
			errorMsgs.add(e.getMessage());
			RequestDispatcher failureView = req
					.getRequestDispatcher("/HomePage.jsp");
			failureView.forward(req, resp);
		}
	}

}
