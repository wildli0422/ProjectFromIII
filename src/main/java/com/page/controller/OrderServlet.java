package com.page.controller;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;





import com.cart.model.RoomTypeCartVO;
import com.hostelOrder.model.HostelOrderService;
import com.hostelOrder.model.HostelOrderVO;

public class OrderServlet extends HttpServlet{
	
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
		
		if("insertNewOrd".equals(action)){
			
			
//			try {
				/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
				Integer tenantNo=new Integer(req.getParameter("tenantNo"));
				Integer hostelNo=new Integer(req.getParameter("hostelNo"));
				Integer tenantScore=new Integer(req.getParameter("tenantScore"));
				Integer hostelScore=new Integer(req.getParameter("hostelScore"));
				Integer customerQuantity=new Integer(req.getParameter("customerQuantity"));
				String orderState=req.getParameter("orderState");
				String paymentState=req.getParameter("paymentState");
				String paymentWay=req.getParameter("paymentWay");
				String hostelComment=req.getParameter("hostelComment");
				String orderRemark =req.getParameter("orderRemark");
				
				java.sql.Date hostelOrderDate = null;
				try {
					hostelOrderDate = java.sql.Date.valueOf(req.getParameter("hostelOrderDate").trim());
				} catch (IllegalArgumentException e) {
					hostelOrderDate=new java.sql.Date(System.currentTimeMillis());
				}
//				Date checkinDate=Date.valueOf(req.getParameter("checkinDate"));
//				Date checkoutDate=Date.valueOf(req.getParameter("checkoutDate"));
				/***************************2.�}�l�d�߸��*****************************************/
				HostelOrderService hostelOrderService=new HostelOrderService();
				HostelOrderVO hostelOrderVO=hostelOrderService.addNewOrder(hostelNo, tenantNo, hostelOrderDate, hostelScore, 
						hostelComment, tenantScore, customerQuantity, paymentWay, paymentState, 
						orderRemark, orderState, cartList);
				req.setAttribute("hostelOrderVO", hostelOrderVO);
//				MailService mailService=new MailService();
//				mailService.sendMail("r4641230@gmail.com", "�q��", "���\�e�F!");	
				
				
				/***************************3.�d�ߧ���,�ǳ����(Send the Success view)*************/
				String url="/orderConfirm.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
//			} catch (Exception e) {
//				RequestDispatcher failureView = req.getRequestDispatcher("/cart.jsp");
//				failureView.forward(req, res);
//				e.getMessage();
//				e.printStackTrace();
//			}
		}
	}
}
