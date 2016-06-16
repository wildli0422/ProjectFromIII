package com.page.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hostel.model.HostelVO;
import com.hostelOrder.model.HostelOrderService;
import com.hostelOrder.model.HostelOrderVO;
import com.room.model.RoomService;
import com.room.model.RoomVO;

public class VerifyServlet extends HttpServlet{
	
	public void doGet(HttpServletRequest req,HttpServletResponse res) 
			throws IOException,ServletException{
		doPost(req, res);
	}
	
	public void doPost(HttpServletRequest req,HttpServletResponse res) 
			throws IOException,ServletException{
		
		req.setCharacterEncoding("UTF-8");
		String action=req.getParameter("action");
		
		if("update".equals(action)){
			HostelOrderService hostelOrderService=new HostelOrderService();
			HostelOrderVO hostelOrderVO =null;
			RoomService roomService=new RoomService();
			RoomVO roomVO=null;
			
			try {
				System.out.println("update2!");
				/***************************1.�����ШD�Ѽ�****************************************/
				Integer hostelOrderNo=null;
				try {
					hostelOrderNo =new Integer(req.getParameter("hostelOrderNo"));
				} catch (Exception e) {
					e.getMessage();
				}
				
				Integer roomNo=null;
				try {
					roomNo=new Integer(req.getParameter("roomNo"));
				} catch (Exception e) {
					roomNo=0;
					e.getMessage();
				}
				
				Integer hostelOrderDetailNo=null;
				try {
					hostelOrderDetailNo=new Integer(req.getParameter("hostelOrderDetailNo"));
				} catch (Exception e) {
					hostelOrderDetailNo=0;
					e.getMessage();
				}
				String roomState =req.getParameter("roomState");
				System.out.println("!!!!!!! detail no:"+hostelOrderDetailNo);
//				System.out.println("!!!!!!!!!"+hostelOrderNo);
				String paymentState=req.getParameter("paymentState");
//				System.out.println("!!!!!!!!!"+paymentState);
				String orderState=req.getParameter("orderState");
//				System.out.println("!!!!!!!!!"+orderState);]
				
			
				roomVO=roomService.getOneRoom(roomNo);
				System.out.println("room!!!!!!!!"+roomNo);
				
				hostelOrderVO=hostelOrderService.getOneHostelOrder(hostelOrderNo);
//				hostelOrderVO.setPaymentState(paymentState);
//				hostelOrderVO.setOrderState(orderState);
				System.out.println("VO:"+hostelOrderVO);
				/***************************2.�}�l�ק���****************************************/
				//hostel order
				hostelOrderVO = hostelOrderService.updateHostelOrder(hostelOrderNo,
						hostelOrderVO.getHostelNo(), hostelOrderVO.getTenantNo(), hostelOrderVO.getHostelOrderDate(),
						hostelOrderVO.getHostelScore(),hostelOrderVO.getHostelComment(), 
						hostelOrderVO.getTenantScore(), hostelOrderVO.getCustomerQuantity(),
						hostelOrderVO.getPaymentWay(), paymentState,hostelOrderVO.getOrderRemark(),orderState);
				//room
				if(roomState.equals("清潔") || roomState.equals("空房")){
					roomVO =roomService.updateRoom(roomNo, roomVO.getHostelNo(),
							roomVO.getRoomTypeNo(), 0, roomState);
				}else{
					roomVO =roomService.updateRoom(roomNo, roomVO.getHostelNo(),
							roomVO.getRoomTypeNo(), hostelOrderDetailNo, roomState);
				}
				/***************************3.�ק粒��,�ǳ����(Send the Success view)************/
				System.out.println("correct~~~~~update");
				req.setAttribute("hostelOrderVO", hostelOrderVO);
				HttpSession session=req.getSession();
				String url="/cal.do?date="+session.getAttribute("date");
				System.out.println("url: "+url);
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
			} catch (Exception e) {
				RequestDispatcher failureView =req.getRequestDispatcher("/check_In_Out.jsp");
				failureView.forward(req, res);			
			}
			
		
		}//update order & room
		
		if("updateRoom".equals(action)){
			
			RoomService roomService=new RoomService();
			RoomVO roomVO=null;
			/***************************1.�����ШD�Ѽ�****************************************/
			try{
				Integer roomNo=null;
				try {
					roomNo=new Integer(req.getParameter("roomNo"));
				} catch (Exception e) {
					roomNo=0;
					e.getMessage();
				}
				
				Integer hostelOrderDetailNo=null;
				try {
					hostelOrderDetailNo=new Integer(req.getParameter("hostelOrderDetailNo"));
				} catch (Exception e) {
					hostelOrderDetailNo=0;
					e.getMessage();
				}
				String roomState =req.getParameter("roomState");
				
				/***************************2.�}�l�ק���****************************************/
				roomVO=roomService.getOneRoom(roomNo);
				roomService.updateRoom(roomNo, roomVO.getHostelNo(), roomVO.getRoomTypeNo(),
						hostelOrderDetailNo, roomState);
				/***************************3.�ק粒��,�ǳ����(Send the Success view)************/
				String url="/room/roomState.jsp?roomNo="+roomNo+"&roomTypeNo="+roomVO.getRoomTypeNo();
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
			}catch(Exception e){
				RequestDispatcher failureView =req.getRequestDispatcher("/check_In_Out.jsp");
				failureView.forward(req, res);
			}
		}
		
	}
}
