package com.tenant.controller;

import java.io.IOException;
import java.sql.Date;
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


@WebServlet("/user/Profile.do")
public class ProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;    

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html; charset=UTF-8");
		
		List<String> errorMsgs = new LinkedList<String>();
		TenantService tenantService=new TenantService();
		req.setAttribute("errorMsgs", errorMsgs);

		try {					
			Integer tenantNo = new Integer(req.getParameter("tenantNo").trim());
			String tenantMail = req.getParameter("tenantMail").trim();
			String tenantPassword = req.getParameter("tenantPassword").trim();
			String tenantName = req.getParameter("tenantName").trim();
			String tenantSex = req.getParameter("tenantSex").trim();
			String tenantAddress = req.getParameter("tenantAddress").trim();
			String tenantPhone = req.getParameter("tenantPhone").trim();
			
			
			System.out.println("edit Test");
			System.out.print(tenantNo+tenantAddress+tenantMail);

			TenantVO tenantVO = new TenantVO();
			
			tenantVO =tenantService.getOneTenant(tenantNo);
			byte[] tenantPic = tenantVO.getTenantPic();
			Date registerDate = tenantVO.getRegisterDate();
			
			tenantVO.setTenantPassword(tenantPassword);
			tenantVO.setTenantName(tenantName);
			tenantVO.setTenantSex(tenantSex);
			tenantVO.setTenantAddress(tenantAddress);
			tenantVO.setTenantPhone(tenantPhone);
			tenantVO.setTenantPic(tenantPic);
			tenantVO.setRegisterDate(registerDate);

//			if (!errorMsgs.isEmpty()) {
//				req.setAttribute("empVO", tenantVO); // 含有輸入格式錯誤的empVO物件,也存入req
//				RequestDispatcher failureView = req
//						.getRequestDispatcher("/emp/addEmp.jsp");
//				failureView.forward(req, resp);
//				return;
//			}
			
			TenantService tenantSvc = new TenantService();
			tenantVO = tenantSvc.updateTenant(tenantNo, tenantMail, tenantPassword, tenantName, tenantSex, tenantAddress, tenantPhone, tenantPic, registerDate);
			HttpSession session = req.getSession();
			session.setAttribute("loginTenant", tenantVO);
			RequestDispatcher successView = req.getRequestDispatcher("/index.jsp"); 

			successView.forward(req, resp);				
			
			
		} catch (Exception e) {

			errorMsgs.add(e.getMessage());
			RequestDispatcher failureView = req
					.getRequestDispatcher("/index.jsp");
			failureView.forward(req, resp);
		}
		
		
		
		
		
	}

}
