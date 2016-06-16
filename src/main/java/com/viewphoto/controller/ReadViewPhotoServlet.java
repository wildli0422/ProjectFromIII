package com.viewphoto.controller;

import java.io.*;
import java.sql.*;
import java.io.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.sql.DataSource;




@WebServlet("/viewphoto/readviewphoto.do")
public class ReadViewPhotoServlet extends HttpServlet {

	Connection con;

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		res.setContentType("image/gif");
		req.setCharacterEncoding("UTF-8");
		ServletOutputStream out = res.getOutputStream();

		try {
			String viewpicno=req.getParameter("viewpicno");
			String viewpicno1=new String (viewpicno.getBytes("ISO-8859-1"),"UTF-8");
			Statement stmt = con.createStatement();
			ResultSet rs=stmt.executeQuery("SELECT VIEWPIC FROM VIEWPHOTO WHERE VIEWPICNO = '"+viewpicno1+"'");
			

			if (rs.next()) {
				BufferedInputStream in = new BufferedInputStream(rs.getBinaryStream("VIEWPIC"));
				byte[] buf = new byte[4 * 1024]; // 4K buffer
				int len;
				while ((len = in.read(buf)) != -1) {
					out.write(buf, 0, len);
				}
				in.close();
			} else {
				res.sendError(HttpServletResponse.SC_NOT_FOUND);
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void init() throws ServletException {
		
		try {
			Context ctx = new javax.naming.InitialContext();//
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/ZA105G3");//
			con = ds.getConnection();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//
	}

	public void destroy() {
		try {
			if (con != null) con.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

}
