package tool;


import java.io.BufferedInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
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

@WebServlet("/tool/GetPhoto")
public class GetPhoto extends HttpServlet {
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
		if(request.getParameter("contentType")!=null){
			contentType=request.getParameter("contentType");
		}
		response.setContentType(contentType);
		ServletOutputStream out = response.getOutputStream();
		String tableName=request.getParameter("tableName");
		Integer pkNo=new Integer(request.getParameter("pkNo"));
		String pkName=null;
		StringBuffer sb= new StringBuffer();
		try{
		Connection con = ds.getConnection();
		DatabaseMetaData dbMetaData = con.getMetaData();
		ResultSet rs = dbMetaData.getPrimaryKeys(null, dbMetaData.getUserName(), tableName.toUpperCase());
		while(rs.next()){
			pkName=rs.getString(4);
		}
		
		rs =dbMetaData.getColumns(null, dbMetaData.getUserName(), tableName.toUpperCase(), null);
		String blobColumnName=null;
		while(rs.next()){
			if("BLOB".equalsIgnoreCase(rs.getString(6))){
				blobColumnName=rs.getString(4);
			}
		}
		sb.append("select "+blobColumnName+" from "+tableName+" where "+pkName+" = ? ");
//		System.out.println(sb.toString());
		PreparedStatement pstat=con.prepareStatement(sb.toString());
		pstat.setInt(1, pkNo);
		rs=pstat.executeQuery();
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
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
