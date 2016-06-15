package com.emp.controller;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.websocket.Session;

import com.accessemp.model.AccessEmpService;
import com.accessemp.model.AccessEmpVO;
import com.emp.model.EmpService;
import com.emp.model.EmpVO;

@WebServlet("/backEnd/emp/emp.do")
public class EmpServlet extends HttpServlet {

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("getlistByCQ".equals(action)) {

			try {
				Map<String, String[]> map = req.getParameterMap();
				EmpService empService = new EmpService();
				List<EmpVO> list = empService.getAll(map);

				for (int i = 0; i < list.size(); i++) {
					for (int j = i + 1; j < list.size(); j++) {
						if (list.get(j).getEmpNo().equals(list.get(i).getEmpNo())) {
							list.remove(j);
							j--;
						}
					}
				}

				req.setAttribute("listByCQ", list);
				RequestDispatcher successView = req.getRequestDispatcher("/backEnd/emp/empManager.jsp"); // 成功轉交listEmps_ByCompositeQuery.jsp
				successView.forward(req, resp);

			} catch (Exception e) {
				RequestDispatcher failureView = req.getRequestDispatcher("/backEnd/emp/empManager.jsp");
				failureView.forward(req, resp);
			}
		}

		if ("showEmp".equals(action)) {
			String requestURL = req.getParameter("requestURL");
			try {
				Integer empNo = new Integer(req.getParameter("empNo"));
				req.setAttribute("empNo", empNo);
				String url = "/backEnd/emp/empProfile.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); 
				successView.forward(req, resp);
			} catch (Exception e) {
				RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
				failureView.forward(req, resp);
			}
		}

		if ("update".equals(action)) { 
			String requestURL = req.getParameter("requestURL"); 
			
			Integer empNo = new Integer(req.getParameter("empNo").trim());
			String empAccount = req.getParameter("empAccount").trim();
			String empPassword = req.getParameter("empPassword").trim();
			String empName = req.getParameter("empName").trim();
			String empSex = req.getParameter("empSex").trim();
			String empAddress = req.getParameter("empAddress").trim();
			String empPhone = req.getParameter("empPhone").trim();
			String empMail = req.getParameter("empMail").trim();

			EmpVO empVO = new EmpVO();
			empVO.setEmpAccount(empAccount);
			empVO.setEmpPassword(empPassword);
			empVO.setEmpName(empName);
			empVO.setEmpSex(empSex);
			empVO.setEmpAddress(empAddress);
			empVO.setEmpMail(empMail);
			empVO.setEmpPhone(empPhone);			
			empVO.setEmpNo(empNo);

			
			try {
				EmpService empService = new EmpService();
				empService.updateEmp(empPassword, empName, empSex, empAddress,  empMail, empPhone, empNo);
				String url = requestURL;
				System.out.println(url);
				if (url.equals("/backEnd/emp/empMyInfo.jsp")) {
					HttpSession session = req.getSession();
					session.setAttribute("loginEmp", empVO);
					System.out.println("myInfo");
				}else {
					req.setAttribute("empNo", empNo);
				}
				
				
				
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, resp);

			} catch (Exception e) {
				req.setAttribute("empVO", empVO);
				RequestDispatcher failureView = req.getRequestDispatcher("");
				failureView.forward(req, resp);
			}
		}



		if ("delete".equals(action)) {
										
			String requestURL = req.getParameter("requestURL"); 
			try {
				Integer empNo = new Integer(req.getParameter("empNo"));
				
				AccessEmpService accessEmpService = new AccessEmpService();
				accessEmpService.revokeAccessAll(empNo);
				
				EmpService empService = new EmpService();
				EmpVO empVO = empService.getOneEmp(empNo);
				empService.deleteEmp(empNo);
				
				

				RequestDispatcher successView = req.getRequestDispatcher("/backEnd/emp/empManager.jsp"); // 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, resp);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
				failureView.forward(req, resp);
			}
		}

	}

}
