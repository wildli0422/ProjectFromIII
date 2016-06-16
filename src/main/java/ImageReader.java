

import java.io.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.sql.DataSource;

@WebServlet("/ImageReader")
public class ImageReader extends HttpServlet {

	Connection con;

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		res.setContentType("image/gif");
		ServletOutputStream out = res.getOutputStream();

		try {
			Statement stmt = con.createStatement();
			String hostelNo=req.getParameter("hostelNo");
			String hostelPicNo=req.getParameter("hostelPicNo");
			String roomTypePicNo=req.getParameter("roomTypePicNo");
			String roomTypeNo=req.getParameter("roomTypeNo");
			String tenantNo=req.getParameter("tenantNo");
			String empNo=req.getParameter("empNo");
			/////////////////////////////////////////////////
			//hostel
			if(hostelNo!=null){
				String hostelPicture=req.getParameter("hostelPicture");
				String dealerVerify=req.getParameter("dealerVerify");
				String hostelNo2=new String(hostelNo.getBytes("ISO-8859-1"),"Big5");
				
				if(hostelPicture!=null){
					ResultSet rs = stmt.executeQuery(
						"SELECT hostelPicture FROM hostel WHERE hostelNo ='"+hostelNo2+"' " );
					if (rs.next()) {
						BufferedInputStream in = new BufferedInputStream(rs.getBinaryStream("hostelPicture"));
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
				}else if(dealerVerify!=null){
					ResultSet rs = stmt.executeQuery(
							"SELECT dealerVerify FROM hostel WHERE hostelNo ='"+hostelNo2+"' " );
						if (rs.next()) {
							BufferedInputStream in = new BufferedInputStream(rs.getBinaryStream("dealerVerify"));
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
				}//else if
			}//hostelNo not null
			//hostelPic
			if(hostelPicNo!=null){
				String hostelPicNo2=new String(hostelPicNo.getBytes("ISO-8859-1"),"Big5");
				ResultSet rs = stmt.executeQuery(
						"SELECT hostelPhoto FROM hostelPic WHERE hostelPicNo ='"+hostelPicNo2+"' " );
					if (rs.next()) {
						BufferedInputStream in = new BufferedInputStream(rs.getBinaryStream("hostelPhoto"));
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
			}//hostePicNo not null  
			
			if(roomTypePicNo!=null){
				String roomTypePicNo2=new String(roomTypePicNo.getBytes("ISO-8859-1"),"Big5");
				ResultSet rs = stmt.executeQuery(
						"SELECT roomTypePhoto FROM roomTypePic WHERE roomTypePicNo ='"+roomTypePicNo2+"' " );
					if (rs.next()) {
						BufferedInputStream in = new BufferedInputStream(rs.getBinaryStream("roomTypePhoto"));
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
			}//hostePicNo not null 
			if(roomTypeNo!=null){
				String roomTypeNo2=new String(roomTypeNo.getBytes("ISO-8859-1"),"Big5");
				ResultSet rs = stmt.executeQuery(
						"SELECT roomTypePicture FROM roomType WHERE roomTypeNo ='"+roomTypeNo2+"' " );
					if (rs.next()) {
						BufferedInputStream in = new BufferedInputStream(rs.getBinaryStream("roomTypePicture"));
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
			}
			if(tenantNo!=null) {
				String tenantNo2=new String(tenantNo.getBytes("ISO-8859-1"),"Big5");
				ResultSet rs = stmt.executeQuery(
						"SELECT tenantPic FROM tenant WHERE tenantNo ='"+tenantNo2+"' " );
					if (rs.next()) {
						BufferedInputStream in = new BufferedInputStream(rs.getBinaryStream("tenantPic"));
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
			}
			
			if (empNo!=null) {
				ResultSet rs = stmt.executeQuery(
						"SELECT empPic FROM emp WHERE empNo ='"+empNo+"' " );
					if (rs.next()) {
						BufferedInputStream in = new BufferedInputStream(rs.getBinaryStream("empPic"));
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
			}
					
			
			stmt.close();
		}catch(IOException ie){
			System.out.println("img: "+ie+" "+ie.getMessage());
		}catch (Exception e) {
			System.out.println(e);
		}
	}

	public void init() throws ServletException {
		try {
			Context ctx = new javax.naming.InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/ZA105G3");
			con = ds.getConnection();
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void destroy() {
		try {
			if (con != null) con.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

}
