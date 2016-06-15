package com.hostel.controller;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet("/hostel/GetHostelPicture")
public class GetHostelPicture extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/ZA105G3");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String contentType="image/jpeg";
		response.setContentType(contentType);
		ServletOutputStream out = response.getOutputStream();
		String sql = "select hostelpicture from hostel where hostelno =?";
		Integer pkNo=new Integer(request.getParameter("pkNo"));
		try {
			Connection con = ds.getConnection();
			PreparedStatement pstat=con.prepareStatement(sql);
			pstat.setInt(1, pkNo);
			ResultSet rs=pstat.executeQuery();
			if(rs.next()){
				BufferedInputStream bin= new BufferedInputStream(rs.getBinaryStream(1));
				byte[]buf=new byte[4*1024];
				int len;
				while((len=bin.read(buf))!=-1){
					out.write(buf,0,len);
				}
				bin.close();
			}
				
				con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
