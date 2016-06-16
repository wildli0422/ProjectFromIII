package com.page.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.sql.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
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
import com.hostelNews.model.HostelNewsService;
import com.hostelNews.model.HostelNewsVO;
import com.roomType.model.RoomTypeVO;


@MultipartConfig
public class HostelModifyServlet extends HttpServlet{
	
	public void doGet(HttpServletRequest req,HttpServletResponse res) 
			throws ServletException,IOException{
		doPost(req,res);
	}
	public void doPost(HttpServletRequest req,HttpServletResponse res) 
			throws ServletException,IOException{
		
		req.setCharacterEncoding("UTF-8");
		String action =req.getParameter("action");
		
		if("getOne_For_Display".equals(action)){// �Ӧ�select_page.jsp���ШD
			
			List<String> errorMsgs=new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
				String str =req.getParameter("hostelNo");
				
				if(str==null || (str.trim()).length()==0){
					errorMsgs.add("�п�J���J�s��");
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
					errorMsgs.add("���J�s�����~");
				}
				
				if(!errorMsgs.isEmpty()){
					RequestDispatcher failureView =req.getRequestDispatcher("/hostel/hostel_select_page.jsp");
					failureView.forward(req, res);
					return;
				}
				/***************************2.�}�l�d�߸��*****************************************/
				HostelService hostelService =new HostelService();
				HostelVO hostelVO=hostelService.getOneHostel(hostelNo);
				if(hostelVO==null){
					errorMsgs.add("�d�L���");
				}
				if(!errorMsgs.isEmpty()){
					RequestDispatcher failureView =req.getRequestDispatcher("/hostel/hostel_select_page.jsp");
					failureView.forward(req, res);
					return;
				}
				/***************************3.�d�ߧ���,�ǳ����(Send the Success view)*************/
				req.setAttribute("hostelVO", hostelVO);
				String url="/hostel/hostel_listOne.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
			} catch (Exception e) {
				errorMsgs.add("�L�k���o���"+e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/hostel/hostel_select_page.jsp");
				failureView.forward(req, res);
			}	
		}//if get one display------------------------------------------
		
		if("getOne_For_Update".equals(action)){//from list all and pressed update button
			List<String> errorMsgs=new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.�����ШD�Ѽ�****************************************/
				Integer hostelNo =new Integer(req.getParameter("hostelNo"));
				/***************************2.�}�l�d�߸��****************************************/
				HostelService hostelService =new HostelService();
				HostelVO hostelVO =hostelService.getOneHostel(hostelNo);
				/***************************3.�d�ߧ���,�ǳ����(Send the Success view)************/
				req.setAttribute("hostelVO", hostelVO);
				String url="/hostel/hostel_update.jsp";
				RequestDispatcher successView =req.getRequestDispatcher(url);
				successView.forward(req, res);
				/***************************��L�i�઺���~�B�z**********************************/
			} catch (Exception e) {
				errorMsgs.add("�L�k���o�n�ק諸���"+e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/hostel/hostel_modify.jsp");
				failureView.forward(req, res);
			}
		}//get one for update
		
		if("update".equals(action)){//from update.jsp ->to update 
			List<String> errorMsgs=new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			HostelService hostelServicetmp=new HostelService();
			
			/***************************1.�����ШD�Ѽ�****************************************/
			try {
				Integer hostelNo =new Integer(req.getParameter("hostelNo"));
				
				Integer dealerNo =hostelServicetmp.getOneHostel(hostelNo).getDealerNo();
				
				String hostelName=hostelServicetmp.getOneHostel(hostelNo).getHostelName();
//				try{
//					hostelName=req.getParameter("hostelName").trim();
//				}catch(Exception e){
//					errorMsgs.add("�п�J���J�W��");
//				}

				
				String hostelPhone=null;
				try {
					hostelPhone=req.getParameter("hostelPhone").trim();
					if(hostelPhone.length()==0){
						hostelPhone="0x-0000000";
						errorMsgs.add("�п�J�q�ܸ��X");
					}
				} catch (Exception e) {
					errorMsgs.add("�п�J�q�ܸ��X");
				}
				String hostelAddress =null;
				try {
					hostelAddress =req.getParameter("hostelAddress").trim();
					if(hostelAddress.length()==0){
						hostelAddress="";
						errorMsgs.add("�п�J�a�}");
					}
				} catch (Exception e) {
					errorMsgs.add("�п�J�a�}");
				}
				
				String hostelWebPages =req.getParameter("hostelWebPages").trim();
				
				Integer hostelState =hostelServicetmp.getOneHostel(hostelNo).getHostelState();
				Integer hostelVerification =hostelServicetmp.getOneHostel(hostelNo).getHostelVerification();
//				Integer hostelState=null;
//				try {
//					hostelState =new Integer(req.getParameter("hostelState"));
//					if(hostelState>1 || hostelState<0 || hostelState==null ){
//						errorMsgs.add("�п��0��1");
//					}
//				} catch (Exception e) {
//					hostelState=0;
//					errorMsgs.add("�п�J�W�U�[���A");
//				}
//				Integer hostelVerification=null;
//				try {
//					hostelVerification=new Integer(req.getParameter("hostelVerification"));
//				} catch (Exception e) {
//					hostelVerification=0;
//					errorMsgs.add("�п�J�f�֪��A");
//				}
				
				Double hostelLat=hostelServicetmp.getOneHostel(hostelNo).getHostelLat();
				Double hostelLon=hostelServicetmp.getOneHostel(hostelNo).getHostelLon();
				
//				Double hostelLat=null;
//				try {
//					hostelLat=new Double(req.getParameter("hostelLat"));
//				} catch (Exception e) {
//					hostelLat=0.0;
//					errorMsgs.add("�п�J�g��");
//				}
//				Double hostelLon=null;
//				try {
//					hostelLon=new Double(req.getParameter("hostelLon"));
//				} catch (Exception e) {
//					hostelLon=0.0;
//					errorMsgs.add("�п�J�n��");
//				}
				
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
					errorMsgs.add("�п�ܹϤ�");
				}
				

//				Part dealerVerify=null;
				byte[] dealerVerifyImg=hostelServicetmp.getOneHostel(hostelNo).getDealerVerify();
//				try {
//					dealerVerify=req.getPart("dealerVerify");
//					if(dealerVerify.getSize()==0){
//						
//					}
//					else{
//						//---------------------------------------------------------------
//						InputStream dealerVerifyStream=dealerVerify.getInputStream();
//						dealerVerifyImg=new byte[dealerVerifyStream.available()];
//						dealerVerifyStream.read(dealerVerifyImg);
//						dealerVerifyStream.close(); dealerVerifyStream=null;
//						//---------------------------------------------------------------
//					}
//				} catch (Exception e) {
//					dealerVerify=null;
//					errorMsgs.add("�п�ܹϤ�");
//				}
				
//				Part part1 =req.getPart("hostelPicture");
//				Part part2 =req.getPart("dealerVerify");
				
				String hostelCautions =hostelServicetmp.getOneHostel(hostelNo).getHostelCautions();
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
					RequestDispatcher failureView =req.getRequestDispatcher("/hostel_modify.jsp");
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
//				String url="/hostel/hostel_listOne.jsp";
				String url="/hostel/hostelManager.jsp";
				RequestDispatcher successView =req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView =req.getRequestDispatcher("/hostel/hostelManager.jsp");
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
					errorMsgs.add("�п�J�~�̽s��");
				}
				String hostelName=null;
				try{
					hostelName=req.getParameter("hostelName").trim();
				}catch(Exception e){
					errorMsgs.add("�п�J���J�W��");
				}
				
				String hostelPhone=null;
				try {
					hostelPhone=req.getParameter("hostelPhone").trim();
				} catch (Exception e) {
					errorMsgs.add("�п�J�q�ܸ��X");
				}
				String hostelAddress =null;
				try {
					hostelAddress =req.getParameter("hostelAddress").trim();
				} catch (Exception e) {
					errorMsgs.add("�п�J�a�}");
				}
				
				String hostelWebPages =req.getParameter("hostelWebPages").trim();
				
				Integer hostelState=null;
				try {
					hostelState =new Integer(req.getParameter("hostelState"));
					if(hostelState>1 || hostelState<0 || hostelState==null ){
						errorMsgs.add("�п��0��1");
					}
				} catch (Exception e) {
					hostelState=0;
					errorMsgs.add("�п�J�W�U�[���A");
				}
				Integer hostelVerification=null;
				try {
					hostelVerification=new Integer(req.getParameter("hostelVerification"));
					if(hostelVerification>1 || hostelVerification<0 || hostelVerification==null ){
						errorMsgs.add("�п��0��1");
					}
				} catch (Exception e) {
					hostelVerification=0;
					errorMsgs.add("�п�J�f�֪��A");
				}
				
				Double hostelLat=null;
				try {
					hostelLat=new Double(req.getParameter("hostelLat"));
				} catch (Exception e) {
					hostelLat=0.0;
					errorMsgs.add("�п�J�g��");
				}
				Double hostelLon=null;
				try {
					hostelLon=new Double(req.getParameter("hostelLon"));
				} catch (Exception e) {
					hostelLon=0.0;
					errorMsgs.add("�п�J�n��");
				}
				Part hostelPicture=null;
				try {
					hostelPicture =req.getPart("hostelPicture");
					if(hostelPicture.getSize()==0){
						errorMsgs.add("�п�ܹϤ�");
					}
				} catch (Exception e) {
					hostelPicture=null;
					errorMsgs.add("�п�ܹϤ�");
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
						errorMsgs.add("�п�ܹϤ�");
					}
				} catch (Exception e) {
					dealerVerify=null;
					errorMsgs.add("�п�ܹϤ�");
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
					errorMsgs.add("�п�J�`�N�ƶ�");
				}
				String hostelContent=null;
				try {
					hostelContent =req.getParameter("hostelContent").trim();
				} catch (Exception e) {
					errorMsgs.add("�п�J���J���e");
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
				/***************************2.�}�l�s�W���****************************************/
				HostelService hostelService =new HostelService();
				hostelVO = hostelService.addHostel(dealerNo, hostelName, hostelPhone, hostelAddress, hostelWebPages, hostelState, hostelVerification, hostelLat, hostelLon, hostelPictureImg, dealerVerifyImg, hostelCautions, hostelContent);
				/***************************3.�s�W����,�ǳ����(Send the Success view)************/
				String url="/hostel/hostel_listAll.jsp";
				RequestDispatcher successView =req.getRequestDispatcher(url);
				successView.forward(req, res);
				
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView =req.getRequestDispatcher("/hostel/hostel_Add.jsp");
				failureView.forward(req, res);			
			}
		}//insert
		
		
		if("delete".equals(action)){
			List<String> errorMsgs =new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			/***************************1.�����ШD�Ѽ�****************************************/
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
				errorMsgs.add("�R������"+e.getMessage());
				RequestDispatcher failureView =req.getRequestDispatcher("/hostel/hostel_listAll.jsp");
				failureView.forward(req, res);
			}
		}//delete
		
		// �Ӧ�roomType_select_page.jsp���ШD                                  // �Ӧ� dept/listAllDept.jsp���ШD
		if("listRoomTypes_ByHostelNo_A".equals(action) || "listRoomTypes_ByHostelNo_B".equals(action)){
			List<String> erroMsgs=new LinkedList<String>();
			req.setAttribute("errorMsgs", erroMsgs);
			
			try {
				/*************************** 1.�����ШD�Ѽ� ****************************************/
				Integer hostelNo =new Integer(req.getParameter("hostelNo"));
				/*************************** 2.�}�l�d�߸�� ****************************************/
				HostelService hostelService =new HostelService();
				Set<RoomTypeVO> set=hostelService.getRoomTypesByHostelNo(hostelNo);
				
				/*************************** 3.�d�ߧ���,�ǳ����(Send the Success view) ************/
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
		
		if("deleteNews".equals(action)){
			try{
				Integer hostelNewsNo=new Integer(req.getParameter("hostelNewsNo"));
				
				
				HostelNewsService hostelNewsService=new HostelNewsService();
				hostelNewsService.deleteHostelNews(hostelNewsNo);
				
				String url="/hostel/hostel_modify.jsp";
				RequestDispatcher successView=req.getRequestDispatcher(url);
				successView.forward(req, res);
			}catch(Exception e){
				e.getMessage();
			}
		}
		
		if("insertNews".equals(action)){
			try {
				/********1*************/
				Integer hostelNo=new Integer(req.getParameter("hostelNo"));
				String newsContent =req.getParameter("newsContent");
				/*********2************/
				HostelNewsService hostelNewsService =new HostelNewsService();
				hostelNewsService.addHostelNews(hostelNo, newsContent);
				
				String url="/hostel/hostel_modify.jsp";
				RequestDispatcher successView =req.getRequestDispatcher(url);
				successView.forward(req, res);
				
			} catch (Exception e) {
				e.getMessage();
			}
		}
		
	}//doPost
}

