

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import jxl.*;
import jxl.read.biff.BiffException;
/**
 * Servlet implementation class InsertFromExcelV2
 */
@WebServlet("/InsertFromExcel")
public class InsertFromExcel extends HttpServlet {
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
		PrintWriter out = response.getWriter();
		ServletContext context = getServletContext();	
		String excelPath = context.getInitParameter("ExcelFile");
		Connection con=null;
		Statement statForMeta=null;
		ResultSet rsForMeta=null;
		ResultSetMetaData rsMeta=null;
		Sheet handlingSheet = null;
		int sheetRows=0;
		int sheetColumns=0;
		String sheetName=null;
		try {
			Workbook execleBook=Workbook.getWorkbook(context.getResourceAsStream(excelPath));
			int sheetNum=execleBook.getNumberOfSheets();
			for(int i =0;i<sheetNum;i++) //run for every sheet ! 
			{
				handlingSheet =execleBook.getSheet(i);
				sheetName=handlingSheet.getName();
				System.out.println("sheetName = "+sheetName);
				if("template".equalsIgnoreCase(sheetName)){
					out.println("First time run this project , InitDB ok , wait 10 second Redirect to HomePage");
					response.setHeader ("refresh", "5;URL=HomePage.jsp");
					return;
				}
				sheetRows = handlingSheet.getRows();
				sheetColumns = handlingSheet.getColumns();
				String getAllQuery="select * from "+sheetName;
				con=ds.getConnection();
				statForMeta=con.createStatement();
				
				rsForMeta=statForMeta.executeQuery(getAllQuery);
				rsMeta=rsForMeta.getMetaData();
				Cell[] handlingRow=null;
//				out.println("rsMeta.getColumnCount() = "+rsMeta.getColumnCount());
				for(int j =1;j<sheetRows;j++) //frist row is columnName not data
				{
					handlingRow=handlingSheet.getRow(j);
					insertFromRow(handlingRow, rsMeta, con, sheetName, out);
				}
				
				con.close();
			}
		} 
		catch (Exception e) {
			// TODO Auto-generated catch blocksys
			System.out.println("yo something wrong ? ");
			e.printStackTrace();
			try{con.close();}
			catch(SQLException ee){
				System.out.println("con cant not close !");
			}
		}		finally{
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
			System.out.println("connection close problem !");
			}
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	protected void insertFromRow(Cell[] handlingRow ,ResultSetMetaData rsMeta,Connection con,String sheetName,PrintWriter out) throws SQLException,IOException 
	{

		ServletContext context = getServletContext();	
		StringBuffer insertSqlSB=new StringBuffer();
		insertSqlSB.append(" insert into "+sheetName+"(");
		
		for(int i =1;i<=rsMeta.getColumnCount();i++)
		{
			if(i==1){
			insertSqlSB.append(rsMeta.getColumnName(i));
			}
			else{
			insertSqlSB.append(","+rsMeta.getColumnName(i));
			}
		}
		insertSqlSB.append(") values(");
//		for(int i =1;i<=rsMeta.getColumnCount();i++){
//			if((i==1)&&!("DATE".equalsIgnoreCase(rsMeta.getColumnTypeName(i)))){
//				
//				insertSqlSB.append("?");
//			}else if((i==1)&&("DATE".equalsIgnoreCase(rsMeta.getColumnTypeName(i)))){
//				insertSqlSB.append("TO_DATE(?,'YYYY/MM/DD')");
//			}else if((i!=1)&&("DATE".equalsIgnoreCase(rsMeta.getColumnTypeName(i)))){
//				insertSqlSB.append(",TO_DATE(?,'YYYY/MM/DD')");
//			}
//			else {
//				insertSqlSB.append(",?");
//			}
//		}
		for(int i =1;i<=rsMeta.getColumnCount();i++){
			if(i==1){
				insertSqlSB.append("?");
			}
			else {
				insertSqlSB.append(",?");
			}
		}
		insertSqlSB.append(")");
		java.sql.Date sqlDate=null;
		Timestamp sqlTimestamp=null;
		PreparedStatement pStat = con.prepareStatement(insertSqlSB.toString());
		System.out.println(insertSqlSB.toString());
		for(int i =1;i<=rsMeta.getColumnCount();i++){
			String columnType=rsMeta.getColumnTypeName(i);
			if(!"BLOB".equalsIgnoreCase(columnType)){
				if("use_seq".equalsIgnoreCase(handlingRow[i-1].getContents().trim()))
				{
					String sql="select "+rsMeta.getColumnName(i)+"_seq.nextval from dual";
//					System.out.println("SQL = "+sql);
					Statement stat=con.createStatement();
					ResultSet rs =stat.executeQuery(sql);
					rs.next();
					String sequenceNo = rs.getString(1);
					System.out.println("sequenceNo = "+sequenceNo);
					pStat.setString(i, sequenceNo);
				}else if("DATE".equalsIgnoreCase(columnType)){
//			   SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//			   SimpleDateFormat format = new SimpleDateFormat("dd-MM'月' -yy");
			   SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			   try{
				   	System.out.println("parse this string to Date => "+handlingRow[i-1].getContents().trim());
			      java.util.Date parsed=  format.parse(handlingRow[i-1].getContents().trim());	
			      sqlDate = new java.sql.Date(parsed.getTime());
			      System.out.println("sqlDate = "+sqlDate);
			   }
			   catch(ParseException e){
			      System.out.println("parseError!");
			      e.printStackTrace();
			        			}
					pStat.setDate(i,sqlDate);
//			   	pStat.setDate(i,new java.sql.Date(System.currentTimeMillis()));
				}else if("TIMESTAMP".equalsIgnoreCase(columnType)){

					   SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
					   try{
						   	System.out.println("parse this string to Date => "+handlingRow[i-1].getContents().trim());
					      java.util.Date parsed=  format.parse(handlingRow[i-1].getContents().trim());	
					      sqlDate = new java.sql.Date(parsed.getTime());
					      sqlTimestamp=new java.sql.Timestamp(parsed.getTime());
					      System.out.println("sqlTimestamp = "+sqlTimestamp);
					   }
					   catch(ParseException e){
					      System.out.println("parseError!");
					      e.printStackTrace();
					        			}
							pStat.setTimestamp(i,sqlTimestamp );
						}
				else{
				pStat.setString(i, handlingRow[i-1].getContents().trim());
				}
			}else{
				String picName=handlingRow[i-1].getContents().trim();
				String picPath="/WEB-INF/data/picFolder/" +sheetName+"/"+picName;
				InputStream in = context.getResourceAsStream(picPath);
				pStat.setBinaryStream(i, in);
				in.close();
			}
		}
		pStat.execute();
		
	}
}
