package com.page.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import net.sf.json.JSONObject;

import com.dealer.model.DealerService;
import com.dealer.model.DealerVO;
import com.hostel.model.HostelService;
import com.roomType.model.RoomTypeService;
import com.roomType.model.RoomTypeVO;

@MultipartConfig
public class RoomTypeModifyServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("getOne_For_Display".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			// 錯誤List 接收所有錯誤---------------------
			req.setAttribute("errorMsgs", errorMsgs);
			// --------------------------------------
			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				// --------------------------------------
				String str = req.getParameter("roomTypeNo");
				// ---------------------------------------

				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入房型編號");
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/roomType/roomType_select_page.jsp");
					failureView.forward(req, res);
					return;
				}

				Integer roomTypeNo = null;
				try {
					roomTypeNo = new Integer(str);
				} catch (Exception e) {
					errorMsgs.add("房型編號錯誤");
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/roomType/roomType_select_page.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始查詢資料 *****************************************/
				RoomTypeService roomTypeService = new RoomTypeService();
				// select one dealer
				RoomTypeVO roomTypeVO = roomTypeService
						.getOneRoomType(roomTypeNo);
				if (roomTypeVO == null) {
					errorMsgs.add("查無資料");
				}
				// if error ->send error message to select_page
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/roomType/roomType_select_page.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("roomTypeVO", roomTypeVO);
				String url = "/roomType/roomType_listOne.jsp";
				// 指定特定url 將attribute傳送到此處
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("無法取得資料" + e.getMessage());
				// 錯誤回傳
				RequestDispatcher failureView = req
						.getRequestDispatcher("/roomType/roomType_select_page.jsp");
				failureView.forward(req, res);
			}
		}// if get one display------------------------------------------

		if ("getOne_For_Update".equals(action)) {// from list all
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			// 送出修改的來源網頁路徑: 可能為【/roomType/roomType_listAll.jsp】
			// 或 【/hostel/listRoomTypes_ByHostelNo.jsp】 或 【
			// /hostel/hostel_listHostelsForRoomTypes.jsp】
			// OR 【/roomType/roomType_compositeQuery.jsp】
			String requestURL = req.getParameter("requestURL");
			System.out.println("get one update: " + requestURL);
			// 送出修改的來源網頁路徑, 存入req (是為了給roomType_update.jsp)
			// req.setAttribute("requestURL", requestURL);

			// String whichPage=req.getParameter("whichPage");
			// 送出修改的來源網頁的第幾頁, 存入req(只用於:roomType_listAll.jsp)
			// req.setAttribute("whichPage", whichPage);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				Integer roomTypeNo = new Integer(req.getParameter("roomTypeNo"));
				/*************************** 2.開始查詢資料 ****************************************/
				RoomTypeService roomTypeService = new RoomTypeService();
				RoomTypeVO roomTypeVO = roomTypeService
						.getOneRoomType(roomTypeNo);
				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("roomTypeVO", roomTypeVO);
				String url = "/roomType_modify_update.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料: " + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/roomType/roomType_listAll.jsp");
				failureView.forward(req, res);
			}
		}// get one update

		if ("ajaxUpdate".equals(action)) {// from update button

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL");
			
			RoomTypeService roomTypeServicetmp = new RoomTypeService();
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				Integer roomTypeNo = null;
				try {
					roomTypeNo = new Integer(req.getParameter("roomTypeNo"));
				} catch (Exception e) {
					System.out.println(roomTypeNo);
					errorMsgs.add("輸入房型編號");
				}
				System.out.println("room-------------" + roomTypeNo);

				Integer hostelNo = roomTypeServicetmp
						.getOneRoomType(roomTypeNo).getHostelNo();

				Integer facilityNo = null;
				try {
					facilityNo = new Integer(req.getParameter("facilityNo")
							.trim());
				} catch (Exception e) {
					facilityNo = 4001;
					errorMsgs.add("請輸入設備編號");
				}

				String roomTypeName = req.getParameter("roomTypeName").trim();
				if (roomTypeName == null || roomTypeName.length() == 0) {
					errorMsgs.add("請輸入房型名稱");
				}

				Integer roomTypeContain = null;
				try {
					roomTypeContain = new Integer(
							req.getParameter("roomTypeContain"));
					if (roomTypeContain == 0 || roomTypeContain == null) {
						errorMsgs.add("請輸入/修正容納人數");
					}
				} catch (Exception e) {
					errorMsgs.add("請輸入人數");
				}

				Integer roomTypePrice = null;
				try {
					roomTypePrice = new Integer(
							req.getParameter("roomTypePrice"));
					if (roomTypePrice == null || roomTypePrice < 1000) {
						errorMsgs.add("請輸入/修正房型價錢");
					}
				} catch (Exception e) {
					errorMsgs.add("請輸入價錢");
				}
				Part roomTypePicture = null;
				byte[] roomTypePictureImg = null;
				try {
					roomTypePicture = req.getPart("roomTypePicture");
					if (roomTypePicture.getSize() == 0) {
						roomTypePictureImg = roomTypeServicetmp.getOneRoomType(
								roomTypeNo).getRoomTypePicture();
					} else {
						// ---------------------------------------------------------------
						InputStream roomTypePictureStream = roomTypePicture
								.getInputStream();
						roomTypePictureImg = new byte[roomTypePictureStream
								.available()];
						roomTypePictureStream.read(roomTypePictureImg);
						roomTypePictureStream.close();
						roomTypePictureStream = null;
						// ---------------------------------------------------------------
					}
				} catch (Exception e) {
					roomTypePicture = null;
					errorMsgs.add("請選擇圖片");
				}

				String roomTypeContent = req.getParameter("roomTypeContent");
				if (roomTypeContent == null || roomTypeContent.length() == 0) {
					errorMsgs.add("請輸入房型內容");
				}
				// 處理資料錯誤狀況，不用重複寫---------
				RoomTypeVO roomTypeVO = new RoomTypeVO();
				roomTypeVO.setRoomTypeNo(roomTypeNo);
				roomTypeVO.setHostelNo(hostelNo);
				roomTypeVO.setFacilityNo(facilityNo);
				roomTypeVO.setRoomTypeName(roomTypeName);
				roomTypeVO.setRoomTypeContain(roomTypeContain);
				roomTypeVO.setRoomTypePrice(roomTypePrice);
				roomTypeVO.setRoomTypeContent(roomTypeContent);
				roomTypeVO.setRoomTypePicture(roomTypePictureImg);

//				if (!errorMsgs.isEmpty()) {
//					req.setAttribute("roomTypeVO", roomTypeVO);
//					RequestDispatcher failureView = req
//							.getRequestDispatcher("/roomType_modify_update.jsp");
//					failureView.forward(req, res);
//					return;
//				}
				/*************************** 2.開始新增資料 ***************************************/
				RoomTypeService roomTypeService = new RoomTypeService();
				roomTypeVO = roomTypeService.updateRoomType(roomTypeNo,
						hostelNo, facilityNo, roomTypeName, roomTypeContain,
						roomTypePrice, roomTypePictureImg, roomTypeContent);
				JSONObject roomTypeJO=new JSONObject();
				roomTypeJO.put("roomTypeNo", roomTypeNo);
				roomTypeJO.put("hostelNo", hostelNo);
				roomTypeJO.put("facilityNo", facilityNo);
				roomTypeJO.put("roomTypeName", roomTypeName);
				roomTypeJO.put("roomTypeContain", roomTypeContain);
				roomTypeJO.put("roomTypePrice", roomTypePrice);
//				roomTypeJO.put("roomTypePictureImg", roomTypePictureImg);
				roomTypeJO.put("roomTypeContent", roomTypeContent);
				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				res.setContentType("text/plain");
				res.setCharacterEncoding("UTF-8");
				PrintWriter out = res.getWriter();
				out.write(roomTypeJO.toString());
				out.flush();
				out.close();
				
		}// update

		if ("update".equals(action)) {// from update button

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			// 送出修改的來源網頁路徑: 可能為【/roomType/roomType_listAll.jsp】
			// 或 【/hostel/listRoomTypes_ByHostelNo.jsp】 或 【
			// /hostel/hostel_listHostelsForRoomTypes.jsp】
			// OR 【/roomType/roomType_compositeQuery.jsp】
			String requestURL = req.getParameter("requestURL");
			// req.setAttribute("requestURL", requestURL);

			// String whichPage =req.getParameter("whichPage");
			// req.setAttribute("whichPage", whichPage);
			RoomTypeService roomTypeServicetmp = new RoomTypeService();
			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				Integer roomTypeNo = null;
				try {
					roomTypeNo = new Integer(req.getParameter("roomTypeNo"));
				} catch (Exception e) {
					System.out.println(roomTypeNo);
					errorMsgs.add("輸入房型編號");
				}
				System.out.println("room-------------" + roomTypeNo);

				Integer hostelNo = roomTypeServicetmp
						.getOneRoomType(roomTypeNo).getHostelNo();

				Integer facilityNo = null;
				try {
					facilityNo = new Integer(req.getParameter("facilityNo")
							.trim());
				} catch (Exception e) {
					facilityNo = 4001;
					errorMsgs.add("請輸入設備編號");
				}

				String roomTypeName = req.getParameter("roomTypeName").trim();
				if (roomTypeName == null || roomTypeName.length() == 0) {
					errorMsgs.add("請輸入房型名稱");
				}

				Integer roomTypeContain = null;
				try {
					roomTypeContain = new Integer(
							req.getParameter("roomTypeContain"));
					if (roomTypeContain == 0 || roomTypeContain == null) {
						errorMsgs.add("請輸入/修正容納人數");
					}
				} catch (Exception e) {
					errorMsgs.add("請輸入人數");
				}

				Integer roomTypePrice = null;
				try {
					roomTypePrice = new Integer(
							req.getParameter("roomTypePrice"));
					if (roomTypePrice == null || roomTypePrice < 1000) {
						errorMsgs.add("請輸入/修正房型價錢");
					}
				} catch (Exception e) {
					errorMsgs.add("請輸入價錢");
				}
				Part roomTypePicture = null;
				byte[] roomTypePictureImg = null;
				try {
					roomTypePicture = req.getPart("roomTypePicture");
					if (roomTypePicture.getSize() == 0) {
						roomTypePictureImg = roomTypeServicetmp.getOneRoomType(
								roomTypeNo).getRoomTypePicture();
					} else {
						// ---------------------------------------------------------------
						InputStream roomTypePictureStream = roomTypePicture
								.getInputStream();
						roomTypePictureImg = new byte[roomTypePictureStream
								.available()];
						roomTypePictureStream.read(roomTypePictureImg);
						roomTypePictureStream.close();
						roomTypePictureStream = null;
						// ---------------------------------------------------------------
					}
				} catch (Exception e) {
					roomTypePicture = null;
					errorMsgs.add("請選擇圖片");
				}

				String roomTypeContent = req.getParameter("roomTypeContent");
				if (roomTypeContent == null || roomTypeContent.length() == 0) {
					errorMsgs.add("請輸入房型內容");
				}
				// 處理資料錯誤狀況，不用重複寫---------
				RoomTypeVO roomTypeVO = new RoomTypeVO();
				roomTypeVO.setRoomTypeNo(roomTypeNo);
				roomTypeVO.setHostelNo(hostelNo);
				roomTypeVO.setFacilityNo(facilityNo);
				roomTypeVO.setRoomTypeName(roomTypeName);
				roomTypeVO.setRoomTypeContain(roomTypeContain);
				roomTypeVO.setRoomTypePrice(roomTypePrice);
				roomTypeVO.setRoomTypeContent(roomTypeContent);
				roomTypeVO.setRoomTypePicture(roomTypePictureImg);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("roomTypeVO", roomTypeVO);
					RequestDispatcher failureView = req
							.getRequestDispatcher(requestURL);
					failureView.forward(req, res);
					return;
				}
				/*************************** 2.開始新增資料 ***************************************/
				RoomTypeService roomTypeService = new RoomTypeService();
				roomTypeVO = roomTypeService.updateRoomType(roomTypeNo,
						hostelNo, facilityNo, roomTypeName, roomTypeContain,
						roomTypePrice, roomTypePictureImg, roomTypeContent);
				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				System.out.println("update requestURL=" + requestURL);
				// System.out.println("whichPage="+whichPage);
				String url = requestURL;
				System.out.println(req.getContextPath()
						+ "/roomType_modify.jsp");
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("資料不符" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}

		}// update------------------------------------------------------
		
		if ("ajaxInsert".equals(action)) {// from
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			// reference errorMsgs to errorMsgs list,so everytime change can
			// refer to req's errorMsgs
			System.out.println("ajax insert");
			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				Integer hostelNo = null;
				try {
					hostelNo = new Integer(req.getParameter("hostelNo"));
				} catch (Exception e) {
					hostelNo = 2001;
					errorMsgs.add("請輸入民宿編號");
				}

				Integer facilityNo = null;
				try {
					facilityNo = new Integer(req.getParameter("facilityNo")
							.trim());
				} catch (Exception e) {
					facilityNo = 4001;
					errorMsgs.add("請輸入設備編號");
				}

				String roomTypeName = req.getParameter("roomTypeName").trim();
				if (roomTypeName == null || roomTypeName.length() == 0) {
					errorMsgs.add("請輸入房型名稱");
				}

				Integer roomTypeContain = null;
				try {
					roomTypeContain = new Integer(
							req.getParameter("roomTypeContain"));
					if (roomTypeContain == 0 || roomTypeContain == null) {
						errorMsgs.add("請輸入/修正容納人數");
					}
				} catch (Exception e) {
					errorMsgs.add("請輸入人數");
				}

				Integer roomTypePrice = null;
				try {
					roomTypePrice = new Integer(
							req.getParameter("roomTypePrice"));
					if (roomTypePrice == null || roomTypePrice < 1000) {
						errorMsgs.add("請輸入/修正房型價錢");
					}
				} catch (Exception e) {
					errorMsgs.add("請輸入價錢");
				}

				// new*********************
				Integer roomQuantity = null;
				try {
					roomQuantity = new Integer(req.getParameter("roomQuantity"));
					if (roomQuantity <= 0) {
						errorMsgs.add("請輸入正確房間數");
					}
				} catch (Exception e) {
					errorMsgs.add("請輸入房間數");
				}
				// new*********************

				Part roomTypePicture = null;
				byte[] roomTypePictureImg = null;
				try {
					roomTypePicture = req.getPart("roomTypePicture");
					if (roomTypePicture.getSize() == 0) {
						errorMsgs.add("請選擇圖片");
					}
				} catch (Exception e) {
					roomTypePicture = null;
					errorMsgs.add("請選擇圖片");
				}
				// ---------------------------------------------------------------
				InputStream roomTypePictureStream = roomTypePicture
						.getInputStream();
				roomTypePictureImg = new byte[roomTypePictureStream.available()];
				roomTypePictureStream.read(roomTypePictureImg);
				roomTypePictureStream.close();
				roomTypePictureStream = null;
				// ---------------------------------------------------------------

				String roomTypeContent = req.getParameter("roomTypeContent");
				if (roomTypeContent == null || roomTypeContent.length() == 0) {
					errorMsgs.add("請輸入房型內容");
				}
				System.out.println("no error");
				// 處理資料錯誤狀況，不用重複寫---------
				RoomTypeVO roomTypeVO = new RoomTypeVO();
				roomTypeVO.setHostelNo(hostelNo);
				roomTypeVO.setFacilityNo(facilityNo);
				roomTypeVO.setRoomTypeName(roomTypeName);
				roomTypeVO.setRoomTypeContain(roomTypeContain);
				roomTypeVO.setRoomTypePrice(roomTypePrice);
				roomTypeVO.setRoomTypePicture(roomTypePictureImg);
				roomTypeVO.setRoomTypeContent(roomTypeContent);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("roomTypeVO", roomTypeVO);
					RequestDispatcher failureView = req
							.getRequestDispatcher("/roomType_modify.jsp");
					failureView.forward(req, res);
					return;
				}
				System.out.println("no!!!"+hostelNo);
				System.out.println("no2....."+facilityNo);
				
				/*************************** 2.開始新增資料 ***************************************/
				System.out.println("00");
				RoomTypeService roomTypeService = new RoomTypeService();
				roomTypeVO = roomTypeService.addRoomTypeWithRoom(hostelNo,
						facilityNo, roomTypeName, roomTypeContain,
						roomTypePrice, roomTypePictureImg, roomTypeContent,
						roomQuantity);
				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String requestURL = req.getParameter("requestURL");
				System.out.println("url:" + requestURL);
			} catch (Exception e) {
				errorMsgs.add("資料不符" + e.getMessage());
			}
			System.out.println("finish!!!!!!!!!!!!");
			res.setContentType("text/plain");
			res.setCharacterEncoding("UTF-8");
			PrintWriter out = res.getWriter();
			out.flush();
			out.close();

		}// insert ajax
		
		
		if ("insert".equals(action)) {// from
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			// reference errorMsgs to errorMsgs list,so everytime change can
			// refer to req's errorMsgs

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				Integer hostelNo = null;
				try {
					hostelNo = new Integer(req.getParameter("hostelNo"));
				} catch (Exception e) {
					hostelNo = 2001;
					errorMsgs.add("請輸入民宿編號");
				}

				Integer facilityNo = null;
				try {
					facilityNo = new Integer(req.getParameter("facilityNo")
							.trim());
				} catch (Exception e) {
					facilityNo = 4001;
					errorMsgs.add("請輸入設備編號");
				}

				String roomTypeName = req.getParameter("roomTypeName").trim();
				if (roomTypeName == null || roomTypeName.length() == 0) {
					errorMsgs.add("請輸入房型名稱");
				}

				Integer roomTypeContain = null;
				try {
					roomTypeContain = new Integer(
							req.getParameter("roomTypeContain"));
					if (roomTypeContain == 0 || roomTypeContain == null) {
						errorMsgs.add("請輸入/修正容納人數");
					}
				} catch (Exception e) {
					errorMsgs.add("請輸入人數");
				}

				Integer roomTypePrice = null;
				try {
					roomTypePrice = new Integer(
							req.getParameter("roomTypePrice"));
					if (roomTypePrice == null || roomTypePrice < 1000) {
						errorMsgs.add("請輸入/修正房型價錢");
					}
				} catch (Exception e) {
					errorMsgs.add("請輸入價錢");
				}

				// new*********************
				Integer roomQuantity = null;
				try {
					roomQuantity = new Integer(req.getParameter("roomQuantity"));
					if (roomQuantity <= 0) {
						errorMsgs.add("請輸入正確房間數");
					}
				} catch (Exception e) {
					errorMsgs.add("請輸入房間數");
				}
				// new*********************

				Part roomTypePicture = null;
				byte[] roomTypePictureImg = null;
				try {
					roomTypePicture = req.getPart("roomTypePicture");
					if (roomTypePicture.getSize() == 0) {
						errorMsgs.add("請選擇圖片");
					}
				} catch (Exception e) {
					roomTypePicture = null;
					errorMsgs.add("請選擇圖片");
				}
				// ---------------------------------------------------------------
				InputStream roomTypePictureStream = roomTypePicture
						.getInputStream();
				roomTypePictureImg = new byte[roomTypePictureStream.available()];
				roomTypePictureStream.read(roomTypePictureImg);
				roomTypePictureStream.close();
				roomTypePictureStream = null;
				// ---------------------------------------------------------------

				String roomTypeContent = req.getParameter("roomTypeContent");
				if (roomTypeContent == null || roomTypeContent.length() == 0) {
					errorMsgs.add("請輸入房型內容");
				}
				// 處理資料錯誤狀況，不用重複寫---------
				RoomTypeVO roomTypeVO = new RoomTypeVO();
				roomTypeVO.setHostelNo(hostelNo);
				roomTypeVO.setFacilityNo(facilityNo);
				roomTypeVO.setRoomTypeName(roomTypeName);
				roomTypeVO.setRoomTypeContain(roomTypeContain);
				roomTypeVO.setRoomTypePrice(roomTypePrice);
				roomTypeVO.setRoomTypePicture(roomTypePictureImg);
				roomTypeVO.setRoomTypeContent(roomTypeContent);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("roomTypeVO", roomTypeVO);
					RequestDispatcher failureView = req
							.getRequestDispatcher("/roomType_modify.jsp");
					failureView.forward(req, res);
					return;
				}
				/*************************** 2.開始新增資料 ***************************************/
				System.out.println("00");
				RoomTypeService roomTypeService = new RoomTypeService();
				roomTypeVO = roomTypeService.addRoomTypeWithRoom(hostelNo,
						facilityNo, roomTypeName, roomTypeContain,
						roomTypePrice, roomTypePictureImg, roomTypeContent,
						roomQuantity);
				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String requestURL = req.getParameter("requestURL");
				System.out.println("url:" + requestURL);
				// String url ="/roomType_modify.jsp";
				// RequestDispatcher successView
				// =req.getRequestDispatcher(requestURL);
				// successView.forward(req, res);
				res.sendRedirect(req.getContextPath() + requestURL);
			} catch (Exception e) {
				errorMsgs.add("資料不符" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/roomType_modify.jsp");
				failureView.forward(req, res);
			}

		}// insert

		if ("delete".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();

			req.setAttribute("errorMsgs", errorMsgs);
			// 送出刪除的來源網頁路徑: 可能為【/roomType/roomType_listAll.jsp】
			// 或 【/hostel/listRoomTypes_ByHostelNo.jsp】 或 【
			// /hostel/hostel_listHostelsForRoomTypes.jsp】
			// OR 【/roomType/roomType_compositeQuery.jsp】
			String requestURL = req.getParameter("requestURL");
			System.out.println("get on delete: " + requestURL);
			// String whichPage=req.getParameter("whichPage");

			try {
				/*************************** 1.接收請求參數 ***************************************/
				Integer roomTypeNo = new Integer(req.getParameter("roomTypeNo"));
				/*************************** 2.開始刪除資料 ***************************************/
				RoomTypeService roomTypeService = new RoomTypeService();
				roomTypeService.deleteRoomType(roomTypeNo);
				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
				// String url="/roomType/roomType_listAll.jsp";
				// RequestDispatcher successView =req.getRequestDispatcher(url);
				// successView.forward(req, res);
				// 比對用
				RoomTypeVO roomTypeVO = roomTypeService
						.getOneRoomType(roomTypeNo);
				HostelService hoslteService = new HostelService();

				// 傳set給特定頁面
				if (requestURL.equals("/hostel/hostel_listRoomTypes.jsp")
						|| requestURL
								.equals("/hostel/hostel_listHostelsForRoomTypes.jsp")) {
					req.setAttribute("listRoomTypes_ByHostelNo", hoslteService
							.getRoomTypesByHostelNo(roomTypeVO.getHostelNo()));
				}
				if (requestURL.equals("/roomType/roomType_CompositeQuery.jsp")) {
					HttpSession session = req.getSession();
					Map<String, String[]> map = (Map<String, String[]>) session.getAttribute("map");
					List<RoomTypeVO> list = roomTypeService.getAll(map);
					req.setAttribute("listRoomTypes_ByCompositeQuery", list);
				}
				// 送出刪除的來源網頁的第幾頁(只用於:istAllEmp.jsp)
				// String url=requestURL+"?whichPage="+whichPage;
				String url = requestURL;
				// 刪除成功後,轉交回送出刪除的來源網頁
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		}// delete

		// from composite query
		if ("listRoomTypes_ByCompositeQuery_A".equals(action)) {

			// String requestURL=req.getParameter("requestURL");
			// System.out.println("get on composite "+requestURL);
			List<String> errorMsgs = new LinkedList<String>();

			req.setAttribute("errorMsgs", errorMsgs);
			try {
				/************ 1.將輸入資料轉為Map *************/
				// 採用Map<String,String[]> getParameterMap()的方法
				// 注意:an immutable java.util.Map
				// Map<String, String[]> map=req.getParameterMap();
				HttpSession session = req.getSession();
				// 先放在取，同reference errorMsgs to errorMsgs list,so everytime
				// change can
				// refer to req's errorMsgs
				Map<String, String[]> map = (Map<String, String[]>) session
						.getAttribute("map");// null
				// req.getParameterMap->immutalbe map->不能修改不能變動(儲存，改變記憶體位置)
				HashMap<String, String[]> map1 = (HashMap<String, String[]>) req
						.getParameterMap();
				// 故要用複製的方式，把一個map1的內容複製到一個可變動性的map，並儲存到session
				HashMap<String, String[]> map2 = new HashMap<String, String[]>();
				map2 = (HashMap<String, String[]>) map1.clone();
				// or HashMap<String, String[]> map2
				// = new HashMap<String, String[]>(map1);

				session.setAttribute("map", map2);
				map = (HashMap<String, String[]>) req.getParameterMap();
				// System.out.println("map: "+map.size());
				/************ 2.開始複合查詢 ******************/
				RoomTypeService roomTypeService = new RoomTypeService();
				List<RoomTypeVO> list = roomTypeService.getAll(map);
				System.out.println("list: " + list);
				if (list.size() == 0) {
					errorMsgs.add("查無資料");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/roomType/roomType_select_page.jsp");
					failureView.forward(req, res);
					return;
				}
				/************ 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("listRoomTypes_ByCompositeQuery", list);
				String url = "/roomType/roomType_CompositeQuery.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("查詢資料出現錯誤 " + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/roomType/roomType_CompositeQuery.jsp");
				failureView.forward(req, res);
			}

		}// list by compositeQuery

	}// doPost
}
