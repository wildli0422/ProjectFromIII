package com.page.controller;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cart.model.RoomTypeCartVO;

public class CheckServlet extends HttpServlet{
	
	public void doGet(HttpServletRequest req,HttpServletResponse res) 
			throws ServletException,IOException{
		doPost(req, res);
	}
	
	public void doPost(HttpServletRequest req,HttpServletResponse res) 
			throws ServletException,IOException{
		
		req.setCharacterEncoding("UTF-8");
		HttpSession session=req.getSession();
		Vector<RoomTypeCartVO> cartList=(Vector<RoomTypeCartVO>)session.getAttribute("cartList");
		String action=req.getParameter("action");
		
		if("ADD".equals(action)){
			
			boolean match=false;
			
			
			try {
				/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
				Integer roomTypeNo=new Integer(req.getParameter("roomTypeNo"));
				String roomTypeName=req.getParameter("roomTypeName");
				Integer roomTypePrice=new Integer(req.getParameter("roomTypePrice"));
				Integer roomQuantity=new Integer(req.getParameter("quantity"));
				java.sql.Timestamp checkInDate = null;
				try {
					checkInDate = java.sql.Timestamp.valueOf(req.getParameter("checkinDate").trim());
				} catch (IllegalArgumentException e) {
					checkInDate=new java.sql.Timestamp(System.currentTimeMillis());
				}
				java.sql.Timestamp checkOutDate = null;
				try {
					checkOutDate = java.sql.Timestamp.valueOf(req.getParameter("checkoutDate").trim());
					
				} catch (IllegalArgumentException e) {
					checkOutDate=new java.sql.Timestamp(System.currentTimeMillis());
				}
//				java.sql.Date checkinDate=java.sql.Date.valueOf(req.getParameter("checkinDate"));
//				java.sql.Date checkoutDate=java.sql.Date.valueOf(req.getParameter("checkoutDate"));
				/***************************2.�}�l�d�߸��*****************************************/
				RoomTypeCartVO roomTypeCartVO=new RoomTypeCartVO();
				
				roomTypeCartVO.setRoomTypeNo(roomTypeNo);
				roomTypeCartVO.setRoomTypeName(roomTypeName);
				roomTypeCartVO.setRoomQuantity(roomQuantity);
				roomTypeCartVO.setRoomTypePrice(roomTypePrice);
				roomTypeCartVO.setCheckInDate(checkInDate);
				roomTypeCartVO.setCheckOutDate(checkOutDate);
				
				if(cartList==null){
					cartList=new Vector<RoomTypeCartVO>();
					cartList.add(roomTypeCartVO);
				}else{
					for(int i=0;i<cartList.size();i++){
						RoomTypeCartVO cart=cartList.get(i);
						
						if(cart.getRoomTypeName().equals(roomTypeCartVO.getRoomTypeName())){
							cart.setRoomQuantity(cart.getRoomQuantity()+roomTypeCartVO.getRoomQuantity());
							cartList.setElementAt(cart, i);
							match=true;
						}
					}//for
					
					if(!match){
						cartList.add(roomTypeCartVO);
					}
				}
				
				/***************************3.�d�ߧ���,�ǳ����(Send the Success view)*************/
				session.setAttribute("cartList", cartList);
				String url="/order_page.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
			} catch (Exception e) {
				RequestDispatcher failureView = req.getRequestDispatcher("/checkCalendar_home.jsp");
				failureView.forward(req, res);
				e.getMessage();
				e.printStackTrace();
			}
		}
		
		if("DELETE".equals(action)){
			String del=req.getParameter("del");
			int index=Integer.parseInt(del);
			cartList.remove(index);
			
			session.setAttribute("cartList", cartList);
			String url="/cart.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
		}
		
		if("CHECKOUT".equals(action)){
			int total=0;
			RoomTypeCartVO roomTypeCartVO=new RoomTypeCartVO();
//			Integer roomQuantity=new Integer(req.getParameter("roomQuantity"));
//			System.out.println(roomQuantity);
			for(int i=0;i<cartList.size();i++){
				roomTypeCartVO=cartList.get(i);
//				if(roomTypeCartVO.getRoomQuantity()!=roomQuantity){
//					roomTypeCartVO.setRoomQuantity(roomQuantity);
//				}
				int singlePrice=roomTypeCartVO.getRoomTypePrice();
				int num=roomTypeCartVO.getRoomQuantity();
				total+=singlePrice*num;
			}
			
			String result=String.valueOf(total);
			req.setAttribute("result", result);
			
			session.setAttribute("cartList", cartList);
			String url="/order.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
		}
		
	}//do Post
}
