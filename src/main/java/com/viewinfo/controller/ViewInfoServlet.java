package com.viewinfo.controller;

import com.caculateDistance.controller.caculDistanceServlet;
import com.hostel.model.HostelService;
import com.hostel.model.HostelVO;
import com.viewinfo.model.*;
import com.viewlist.model.*;
import com.viewphoto.model.*;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/viewinfo/viewinfo.do")
@MultipartConfig
public class ViewInfoServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("to_viewlist_listAllViewInfo".equals(action)) {
			Integer tenantno = new Integer(req.getParameter("tenantno").trim());
			req.setAttribute("tenantno", tenantno);
			System.out.println("teantno" + tenantno);
			RequestDispatcher failureView = req
					.getRequestDispatcher("/view/allViewInfo.jsp");
			failureView.forward(req, res);
			return;

		}

		/*---------------查詢--------------*/
		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String str = req.getParameter("viewno");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入景點編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/viewinfo/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				Integer viewno = null;
				try {
					viewno = new Integer(str);
				} catch (Exception e) {
					errorMsgs.add("景點編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/viewinfo/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 2.開始查詢資料 *****************************************/
				ViewInfoService viewinfoSvc = new ViewInfoService();
				ViewInfoVO viewinfoVO = viewinfoSvc.getOneViewInfo(viewno);

				// ***********************************************************************************
				if (viewinfoVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/viewinfo/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("viewinfoVO", viewinfoVO); // 資料庫取出的viewinfoVO物件,存入req
				String url = "/view/eachView.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交
																				// listOneEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/viewinfo/select_page.jsp");
				failureView.forward(req, res);
			}
		}

		//listViewPhoto_ByViewno_A||listViewPhoto_ByViewno_B
		// 來自view_select_page.jsp的請求 // 來自 viewinfo/listAllViewInfo.jsp的請求
		if ("listViewPhoto_ByViewno_A".equals(action)
				|| "listViewPhoto_ByViewno_B".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				Integer viewno = new Integer(req.getParameter("viewno"));

				/*************************** 2.開始查詢資料 ****************************************/
				ViewInfoService viewinfoSvc = new ViewInfoService();
				Set<ViewPhotoVO> set = viewinfoSvc.getViewPhotoByViewno(viewno);
				ViewInfoVO viewinfoVO = viewinfoSvc.getOneViewInfo(viewno);

				System.out.println("viewlon" + viewinfoVO.getViewlon() + "   "
						+ "viewlat" + viewinfoVO.getViewlat());

				HostelService hostelSvc = new HostelService();
				List<HostelVO> list = hostelSvc.getAll();
				
				HostelVO newHostelVO = new HostelVO() ;

				for (HostelVO hostelVO : list) {

					Double distance;
					distance = caculDistanceServlet.GetDistance(
							viewinfoVO.getViewlon(), viewinfoVO.getViewlat(),
							hostelVO.getHostelLon(), hostelVO.getHostelLat());
					
					
					
//					if(distance <=50 )
//					{
//						newHostelVO.setHostelNo(hostelVO.getHostelNo());
//						newHostelVO.setHostelName(hostelVO.getHostelName());
//						newHostelVO.setHostelAddress(hostelVO.getHostelAddress());
//						newHostelVO.setHostelLat(hostelVO.getHostelLat());
//						newHostelVO.setHostelLon(hostelVO.getHostelLon());
//						
//					}
					
					
					//System.out.println("lon:" + newHostelVO.getHostelName());
					//System.out.println(distance);

				}

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("listViewPhoto_ByViewno", set); // 資料庫取出的set物件,存入request
				req.setAttribute("viewinfoVO", viewinfoVO);
				req.setAttribute("hostelVO", list);
				//req.setAttribute("newHostelVO", newHostelVO);
				// System.out.println(viewinfoVO.getViewname());
				String url = null;
				if ("listViewPhoto_ByViewno_A".equals(action))
					url = "/view/eachView.jsp"; // 成功轉交
												// viewinfo/listViewPhoto_ByViewno.jsp
				else if ("listViewPhoto_ByViewno_B".equals(action))
					url = "/backEnd/view/viewInfo.jsp"; // 成功轉交
															// viewinfo/listAllViewInfo.jsp

				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 ***********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/view/view_select_page.jsp");
				failureView.forward(req, res);
			}
		}

		// 來自select_page.jsp的請求 // 來自 viewlist/listAllViewList.jsp的請求
		if ("listViewList_ByViewno_A".equals(action)
				|| "listViewList_ByViewno_B".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				Integer viewno = new Integer(req.getParameter("viewno"));

				/*************************** 2.開始查詢資料 ****************************************/

				ViewInfoService viewinfoSvc = new ViewInfoService();
				ViewInfoVO viewinfoVO = viewinfoSvc.getOneViewInfo(viewno);
				Set<ViewListVO> set = viewinfoSvc.getViewListByViewno(viewno);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("listViewList_ByViewno", set); // 資料庫取出的set物件,存入request
				req.setAttribute("viewinfoVO", viewinfoVO);

				// System.out.println(viewinfoVO.getViewname());
				String url = null;
				if ("listViewList_ByViewno_A".equals(action))
					url = "/viewinfo/listViewList_ByViewno.jsp"; // 成功轉交
																	// viewlist/listViewList_ByViewno.jsp
				else if ("listViewPhoto_ByViewno_B".equals(action))
					url = "/viewinfo/listAllViewInfo.jsp"; // 成功轉交
															// viewlist/listAllViewInfo.jsp

				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 ***********************************/
			} catch (Exception e) {
				throw new ServletException(e);
			}
		}

		/*---------------修改--------------*/
		if ("getOne_For_Update".equals(action)) { // 來自listAllViewInfo.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				Integer viewno = new Integer(req.getParameter("viewno"));

				/*************************** 2.開始查詢資料 ****************************************/
				ViewInfoService viewinfoSvc = new ViewInfoService();
				ViewInfoVO viewinfoVO = viewinfoSvc.getOneViewInfo(viewno);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("viewinfoVO", viewinfoVO); // 資料庫取出的viewinfoVO物件,存入req
				String url = "/viewinfo/update_viewinfo_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交
																				// update_viewinfo_input.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/viewinfo/listAllViewInfo_manager.jsp");
				failureView.forward(req, res);
			}
		}// get one for update

		if ("update".equals(action)) { // 來自update_emp_input.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				Integer viewno = null;
				viewno = new Integer(req.getParameter("viewno").trim());
				if (viewno == null || viewno == 0) {
					errorMsgs.add("請輸入景點編號");
				}

				String viewname = req.getParameter("viewname").trim();
				if (viewname == null || viewname.length() == 0) {
					errorMsgs.add("請輸入景點名稱");
				}
				
				String viewmanager = req.getParameter("viewmanager").trim();
				if (viewmanager==null||viewmanager.length()==0){
					errorMsgs.add("請輸入景點管理單位");
				}
				

				String viewphone = req.getParameter("viewphone").trim();
					if (viewphone==null||viewphone.length()==0){
						errorMsgs.add("請輸入景點聯絡電話");
					}
	
				String viewaddress =  req.getParameter("viewaddress").trim();
					if (viewaddress==null||viewaddress.length()==0){
						errorMsgs.add("請輸入景點地址");
					}


				String viewweb = null;
				try {
					viewweb = req.getParameter("viewweb").trim();
				} catch (Exception e) {
					errorMsgs.add("請輸入景點網站");
				}

				Double viewlat = null;
				try {
					viewlat = new Double(req.getParameter("viewlat").trim());
				} catch (NumberFormatException e) {
					if(viewlat==null||viewlat<0){
						errorMsgs.add("請輸入景點緯度");
					}
					viewlat = 0.0;
					errorMsgs.add("請輸入景點緯度");
				}
				
				

				Double viewlon = null;
				try {
					viewlon = new Double(req.getParameter("viewlon").trim());
				} catch (NumberFormatException e) {
					if(viewlon==null||viewlon<0){
						errorMsgs.add("請輸入景點經度");
					}
					viewlon = 0.0;
					errorMsgs.add("請輸入景點經度");
				}

				String viewopen = viewopen = req.getParameter("viewopen").trim();;
			
					if(viewopen==null||viewopen.length()==0){
						errorMsgs.add("請輸入景點開放資訊");
					}
					
			

				String viewticket = req.getParameter("viewticket").trim();
				
					if(viewticket==null||viewticket.length()==0){
						errorMsgs.add("請輸入景點景點票價");
					}
				

				String viewequi = req.getParameter("viewequi").trim();
					if(viewequi==null||viewequi.length()==0)
					{
						errorMsgs.add("請輸入景點設施");
					}
				

				String viewcontent = req.getParameter("viewcontent").trim();	
					if(viewcontent==null||viewcontent.length()==0){
						errorMsgs.add("請輸入景點簡介");
					}
				

				ViewInfoVO viewinfoVO = new ViewInfoVO();
				viewinfoVO.setViewno(viewno);
				viewinfoVO.setViewname(viewname);
				viewinfoVO.setViewmanager(viewmanager);
				viewinfoVO.setViewphone(viewphone);
				viewinfoVO.setViewaddress(viewaddress);
				viewinfoVO.setViewweb(viewweb);
				viewinfoVO.setViewlon(viewlon);
				viewinfoVO.setViewlat(viewlat);
				viewinfoVO.setViewopen(viewopen);
				viewinfoVO.setViewticket(viewticket);
				viewinfoVO.setViewequi(viewequi);
				viewinfoVO.setViewcontent(viewcontent);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("viewinfoVO", viewinfoVO); // 含有輸入格式錯誤的viewinfoVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/viewinfo/update_viewinfo_input.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}

				/*************************** 2.開始修改資料 *****************************************/
				ViewInfoService viewinfoSvc = new ViewInfoService();
				viewinfoVO = viewinfoSvc.updateViewInfo(viewno, viewname,
						viewmanager, viewphone, viewaddress, viewweb, viewlon,
						viewlat, viewopen, viewticket, viewequi, viewcontent);

				/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
				req.setAttribute("viewinfoVO", viewinfoVO); // 資料庫update成功後,正確的的empVO物件,存入req
				Set<ViewPhotoVO> set = viewinfoSvc.getViewPhotoByViewno(viewno);
				req.setAttribute("listViewPhoto_ByViewno", set); // // 鞈�澈����et�隞�,摮request
				req.setAttribute("viewinfoVO", viewinfoVO);
				
				String url = "/backEnd/view/viewInfo.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 嚙論改成嚙穀嚙踝蕭,嚙踝蕭嚙締istOneEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/viewinfo/update_viewinfo_input.jsp");
				failureView.forward(req, res);
			}
		}// update

		/*************************************** 新增 *********************************************/

		if ("insert".equals(action)) { // 來自viewinfo/addViewInfo.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/

				String viewname = req.getParameter("viewname").trim();
				
				if (viewname == null || viewname.length() == 0) {
					errorMsgs.add("請輸入景點名稱");
				}
				
				String viewmanager = req.getParameter("viewmanager").trim();
				if (viewmanager==null||viewmanager.length()==0){
					errorMsgs.add("請輸入景點管理單位");
				}
				

				String viewphone = req.getParameter("viewphone").trim();
					if (viewphone==null||viewphone.length()==0){
						errorMsgs.add("請輸入景點聯絡電話");
					}
	
				String viewaddress =  req.getParameter("viewaddress").trim();
					if (viewaddress==null||viewaddress.length()==0){
						errorMsgs.add("請輸入景點地址");
					}


				String viewweb = null;
				try {
					viewweb = req.getParameter("viewweb").trim();
				} catch (Exception e) {
					errorMsgs.add("請輸入景點網站");
				}

				Double viewlat = null;
				try {
					viewlat = new Double(req.getParameter("viewlat").trim());
				} catch (NumberFormatException e) {
					if(viewlat==null||viewlat<0){
						errorMsgs.add("請輸入景點緯度");
					}
					viewlat = 0.0;
					errorMsgs.add("請輸入景點緯度");
				}
				
				

				Double viewlon = null;
				try {
					viewlon = new Double(req.getParameter("viewlon").trim());
				} catch (NumberFormatException e) {
					if(viewlon==null||viewlon<0){
						errorMsgs.add("請輸入景點經度");
					}
					viewlon = 0.0;
					errorMsgs.add("請輸入景點經度");
				}

				String viewopen = viewopen = req.getParameter("viewopen").trim();;
			
					if(viewopen==null||viewopen.length()==0){
						errorMsgs.add("請輸入景點開放資訊");
					}
					
			

				String viewticket = req.getParameter("viewticket").trim();
				
					if(viewticket==null||viewticket.length()==0){
						errorMsgs.add("請輸入景點景點票價");
					}
				

				String viewequi = req.getParameter("viewequi").trim();
					if(viewequi==null||viewequi.length()==0)
					{
						errorMsgs.add("請輸入景點設施");
					}
				

				String viewcontent = req.getParameter("viewcontent").trim();	
					if(viewcontent==null||viewcontent.length()==0){
						errorMsgs.add("請輸入景點簡介");
					}
					
				


				ViewInfoVO viewinfoVO = new ViewInfoVO();
				
				viewinfoVO.setViewname(viewname);
				viewinfoVO.setViewmanager(viewmanager);
				viewinfoVO.setViewphone(viewphone);
				viewinfoVO.setViewaddress(viewaddress);
				viewinfoVO.setViewweb(viewweb);
				viewinfoVO.setViewlon(viewlon);
				viewinfoVO.setViewlat(viewlat);
				viewinfoVO.setViewopen(viewopen);
				viewinfoVO.setViewticket(viewticket);
				viewinfoVO.setViewequi(viewequi);
				viewinfoVO.setViewcontent(viewcontent);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("viewinfoVO", viewinfoVO); // 含有輸入格式錯誤的viewinfoVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/backEnd/view/newView.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始新增資料 ***************************************/
				ViewInfoService viewinfoSvc = new ViewInfoService();
				viewinfoVO = viewinfoSvc.addViewInfo(viewname, viewmanager,
						viewphone, viewaddress, viewweb, viewlon, viewlat,
						viewopen, viewticket, viewequi, viewcontent);
				//i用來判斷是否要繼續加入景點照片
				Integer i =null;
				i = 1;
				
				///i
				
				//取最後一個景點 目的是加入照片
				ViewInfoService viewinfoSvc1 = new ViewInfoService();
				ViewInfoVO viewinfoVO1 = viewinfoSvc1.getLastOneViewInfo();
				//Integer viewno=viewinfoVO1.getViewno();
				//System.out.println("!!!!!!"+ viewinfoVO1.getViewno());

				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String url = "/backEnd/view/newView.jsp"; // 傳至list_AllViewinfo頁面
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				req.setAttribute("i", i);
				
				
				req.setAttribute("viewinfoVO1", viewinfoVO1);
				
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料錯誤" + e.getMessage());
				e.printStackTrace();
				RequestDispatcher failureView = req
						.getRequestDispatcher("/viewinfo/addViewInfo_manager.jsp");
				failureView.forward(req, res);
			}
		}// insert
		/*---------------刪除--------------*/

		if ("delete".equals(action)) { // 來自listAllViewInfo.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ***************************************/
				Integer viewno = new Integer(req.getParameter("viewno"));

				/*************************** 2.開始刪除資料 ***************************************/
				ViewInfoService viewinfoSvc = new ViewInfoService();
				viewinfoSvc.deleteViewInfo(viewno);

				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
				String url = "/backEnd/view/view.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/view/allViewInfo.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("getlistByCQ".equals(action)) {

			try {
				Map<String, String[]> map = req.getParameterMap();
				ViewInfoService viewInfoService = new ViewInfoService();
				List<ViewInfoVO> list = viewInfoService.getAll(map);

				for (int i = 0; i < list.size(); i++) {
					for (int j = i + 1; j < list.size(); j++) {
						if (list.get(j).getViewno().equals(list.get(i).getViewno())) {
							list.remove(j);
							j--;
						}
					}
				}
				
				req.setAttribute("viewlist", list);
				RequestDispatcher successView = req.getRequestDispatcher("/backEnd/view/view.jsp"); 
				successView.forward(req, res);

			} catch (Exception e) {
				RequestDispatcher failureView = req.getRequestDispatcher("/backEnd/view/view.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("get_view_bk".equals(action)) {
			System.out.println("in");
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.嚙踝蕭嚙踝蕭嚙請求嚙諸潘蕭 ****************************************/
				Integer viewno = new Integer(req.getParameter("viewno"));
				System.out.println("get");
				/*************************** 2.嚙罷嚙締嚙範嚙賠賂蕭嚙� ****************************************/
				ViewInfoService viewinfoSvc = new ViewInfoService();
				Set<ViewPhotoVO> set = viewinfoSvc.getViewPhotoByViewno(viewno);
				ViewInfoVO viewinfoVO = viewinfoSvc.getOneViewInfo(viewno);
					
				System.out.println("all");
				/*************************** 3.嚙範嚙賠改蕭嚙踝蕭,嚙褒喉蕭嚙踝蕭嚙�(Send the Success view) ************/
				req.setAttribute("listViewPhoto_ByViewno", set); 
				req.setAttribute("viewinfoVO", viewinfoVO);
				
				System.out.println("123");
				
				RequestDispatcher successView = req.getRequestDispatcher("/backEnd/view/viewInfo.jsp");
				successView.forward(req, res);

				/*************************** 嚙踝蕭L嚙箠嚙賞的嚙踝蕭嚙羯嚙畿嚙緲 ***********************************/
			} catch (Exception e) {
				errorMsgs.add("嚙盤嚙糊嚙踝蕭嚙緻嚙踝蕭嚙�" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/view/view_select_page.jsp");
				failureView.forward(req, res);
			}
		}
	}

}
