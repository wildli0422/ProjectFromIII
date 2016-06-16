package com.dealer.controller;

import java.io.IOException;
import java.io.InputStream;
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
import javax.websocket.Session;

import com.dealer.model.DealerService;
import com.dealer.model.DealerVO;
import com.hostel.model.HostelService;
import com.hostel.model.HostelVO;

@WebServlet("/dealer.reg")
@MultipartConfig
public class DRegiServlet extends HttpServlet{
	
	public void doGet(HttpServletRequest req,HttpServletResponse res) 
			throws ServletException,IOException{
		doPost(req, res);
	}
	//doGet----------------------------------------------
	
	public void doPost(HttpServletRequest req,HttpServletResponse res) 
			throws ServletException,IOException{
		
		req.setCharacterEncoding("UTF-8");
		String action =req.getParameter("action");
		
		
		if("dealer".equals(action)){
			DealerService dealerService =new DealerService();
			List<String> errorMsgs =new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			System.out.println("in");
			try{
				
				String dealerName =req.getParameter("dealerName").trim();
				if (dealerName == null || dealerName.length() == 0) {
					dealerName="";
					errorMsgs.add("請輸入姓名");
				}
				String dealerPassword=null;
				
				dealerPassword =req.getParameter("dealerPassword").trim();
				if (dealerPassword == null || dealerPassword.length() == 0) {
					dealerPassword="";
					errorMsgs.add("請輸入密碼");
				}
			
				
				String dealerSex = req.getParameter("dealerSex").trim();
				
				
				String dealerPhone = null;
				dealerPhone = req.getParameter("dealerPhone").trim();
				if (dealerPhone == null || dealerPhone.length() == 0) {
					dealerPhone="";
					errorMsgs.add("請輸入電話");
				}

				String dealerAddress =null;
				dealerAddress = req.getParameter("dealerAddress").trim();
				if (dealerAddress == null || dealerAddress.length() == 0) {
					dealerAddress="";
					errorMsgs.add("請輸入地址");
				}
				
				String dealerMail =null;
				dealerMail = req.getParameter("dealerMail").trim();
				if (dealerMail == null || dealerMail.length() == 0) {
					dealerMail="";
					errorMsgs.add("請輸入電郵地址");
				}
				try {
					Map<String, String[]> check =  new TreeMap<String, String[]>();
					String[] mail= {dealerMail};
					check.put("dealerMail", mail);
					List<DealerVO> d = dealerService.getAll(check);
					if (dealerMail.equals(d.get(0).getDealerMail()) ) {
						
						errorMsgs.add("此信箱已被使用");
					}
				} catch (Exception e2) {
				}
				
				
				Integer dealerState = 0;
				String dealerAccount =null;	
				dealerAccount=req.getParameter("dealerAccount").trim();
				if (dealerAccount == null || dealerAccount.length() == 0) {
					dealerAccount="";
					errorMsgs.add("請輸入金流帳戶");
				}
				
				DealerVO dealerVO =new DealerVO();
				dealerVO.setDealerName(dealerPassword);
				dealerVO.setDealerPassword(dealerPassword);
				dealerVO.setDealerSex(dealerSex);
				dealerVO.setDealerAddress(dealerAddress);
				dealerVO.setDealerMail(dealerMail);
				dealerVO.setDealerState(dealerState);
				dealerVO.setDealerPhone(dealerPhone);
				dealerVO.setDealerAccount(dealerAccount);
				if(!errorMsgs.isEmpty()){
					req.setAttribute("dealerVO", dealerVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/user/regisDealer.jsp");
					failureView.forward(req, res);
					return;
				}
				
				
				
				dealerVO=dealerService.addDealer(dealerPassword, dealerPassword, dealerSex, dealerAddress, dealerPhone, dealerMail, dealerState, dealerAccount);
				
				Map<String, String[]> getNo =  new TreeMap<String, String[]>();
				String[] mail= {dealerMail};
				getNo.put("dealerMail", mail);
				List<DealerVO> d = dealerService.getAll(getNo);
				System.out.println(d.get(0).getDealerNo());
				Integer dealerNo = d.get(0).getDealerNo();
				
				String url ="/user/regisHostel.jsp";
				HttpSession session = req.getSession();
				dealerVO.setDealerNo(dealerNo);
				session.setAttribute("dealerVObyAccount", dealerVO);
				RequestDispatcher successView =req.getRequestDispatcher(url);
				successView.forward(req, res);
			
			}catch(Exception e){
				errorMsgs.add("失敗"+e.getMessage());
				RequestDispatcher failureView=req.getRequestDispatcher("/user/regisDealer.jsp");
				failureView.forward(req, res);
			}
			
		}//insert
		
		if("hostel".equals(action)){//from dealer_add
			List<String> errorMsgs =new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			//reference errorMsgs to errorMsgs list,so everytime change can
			//refer to req's errorMsgs
			try {
		
				Integer dealerNo =null;
				HttpSession session = req.getSession();
				DealerVO dv = (DealerVO) session.getAttribute("dealerVObyAccount");
				dealerNo = dv.getDealerNo();
				
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
				
				Integer hostelState= 0;
				
				Integer hostelVerification= 0;
				
				Part hostelPicture=null;
				try {
					hostelPicture =req.getPart("hostelPicture");
					if(hostelPicture.getSize()==0){
						errorMsgs.add("請選擇圖片1");
					}
				} catch (Exception e) {
					hostelPicture=null;
					errorMsgs.add("請選擇圖片2");
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
						errorMsgs.add("請選擇圖片3");
					}
				} catch (Exception e) {
					dealerVerify=null;
					errorMsgs.add("請選擇圖片4");
				}
				//---------------------------------------------------------------
				InputStream dealerVerifyStream=dealerVerify.getInputStream();
				byte[] dealerVerifyImg=new byte[dealerVerifyStream.available()];
				dealerVerifyStream.read(dealerVerifyImg);
				dealerVerifyStream.close(); dealerVerifyStream=null;
				//---------------------------------------------------------------

//				Part part1 =req.getPart("hostelPicture");
//				Part part2 =req.getPart("dealerVerify");
				
			
				HostelVO hostelVO =new HostelVO();
				hostelVO.setDealerNo(dealerNo);
				hostelVO.setHostelName(hostelName);
				hostelVO.setHostelPhone(hostelPhone);
				hostelVO.setHostelAddress(hostelAddress);
				hostelVO.setHostelState(hostelState);
				hostelVO.setHostelVerification(hostelVerification);
				hostelVO.setHostelPicture(hostelPictureImg);
				hostelVO.setDealerVerify(dealerVerifyImg);
				
				if(!errorMsgs.isEmpty()){
					req.setAttribute("hostelVO", hostelVO);
					RequestDispatcher failureView =req.getRequestDispatcher("/user/regisHostel.jsp");
					failureView.forward(req, res);
					return;
				}
				/***************************2.開始新增資料****************************************/
				HostelService hostelService =new HostelService();
				hostelVO = hostelService.addHostel(dealerNo, hostelName, hostelPhone, hostelAddress, null, hostelState, hostelVerification, 0.0, 0.0, hostelPictureImg, dealerVerifyImg, null, null);
				/***************************3.新增完成,準備轉交(Send the Success view)************/
				String url="/dealer/dealer_One.jsp";
				RequestDispatcher successView =req.getRequestDispatcher(url);
				successView.forward(req, res);
				
			} catch (Exception e) {
				errorMsgs.add("新增資料錯誤"+e.getMessage());
				RequestDispatcher failureView =req.getRequestDispatcher("/user/regisHostel.jsp");
				failureView.forward(req, res);			
			}
		}//insert
		
		
	}//doPost---------------------------------------------
}
