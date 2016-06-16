package com.hostelOrder.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hostelOrder.model.HostelOrderService;

@WebServlet("/CommentServlet")
public class CommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String comment = req.getParameter("comment");
		Integer hostelScore =new Integer(req.getParameter("hostelScore"));
		Integer hostelOrderNo = new Integer(req.getParameter("hostelOrderNo"));
		HostelOrderService hoService = new HostelOrderService();
		hoService.updateComment(hostelOrderNo, comment,hostelScore);
		
		RequestDispatcher patcher= req.getRequestDispatcher("/tenant/tenant_One.jsp");
		patcher.forward(req, res);
	}

}
