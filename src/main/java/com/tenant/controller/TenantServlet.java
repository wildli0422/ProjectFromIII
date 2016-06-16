package com.tenant.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.tenant.model.TenantService;
import com.tenant.model.TenantVO;

@WebServlet("/tenant.do")
@MultipartConfig
public class TenantServlet extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html; charset=UTF-8");
		String action = req.getParameter("action");

		if ("keyword".equals(action)) {
			String tenantName = req.getParameter("tenantName"); // get String
																// and
			// cut it
			String[] tenantList = tenantName.split(" ");
			for (int i = 0; i < tenantList.length; i++) {
				tenantList[i].trim();
				if (tenantList[i].equals("")) {
					tenantList[i] = null;
				}
			}
			List<TenantVO> totallist = new LinkedList<TenantVO>();
			for (int i = 0; i < tenantList.length; i++) {

				try {
					TenantVO tenantVO = new TenantVO();
					tenantVO.setTenantName(tenantList[i]);

					TenantService tenantService = new TenantService();
					List<TenantVO> list = null;
					list = tenantService.getListByName(tenantList[i]);
					totallist.addAll(list);

				} catch (Exception e) {
					req.setAttribute("keyin", tenantName);
					System.out.println("exception");
					RequestDispatcher failureView = req
							.getRequestDispatcher("/backEnd/user/tenant.jsp");

					failureView.forward(req, resp);

				}

			}
			for (int i = 0; i < tenantList.length; i++) {

				try {
					TenantVO tenantVO = new TenantVO();
					tenantVO.setTenantName(tenantList[i]);

					TenantService tenantService = new TenantService();
					List<TenantVO> list = null;
					list = tenantService.getListByAddress(tenantList[i]);
					totallist.addAll(list);

				} catch (Exception e) {
					req.setAttribute("keyin", tenantName);
					System.out.println("exception");
					RequestDispatcher failureView = req
							.getRequestDispatcher("/backEnd/user/tenant.jsp");

					failureView.forward(req, resp);

				}

			}
			for (int i = 0; i < totallist.size(); i++) {
				for (int j = i + 1; j < totallist.size(); j++) {
					if (totallist.get(j).getTenantNo()
							.equals(totallist.get(i).getTenantNo())) {
						totallist.remove(j);
						j--;
					}
				}
			}
			req.setAttribute("tenantList", totallist);
			req.setAttribute("keyin", tenantName);
			RequestDispatcher successView = req
					.getRequestDispatcher("/backEnd/user/tenant.jsp");
			successView.forward(req, resp);
		}

		if ("getlistByCQ".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				Map<String, String[]> map = req.getParameterMap();
				TenantService tenantService = new TenantService();
				List<TenantVO> list = tenantService.getAll(map);

				for (int i = 0; i < list.size(); i++) {
					for (int j = i + 1; j < list.size(); j++) {
						if (list.get(j).getTenantNo()
								.equals(list.get(i).getTenantNo())) {
							list.remove(j);
							j--;
						}
					}
				}

				req.setAttribute("tenantList", list);
				RequestDispatcher successView = req
						.getRequestDispatcher("/backEnd/user/tenant.jsp"); // ����漱listEmps_ByCompositeQuery.jsp
				successView.forward(req, resp);

			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/backEnd/user/tenant.jsp");
				failureView.forward(req, resp);
			}
		}

		if ("update".equals(action)) { // 靘update_emp_input.jsp�����
			List<String> errorMsgs = new LinkedList<String>();

			req.setAttribute("errorMsgs", errorMsgs);
			TenantService tenantServicetmp = new TenantService();

			try {
				Integer tenantNo = new Integer(req.getParameter("tenantNo").trim());
				String tenantMail = req.getParameter("tenantMail").trim();
				String tenantPassword = req.getParameter("tenantPassword").trim();
				String tenantName = req.getParameter("tenantName").trim();
				String tenantSex = req.getParameter("tenantSex").trim();
				String tenantAddress = req.getParameter("tenantAddress").trim();
				String tenantPhone = req.getParameter("tenantPhone").trim();
				
				java.sql.Date registerDate = tenantServicetmp.getOneTenant(tenantNo).getRegisterDate();
				byte[] tenantPic=tenantServicetmp.getOneTenant(tenantNo).getTenantPic();

				System.out.print(tenantNo + tenantAddress + tenantMail);

				TenantVO tenantVO = new TenantVO();

				tenantVO.setTenantPassword(tenantPassword);
				tenantVO.setTenantName(tenantName);
				tenantVO.setTenantSex(tenantSex);
				tenantVO.setTenantAddress(tenantAddress);
				tenantVO.setTenantPhone(tenantPhone);
//				tenantVO.setTenantPic(tenantServicetmp.getOneTenant(tenantNo).getTenantPic());
//				tenantVO.setRegisterDate(tenantServicetmp.getOneTenant(tenantNo).getRegisterDate());


				TenantService tenantSvc = new TenantService();
				tenantVO = tenantSvc.updateTenant(tenantNo, tenantMail,
						tenantPassword, tenantName, tenantSex, tenantAddress,
						tenantPhone, tenantPic, registerDate);
				HttpSession session = req.getSession();
				session.setAttribute("tenantNo", tenantNo);
				session.setAttribute("tenantVO", tenantVO);
				String url = req.getParameter("requestURL");
				RequestDispatcher successView = req.getRequestDispatcher(url);

				successView.forward(req, resp);

			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				String url = req.getParameter("requestURL");
				RequestDispatcher failureView = req.getRequestDispatcher(url);
				failureView.forward(req, resp);
			}

		}
		if ("update_pic".equals(action)) {// from update.jsp ->to update
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			TenantService tenantService = new TenantService();

			try {

				Integer tenantNo = new Integer(req.getParameter("tenantNo"));
				Part tenantPicture = req.getPart("tenantPic");
				InputStream tenantPicStream = tenantPicture.getInputStream();
				byte[] tenantPic = new byte[tenantPicStream.available()];
				tenantPicStream.read(tenantPic);
				tenantPicStream.close();
				tenantPicStream = null;

				System.out.println(tenantPic);

				System.out.println("setPic");

				TenantVO tenantVO = new TenantVO();
				tenantVO.setTenantNo(tenantNo);
				tenantVO.setTenantPic(tenantPic);

				/*************************** 2.���耨�鞈�� ****************************************/

				tenantService.update_pic_bk(tenantVO);
				/*************************** 3.靽格摰��,皞��漱(Send the Success view) ************/
				String url = req.getParameter("requestURL");
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, resp);
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("");
				failureView.forward(req, resp);
			}
		}

	}

}
