package com.page.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hostelOrder.model.HostelOrderService;
import com.hostelOrder.model.HostelOrderVO;


public class CalendarServlet extends HttpServlet{
	
	public void doGet(HttpServletRequest req,HttpServletResponse res) 
			throws ServletException,IOException{
		doPost(req, res);
	}
	
	public void doPost(HttpServletRequest req,HttpServletResponse res) 
			throws ServletException,IOException{
		
		req.setCharacterEncoding("UTF-8");
		
		
		HostelOrderVO hostelOrderVO=null;
		List<HostelOrderVO > orderList=null;
//		String date=req.getParameter("date").toString();
//		PrintWriter out=res.getWriter();
		
//		out.println(date);
		
		try {
			/***************************1.**********************/
			String hostelNo=req.getParameter("hostelNo");
			String date=req.getParameter("date").toString();
//			System.out.println("date-------------------"+date);
			Date date2=Date.valueOf(date);
			System.out.println("date2~~~~~~~"+date2.toString());
			
			//date session
			HttpSession session=req.getSession();
			session.setAttribute("date", date2.toString());
			/***************************2.*****************************************/
			HostelOrderService hostelOrderService=new HostelOrderService();
			Map<String,String[]> map =new TreeMap<String, String[]>();
			map.put("hostelOrderDate",new String[]{date2.toString()} );
			map.put("hostelNo", new String[]{hostelNo});
//			orderList=hostelOrderService.getAllByHostelNo(hostelNo);
			
			orderList=hostelOrderService.getAll(map);
//			System.out.println(orderList);
			/***************************3.(Send the Success view)*************/
			req.setAttribute("hostelOrderList", orderList);
			String url="/check_In_Out.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
//			System.out.println("correct");
			successView.forward(req, res);
			
		} catch (Exception e) {
			RequestDispatcher failureView = req.getRequestDispatcher("/hostelCalendar.jsp");
			failureView.forward(req, res);
			e.getMessage();
			e.printStackTrace();
		}
		
	}
}
