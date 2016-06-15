package com.viewphoto.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.viewinfo.model.ViewInfoService;
import com.viewinfo.model.ViewInfoVO;
import com.viewphoto.model.*;

@MultipartConfig
@WebServlet("/viewphoto/viewphoto.do")
public class ViewPhotoServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		//-----------查詢---------------
		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String str = req.getParameter("viewpicno");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入景點照片編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/viewphoto/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				Integer viewpicno = null;
				try {
					viewpicno = new Integer(str);
				} catch (Exception e) {
					errorMsgs.add("景點照片編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/viewphoto/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}
				/*
				 * /***************************2.開始查詢資料**************************
				 * **************
				 */
				ViewPhotoService viewphotoSvc = new ViewPhotoService();
				ViewPhotoVO viewphotoVO = viewphotoSvc
						.getOneViewPhoto(viewpicno);
				if (viewphotoVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/viewphoto/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				// ***************************3.查詢完成,準備轉交(Send the Successview)*************/
				req.setAttribute("viewphotoVO", viewphotoVO); // 資料庫取出的empVO物件,存入req
				String url = "/viewphoto/listOneViewPhoto.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交
																				// listOneEmp.jsp
				successView.forward(req, res);

				// ***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/viewphoto/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		//-----------修改---------------
		if ("getOne_For_Update".equals(action)) { // 來自listAllEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數****************************************/
				Integer viewpicno = new Integer(req.getParameter("viewpicno"));
				
				/***************************2.開始查詢資料****************************************/
				ViewPhotoService viewphotoSvc = new ViewPhotoService();
				ViewPhotoVO viewphotoVO = viewphotoSvc.getOneViewPhoto(viewpicno);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("viewphotoVO", viewphotoVO);         // 資料庫取出的empVO物件,存入req
				String url = "/viewphoto/update_viewphoto_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/viewphoto/listAllViewPhoto.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("update".equals(action)) { // 來自update_emp_input.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				Integer viewpicno = new Integer(req.getParameter("viewpicno").trim());
				Integer viewno = new Integer(req.getParameter("viewno").trim());
				
				Part part = req.getPart("viewpic");
				InputStream in = part.getInputStream();
				byte[] viewpic = new byte[in.available()];
				in.read(viewpic);
				in.close();
				
				 ViewPhotoVO viewphotoVO = new ViewPhotoVO();
				 viewphotoVO.setViewno(viewpicno);
				 viewphotoVO.setViewno(viewno);
				 viewphotoVO.setViewpic(viewpic);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("viewphotoVO", viewphotoVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/viewphoto/update_viewphoto_input.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				ViewPhotoService viewphotoSvc = new ViewPhotoService();
				viewphotoVO = viewphotoSvc.updateViewPhoto(viewpicno, viewno, viewpic);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("viewphotoVO", viewphotoVO); // 資料庫update成功後,正確的的empVO物件,存入req
				String url = "/viewphoto/listOneViewPhoto.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/viewphoto/update_viewphoto_input.jsp");
				failureView.forward(req, res);
			}
		}
		

		//-----------新增---------------
		 if ("insert".equals(action)) { // 來自addViewPhoto.jsp的請求
		
		 List<String> errorMsgs = new LinkedList<String>();
		 // Store this set in the request scope, in case we need to
		 // send the ErrorPage view.
		 req.setAttribute("errorMsgs", errorMsgs);
		
		 try {
		 /***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
		
		 Integer viewno = new Integer(req.getParameter("viewno").trim());
		
		 Part part = req.getPart("viewpic");
		
		 InputStream in = part.getInputStream();
		 byte[] viewpic = new byte[in.available()];
		 in.read(viewpic);
		 in.close();
		
		 ViewPhotoVO viewphotoVO = new ViewPhotoVO();
		 viewphotoVO.setViewno(viewno);
		 viewphotoVO.setViewpic(viewpic);
		
		 // Send the use back to the form, if there were errors
		 if (!errorMsgs.isEmpty()) {
		 req.setAttribute("viewphotoVO", viewphotoVO); //
		 //含有輸入格式錯誤的empVO物件,也存入req
		 RequestDispatcher failureView = req
		 .getRequestDispatcher("/viewinfo/addViewInfo_manager.jsp");
		 failureView.forward(req, res);
		 return;
		 }
		
		 /***************************2.開始新增資料***************************************/
		 ViewPhotoService viewphotoSvc = new ViewPhotoService();
		 viewphotoVO = viewphotoSvc.addViewPhoto(viewno, viewpic);
		
		 /***************************3.新增完成,準備轉交(Send the Success
		 view)***********/
		 String url = "/viewinfo/listAllViewInfo_manager.jsp";
		 RequestDispatcher successView = req.getRequestDispatcher(url); //
		// 新增成功後轉交listAllEmp.jsp
		 successView.forward(req, res);
		
		 /***************************其他可能的錯誤處理**********************************/
		 } catch (Exception e) {
		 errorMsgs.add(e.getMessage());
		 RequestDispatcher failureView = req
		 .getRequestDispatcher("/viewphoto/addViewPhoto.jsp");
		 failureView.forward(req, res);
		 }
		 }
		//-----------刪除---------------
		if ("delete".equals(action)) { // 來自listAllViewPhoto.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ***************************************/
				Integer viewpicno = new Integer(req.getParameter("viewpicno"));

				/*************************** 2.開始刪除資料 ***************************************/
				ViewPhotoService viewphotoSvc = new ViewPhotoService();
				viewphotoSvc.deleteViewPhoto(viewpicno);

				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
				Integer viewno = new Integer(req.getParameter("viewno"));
				ViewInfoService viewinfoSvc = new ViewInfoService();
				ViewInfoVO viewinfoVO = viewinfoSvc.getOneViewInfo(viewno);
				Set<ViewPhotoVO> set = viewinfoSvc.getViewPhotoByViewno(viewno);
				req.setAttribute("listViewPhoto_ByViewno", set); 
				req.setAttribute("viewinfoVO", viewinfoVO);
				
				String url = "/backEnd/view/viewInfo.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// �������,頧漱���������雯���
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/backEnd/view/viewInfo.jsp");
				failureView.forward(req, res);
			}
		}

	}

}
