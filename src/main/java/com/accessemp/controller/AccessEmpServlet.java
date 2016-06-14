package com.accessemp.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.accessemp.model.AccessEmpService;
import com.emp.model.EmpService;
import com.emp.model.EmpVO;


@WebServlet("/backEnd/emp/Access.do")
public class AccessEmpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		AccessEmpService accessEmpService = new AccessEmpService();
				
		List<String> errorMsgs = new LinkedList<String>();
		req.setAttribute("errorMsgs", errorMsgs);

		try {
			Integer empNo = new Integer(req.getParameter("empNo"));
			accessEmpService.revokeAccessAll(empNo);
			accessEmpService.grantAccess(empNo, 9000);
			
			try {
				Integer pc9001 = new Integer(req.getParameter("pc9001"));
				accessEmpService.grantAccess(empNo, pc9001);
			} catch (Exception e) {
			}
			
			try {
				Integer pc9002 = new Integer(req.getParameter("pc9002"));
				accessEmpService.grantAccess(empNo, pc9002);
			} catch (Exception e) {
			}
			
			try {
				Integer pc9003 = new Integer(req.getParameter("pc9003"));
				accessEmpService.grantAccess(empNo, pc9003);
			} catch (Exception e) {
			}
			
			try {
				Integer pc9004 = new Integer(req.getParameter("pc9004"));
				accessEmpService.grantAccess(empNo, pc9004);
			} catch (Exception e) {
			}
			
			try {
				Integer pc9005 = new Integer(req.getParameter("pc9005"));
				accessEmpService.grantAccess(empNo, pc9005);
			} catch (Exception e) {
			}
			
			
			req.setAttribute("empNo", empNo);
			RequestDispatcher successView = req.getRequestDispatcher("/backEnd/emp/empProfile.jsp"); // 成功轉交listEmps_ByCompositeQuery.jsp
			successView.forward(req, resp);

		} catch (Exception e) {
			errorMsgs.add(e.getMessage());
			RequestDispatcher failureView = req.getRequestDispatcher("/backEnd/emp/empProfile.jsp");
			failureView.forward(req, resp);
		}
		
		
		
		

	}

}
