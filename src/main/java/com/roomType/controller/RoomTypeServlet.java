package com.roomType.controller;

import java.io.IOException;
import java.io.InputStream;
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

import com.dealer.model.DealerService;
import com.dealer.model.DealerVO;
import com.hostel.model.HostelService;
import com.roomType.model.RoomTypeService;
import com.roomType.model.RoomTypeVO;

@WebServlet("/roomType.do")
@MultipartConfig
public class RoomTypeServlet extends HttpServlet{
	
	public void doGet(HttpServletRequest req,HttpServletResponse res) 
			throws ServletException,IOException{
		doPost(req,res);
	}
	
	public void doPost(HttpServletRequest req,HttpServletResponse res) 
			throws ServletException,IOException{
		
		req.setCharacterEncoding("UTF-8");
		String action =req.getParameter("action");
		
		
		if("getOne_For_Display".equals(action)){
			
			List<String> errorMsgs =new LinkedList<String>();		
			//���~List �����Ҧ����~---------------------
			req.setAttribute("errorMsgs",errorMsgs);
			//--------------------------------------
			try {
				/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
				//--------------------------------------
				String str =req.getParameter("roomTypeNo");
				//---------------------------------------
				
				if(str==null || (str.trim()).length()==0){
					errorMsgs.add("�п�J�Ы��s��");
				}
				
				if(!errorMsgs.isEmpty()){
					RequestDispatcher failureView =req.getRequestDispatcher("/roomType/roomType_select_page.jsp");
					failureView.forward(req, res);
					return;
				}
				
				Integer roomTypeNo=null;
				try{
					roomTypeNo=new Integer(str);
				}catch(Exception e){
					errorMsgs.add("�Ы��s�����~");
				}
				
				if(!errorMsgs.isEmpty()){
					RequestDispatcher failureView =req.getRequestDispatcher("/roomType/roomType_select_page.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.�}�l�d�߸��*****************************************/
				RoomTypeService roomTypeService =new RoomTypeService();
				//select one dealer
				RoomTypeVO roomTypeVO =roomTypeService.getOneRoomType(roomTypeNo);
				if(roomTypeVO==null){
					errorMsgs.add("�d�L���");
				}
				//if error ->send error message to select_page
				if(!errorMsgs.isEmpty()){
					RequestDispatcher failureView =req.getRequestDispatcher("/roomType/roomType_select_page.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************3.�d�ߧ���,�ǳ����(Send the Success view)*************/
				req.setAttribute("roomTypeVO", roomTypeVO);
				String url="/roomType/roomType_listOne.jsp";
				//���w�S�wurl �Nattribute�ǰe�즹�B
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
			} catch (Exception e) {
				errorMsgs.add("�L�k���o���"+e.getMessage());
				//���~�^��
				RequestDispatcher failureView = req.getRequestDispatcher("/roomType/roomType_select_page.jsp");
				failureView.forward(req, res);
			}
		}//if get one display------------------------------------------
		
		if("getOne_For_Update".equals(action)){//from list all
			List<String> errorMsgs =new LinkedList<String>();
			req.setAttribute("errorMsgs",errorMsgs);
// �e�X�ק諸�ӷ��������|: �i�ର�i/roomType/roomType_listAll.jsp�j
// �� �i/hostel/listRoomTypes_ByHostelNo.jsp�j �� �i /hostel/hostel_listHostelsForRoomTypes.jsp�j
// OR �i/roomType/roomType_compositeQuery.jsp�j
			String requestURL =req.getParameter("requestURL");
			System.out.println("get one update: "+requestURL);
			// �e�X�ק諸�ӷ��������|, �s�Jreq (�O���F��roomType_update.jsp)
//			req.setAttribute("requestURL", requestURL);
			
//			String whichPage=req.getParameter("whichPage");
			// �e�X�ק諸�ӷ��������ĴX��, �s�Jreq(�u�Ω�:roomType_listAll.jsp)
//			req.setAttribute("whichPage", whichPage);
			
			try {
				/***************************1.�����ШD�Ѽ�****************************************/
				Integer roomTypeNo =new Integer(req.getParameter("roomTypeNo"));
				/***************************2.�}�l�d�߸��****************************************/
				RoomTypeService roomTypeService = new RoomTypeService();
				RoomTypeVO roomTypeVO=roomTypeService.getOneRoomType(roomTypeNo);
				/***************************3.�d�ߧ���,�ǳ����(Send the Success view)************/
				req.setAttribute("roomTypeVO", roomTypeVO);
				String url ="/roomType/roomType_update.jsp";
				RequestDispatcher successView =req.getRequestDispatcher(url);
				successView.forward(req, res);
				
				/***************************��L�i�઺���~�B�z**********************************/
			} catch (Exception e) {
				errorMsgs.add("�L�k���o�n�ק諸���: "+e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/roomType/roomType_listAll.jsp");
				failureView.forward(req, res);
			}
		}//get one update
		
		
		if("update".equals(action)){//from update button
			
			List<String> errorMsgs=new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
// �e�X�ק諸�ӷ��������|: �i�ର�i/roomType/roomType_listAll.jsp�j
// �� �i/hostel/listRoomTypes_ByHostelNo.jsp�j �� �i /hostel/hostel_listHostelsForRoomTypes.jsp�j	
// OR �i/roomType/roomType_compositeQuery.jsp�j
			String requestURL=req.getParameter("requestURL");
//			req.setAttribute("requestURL", requestURL);
			
//			String whichPage =req.getParameter("whichPage");
//			req.setAttribute("whichPage", whichPage);
			RoomTypeService roomTypeServicetmp=new RoomTypeService();
//			try {
				/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
				Integer roomTypeNo=null;
				try {
					roomTypeNo=new Integer(req.getParameter("roomTypeNo"));	
				} catch (Exception e) {
					System.out.println(roomTypeNo);
					errorMsgs.add("��J�Ы��s��");
				}
				System.out.println("room-------------"+roomTypeNo);
				
				Integer hostelNo=null;
				try{
					hostelNo =new Integer(req.getParameter("hostelNo"));
				}catch(Exception e){
					hostelNo=2001;
					errorMsgs.add("�п�J���J�s��");
				}
				
				Integer facilityNo=null;
				try{
					facilityNo=new Integer(req.getParameter("facilityNo").trim());
				}catch(Exception e){
					facilityNo=4001;
					errorMsgs.add("�п�J�]�ƽs��");
				}
				
				String roomTypeName=req.getParameter("roomTypeName").trim();
				if(roomTypeName==null || roomTypeName.length()==0){
					errorMsgs.add("�п�J�Ы��W��");
				}
				
				Integer roomTypeContain=null;
				try{
					roomTypeContain=new Integer(req.getParameter("roomTypeContain"));
					if(roomTypeContain==0 || roomTypeContain==null){
						errorMsgs.add("�п�J/�ץ��e�ǤH��");
					}
				}catch(Exception e){
					errorMsgs.add("�п�J�H��");
				}
				
				Integer roomTypePrice=null;
				try{
					roomTypePrice=new Integer(req.getParameter("roomTypePrice"));
					if(roomTypePrice==null || roomTypePrice<1000){
						errorMsgs.add("�п�J/�ץ��Ы�����");
					}
				}catch(Exception e){
					errorMsgs.add("�п�J����");
				}
				
				Part roomTypePicture=null;
				byte[] roomTypePictureImg=null;
				try {
					roomTypePicture =req.getPart("roomTypePicture");
					if(roomTypePicture.getSize()==0){
						roomTypePictureImg=roomTypeServicetmp.getOneRoomType(roomTypeNo).getRoomTypePicture();
					}else{
						//---------------------------------------------------------------
						InputStream roomTypePictureStream=roomTypePicture.getInputStream();
						roomTypePictureImg=new byte[roomTypePictureStream.available()];
						roomTypePictureStream.read(roomTypePictureImg);
						roomTypePictureStream.close(); roomTypePictureStream=null;
						//---------------------------------------------------------------
					}
				} catch (Exception e) {
					roomTypePicture=null;
					errorMsgs.add("�п�ܹϤ�");
				}
				
				
				String roomTypeContent =req.getParameter("roomTypeContent");
				if(roomTypeContent==null || roomTypeContent.length()==0){
					errorMsgs.add("�п�J�Ы����e");
				}
				//�B�z��ƿ��~���p�A���έ��Ƽg---------
				RoomTypeVO roomTypeVO=new RoomTypeVO();
				roomTypeVO.setRoomTypeNo(roomTypeNo);
				roomTypeVO.setHostelNo(hostelNo);
				roomTypeVO.setFacilityNo(facilityNo);
				roomTypeVO.setRoomTypeName(roomTypeName);
				roomTypeVO.setRoomTypeContain(roomTypeContain);
				roomTypeVO.setRoomTypePrice(roomTypePrice);
				roomTypeVO.setRoomTypeContent(roomTypeContent);
				roomTypeVO.setRoomTypePicture(roomTypePictureImg);
				
			
				if(!errorMsgs.isEmpty()){
					req.setAttribute("roomTypeVO", roomTypeVO);
					RequestDispatcher failureView =req.getRequestDispatcher("/roomType/roomType_update.jsp");
					failureView.forward(req, res);
					return;
				}
			/***************************2.�}�l�s�W���***************************************/
			RoomTypeService roomTypeService =new RoomTypeService();
			roomTypeVO =roomTypeService.updateRoomType(roomTypeNo, hostelNo, facilityNo, 
						roomTypeName, roomTypeContain, roomTypePrice, roomTypePictureImg,roomTypeContent);
			/***************************3.�s�W����,�ǳ����(Send the Success view)***********/
//			req.setAttribute("roomTypeVO", roomTypeVO);
//			String url ="/roomType/roomType_listOne.jsp";
//			RequestDispatcher successView =req.getRequestDispatcher(url);
//			successView.forward(req, res);
			System.out.println("update requestURL="+requestURL);
//			System.out.println("whichPage="+whichPage);
		//������e�����A�ç�set���J�Ы��ǵ��S�w2�ӭ����A�䥦�h�O����
			HostelService hostelService=new HostelService();
			if(requestURL.equals("/hostel/hostel_listRoomTypes.jsp")
			|| requestURL.equals("/hostel/hostel_listHostelsForRoomTypes.jsp")){
				req.setAttribute("listRoomTypes_ByHostelNo", hostelService.getRoomTypesByHostelNo(hostelNo));
			}
			if(requestURL.equals("/roomType/roomType_CompositeQuery.jsp")){
				HttpSession session=req.getSession();
				Map<String, String[]> map = (Map<String, String[]>)session.getAttribute("map");
				List<RoomTypeVO> list =roomTypeService.getAll(map);
				req.setAttribute("listRoomTypes_ByCompositeQuery", list);
			}
			// �e�X�R�����ӷ��������ĴX��(�u�Ω�:listAllEmp.jsp)

//			String url=requestURL+"?whichPage="+whichPage+"&roomTypeNo="+roomTypeNo;
			String url=requestURL;
			System.out.println(url);
			RequestDispatcher successView=req.getRequestDispatcher(url);
			successView.forward(req, res);
//			}catch(Exception e){
//				errorMsgs.add("��Ƥ���"+e.getMessage());
//				RequestDispatcher failureView=req.getRequestDispatcher("/roomType/roomType_update.jsp");
//				failureView.forward(req, res);
//			}
				
		}//update------------------------------------------------------
		
		if("insert".equals(action)){//from dealer_add
			List<String> errorMsgs =new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			//reference errorMsgs to errorMsgs list,so everytime change can
			//refer to req's errorMsgs
			RoomTypeService roomTypeServicetmp=new RoomTypeService();
			try {
				/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
				Integer hostelNo=null;
				try{
					hostelNo =new Integer(req.getParameter("hostelNo"));
				}catch(Exception e){
					hostelNo=2001;
					errorMsgs.add("�п�J���J�s��");
				}
				
				Integer facilityNo=null;
				try{
					facilityNo=new Integer(req.getParameter("facilityNo").trim());
				}catch(Exception e){
					facilityNo=4001;
					errorMsgs.add("�п�J�]�ƽs��");
				}
				
				String roomTypeName=req.getParameter("roomTypeName").trim();
				if(roomTypeName==null || roomTypeName.length()==0){
					errorMsgs.add("�п�J�Ы��W��");
				}
				
				Integer roomTypeContain=null;
				try{
					roomTypeContain=new Integer(req.getParameter("roomTypeContain"));
					if(roomTypeContain==0 || roomTypeContain==null){
						errorMsgs.add("�п�J/�ץ��e�ǤH��");
					}
				}catch(NumberFormatException ne){
					errorMsgs.add("�п�J���T���");
				}catch(Exception e){
					errorMsgs.add("�п�J�H��");
				}
				
				Integer roomTypePrice=null;
				try{
					roomTypePrice=new Integer(req.getParameter("roomTypePrice"));
					if(roomTypePrice==null || roomTypePrice<1000){
						errorMsgs.add("�п�J/�ץ��Ы�����");
					}
				}catch(Exception e){
					errorMsgs.add("�п�J����");
				}
				
				
				Part roomTypePicture=null;
				byte[] roomTypePictureImg=null;
				try {
					roomTypePicture =req.getPart("roomTypePicture");
					if(roomTypePicture.getSize()==0){
						errorMsgs.add("�п�ܹϤ�");
					}
				} catch (Exception e) {
					roomTypePicture=null;
					errorMsgs.add("�п�ܹϤ�");
				}
				//---------------------------------------------------------------
				InputStream roomTypePictureStream=roomTypePicture.getInputStream();
				roomTypePictureImg=new byte[roomTypePictureStream.available()];
				roomTypePictureStream.read(roomTypePictureImg);
				roomTypePictureStream.close(); roomTypePictureStream=null;
				//---------------------------------------------------------------
				
				String roomTypeContent =req.getParameter("roomTypeContent");
				if(roomTypeContent==null || roomTypeContent.length()==0){
					errorMsgs.add("�п�J�Ы����e");
				}
				//�B�z��ƿ��~���p�A���έ��Ƽg---------
				RoomTypeVO roomTypeVO=new RoomTypeVO();
				roomTypeVO.setHostelNo(hostelNo);
				roomTypeVO.setFacilityNo(facilityNo);
				roomTypeVO.setRoomTypeName(roomTypeName);
				roomTypeVO.setRoomTypeContain(roomTypeContain);
				roomTypeVO.setRoomTypePrice(roomTypePrice);
				roomTypeVO.setRoomTypePicture(roomTypePictureImg);
				roomTypeVO.setRoomTypeContent(roomTypeContent);
			
				if(!errorMsgs.isEmpty()){
					req.setAttribute("roomTypeVO", roomTypeVO);
					RequestDispatcher failureView =req.getRequestDispatcher("/roomType_modify.jsp");
					failureView.forward(req, res);
					return;
				}
			/***************************2.�}�l�s�W���***************************************/
				RoomTypeService roomTypeService =new RoomTypeService();
				roomTypeVO =roomTypeService.addRoomType(hostelNo, facilityNo, roomTypeName,
						roomTypeContain, roomTypePrice, roomTypePictureImg,roomTypeContent);
			/***************************3.�s�W����,�ǳ����(Send the Success view)***********/
			String url ="/roomType_modify.jsp";
			RequestDispatcher successView =req.getRequestDispatcher(url);
			successView.forward(req, res);
			
			}catch(Exception e){
				errorMsgs.add("��Ƥ���"+e.getMessage());
				RequestDispatcher failureView=req.getRequestDispatcher("/roomType_modifyd.jsp");
				failureView.forward(req, res);
			}
			
		}//insert
		
		
		if("delete".equals(action)){
			List<String> errorMsgs=new LinkedList<String>();
			
			req.setAttribute("errorMsgs", errorMsgs);
// �e�X�R�����ӷ��������|: �i�ର�i/roomType/roomType_listAll.jsp�j
// �� �i/hostel/listRoomTypes_ByHostelNo.jsp�j �� �i /hostel/hostel_listHostelsForRoomTypes.jsp�j	
// OR �i/roomType/roomType_compositeQuery.jsp�j
			String requestURL=req.getParameter("requestURL");
			System.out.println("get on delete: "+requestURL);
//			String whichPage=req.getParameter("whichPage");
			
			try{
				/***************************1.�����ШD�Ѽ�***************************************/
				Integer roomTypeNo=new Integer(req.getParameter("roomTypeNo"));
				/***************************2.�}�l�R�����***************************************/
				RoomTypeService roomTypeService =new RoomTypeService();
				roomTypeService.deleteRoomType(roomTypeNo);
				/***************************3.�R������,�ǳ����(Send the Success view)***********/								
//				String url="/roomType/roomType_listAll.jsp";
//				RequestDispatcher successView =req.getRequestDispatcher(url);
//				successView.forward(req, res);
				//����
				RoomTypeVO roomTypeVO=roomTypeService.getOneRoomType(roomTypeNo);
				HostelService hoslteService=new HostelService();
				
				//��set���S�w����
				if(requestURL.equals("/hostel/hostel_listRoomTypes.jsp")
				|| requestURL.equals("/hostel/hostel_listHostelsForRoomTypes.jsp")){
					req.setAttribute("listRoomTypes_ByHostelNo",
					hoslteService.getRoomTypesByHostelNo(roomTypeVO.getHostelNo()) );
				}
				if(requestURL.equals("/roomType/roomType_CompositeQuery.jsp")){
					HttpSession session =req.getSession();
					Map<String,String[]> map= (Map<String, String[]>)session.getAttribute("map");
					List<RoomTypeVO> list = roomTypeService.getAll(map);
					req.setAttribute("listRoomTypes_ByCompositeQuery", list);
				}
				// �e�X�R�����ӷ��������ĴX��(�u�Ω�:istAllEmp.jsp)
//				String url=requestURL+"?whichPage="+whichPage;
				String url=requestURL;
				// �R�����\��,���^�e�X�R�����ӷ�����
				RequestDispatcher successView =req.getRequestDispatcher(url);
				successView.forward(req, res);
			}catch(Exception e){
				errorMsgs.add("�R����ƥ���"+e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/roomType/roomType_listAll.jsp");
				failureView.forward(req, res);
			}
		}//delete
		
		//from composite query
		if("listRoomTypes_ByCompositeQuery_A".equals(action)){
			
//			String requestURL=req.getParameter("requestURL");
//			System.out.println("get on composite "+requestURL);
			List<String> errorMsgs=new LinkedList<String>();
			
			req.setAttribute("errorMsgs", errorMsgs);
			try {
			/************1.�N��J����ରMap*************/ 
				//�ĥ�Map<String,String[]> getParameterMap()����k 
				//�`�N:an immutable java.util.Map 
//			Map<String, String[]> map=req.getParameterMap();
				HttpSession session =req.getSession();
			//����b���A�Preference errorMsgs to errorMsgs list,so everytime change can
			//refer to req's errorMsgs
				Map<String, String[]> map=(Map<String, String[]>) session.getAttribute("map");//null
			//req.getParameterMap->immutalbe map->����ק藍���ܰ�(�x�s�A���ܰO�����m)
				HashMap<String, String[]> map1=(HashMap<String, String[]>)req.getParameterMap();
			//�G�n�νƻs���覡�A��@��map1�����e�ƻs��@�ӥi�ܰʩʪ�map�A���x�s��session
				HashMap<String, String[]> map2=new HashMap<String, String[]>();
				map2=(HashMap<String, String[]>) map1.clone();
			//or HashMap<String, String[]> map2 
			//	 = new HashMap<String, String[]>(map1);
				
				session.setAttribute("map", map2);
				map=(HashMap<String, String[]>)req.getParameterMap();
//				System.out.println("map: "+map.size());
			/************2.�}�l�ƦX�d��******************/
				RoomTypeService roomTypeService=new RoomTypeService();
				List<RoomTypeVO> list = roomTypeService.getAll(map);
				System.out.println("list: "+list);
				if(list.size()==0){
					errorMsgs.add("�d�L���");
				}
				if(!errorMsgs.isEmpty()){
					RequestDispatcher failureView =req.getRequestDispatcher("/roomType/roomType_select_page.jsp");
					failureView.forward(req, res);
					return;
				}
			/************3.�d�ߧ���,�ǳ����(Send the Success view)************/	
				req.setAttribute("listRoomTypes_ByCompositeQuery", list);
				String url="/roomType/roomType_CompositeQuery.jsp";
				RequestDispatcher successView=req.getRequestDispatcher(url);
				successView.forward(req, res);
				
			} catch (Exception e) {
				errorMsgs.add("�d�߸�ƥX�{���~ "+e.getMessage());
				RequestDispatcher failureView =req
						.getRequestDispatcher("/roomType/roomType_CompositeQuery.jsp");
				failureView.forward(req, res);
			}
			
		}//list by compositeQuery
		
	}//doPost
}
