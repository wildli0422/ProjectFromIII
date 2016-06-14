package com.dealer.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.mail.Session;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import com.dealer.model.DealerService;
import com.dealer.model.DealerVO;

public class DealerServlet extends HttpServlet{
	
	public void doGet(HttpServletRequest req,HttpServletResponse res) 
			throws ServletException,IOException{
		doPost(req, res);
	}
	//doGet----------------------------------------------
	
	public void doPost(HttpServletRequest req,HttpServletResponse res) 
			throws ServletException,IOException{
		
		req.setCharacterEncoding("UTF-8");
		String action =req.getParameter("action");
		
		//輸入格式及空值處理
		if("getOne_For_Display".equals(action)){
			
			List<String> errorMsgs =new LinkedList<String>();		
			//錯誤List 接收所有錯誤---------------------
			req.setAttribute("errorMsgs",errorMsgs);
			//--------------------------------------
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				//--------------------------------------
				String str =req.getParameter("dealerNo");
				//---------------------------------------
				
				if(str==null || (str.trim()).length()==0){
					errorMsgs.add("請輸入業者編號");
				}
				
				if(!errorMsgs.isEmpty()){
					RequestDispatcher failureView =req.getRequestDispatcher("/dealer/dealer_select_page.jsp");
					failureView.forward(req, res);
					return;
				}
				
				Integer dealerNo=null;
				try{
					dealerNo=new Integer(str);
				}catch(Exception e){
					errorMsgs.add("業者編號錯誤");
				}
				
				if(!errorMsgs.isEmpty()){
					RequestDispatcher failureView =req.getRequestDispatcher("/dealer/dealer_select_page.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始查詢資料*****************************************/
				DealerService dealerService =new  DealerService();
				//select one dealer
				DealerVO dealerVO =dealerService.getOneDealer(dealerNo);
				if(dealerVO==null){
					errorMsgs.add("查無資料");
				}
				//if error ->send error message to select_page
				if(!errorMsgs.isEmpty()){
					RequestDispatcher failureView =req.getRequestDispatcher("/dealer/dealer_select_page.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("dealerVO", dealerVO);
				String url="/dealer/dealer_listOne.jsp";
				//指定特定url 將attribute傳送到此處
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
			} catch (Exception e) {
				errorMsgs.add("無法取得資料"+e.getMessage());
				//錯誤回傳
				RequestDispatcher failureView = req.getRequestDispatcher("/dealer/dealer_select_page.jsp");
				failureView.forward(req, res);
			}
		}//if get one display------------------------------------------
		
		if("getOne_For_Update".equals(action)){//from list all
			List<String> errorMsgs =new LinkedList<String>();
			req.setAttribute("errorMsgs",errorMsgs);
			
			try {
				/***************************1.接收請求參數****************************************/
				Integer dealerNo =new Integer(req.getParameter("dealerNo"));
				/***************************2.開始查詢資料****************************************/
				DealerService dealerService =new DealerService();
				DealerVO dealerVO =dealerService.getOneDealer(dealerNo);
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("dealerVO", dealerVO);
				String url ="/dealer/dealer_update.jsp";
				RequestDispatcher successView =req.getRequestDispatcher(url);
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料: "+e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/dealer/dealer_listAll.jsp");
				failureView.forward(req, res);
			}
		}//get one update
		
		
		if("update".equals(action)){//from update button
			
			List<String> errorMsgs=new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			DealerService dealerServicetmp=new DealerService();
			
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				Integer dealerNo =new Integer(req.getParameter("dealerNo"));
				
				String dealerName =dealerServicetmp.getOneDealer(dealerNo).getDealerName();
				
				String dealerPassword=null;
				try{
					dealerPassword =req.getParameter("dealerPassword").trim();
					if(dealerPassword==null || dealerPassword.length()==0){
						errorMsgs.add("請輸入密碼");
					}
				}catch(Exception E){
					errorMsgs.add("請輸入密碼");
				}
				
				String dealerSex = dealerServicetmp.getOneDealer(dealerNo).getDealerSex();
				
				
				String dealerPhone =null;
				try{
					dealerPhone=req.getParameter("dealerPhone").trim();
					if(dealerPhone==null || dealerPhone.length()==0){
						errorMsgs.add("請輸入電話號碼");
					}
				}catch(Exception e){
					errorMsgs.add("請輸入電話號碼");
				}
				
				String dealerAddress =null;
				try{
					dealerAddress = req.getParameter("dealerAddress").trim();
					if(dealerAddress==null || dealerAddress.length()==0){
						errorMsgs.add("請輸入地址");
					}
				}catch(IllegalArgumentException e){
					errorMsgs.add("請輸入地址");
				}
				String dealerMail =dealerServicetmp.getOneDealer(dealerNo).getDealerMail();
				
				Integer dealerState =dealerServicetmp.getOneDealer(dealerNo).getDealerState();
				
				String dealerAccount =null;
				try{
					dealerAccount=req.getParameter("dealerAccount").trim();
					if(dealerAccount==null || dealerAccount.length()==0){
						errorMsgs.add("請輸入帳戶");
					}
				}catch(Exception e){
					errorMsgs.add("請輸入帳戶");
				}
				DealerVO dealerVO =new DealerVO();
				dealerVO.setDealerNo(dealerNo);
				dealerVO.setDealerName(dealerName);
				dealerVO.setDealerPassword(dealerPassword);
				dealerVO.setDealerSex(dealerSex);
				dealerVO.setDealerAddress(dealerAddress);
				dealerVO.setDealerMail(dealerMail);
				dealerVO.setDealerState(dealerState);
				dealerVO.setDealerPhone(dealerPhone);
				dealerVO.setDealerAccount(dealerAccount);
				
				if(!errorMsgs.isEmpty()){
					System.out.println("error");
					req.setAttribute("dealerVO", dealerVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/dealer/dealer_One.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				DealerService dealerService =new DealerService();
				dealerVO = dealerService.updateDealer(dealerNo, dealerPassword, dealerName, dealerSex, dealerAddress, 
						dealerPhone, dealerMail, dealerState, dealerAccount);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				HttpSession session=req.getSession();
				session.setAttribute("dealerVObyAccount",dealerVO);
				req.setAttribute("dealerVO", dealerVO);
				String requestURL=req.getParameter("requestURL");
//				String url= "/dealer/dealer_listOne.jsp";
				RequestDispatcher successView =req.getRequestDispatcher(requestURL);
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				System.out.println("ex error");
				errorMsgs.add("修改資料錯誤"+e.getMessage());
				RequestDispatcher failureView =req.getRequestDispatcher("/dealer/dealer_One.jsp");
				failureView.forward(req, res);
			}
		}//update------------------------------------------------------
		
		if("insert".equals(action)){
			List<String> errorMsgs =new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
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
					RequestDispatcher failureView = req.getRequestDispatcher("/user/registerForDealer.jsp");
					failureView.forward(req, res);
					return;
				}
				
				
				DealerService dealerService =new DealerService();
				dealerVO=dealerService.addDealer(dealerPassword, dealerPassword, dealerSex, dealerAddress, dealerPhone, dealerMail, dealerState, dealerAccount);
				
				String url ="/user/registerForDealer2.jsp";
				HttpSession session = req.getSession();
				session.setAttribute("dealerVO", dealerVO);
				RequestDispatcher successView =req.getRequestDispatcher(url);
				successView.forward(req, res);
			
			}catch(Exception e){
				errorMsgs.add("失敗"+e.getMessage());
				RequestDispatcher failureView=req.getRequestDispatcher("/user/registerForDealer.jsp");
				failureView.forward(req, res);
			}
			
		}//insert
		
		
		if("delete".equals(action)){
			List<String> errorMsgs=new LinkedList<String>();
			
			req.setAttribute("errorMsgs", errorMsgs);
			
			try{
				Integer dealerNo=new Integer(req.getParameter("dealerNo"));

				DealerService dealerService =new DealerService();
				dealerService.deleteDealer(dealerNo);

				String url="/dealer/dealer_listAll.jsp";
				RequestDispatcher successView =req.getRequestDispatcher(url);
				successView.forward(req, res);
				
			}catch(Exception e){
				errorMsgs.add("�R����ƥ���"+e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/dealer/dealer_listAll.jsp");
				failureView.forward(req, res);
			}
		}//delete
		
		
		
		
		
		///backend///
		if("update2".equals(action)){//from update button
			
			List<String> errorMsgs=new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
				Integer dealerNo =new Integer(req.getParameter("dealerNo"));
				String dealerName =req.getParameter("dealerName").trim();
				
				String dealerPassword=null;
				try{
					dealerPassword =req.getParameter("dealerPassword").trim();
					if(dealerPassword==null || dealerPassword.length()==0){
						errorMsgs.add("�п�J�K�X");
					}
				}catch(Exception E){
					errorMsgs.add("�п�J�K�X");
				}
				
				String dealerSex = req.getParameter("dealerSex").trim();
				
				String dealerPhone =null;
				try{
					dealerPhone=req.getParameter("dealerPhone").trim();
					if(dealerPhone==null || dealerPhone.length()==0){
						errorMsgs.add("�п�J�q�ܸ��X");
					}
				}catch(Exception e){
					errorMsgs.add("�п�J�q�ܸ��X");
				}
				
				String dealerAddress =null;
				try{
					dealerAddress = req.getParameter("dealerAddress").trim();
					if(dealerAddress==null || dealerAddress.length()==0){
						errorMsgs.add("�п�J�a�}");
					}
				}catch(IllegalArgumentException e){
					errorMsgs.add("�п�J�a�}");
				}
				
				String dealerMail =null;
				try{
					dealerMail =req.getParameter("dealerMail").trim();
					if(dealerMail==null || dealerMail.length()==0){
						errorMsgs.add("�п�Je-mail");
					}
				}catch(Exception e){
					errorMsgs.add("�п�Je-mail");
				}
				
				Integer dealerState =null;
				try{
					dealerState =new Integer(req.getParameter("dealerState"));
					if(dealerState>1 || dealerState<0 || dealerState==null ){
						errorMsgs.add("�п��0��1");
					}
				}catch(Exception e){
					dealerState=0;
					errorMsgs.add("�п�J�f�֪��A");
				}
				
				
				String dealerAccount =null;
				try{
					dealerAccount=req.getParameter("dealerAccount").trim();
					if(dealerAccount==null || dealerAccount.length()==0){
						errorMsgs.add("�п�J�b��");
					}
				}catch(Exception e){
					errorMsgs.add("�п�J�b��");
				}
				
				DealerVO dealerVO =new DealerVO();
				dealerVO.setDealerNo(dealerNo);
				dealerVO.setDealerName(dealerName);
				dealerVO.setDealerPassword(dealerPassword);
				dealerVO.setDealerSex(dealerSex);
				dealerVO.setDealerAddress(dealerAddress);
				dealerVO.setDealerMail(dealerMail);
				dealerVO.setDealerState(dealerState);
				dealerVO.setDealerPhone(dealerPhone);
				dealerVO.setDealerAccount(dealerAccount);
				
				if(!errorMsgs.isEmpty()){
					req.setAttribute("dealerVO", dealerVO);
					
					RequestDispatcher failureView = req.getRequestDispatcher("/dealer/dealer_update.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
				
				/***************************2.�}�l�ק���*****************************************/
				DealerService dealerService =new DealerService();
				dealerVO = dealerService.updateDealer(dealerNo, dealerPassword, dealerName, dealerSex, dealerAddress, 
						dealerPhone, dealerMail, dealerState, dealerAccount);
				/***************************3.�ק粒��,�ǳ����(Send the Success view)*************/
				req.setAttribute("dealerVO", dealerVO);
//				String url= "/dealer/dealer_listOne.jsp";
				String url = req.getParameter("requestURL");
				RequestDispatcher successView =req.getRequestDispatcher(url);
				successView.forward(req, res);
				
				/***************************��L�i�઺���~�B�z*************************************/
			} catch (Exception e) {
				
				errorMsgs.add("�ק��ƿ��~"+e.getMessage());
				RequestDispatcher failureView =req.getRequestDispatcher("/dealer/dealer_update.jsp");
				failureView.forward(req, res);
			}
		}//update---------
		
		if ("getlistByCQ".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				Map<String, String[]> map = req.getParameterMap();
				DealerService dealerService = new DealerService();
				List<DealerVO> list = dealerService.getAll(map);

				for (int i = 0; i < list.size(); i++) {
					for (int j = i + 1; j < list.size(); j++) {
						if (list.get(j).getDealerNo().equals(list.get(i).getDealerNo())) {
							list.remove(j);
							j--;
						}
					}
				}
				
				
				
				
				req.setAttribute("dealerlist", list);
				RequestDispatcher successView = req.getRequestDispatcher("/backEnd/user/dealerManager.jsp"); // 成功轉交listEmps_ByCompositeQuery.jsp
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/backEnd/user/dealerManager.jsp");
				failureView.forward(req, res);
			}
		}
		
		if("getOne_For_Display_bk".equals(action)){
			
			List<String> errorMsgs =new LinkedList<String>();		
			req.setAttribute("errorMsgs",errorMsgs);
			//--------------------------------------
			try {
				//--------------------------------------
				String str =req.getParameter("dealerNo");
				//---------------------------------------
				
				if(str==null || (str.trim()).length()==0){
					errorMsgs.add("�п�J�~�̽s��");
				}
				if(!errorMsgs.isEmpty()){
					RequestDispatcher failureView =req.getRequestDispatcher("/dealer/dealer_select_page.jsp");
					failureView.forward(req, res);
					return;
				}
				
				Integer dealerNo=null;
				try{
					dealerNo=new Integer(str);
				}catch(Exception e){
					errorMsgs.add("�~�̽s�����~");
				}
				if(!errorMsgs.isEmpty()){
					RequestDispatcher failureView =req.getRequestDispatcher("/dealer/dealer_select_page.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.�}�l�d�߸��*****************************************/
				DealerService dealerService =new  DealerService();
				//select one dealer
				DealerVO dealerVO =dealerService.getOneDealer(dealerNo);
				if(dealerVO==null){
					errorMsgs.add("�d�L���");
				}
				//if error ->send error message to select_page
				if(!errorMsgs.isEmpty()){
					RequestDispatcher failureView =req.getRequestDispatcher("/dealer/dealer_select_page.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************3.�d�ߧ���,�ǳ����(Send the Success view)*************/
				req.setAttribute("dealerVO", dealerVO);
				req.setAttribute("dealerNo", dealerNo);
//				String url="/dealer/dealer_listOne.jsp";
				String url = req.getParameter("requestURL");
				//���w�S�wurl �Nattribute�ǰe�즹�B
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
			} catch (Exception e) {
				errorMsgs.add("�L�k���o���"+e.getMessage());
				//���~�^��
				RequestDispatcher failureView = req.getRequestDispatcher("/dealer/dealer_select_page.jsp");
				failureView.forward(req, res);
			}
		}
	}//doPost---------------------------------------------
}
