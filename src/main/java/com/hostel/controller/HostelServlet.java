package com.hostel.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.hostel.model.HostelService;
import com.hostel.model.HostelVO;
import com.roomType.model.RoomTypeVO;

@WebServlet("/hostel.do")
@MultipartConfig
public class HostelServlet extends HttpServlet{
	
	public void doGet(HttpServletRequest req,HttpServletResponse res) 
			throws ServletException,IOException{
		doPost(req,res);
	}
	public void doPost(HttpServletRequest req,HttpServletResponse res) 
			throws ServletException,IOException{
		
		req.setCharacterEncoding("UTF-8");
		String action =req.getParameter("action");
		
		if("getOne_For_Display".equals(action)){// 來自select_page.jsp的請求
			
			List<String> errorMsgs=new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String str =req.getParameter("hostelNo");
				
				if(str==null || (str.trim()).length()==0){
					errorMsgs.add("請輸入民宿編號");
				}
				if(!errorMsgs.isEmpty()){
					RequestDispatcher failureView =req.getRequestDispatcher("/hostel/hostel_select_page.jsp");
					failureView.forward(req, res);
					return;
				}
				
				Integer hostelNo=null;
				try {
					hostelNo=new Integer(str);
				} catch (Exception e) {
					errorMsgs.add("民宿編號錯誤");
				}
				
				if(!errorMsgs.isEmpty()){
					RequestDispatcher failureView =req.getRequestDispatcher("/hostel/hostel_select_page.jsp");
					failureView.forward(req, res);
					return;
				}
				/***************************2.開始查詢資料*****************************************/
				HostelService hostelService =new HostelService();
				HostelVO hostelVO=hostelService.getOneHostel(hostelNo);
				if(hostelVO==null){
					errorMsgs.add("查無資料");
				}
				if(!errorMsgs.isEmpty()){
					RequestDispatcher failureView =req.getRequestDispatcher("/hostel/hostel_select_page.jsp");
					failureView.forward(req, res);
					return;
				}
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("hostelVO", hostelVO);
				String url="/hostel/hostel_listOne.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
			} catch (Exception e) {
				errorMsgs.add("無法取得資料"+e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/hostel/hostel_select_page.jsp");
				failureView.forward(req, res);
			}	
		}//if get one display------------------------------------------
		
		if("getOne_For_Update".equals(action)){//from list all and pressed update button
			List<String> errorMsgs=new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數****************************************/
				Integer hostelNo =new Integer(req.getParameter("hostelNo"));
				/***************************2.開始查詢資料****************************************/
				HostelService hostelService =new HostelService();
				HostelVO hostelVO =hostelService.getOneHostel(hostelNo);
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("hostelVO", hostelVO);
				String url="/hostel/hostel_update.jsp";
				RequestDispatcher successView =req.getRequestDispatcher(url);
				successView.forward(req, res);
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料"+e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/hostel/hostel_listAll.jsp");
				failureView.forward(req, res);
			}
		}//get one for update
		
		if("update".equals(action)){//from update.jsp ->to update 
			List<String> errorMsgs=new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			HostelService hostelServicetmp=new HostelService();
			
			/***************************1.接收請求參數****************************************/
			try {
				Integer hostelNo =new Integer(req.getParameter("hostelNo"));
				
				Integer dealerNo =null;
				try {
					dealerNo =new Integer(req.getParameter("dealerNo"));
				} catch (Exception e) {
					errorMsgs.add("請輸入業者編號");
				}
				String hostelName=null;
				try{
					hostelName=req.getParameter("hostelName").trim();
				}catch(Exception e){
					errorMsgs.add("請輸入民宿名稱");
				}
				
				String hostelPhone=null;
				try {
					hostelPhone=req.getParameter("hostelPhone").trim();
				} catch (Exception e) {
					errorMsgs.add("請輸入電話號碼");
				}
				String hostelAddress =null;
				try {
					hostelAddress =req.getParameter("hostelAddress").trim();
				} catch (Exception e) {
					errorMsgs.add("請輸入地址");
				}
				
				String hostelWebPages =req.getParameter("hostelWebPages").trim();
				
				Integer hostelState=null;
				try {
					hostelState =new Integer(req.getParameter("hostelState"));
					if(hostelState>1 || hostelState<0 || hostelState==null ){
						errorMsgs.add("請選擇0或1");
					}
				} catch (Exception e) {
					hostelState=0;
					errorMsgs.add("請輸入上下架狀態");
				}
				Integer hostelVerification=null;
				try {
					hostelVerification=new Integer(req.getParameter("hostelVerification"));
				} catch (Exception e) {
					hostelVerification=0;
					errorMsgs.add("請輸入審核狀態");
				}
				
				Double hostelLat=null;
				try {
					hostelLat=new Double(req.getParameter("hostelLat"));
				} catch (Exception e) {
					hostelLat=0.0;
					errorMsgs.add("請輸入經度");
				}
				Double hostelLon=null;
				try {
					hostelLon=new Double(req.getParameter("hostelLon"));
				} catch (Exception e) {
					hostelLon=0.0;
					errorMsgs.add("請輸入緯度");
				}
				
				Part hostelPicture=null;
				byte[] hostelPictureImg=null;
				try {
					hostelPicture =req.getPart("hostelPicture");
					if(hostelPicture.getSize()==0){
						hostelPictureImg=hostelServicetmp.getOneHostel(hostelNo).getHostelPicture();
					}else{
						//---------------------------------------------------------------
						InputStream hostelPictureStream=hostelPicture.getInputStream();
						hostelPictureImg=new byte[hostelPictureStream.available()];
						hostelPictureStream.read(hostelPictureImg);
						hostelPictureStream.close(); hostelPictureStream=null;
						//---------------------------------------------------------------
					}
				} catch (Exception e) {
					hostelPicture=null;
					errorMsgs.add("請選擇圖片");
				}
				

				Part dealerVerify=null;
				byte[] dealerVerifyImg=null;
				try {
					dealerVerify=req.getPart("dealerVerify");
					if(dealerVerify.getSize()==0){
						dealerVerifyImg=hostelServicetmp.getOneHostel(hostelNo).getDealerVerify();
					}
					else{
						//---------------------------------------------------------------
						InputStream dealerVerifyStream=dealerVerify.getInputStream();
						dealerVerifyImg=new byte[dealerVerifyStream.available()];
						dealerVerifyStream.read(dealerVerifyImg);
						dealerVerifyStream.close(); dealerVerifyStream=null;
						//---------------------------------------------------------------
					}
				} catch (Exception e) {
					dealerVerify=null;
					errorMsgs.add("請選擇圖片");
				}
				
//				Part part1 =req.getPart("hostelPicture");
//				Part part2 =req.getPart("dealerVerify");
				
				String hostelCautions =null;
				try {
					hostelCautions =req.getParameter("hostelCautions").trim();
				} catch (Exception e) {
					errorMsgs.add("請輸入注意事項");
				}
				String hostelContent=null;
				try {
					hostelContent =req.getParameter("hostelContent").trim();
				} catch (Exception e) {
					errorMsgs.add("請輸入民宿內容");
				}
				
				HostelVO hostelVO =new HostelVO();
				hostelVO.setHostelNo(hostelNo);
				hostelVO.setDealerNo(dealerNo);
				hostelVO.setHostelName(hostelName);
				hostelVO.setHostelPhone(hostelPhone);
				hostelVO.setHostelAddress(hostelAddress);
				hostelVO.setHostelWebPages(hostelWebPages);
				hostelVO.setHostelState(hostelState);
				hostelVO.setHostelVerification(hostelVerification);
				hostelVO.setHostelLat(hostelLat);
				hostelVO.setHostelLon(hostelLon);
				hostelVO.setHostelPicture(hostelPictureImg);
				hostelVO.setDealerVerify(dealerVerifyImg);
				hostelVO.setHostelCautions(hostelCautions);
				hostelVO.setHostelContent(hostelContent);
				
				if(!errorMsgs.isEmpty()){
					req.setAttribute("hostelVO", hostelVO);
					RequestDispatcher failureView =req.getRequestDispatcher("/hostel/hostel_update.jsp");
					failureView.forward(req, res);
					return;
				}
				/***************************2.開始修改資料****************************************/
				HostelService hostelService =new HostelService();
				hostelVO = hostelService.updateHostel(hostelNo, dealerNo, hostelName, hostelPhone,
						hostelAddress, hostelWebPages, hostelState, hostelVerification, hostelLat,
						hostelLon, hostelPictureImg, dealerVerifyImg, hostelCautions, hostelContent);
				/***************************3.修改完成,準備轉交(Send the Success view)************/
				req.setAttribute("hostelVO", hostelVO);
				String url="/hostel/hostel_listOne.jsp";
				RequestDispatcher successView =req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("修改資料錯誤"+e.getMessage());
				RequestDispatcher failureView =req.getRequestDispatcher("/hostel/hostel_update.jsp");
				failureView.forward(req, res);			
			}
		}//update 
		
		if("insert".equals(action)){//from dealer_add
			List<String> errorMsgs =new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			//reference errorMsgs to errorMsgs list,so everytime change can
			//refer to req's errorMsgs
			try {
		
				Integer dealerNo =null;
				try {
					dealerNo=new Integer(req.getParameter("dealerNo"));
				} catch (Exception e) {
					errorMsgs.add("請輸入業者編號");
				}
				String hostelName=null;
				try{
					hostelName=req.getParameter("hostelName").trim();
				}catch(Exception e){
					errorMsgs.add("請輸入民宿名稱");
				}
				
				String hostelPhone=null;
				try {
					hostelPhone=req.getParameter("hostelPhone").trim();
				} catch (Exception e) {
					errorMsgs.add("請輸入電話號碼");
				}
				String hostelAddress =null;
				try {
					hostelAddress =req.getParameter("hostelAddress").trim();
				} catch (Exception e) {
					errorMsgs.add("請輸入地址");
				}
				
				String hostelWebPages =req.getParameter("hostelWebPages").trim();
				
				Integer hostelState=null;
				try {
					hostelState =new Integer(req.getParameter("hostelState"));
					if(hostelState>1 || hostelState<0 || hostelState==null ){
						errorMsgs.add("請選擇0或1");
					}
				} catch (Exception e) {
					hostelState=0;
					errorMsgs.add("請輸入上下架狀態");
				}
				Integer hostelVerification=null;
				try {
					hostelVerification=new Integer(req.getParameter("hostelVerification"));
					if(hostelVerification>1 || hostelVerification<0 || hostelVerification==null ){
						errorMsgs.add("請選擇0或1");
					}
				} catch (Exception e) {
					hostelVerification=0;
					errorMsgs.add("請輸入審核狀態");
				}
				
				Double hostelLat=null;
				try {
					hostelLat=new Double(req.getParameter("hostelLat"));
				} catch (Exception e) {
					hostelLat=0.0;
					errorMsgs.add("請輸入經度");
				}
				Double hostelLon=null;
				try {
					hostelLon=new Double(req.getParameter("hostelLon"));
				} catch (Exception e) {
					hostelLon=0.0;
					errorMsgs.add("請輸入緯度");
				}
				Part hostelPicture=null;
				try {
					hostelPicture =req.getPart("hostelPicture");
					if(hostelPicture.getSize()==0){
						errorMsgs.add("請選擇圖片");
					}
				} catch (Exception e) {
					hostelPicture=null;
					errorMsgs.add("請選擇圖片");
				}
				//---------------------------------------------------------------
				InputStream hostelPictureStream=hostelPicture.getInputStream();
				byte[] hostelPictureImg=new byte[hostelPictureStream.available()];
				hostelPictureStream.read(hostelPictureImg);
				hostelPictureStream.close(); hostelPictureStream=null;
				//---------------------------------------------------------------

				Part dealerVerify=null;
				try {
					dealerVerify=req.getPart("dealerVerify");
					if(dealerVerify.getSize()==0){
						errorMsgs.add("請選擇圖片");
					}
				} catch (Exception e) {
					dealerVerify=null;
					errorMsgs.add("請選擇圖片");
				}
				//---------------------------------------------------------------
				InputStream dealerVerifyStream=dealerVerify.getInputStream();
				byte[] dealerVerifyImg=new byte[dealerVerifyStream.available()];
				dealerVerifyStream.read(dealerVerifyImg);
				dealerVerifyStream.close(); dealerVerifyStream=null;
				//---------------------------------------------------------------

//				Part part1 =req.getPart("hostelPicture");
//				Part part2 =req.getPart("dealerVerify");
				
				String hostelCautions =null;
				try {
					hostelCautions =req.getParameter("hostelCautions").trim();
				} catch (Exception e) {
					errorMsgs.add("請輸入注意事項");
				}
				String hostelContent=null;
				try {
					hostelContent =req.getParameter("hostelContent").trim();
				} catch (Exception e) {
					errorMsgs.add("請輸入民宿內容");
				}
				
				
				HostelVO hostelVO =new HostelVO();
				hostelVO.setDealerNo(dealerNo);
				hostelVO.setHostelName(hostelName);
				hostelVO.setHostelPhone(hostelPhone);
				hostelVO.setHostelAddress(hostelAddress);
				hostelVO.setHostelWebPages(hostelWebPages);
				hostelVO.setHostelState(hostelState);
				hostelVO.setHostelVerification(hostelVerification);
				hostelVO.setHostelLat(hostelLat);
				hostelVO.setHostelLon(hostelLon);
				hostelVO.setHostelPicture(hostelPictureImg);
				hostelVO.setDealerVerify(dealerVerifyImg);
				hostelVO.setHostelCautions(hostelCautions);
				hostelVO.setHostelContent(hostelContent);
				
				if(!errorMsgs.isEmpty()){
					req.setAttribute("hostelVO", hostelVO);
					RequestDispatcher failureView =req.getRequestDispatcher("/hostel/hostel_Add.jsp");
					failureView.forward(req, res);
					return;
				}
				/***************************2.開始新增資料****************************************/
				HostelService hostelService =new HostelService();
				hostelVO = hostelService.addHostel(dealerNo, hostelName, hostelPhone, hostelAddress, hostelWebPages, hostelState, hostelVerification, hostelLat, hostelLon, hostelPictureImg, dealerVerifyImg, hostelCautions, hostelContent);
				/***************************3.新增完成,準備轉交(Send the Success view)************/
				String url="/hostel/hostel_listAll.jsp";
				RequestDispatcher successView =req.getRequestDispatcher(url);
				successView.forward(req, res);
				
			} catch (Exception e) {
				errorMsgs.add("新增資料錯誤"+e.getMessage());
				RequestDispatcher failureView =req.getRequestDispatcher("/hostel/hostel_Add.jsp");
				failureView.forward(req, res);			
			}
		}//insert
		
		
		if("delete".equals(action)){
			List<String> errorMsgs =new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			/***************************1.接收請求參數****************************************/
			try {
				Integer hostelNo =new Integer(req.getParameter("hostelNo"));
				/**delete data**/
				HostelService hostelService =new HostelService();
				hostelService.deleteHostel(hostelNo);
				/**after delete**/
				String url="/hostel/hostel_listAll.jsp";
				RequestDispatcher successView =req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("刪除失敗"+e.getMessage());
				RequestDispatcher failureView =req.getRequestDispatcher("/hostel/hostel_listAll.jsp");
				failureView.forward(req, res);
			}
		}//delete
		
		// 來自roomType_select_page.jsp的請求                                  // 來自 dept/listAllDept.jsp的請求
				if("listRoomTypes_ByHostelNo_A".equals(action) || "listRoomTypes_ByHostelNo_B".equals(action)){
					List<String> erroMsgs=new LinkedList<String>();
					req.setAttribute("errorMsgs", erroMsgs);
					
					try {
						/*************************** 1.接收請求參數 ****************************************/
						Integer hostelNo =new Integer(req.getParameter("hostelNo"));
						/*************************** 2.開始查詢資料 ****************************************/
						HostelService hostelService =new HostelService();
						Set<RoomTypeVO> set=hostelService.getRoomTypesByHostelNo(hostelNo);
						
						/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
						req.setAttribute("listRoomTypes_ByHostelNo", set);
						
						String url=null;
						if("listRoomTypes_ByHostelNo_A".equals(action))
							url="/hostel/hostel_listRoomTypes.jsp";
						else if("listRoomTypes_ByHostelNo_B".equals(action))
							url="/hostel/hostel_listHostelsForRoomTypes.jsp";
						RequestDispatcher successView =req.getRequestDispatcher(url);
						successView.forward(req, res);
					} catch (Exception e) {
						throw new ServletException(e);
					}
				}//list roomTypes by hostel
				

				///backend///
				
				if("update".equals(action)){//from update.jsp ->to update 
					List<String> errorMsgs=new LinkedList<String>();
					req.setAttribute("errorMsgs", errorMsgs);
					HostelService hostelServicetmp=new HostelService();
					String url=req.getParameter("requestURL");

					/***************************1.�����ШD�Ѽ�****************************************/
					try {
						Integer hostelNo =new Integer(req.getParameter("hostelNo"));
						Integer dealerNo =null;
						try {
							dealerNo =new Integer(req.getParameter("dealerNo"));
						} catch (Exception e) {
							errorMsgs.add("請輸入業者編號");
						}
						String hostelName=null;
						try{
							hostelName=req.getParameter("hostelName").trim();
						}catch(Exception e){
							errorMsgs.add("請輸入民宿名稱");
						}
						
						String hostelPhone=null;
						try {
							hostelPhone=req.getParameter("hostelPhone").trim();
						} catch (Exception e) {
							errorMsgs.add("請輸入電話號碼");
						}
						String hostelAddress =null;
						try {
							hostelAddress =req.getParameter("hostelAddress").trim();
						} catch (Exception e) {
							errorMsgs.add("請輸入地址");
						}
						
						String hostelWebPages =req.getParameter("hostelWebPages").trim();
						
						Integer hostelState=null;
						try {
							hostelState =new Integer(req.getParameter("hostelState"));
							if(hostelState>1 || hostelState<0 || hostelState==null ){
								errorMsgs.add("請選擇0或1");
							}
						} catch (Exception e) {
							hostelState=0;
							errorMsgs.add("請輸入上下架狀態");
						}
						Integer hostelVerification=null;
						try {
							hostelVerification=new Integer(req.getParameter("hostelVerification"));
						} catch (Exception e) {
							hostelVerification=0;
							errorMsgs.add("請輸入審核狀態");
						}
						Double hostelLat=null;
						try {
							hostelLat=new Double(req.getParameter("hostelLat"));
						} catch (Exception e) {
							hostelLat=0.0;
							errorMsgs.add("請輸入經度");
						}
						Double hostelLon=null;
						try {
							hostelLon=new Double(req.getParameter("hostelLon"));
						} catch (Exception e) {
							hostelLon=0.0;
							errorMsgs.add("請輸入緯度");
						}
						Part hostelPicture=null;
						byte[] hostelPictureImg=null;
						try {
							hostelPicture =req.getPart("hostelPicture");
							if(hostelPicture.getSize()==0){
								hostelPictureImg=hostelServicetmp.getOneHostel(hostelNo).getHostelPicture();
							}else{
								//---------------------------------------------------------------
								InputStream hostelPictureStream=hostelPicture.getInputStream();
								hostelPictureImg=new byte[hostelPictureStream.available()];
								hostelPictureStream.read(hostelPictureImg);
								hostelPictureStream.close(); hostelPictureStream=null;
								//---------------------------------------------------------------
							}
						} catch (Exception e) {
							hostelPicture=null;
							errorMsgs.add("請選擇圖片");
							
						}
						
						Part dealerVerify=null;
						byte[] dealerVerifyImg=null;
						try {
							dealerVerify=req.getPart("dealerVerify");
							if(dealerVerify.getSize()==0){
								dealerVerifyImg=hostelServicetmp.getOneHostel(hostelNo).getDealerVerify();
							}
							else{
								//---------------------------------------------------------------
								InputStream dealerVerifyStream=dealerVerify.getInputStream();
								dealerVerifyImg=new byte[dealerVerifyStream.available()];
								dealerVerifyStream.read(dealerVerifyImg);
								dealerVerifyStream.close(); dealerVerifyStream=null;
								//---------------------------------------------------------------
							}
						} catch (Exception e) {
							dealerVerify=null;
							errorMsgs.add("請選擇圖片");

						}
//						Part part1 =req.getPart("hostelPicture");
//						Part part2 =req.getPart("dealerVerify");
						
						String hostelCautions =null;
						try {
							hostelCautions =req.getParameter("hostelCautions").trim();
						} catch (Exception e) {
							errorMsgs.add("請輸入注意事項");
						}
						String hostelContent=null;
						try {
							hostelContent =req.getParameter("hostelContent").trim();
						} catch (Exception e) {
							errorMsgs.add("�п�J���J���e");
						}
						HostelVO hostelVO =new HostelVO();
						hostelVO.setHostelNo(hostelNo);
						hostelVO.setDealerNo(dealerNo);
						hostelVO.setHostelName(hostelName);
						hostelVO.setHostelPhone(hostelPhone);
						hostelVO.setHostelAddress(hostelAddress);
						hostelVO.setHostelWebPages(hostelWebPages);
						hostelVO.setHostelState(hostelState);
						hostelVO.setHostelVerification(hostelVerification);
						hostelVO.setHostelLat(hostelLat);
						hostelVO.setHostelLon(hostelLon);
						hostelVO.setHostelPicture(hostelPictureImg);
						hostelVO.setDealerVerify(dealerVerifyImg);
						hostelVO.setHostelCautions(hostelCautions);
						hostelVO.setHostelContent(hostelContent);
						if(!errorMsgs.isEmpty()){
							req.setAttribute("hostelVO", hostelVO);
							RequestDispatcher failureView =req.getRequestDispatcher("/hostel/hostel_update.jsp");
							failureView.forward(req, res);
							return;
						}
						/***************************2.�}�l�ק���****************************************/
						HostelService hostelService =new HostelService();
						hostelVO = hostelService.updateHostel(hostelNo, dealerNo, hostelName, hostelPhone,
								hostelAddress, hostelWebPages, hostelState, hostelVerification, hostelLat,
								hostelLon, hostelPictureImg, dealerVerifyImg, hostelCautions, hostelContent);
						/***************************3.�ק粒��,�ǳ����(Send the Success view)************/
						req.setAttribute("hostelVO", hostelVO);
						RequestDispatcher successView =req.getRequestDispatcher(url);
						successView.forward(req, res);
					} catch (Exception e) {
						errorMsgs.add("�ק��ƿ��~"+e.getMessage());
						
						RequestDispatcher failureView =req.getRequestDispatcher(url);
						failureView.forward(req, res);			
					}
				}
				
				if("delete".equals(action)){
					List<String> errorMsgs =new LinkedList<String>();
					req.setAttribute("errorMsgs", errorMsgs);
					String url=req.getParameter("requestURL");
					/***************************1.�����ШD�Ѽ�****************************************/
					try {
						Integer hostelNo =new Integer(req.getParameter("hostelNo"));
						/**delete data**/
						HostelService hostelService =new HostelService();
						
						hostelService.deleteHostel(hostelNo);
						/**after delete**/
						System.out.println("delete");
						
						RequestDispatcher successView =req.getRequestDispatcher(url);
						successView.forward(req, res);
					} catch (Exception e) {
						errorMsgs.add("�R������"+e.getMessage());
						RequestDispatcher failureView =req.getRequestDispatcher(url);
						failureView.forward(req, res);
					}
				}//delete
				
				if("updateBK".equals(action)){
					List<String> errorMsgs=new LinkedList<String>();
					req.setAttribute("errorMsgs", errorMsgs);
					try {
						Integer hostelNo =new Integer(req.getParameter("hostelNo"));
						Integer dealerNo =new Integer(req.getParameter("dealerNo"));
						String hostelName=null;
						try{
							hostelName=req.getParameter("hostelName").trim();
						}catch(Exception e){
							errorMsgs.add("請輸入民宿名稱");
						}
						
						String hostelPhone=null;
						try {
							hostelPhone=req.getParameter("hostelPhone").trim();
						} catch (Exception e) {
							errorMsgs.add("請輸入電話號碼");
						}
						String hostelAddress =null;
						try {
							hostelAddress =req.getParameter("hostelAddress").trim();
						} catch (Exception e) {
							errorMsgs.add("請輸入地址");
						}
						
						String hostelWebPages =req.getParameter("hostelWebPages").trim();
						
						Integer hostelState=null;
						try {
							hostelState =new Integer(req.getParameter("hostelState"));
							if(hostelState>1 || hostelState<0 || hostelState==null ){
								errorMsgs.add("請選擇0或1");
							}
						} catch (Exception e) {
							hostelState=0;
							errorMsgs.add("請輸入上下架狀態");
						}
						Integer hostelVerification=null;
						try {
							hostelVerification=new Integer(req.getParameter("hostelVerification"));
						} catch (Exception e) {
							hostelVerification=0;
							errorMsgs.add("請輸入審核狀態");
						}
						Double hostelLat=null;
						try {
							hostelLat=new Double(req.getParameter("hostelLat"));
						} catch (Exception e) {
							hostelLat=0.0;
							errorMsgs.add("請輸入經度");
						}
						Double hostelLon=null;
						try {
							hostelLon=new Double(req.getParameter("hostelLon"));
						} catch (Exception e) {
							hostelLon=0.0;
							errorMsgs.add("請輸入緯度");
						}
										
						String hostelCautions =null;
						try {
							hostelCautions =req.getParameter("hostelCautions").trim();
						} catch (Exception e) {
							errorMsgs.add("請輸入注意事項");
						}
						String hostelContent=null;
						try {
							hostelContent =req.getParameter("hostelContent").trim();
						} catch (Exception e) {
							errorMsgs.add("�п�J���J���e");
						}
						HostelVO hostelVO =new HostelVO();
						hostelVO.setHostelNo(hostelNo);
						hostelVO.setHostelName(hostelName);
						hostelVO.setHostelPhone(hostelPhone);
						hostelVO.setHostelAddress(hostelAddress);
						hostelVO.setHostelWebPages(hostelWebPages);
						hostelVO.setHostelState(hostelState);
						hostelVO.setHostelVerification(hostelVerification);
						hostelVO.setHostelLat(hostelLat);
						hostelVO.setHostelLon(hostelLon);
						hostelVO.setHostelCautions(hostelCautions);
						hostelVO.setHostelContent(hostelContent);
						if(!errorMsgs.isEmpty()){
							req.setAttribute("hostelVO", hostelVO);
							RequestDispatcher failureView =req.getRequestDispatcher("/hostel/hostel_update.jsp");
							failureView.forward(req, res);
							return;
						}
						HostelService hostelService =new HostelService();
						hostelVO = hostelService.updateHostel_bk(hostelNo, hostelName, hostelPhone,
								hostelAddress, hostelWebPages, hostelState, hostelVerification, hostelLat,
								hostelLon, hostelCautions, hostelContent);

						req.setAttribute("hostelVO", hostelVO);
						req.setAttribute("dealerNo", dealerNo);
						String url=req.getParameter("requestURL");
						RequestDispatcher successView =req.getRequestDispatcher(url);
						successView.forward(req, res);
					} catch (Exception e) {
						errorMsgs.add("�ק��ƿ��~"+e.getMessage());
						
						RequestDispatcher failureView =req.getRequestDispatcher("/hostel/hostel_update.jsp");
						failureView.forward(req, res);			
					}
				}
				if("updateImgBK".equals(action)){
					List<String> errorMsgs=new LinkedList<String>();
					req.setAttribute("errorMsgs", errorMsgs);
					HostelService hostelServicetmp=new HostelService();
					
					try {
						Integer hostelNo =new Integer(req.getParameter("hostelNo"));
						Integer dealerNo =null;
						try {
							dealerNo =new Integer(req.getParameter("dealerNo"));
						} catch (Exception e) {
							errorMsgs.add("請輸入業者編號");
						}
						
						Part hostelPicture=null;
						byte[] hostelPictureImg=null;
						try {
							hostelPicture =req.getPart("hostelPicture");
							if(hostelPicture.getSize()==0){
								hostelPictureImg=hostelServicetmp.getOneHostel(hostelNo).getHostelPicture();
							}else{
								//---------------------------------------------------------------
								InputStream hostelPictureStream=hostelPicture.getInputStream();
								hostelPictureImg=new byte[hostelPictureStream.available()];
								hostelPictureStream.read(hostelPictureImg);
								hostelPictureStream.close(); hostelPictureStream=null;
								//---------------------------------------------------------------
							}
						} catch (Exception e) {
							hostelPicture=null;
							errorMsgs.add("請選擇圖片");
							
						}
						
						Part dealerVerify=null;
						byte[] dealerVerifyImg=null;
						try {
							dealerVerify=req.getPart("dealerVerify");
							if(dealerVerify.getSize()==0){
								dealerVerifyImg=hostelServicetmp.getOneHostel(hostelNo).getDealerVerify();
							}
							else{
								//---------------------------------------------------------------
								InputStream dealerVerifyStream=dealerVerify.getInputStream();
								dealerVerifyImg=new byte[dealerVerifyStream.available()];
								dealerVerifyStream.read(dealerVerifyImg);
								dealerVerifyStream.close(); dealerVerifyStream=null;
								//---------------------------------------------------------------
							}
						} catch (Exception e) {
							dealerVerify=null;
							errorMsgs.add("請選擇圖片");

						}
						
						HostelVO hostelVO =new HostelVO();
						hostelVO.setHostelNo(hostelNo);
						hostelVO.setDealerNo(dealerNo);
						hostelVO.setHostelPicture(hostelPictureImg);
						hostelVO.setDealerVerify(dealerVerifyImg);
						if(!errorMsgs.isEmpty()){
							req.setAttribute("hostelVO", hostelVO);
							RequestDispatcher failureView =req.getRequestDispatcher("/hostel/hostel_update.jsp");
							failureView.forward(req, res);
							return;
						}

						HostelService hostelService =new HostelService();
						hostelVO = hostelService.updateHostel_Img(hostelNo, dealerNo, hostelPictureImg, dealerVerifyImg);

						req.setAttribute("hostelVO", hostelVO);
						req.setAttribute("dealerNO", dealerNo);
						String url=req.getParameter("requestURL");
						RequestDispatcher successView =req.getRequestDispatcher(url);
						successView.forward(req, res);
					} catch (Exception e) {
						errorMsgs.add("�ק��ƿ��~"+e.getMessage());
						
						RequestDispatcher failureView =req.getRequestDispatcher("/hostel/hostel_update.jsp");
						failureView.forward(req, res);			
					}
				}//update 
				
				if ("getlistByCQbk".equals(action)) {
					List<String> errorMsgs = new LinkedList<String>();
					req.setAttribute("errorMsgs", errorMsgs);

					try {
						Map<String, String[]> map = req.getParameterMap();
						HostelService hostelService = new HostelService();
						List<HostelVO> list = hostelService.getAllbk(map);

						for (int i = 0; i < list.size(); i++) {
							for (int j = i + 1; j < list.size(); j++) {
								if (list.get(j).getDealerNo().equals(list.get(i).getDealerNo())) {
									list.remove(j);
									j--;
								}
							}
						}
						
						
						
						
						req.setAttribute("hostellist", list);
						RequestDispatcher successView = req.getRequestDispatcher("/backEnd/user/hostel.jsp"); 
						successView.forward(req, res);

					} catch (Exception e) {
						errorMsgs.add(e.getMessage());
						RequestDispatcher failureView = req.getRequestDispatcher("/backEnd/user/hostel.jsp");
						failureView.forward(req, res);
					}
				}
		
	}//doPost
	
	
	
}
