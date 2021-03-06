package com.tenant.controller;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;


import com.tenant.model.TenantService;
import com.tenant.model.TenantVO;

@WebServlet("/TenantLogin")
public class LoginServlet extends HttpServlet{
	TenantService tenantService = new TenantService();
		public void doPost(HttpServletRequest req, HttpServletResponse resp)
              throws ServletException, IOException {
		  
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
						errorMsgs.add("隢撓���");
					}
				} catch (Exception e) {
					errorMsgs.add("Mail(Account) Error");
				}
	
				try {
					password = req.getParameter("password").trim();
					if (password == null || password.length() == 0) {
						errorMsgs.add("隢撓�撖Ⅳ");
					}
				} catch (Exception e) {
					errorMsgs.add("password Error");
				}
	
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/login/loginTest.jsp");
					failureView.forward(req, resp);
					return;
				}
				
				tenantService.getTenantPassword(account);
				
				//鞈�葫閰�
				System.out.println(account);
				System.out.println(password);
				System.out.println("getpw:"+tenantService.getTenantPassword(account));
				System.out.println(tenantService.getTenantPassword(account).equals(password));
	
	
				if (tenantService.getTenantPassword(account) != null) {
					
					if (tenantService.getTenantPassword(account).equals(password)) {
						System.out.println("撌脫���");
						TenantVO tenantVO=tenantService.getOneTenant(tenantService.getTenantNo(account));				
						HttpSession session = req.getSession();
						session.setAttribute("loginTenant", tenantVO); 
						System.out.println("setSession Done");
						
						
						
						resp.sendRedirect(req.getContextPath()+"/user/profile.jsp");
	
					} else {
						errorMsgs.add("��/撖Ⅳ��炊");
					}
				} else {
					errorMsgs.add("��/撖Ⅳ��炊");
				}
	
	
			} catch (Exception e) {
				errorMsgs.add("��/撖Ⅳ��炊");
			}
		    System.out.println("�甇支����");
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req
						.getRequestDispatcher("/login/loginTest.jsp");
				failureView.forward(req, resp);
				return;
			}
		    
	    
		}
}
