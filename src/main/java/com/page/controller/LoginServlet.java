package com.page.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dealer.model.DealerService;
import com.dealer.model.DealerVO;
import com.tenant.model.TenantService;
import com.tenant.model.TenantVO;

public class LoginServlet extends HttpServlet {

	DealerService dealerService = new DealerService();
	TenantService tenantService = new TenantService();

	public void doGet(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException{
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=UTF-8");
		
		PrintWriter out = res.getWriter();

		String action = req.getParameter("action").trim();

		if ("logout".equals(action)) {
			System.out.println("logout");
			HttpSession session = req.getSession();
			if (session.getAttribute("dealerVObyAccount") != null) {
				session.removeAttribute("dealerVObyAccount");
			}
			if (session.getAttribute("tenantVO") != null) {
				session.removeAttribute("tenantVO");
			}
			System.out.println("dealer: "+session.getAttribute("dealerVObyAccount"));
			System.out.println("tenant: "+session.getAttribute("tenantVO"));

			res.sendRedirect(req.getContextPath() + "/HomePage.jsp");
		}

		if ("tenantLogin".equals(action)) {
			List<String> errorMsgs = new ArrayList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			// session
			HttpSession session = req.getSession();

			if (session.getAttribute("dealerVObyAccount") != null) {
				session.removeAttribute("dealerVObyAccount");
			}

			try {
				// 【取得使用者 帳號(account) 密碼(password)】
				// 取得資料庫帳號密碼
				String account = null;
				try {
					account = req.getParameter("account").trim();
					System.out.println(account);
					if (account == null || account.length() == 0) {
						account = "";
						errorMsgs.add("請輸入帳號");
					}
				} catch (Exception e) {
					errorMsgs.add("帳號error");
				}

				String password = null;
				try {
					password = req.getParameter("password").trim();
					System.out.println(password);
					if (password == null || password.length() == 0) {
						errorMsgs.add("請輸入密碼");
					}
				} catch (Exception e) {
					errorMsgs.add("密碼error");
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/login.jsp");
					// req.setAttribute("account", account);
					failureView.forward(req, res);
					return;
				}
				// -------------VO----------------------
				tenantService.getTenantPassword(account);
				// -------------VO----------------------

				// 【檢查該帳號 , 密碼是否有效】
				// System.out.println("null?:"+dealerService.getDealerPassword(account));
				if (tenantService.getTenantPassword(account) != null) {
					if (tenantService.getTenantPassword(account).equals(
							password)) {

						TenantVO tenantVO = tenantService
								.getOneTenant(tenantService
										.getTenantNo(account));

						session.setAttribute("tenantVO", tenantVO); // *工作1:
																	// 才在session內做已經登入過的標識
																	// System.out.println(dealerVO.getDealerName());
						// System.out.println(dealerVO.getDealerNo());
						try {
							String location = (String) session
									.getAttribute("location");
							if (location != null) {
								session.removeAttribute("location"); // *工作2:
																		// 看看有無來源網頁
																		// (-->如有來源網頁:則重導至來源網頁)
								res.sendRedirect(location);
								return;
							}
						} catch (Exception ignored) {

						}
						res.sendRedirect(req.getContextPath() + "/HomePage.jsp"); // *工作3:
																					// (-->如無來源網頁:則重導至login_success.jsp)
					} else {
						// System.out.println("error");
						errorMsgs.add("帳號/密碼有誤");
					}
				} else {
					errorMsgs.add("帳號/密碼有誤");
				}
				// 保留帳號
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/login.jsp");
					req.setAttribute("account", account);
					failureView.forward(req, res);
					// System.out.println(req.getServletPath());
					// System.out.println(req.getRequestURI());
					return;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if ("dealerLogin".equals(action)) {
			List<String> errorMsgs = new ArrayList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			// session
			HttpSession session = req.getSession();
			if (session.getAttribute("tenantVO") != null) {
				session.removeAttribute("tenantVO");
			}

			try {
				// 【取得使用者 帳號(account) 密碼(password)】
				// 取得資料庫帳號密碼
				String account = null;
				try {
					account = req.getParameter("account").trim();

					if (account == null || account.length() == 0) {
						account = "";
						errorMsgs.add("請輸入帳號");
					}
				} catch (Exception e) {
					errorMsgs.add("帳號error");
				}

				String password = null;
				try {
					password = req.getParameter("password").trim();
					System.out.println(password);
					if (password == null || password.length() == 0) {
						errorMsgs.add("請輸入密碼");
					}
				} catch (Exception e) {
					errorMsgs.add("密碼error");
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/login.jsp");
					// req.setAttribute("account", account);
					failureView.forward(req, res);
					return;
				}
				// -------------VO----------------------
				dealerService.getDealerPassword(account);
				// -------------VO----------------------

				// 【檢查該帳號 , 密碼是否有效】
				// System.out.println("null?:"+dealerService.getDealerPassword(account));
				if (dealerService.getDealerPassword(account) != null) {
					if (dealerService.getDealerPassword(account).equals(
							password)) {
						DealerVO dealerVO = dealerService
								.getOneDealer(dealerService
										.getDealerNo(account));

						session.setAttribute("dealerVObyAccount", dealerVO); // *工作1:
						// 才在session內做已經登入過的標識
						// System.out.println(dealerVO.getDealerName());
						// System.out.println(dealerVO.getDealerNo());
						try {
							String location = (String) session
									.getAttribute("location");
							if (location != null) {
								session.removeAttribute("location"); // *工作2:
																		// 看看有無來源網頁
																		// (-->如有來源網頁:則重導至來源網頁)
								res.sendRedirect(location);
								return;
							}
						} catch (Exception ignored) {

						}
						res.sendRedirect(req.getContextPath() + "/HomePage.jsp"); // *工作3:
																					// (-->如無來源網頁:則重導至login_success.jsp)
					} else {
						// System.out.println("error");
						errorMsgs.add("帳號/密碼有誤");
					}
				} else {
					errorMsgs.add("帳號/密碼有誤");
				}
				// 保留帳號
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/login.jsp");
					req.setAttribute("account", account);
					failureView.forward(req, res);
					// System.out.println(req.getServletPath());
					// System.out.println(req.getRequestURI());
					return;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}// doPost
}
