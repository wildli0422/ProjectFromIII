package com.viewlist.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.viewinfo.model.ViewInfoService;
import com.viewinfo.model.ViewInfoVO;
import com.viewlist.model.*;
import com.viewphoto.model.ViewPhotoVO;


@WebServlet("/viewlist/viewlist.do")
public class ViewListServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String str = req.getParameter("viewlistno");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入景點清單編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/viewlist/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				Integer viewlistno = null;
				try {
					viewlistno = new Integer(str);
				} catch (Exception e) {
					errorMsgs.add("景點清單編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/viewlist/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				// ***************************2.開始查詢資料**************************
				ViewListService viewlistSvc = new ViewListService();
				ViewListVO viewlistVO = viewlistSvc.getOneViewList(viewlistno);
				if (viewlistVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/viewlist/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				// ***************************3.查詢完成,準備轉交(Send the Success
				// view)*************/
				req.setAttribute("viewlistVO", viewlistVO); // 資料庫取出的empVO物件,存入req
				String url = "/viewlist/listOneViewList.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交
																				// listOneEmp.jsp
				successView.forward(req, res);

				// ***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/viewlist/select_page.jsp");
				failureView.forward(req, res);
			}

		}

		if ("getOne_For_Update".equals(action)) { // 來自listAllViewList.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				Integer viewlistno = new Integer(req.getParameter("viewlistno"));

				/*************************** 2.開始查詢資料 ****************************************/
				ViewListService viewlistSvc = new ViewListService();
				ViewListVO viewlistVO = viewlistSvc.getOneViewList(viewlistno);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("viewlistVO", viewlistVO); // 資料庫取出的empVO物件,存入req
				String url = "/viewlist/update_viewlist_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交
																				// update_emp_input.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/viewlist/listAllViewList.jsp");
				failureView.forward(req, res);
			}
		}

		if ("update".equals(action)) { // 來自update_viewlist_input.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				Integer viewlistno = new Integer(req.getParameter("viewlistno")
						.trim());
				Integer tenantNo = new Integer(req.getParameter("tenantNo")
						.trim());
				Integer viewno = new Integer(req.getParameter("viewno").trim());

				java.sql.Date viewlistdate = null;
				try {
					viewlistdate = java.sql.Date.valueOf(req.getParameter(
							"viewlistdate").trim());
				} catch (IllegalArgumentException e) {
					viewlistdate = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}

				ViewListVO viewlistVO = new ViewListVO();
				viewlistVO.setViewlistno(viewlistno);
				viewlistVO.setTenantNo(tenantNo);
				viewlistVO.setViewno(viewno);
				viewlistVO.setViewlistdate(viewlistdate);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("viewlistVO", viewlistVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/viewlist/update_viewlist_input.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}

				/*************************** 2.開始修改資料 *****************************************/
				ViewListService viewlistSvc = new ViewListService();
				viewlistVO = viewlistSvc.updateViewList(viewlistno, tenantNo,
						viewno, viewlistdate);

				/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
				req.setAttribute("viewlistVO", viewlistVO); // 資料庫update成功後,正確的的empVO物件,存入req
				String url = "/viewlist/listOneViewList.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/viewlist/update_viewlist_input.jsp");
				failureView.forward(req, res);
			}
		}

	//AllViewInfoInsert
		if ("insert".equals(action)) { // 來自addViewList.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				Integer tenantNo =null;
				try{
				 tenantNo = new Integer(req.getParameter("tenantNo").trim());
				}catch(Exception e){
						System.out.println(e.getMessage());
				}
				Integer viewno = new Integer(req.getParameter("viewno").trim());
			//	System.out.println("teantno INSERT" + tenantno);
			//	System.out.println("no" + viewno);
				/*
				 * java.sql.Date viewlistdate = null; try { viewlistdate =
				 * java.sql
				 * .Date.valueOf(req.getParameter("viewlistdate").trim()); }
				 * catch (IllegalArgumentException e) { viewlistdate=new
				 * java.sql.Date(System.currentTimeMillis());
				 * errorMsgs.add("請輸入日期!"); }
				 */

				ViewListVO viewlistVO = new ViewListVO();
				viewlistVO.setTenantNo(tenantNo);
				viewlistVO.setViewno(viewno);
				// viewlistVO.setViewlistdate(viewlistdate);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("viewlistVO", viewlistVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/view/allViewInfo.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始新增資料 ***************************************/
				req.setAttribute("tenantno", tenantNo);
				ViewListService viewlistSvc = new ViewListService();
				viewlistVO = viewlistSvc.addViewList(tenantNo, viewno);
				System.out.println("teantno" + tenantNo);
				System.out.println("no" + viewno);
				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String url = "/view/allViewInfo.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/view/allViewInfo.jsp");
				failureView.forward(req, res);
			}
		}

		
	
		if ("eachViewInsert".equals(action)) { // 來自addViewList.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				String requestURL=req.getParameter("requestURL");
				System.out.println("url:new!!!!!!!!!!!!"+requestURL);
				//Integer tenantNo =null;
//				try{
//				 tenantNo = new Integer(req.getParameter("tenantNo").trim());
//				}catch(Exception e){
//						System.out.println(e.getMessage());
//				}
				Integer viewno = new Integer(req.getParameter("viewno").trim());
				Integer tenantNo = new Integer(req.getParameter("tenantNo"));

				ViewListVO viewlistVO = new ViewListVO();
				viewlistVO.setTenantNo(tenantNo);
				System.out.println(tenantNo);
				
				viewlistVO.setViewno(viewno);
				System.out.println(viewno);
				// viewlistVO.setViewlistdate(viewlistdate);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("viewlistVO", viewlistVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/view/allViewInfo.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始新增資料 ***************************************/
				
				req.setAttribute("viewno", viewno);
				req.setAttribute("tenantno", tenantNo);
				ViewListService viewlistSvc = new ViewListService();
				viewlistVO = viewlistSvc.addViewList(tenantNo, viewno);
				System.out.println("teantno" + tenantNo);
				System.out.println("no" + viewno);
				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String url=null;
				url=requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/view/allViewInfo.jsp");
				failureView.forward(req, res);
			}
		}

		
		if("eachViewDelete".equals(action)){
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				String requestURL=req.getParameter("requestURL");
				System.out.println("url:123!!!!!!!!!!!!"+requestURL);
				Integer viewlistno = new Integer(req.getParameter("viewlistno"));
				Integer viewno = new Integer(req.getParameter("viewno"));
				Integer tenantNo = new Integer(req.getParameter("tenantNo"));
				System.out.println("list no"+viewlistno);
				System.out.println("no "+viewno);
				System.out.println("tenant no "+ tenantNo);
				/*************************** 2.開始刪除資料 ***************************************/
				ViewListService viewlistSvc = new ViewListService();
				viewlistSvc.deleteViewList(viewlistno);
				
				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
				req.setAttribute("tenantNo", tenantNo);
				req.setAttribute("viewno", viewno);
				
				String url = null;
//				if ("eachViewDelete".equals(action)){
//					url = "/view/eachView.jsp"; // 成功轉交
//					// viewinfo/listViewPhoto_ByViewno.jsp
//				}
			
				url=requestURL;
				
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
				

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				RequestDispatcher failureView = req
						.getRequestDispatcher("/view/allViewInfo.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("allViewInfoDelete".equals(action)||"tenantViewListDelete".equals(action)) { // 來自listAllViewList.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				String requestURL=req.getParameter("requestURL");
				System.out.println("url:!!!!!!!!!!!!!!!"+requestURL);
				Integer viewlistno = new Integer(req.getParameter("viewlistno"));
				Integer tenantNo = new Integer(req.getParameter("tenantNo"));
				/*************************** 2.開始刪除資料 ***************************************/
				ViewListService viewlistSvc = new ViewListService();
				viewlistSvc.deleteViewList(viewlistno);
				
				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
				req.setAttribute("tenantNo", tenantNo);
				
				String url = null;
				if ("allViewInfoDelete".equals(action)){
					url = "/view/allViewInfo.jsp"; // 成功轉交
					// viewinfo/listViewPhoto_ByViewno.jsp
				}
					
				else if ("tenantViewListDelete".equals(action)){
					url = "/view/tenantViewList.jsp"; // 成功轉交
				}
					
				
				//url=requestURL;
				
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
				return;
				
				

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				RequestDispatcher failureView = req
						.getRequestDispatcher("/view/allViewInfo.jsp");
				failureView.forward(req, res);
			}
		}

		// // 來自test.jsp的請求-----allViewInfo // 來自 test.jsp-----tenantViewList的請求
		if ("myTenantnoToAllViewInfo".equals(action)||"myTenantnoToTenantViewList".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				Integer tenantNo=null;
				try {
					tenantNo = new Integer(req.getParameter("tenantNo"));
					System.out.print("!!!!!!!!!!!!!!!"+tenantNo);
				} catch (Exception e) {
					e.getMessage();
				}
				
				
				/*************************** 2.開始查詢資料 ****************************************/
				ViewListService viewlistSvc = new ViewListService();
				Set<ViewListVO> set = viewlistSvc
						.getViewListByTenantno(tenantNo);
				//System.out.println(set);
//				
//				for (ViewListVO viewlistVO:set){
//					ViewInfoService viewinfoSvc = new ViewInfoService();
//					ViewInfoVO viewinfoVO =  viewinfoSvc.getOneViewInfo(new Integer(
//							viewlistVO.getViewno()));
//					
//					System.out.println("VIEWNAME:"+viewinfoVO.getViewno());
//				
//				}
				req.setAttribute("listViewList_ByTenantno", set);
//				req.setAttribute("viewinfoVO", viewinfoVO);
				req.setAttribute("tenantNo", tenantNo);
			
		
				//RequestDispatcher failureView = req
				//.getRequestDispatcher("/view/tenantViewList.jsp");	

				String url = null;
				if ("myTenantnoToAllViewInfo".equals(action))
					url = "/view/allViewInfo.jsp"; // 成功轉交
												// viewinfo/listViewPhoto_ByViewno.jsp
				else if ("myTenantnoToTenantViewList".equals(action))
					url = "/view/tenantViewList.jsp"; // 成功轉交
				
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
				return;

				/*************************** 其他可能的錯誤處理 ***********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/view/view_select_page.jsp");
				failureView.forward(req, res);
			}
		}
	}
}
