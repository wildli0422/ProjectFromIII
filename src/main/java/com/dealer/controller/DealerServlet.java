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
		
		//頛詨�撘�征�潸���
		if("getOne_For_Display".equals(action)){
			
			List<String> errorMsgs =new LinkedList<String>();		
			//�隤几ist ������隤�---------------------
			req.setAttribute("errorMsgs",errorMsgs);
			//--------------------------------------
			try {
				/***************************1.��隢�� - 頛詨�撘�隤方���**********************/
				//--------------------------------------
				String str =req.getParameter("dealerNo");
				//---------------------------------------
				
				if(str==null || (str.trim()).length()==0){
					errorMsgs.add("隢撓�璆剛�楊���");
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
					errorMsgs.add("璆剛�楊��隤�");
				}
				
				if(!errorMsgs.isEmpty()){
					RequestDispatcher failureView =req.getRequestDispatcher("/dealer/dealer_select_page.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.���閰Ｚ���*****************************************/
				DealerService dealerService =new  DealerService();
				//select one dealer
				DealerVO dealerVO =dealerService.getOneDealer(dealerNo);
				if(dealerVO==null){
					errorMsgs.add("��鞈��");
				}
				//if error ->send error message to select_page
				if(!errorMsgs.isEmpty()){
					RequestDispatcher failureView =req.getRequestDispatcher("/dealer/dealer_select_page.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************3.�閰Ｗ���,皞��漱(Send the Success view)*************/
				req.setAttribute("dealerVO", dealerVO);
				String url="/dealer/dealer_listOne.jsp";
				//���摰rl 撠ttribute��甇方��
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
			} catch (Exception e) {
				errorMsgs.add("�瘜�����"+e.getMessage());
				//�隤文�
				RequestDispatcher failureView = req.getRequestDispatcher("/dealer/dealer_select_page.jsp");
				failureView.forward(req, res);
			}
		}//if get one display------------------------------------------
		
		if("getOne_For_Update".equals(action)){//from list all
			List<String> errorMsgs =new LinkedList<String>();
			req.setAttribute("errorMsgs",errorMsgs);
			
			try {
				/***************************1.��隢��****************************************/
				Integer dealerNo =new Integer(req.getParameter("dealerNo"));
				/***************************2.���閰Ｚ���****************************************/
				DealerService dealerService =new DealerService();
				DealerVO dealerVO =dealerService.getOneDealer(dealerNo);
				/***************************3.�閰Ｗ���,皞��漱(Send the Success view)************/
				req.setAttribute("dealerVO", dealerVO);
				String url ="/dealer/dealer_update.jsp";
				RequestDispatcher successView =req.getRequestDispatcher(url);
				successView.forward(req, res);
				
				/***************************�隞���隤方���**********************************/
			} catch (Exception e) {
				errorMsgs.add("�瘜���耨������: "+e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/dealer/dealer_listAll.jsp");
				failureView.forward(req, res);
			}
		}//get one update
		
		
		if("update".equals(action)){//from update button
			
			List<String> errorMsgs=new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			DealerService dealerServicetmp=new DealerService();
			
			try {
				/***************************1.��隢�� - 頛詨�撘�隤方���**********************/
				Integer dealerNo =new Integer(req.getParameter("dealerNo"));
				
				String dealerName =dealerServicetmp.getOneDealer(dealerNo).getDealerName();
				
				String dealerPassword=null;
				try{
					dealerPassword =req.getParameter("dealerPassword").trim();
					if(dealerPassword==null || dealerPassword.length()==0){
						errorMsgs.add("隢撓�撖Ⅳ");
					}
				}catch(Exception E){
					errorMsgs.add("隢撓�撖Ⅳ");
				}
				
				String dealerSex = dealerServicetmp.getOneDealer(dealerNo).getDealerSex();
				
				
				String dealerPhone =null;
				try{
					dealerPhone=req.getParameter("dealerPhone").trim();
					if(dealerPhone==null || dealerPhone.length()==0){
						errorMsgs.add("隢撓��閰梯�Ⅳ");
					}
				}catch(Exception e){
					errorMsgs.add("隢撓��閰梯�Ⅳ");
				}
				
				String dealerAddress =null;
				try{
					dealerAddress = req.getParameter("dealerAddress").trim();
					if(dealerAddress==null || dealerAddress.length()==0){
						errorMsgs.add("隢撓����");
					}
				}catch(IllegalArgumentException e){
					errorMsgs.add("隢撓����");
				}
				String dealerMail =dealerServicetmp.getOneDealer(dealerNo).getDealerMail();
				
				Integer dealerState =dealerServicetmp.getOneDealer(dealerNo).getDealerState();
				
				String dealerAccount =null;
				try{
					dealerAccount=req.getParameter("dealerAccount").trim();
					if(dealerAccount==null || dealerAccount.length()==0){
						errorMsgs.add("隢撓�撣單");
					}
				}catch(Exception e){
					errorMsgs.add("隢撓�撣單");
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
					return;//蝔�葉�
				}
				
				/***************************2.���耨�鞈��*****************************************/
				DealerService dealerService =new DealerService();
				dealerVO = dealerService.updateDealer(dealerNo, dealerPassword, dealerName, dealerSex, dealerAddress, 
						dealerPhone, dealerMail, dealerState, dealerAccount);
				
				/***************************3.靽格摰��,皞��漱(Send the Success view)*************/
				HttpSession session=req.getSession();
				session.setAttribute("dealerVObyAccount",dealerVO);
				req.setAttribute("dealerVO", dealerVO);
				String requestURL=req.getParameter("requestURL");
//				String url= "/dealer/dealer_listOne.jsp";
				RequestDispatcher successView =req.getRequestDispatcher(requestURL);
				successView.forward(req, res);
				
				/***************************�隞���隤方���*************************************/
			} catch (Exception e) {
				System.out.println("ex error");
				errorMsgs.add("靽格鞈�隤�"+e.getMessage());
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
					errorMsgs.add("隢撓�憪��");
				}
				String dealerPassword=null;
				
				dealerPassword =req.getParameter("dealerPassword").trim();
				if (dealerPassword == null || dealerPassword.length() == 0) {
					dealerPassword="";
					errorMsgs.add("隢撓�撖Ⅳ");
				}
			
				
				String dealerSex = req.getParameter("dealerSex").trim();
				
				
				String dealerPhone = null;
				dealerPhone = req.getParameter("dealerPhone").trim();
				if (dealerPhone == null || dealerPhone.length() == 0) {
					dealerPhone="";
					errorMsgs.add("隢撓��閰�");
				}

				String dealerAddress =null;
				dealerAddress = req.getParameter("dealerAddress").trim();
				if (dealerAddress == null || dealerAddress.length() == 0) {
					dealerAddress="";
					errorMsgs.add("隢撓����");
				}
				
				String dealerMail =null;
				dealerMail = req.getParameter("dealerMail").trim();
				if (dealerMail == null || dealerMail.length() == 0) {
					dealerMail="";
					errorMsgs.add("隢撓������");
				}
				
				Integer dealerState = 0;
				String dealerAccount =null;	
				dealerAccount=req.getParameter("dealerAccount").trim();
				if (dealerAccount == null || dealerAccount.length() == 0) {
					dealerAccount="";
					errorMsgs.add("隢撓����董�");
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
				errorMsgs.add("憭望��"+e.getMessage());
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
				errorMsgs.add("嚙磋嚙踝蕭嚙踝蕭嚙踝蕭嚙�"+e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/dealer/dealer_listAll.jsp");
				failureView.forward(req, res);
			}
		}//delete
		
		
		
		
		
		///backend///
		if("update2".equals(action)){//from update button
			
			List<String> errorMsgs=new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				/***************************1.嚙踝蕭嚙踝蕭嚙請求嚙諸潘蕭 - 嚙踝蕭J嚙賣式嚙踝蕭嚙踝蕭嚙羯嚙畿嚙緲**********************/
				Integer dealerNo =new Integer(req.getParameter("dealerNo"));
				String dealerName =req.getParameter("dealerName").trim();
				
				String dealerPassword=null;
				try{
					dealerPassword =req.getParameter("dealerPassword").trim();
					if(dealerPassword==null || dealerPassword.length()==0){
						errorMsgs.add("嚙請選蕭J嚙皺嚙碼");
					}
				}catch(Exception E){
					errorMsgs.add("嚙請選蕭J嚙皺嚙碼");
				}
				
				String dealerSex = req.getParameter("dealerSex").trim();
				
				String dealerPhone =null;
				try{
					dealerPhone=req.getParameter("dealerPhone").trim();
					if(dealerPhone==null || dealerPhone.length()==0){
						errorMsgs.add("嚙請選蕭J嚙緬嚙豌賂蕭嚙碼");
					}
				}catch(Exception e){
					errorMsgs.add("嚙請選蕭J嚙緬嚙豌賂蕭嚙碼");
				}
				
				String dealerAddress =null;
				try{
					dealerAddress = req.getParameter("dealerAddress").trim();
					if(dealerAddress==null || dealerAddress.length()==0){
						errorMsgs.add("嚙請選蕭J嚙窮嚙罷");
					}
				}catch(IllegalArgumentException e){
					errorMsgs.add("嚙請選蕭J嚙窮嚙罷");
				}
				
				String dealerMail =null;
				try{
					dealerMail =req.getParameter("dealerMail").trim();
					if(dealerMail==null || dealerMail.length()==0){
						errorMsgs.add("嚙請選蕭Je-mail");
					}
				}catch(Exception e){
					errorMsgs.add("嚙請選蕭Je-mail");
				}
				
				Integer dealerState =null;
				try{
					dealerState =new Integer(req.getParameter("dealerState"));
					if(dealerState>1 || dealerState<0 || dealerState==null ){
						errorMsgs.add("嚙請選蕭嚙�0嚙踝蕭1");
					}
				}catch(Exception e){
					dealerState=0;
					errorMsgs.add("嚙請選蕭J嚙篆嚙誰迎蕭嚙璀");
				}
				
				
				String dealerAccount =null;
				try{
					dealerAccount=req.getParameter("dealerAccount").trim();
					if(dealerAccount==null || dealerAccount.length()==0){
						errorMsgs.add("嚙請選蕭J嚙箭嚙踝蕭");
					}
				}catch(Exception e){
					errorMsgs.add("嚙請選蕭J嚙箭嚙踝蕭");
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
					return;//嚙緹嚙踝蕭嚙踝蕭嚙稻
				}
				
				/***************************2.嚙罷嚙締嚙論改蕭嚙踝蕭*****************************************/
				DealerService dealerService =new DealerService();
				dealerVO = dealerService.updateDealer(dealerNo, dealerPassword, dealerName, dealerSex, dealerAddress, 
						dealerPhone, dealerMail, dealerState, dealerAccount);
				/***************************3.嚙論改完嚙踝蕭,嚙褒喉蕭嚙踝蕭嚙�(Send the Success view)*************/
				req.setAttribute("dealerVO", dealerVO);
//				String url= "/dealer/dealer_listOne.jsp";
				String url = req.getParameter("requestURL");
				RequestDispatcher successView =req.getRequestDispatcher(url);
				successView.forward(req, res);
				
				/***************************嚙踝蕭L嚙箠嚙賞的嚙踝蕭嚙羯嚙畿嚙緲*************************************/
			} catch (Exception e) {
				
				errorMsgs.add("嚙論改蕭嚙複選蕭嚙羯"+e.getMessage());
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
				RequestDispatcher successView = req.getRequestDispatcher("/backEnd/user/dealerManager.jsp"); // ����漱listEmps_ByCompositeQuery.jsp
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
					errorMsgs.add("嚙請選蕭J嚙羯嚙諒編嚙踝蕭");
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
					errorMsgs.add("嚙羯嚙諒編嚙踝蕭嚙踝蕭嚙羯");
				}
				if(!errorMsgs.isEmpty()){
					RequestDispatcher failureView =req.getRequestDispatcher("/dealer/dealer_select_page.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.嚙罷嚙締嚙範嚙賠賂蕭嚙�*****************************************/
				DealerService dealerService =new  DealerService();
				//select one dealer
				DealerVO dealerVO =dealerService.getOneDealer(dealerNo);
				if(dealerVO==null){
					errorMsgs.add("嚙範嚙盤嚙踝蕭嚙�");
				}
				//if error ->send error message to select_page
				if(!errorMsgs.isEmpty()){
					RequestDispatcher failureView =req.getRequestDispatcher("/dealer/dealer_select_page.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************3.嚙範嚙賠改蕭嚙踝蕭,嚙褒喉蕭嚙踝蕭嚙�(Send the Success view)*************/
				req.setAttribute("dealerVO", dealerVO);
				req.setAttribute("dealerNo", dealerNo);
//				String url="/dealer/dealer_listOne.jsp";
				String url = req.getParameter("requestURL");
				//嚙踝蕭嚙緩嚙磅嚙緩url 嚙瞇attribute嚙褒送嚙趣此嚙畿
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
			} catch (Exception e) {
				errorMsgs.add("嚙盤嚙糊嚙踝蕭嚙緻嚙踝蕭嚙�"+e.getMessage());
				//嚙踝蕭嚙羯嚙稷嚙踝蕭
				RequestDispatcher failureView = req.getRequestDispatcher("/dealer/dealer_select_page.jsp");
				failureView.forward(req, res);
			}
		}
	}//doPost---------------------------------------------
}
