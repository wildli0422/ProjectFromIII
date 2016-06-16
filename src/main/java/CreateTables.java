

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;


@WebServlet("/CreateUser")
public class CreateTables extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static DataSource dsUser = null;
	static {
		try {
			Context ctx = new InitialContext();
			dsUser = (DataSource) ctx.lookup("java:comp/env/jdbc/ZA105G3");
			
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	protected void runSql(String sql ,DataSource datasource){
		Connection con=null;
		try {
			con=datasource.getConnection();
			Statement stat = con.createStatement();
			boolean b = stat.execute(sql);
			System.out.println(b);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("execute sql problem !");
		}
		finally{
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
			System.out.println("connection close problem !");
			}
		}
	}
	
	protected void readSqlFileAndRun(String sqlFilePath,DataSource datasource,PrintWriter out) throws IOException{
		
		ServletContext context = getServletContext();
		InputStreamReader inReader=new InputStreamReader(context.getResourceAsStream(sqlFilePath));
		StringBuffer sb=new StringBuffer();
		StringBuffer sbTotal=new StringBuffer();
		char tempChar='a';
		int charCode=0;
		while((charCode=inReader.read())!=-1){
			tempChar=(char) charCode;
			if(';'==tempChar)
			{
				runSql(sb.toString(),datasource);
				sbTotal.append(sb);
				sb=null;
				sb=new StringBuffer();
			}
			else{
				sb.append(tempChar);
			}
		}
		inReader.close();
		out.print(sbTotal.toString());
		
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out=response.getWriter();
		ServletContext context = getServletContext();
		String sqlFilePath = context.getInitParameter("sqlFile");
		out.print("---------------Run create table as za105G3------------");
		sqlFilePath = context.getInitParameter("sqlFileCreateTable");
		readSqlFileAndRun(sqlFilePath, dsUser, out);
		
		RequestDispatcher toInsert=request.getRequestDispatcher("/InsertFromExcel");
		toInsert.forward(request, response);
	}



	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
