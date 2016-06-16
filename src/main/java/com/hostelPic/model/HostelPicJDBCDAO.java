package com.hostelPic.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;




public class HostelPicJDBCDAO implements HostelPicDAO_interface{
	
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "ZA105G3";
	String passwd = "ZA105G3";
	
		private static final String INSERT_STMT = 
			"INSERT INTO hostelPic VALUES (hostelPic_seq.NEXTVAL, ?, ?)";
		private static final String GET_ALL_STMT = 
			"SELECT * FROM hostelPic order by hostelPicNo";
		private static final String GET_ONE_STMT = 
			"SELECT * FROM hostelPic where hostelPicNo = ?";
		private static final String DELETE = 
			"DELETE FROM hostelPic where hostelPicNo = ?";
		private static final String UPDATE = 
			"UPDATE hostelPic set hostelNo=?,hostelPhoto=?  where hostelPicNo = ?";
		
		@Override
		public void insert(HostelPicVO hostelPicVO) {
			
			Connection con = null;
			PreparedStatement pstmt = null;
			
			try {

				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(INSERT_STMT);

				pstmt.setInt(1, hostelPicVO.getHostelNo());
				pstmt.setBytes(2, hostelPicVO.getHostelPhoto());

				pstmt.executeUpdate();

				// Handle any driver errors
			} catch (ClassNotFoundException e) {
				throw new RuntimeException("Couldn't load database driver. "
						+ e.getMessage());
				// Handle any SQL errors
			} catch (SQLException se) {
				throw new RuntimeException("A database error occured. "
						+ se.getMessage());
				// Clean up JDBC resources
			} finally {
				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException se) {
						se.printStackTrace(System.err);
					}
				}
				if (con != null) {
					try {
						con.close();
					} catch (Exception e) {
						e.printStackTrace(System.err);
					}
				}
			}
		}//insert-------------------------------
		
		@Override
		public void update(HostelPicVO hostelPicVO) {
			
			Connection con = null;
			PreparedStatement pstmt = null;

			try {

				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(UPDATE);

				pstmt.setInt(1, hostelPicVO.getHostelNo());
				pstmt.setBytes(2, hostelPicVO.getHostelPhoto());
				pstmt.setInt(3, hostelPicVO.getHostelPicNo());
				
				pstmt.executeUpdate();

				// Handle any driver errors
			} catch (ClassNotFoundException e) {
				throw new RuntimeException("Couldn't load database driver. "
						+ e.getMessage());
				// Handle any SQL errors
			} catch (SQLException se) {
				throw new RuntimeException("A database error occured. "
						+ se.getMessage());
				// Clean up JDBC resources
			} finally {
				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException se) {
						se.printStackTrace(System.err);
					}
				}
				if (con != null) {
					try {
						con.close();
					} catch (Exception e) {
						e.printStackTrace(System.err);
					}
				}
			}

		}//update-------------------------------------
		
		@Override
		public void delete(Integer hostelPicNo) {
			
			Connection con = null;
			PreparedStatement pstmt = null;

			try {

				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(DELETE);

				pstmt.setInt(1, hostelPicNo);

				pstmt.executeUpdate();

				// Handle any driver errors
			} catch (ClassNotFoundException e) {
				throw new RuntimeException("Couldn't load database driver. "
						+ e.getMessage());
				// Handle any SQL errors
			} catch (SQLException se) {
				throw new RuntimeException("A database error occured. "
						+ se.getMessage());
				// Clean up JDBC resources
			} finally {
				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException se) {
						se.printStackTrace(System.err);
					}
				}
				if (con != null) {
					try {
						con.close();
					} catch (Exception e) {
						e.printStackTrace(System.err);
					}
				}
			}

		}
		
		@Override
		public HostelPicVO findByPrimaryKey(Integer hostelPicNo) {
			
			HostelPicVO hostelPicVO = null;
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {

				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(GET_ONE_STMT);

				pstmt.setInt(1, hostelPicNo);

				rs = pstmt.executeQuery();

				while (rs.next()) {
					// empVo 也稱為 Domain objects
					hostelPicVO=new HostelPicVO();
					hostelPicVO.setHostelPicNo(rs.getInt("hostelPicNo"));
					hostelPicVO.setHostelNo(rs.getInt("hostelNo"));
					hostelPicVO.setHostelPhoto(rs.getBytes("hostelPhoto"));
				}

				// Handle any driver errors
			} catch (ClassNotFoundException e) {
				throw new RuntimeException("Couldn't load database driver. "
						+ e.getMessage());
				// Handle any SQL errors
			} catch (SQLException se) {
				throw new RuntimeException("A database error occured. "
						+ se.getMessage());
				// Clean up JDBC resources
			} finally {
				if (rs != null) {
					try {
						rs.close();
					} catch (SQLException se) {
						se.printStackTrace(System.err);
					}
				}
				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException se) {
						se.printStackTrace(System.err);
					}
				}
				if (con != null) {
					try {
						con.close();
					} catch (Exception e) {
						e.printStackTrace(System.err);
					}
				}
			}
			return hostelPicVO;
		}
		@Override
		public List<HostelPicVO> getAll() {
			
			List<HostelPicVO> list = new ArrayList<HostelPicVO>();
			HostelPicVO hostelPicVO = null;

			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {

				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(GET_ALL_STMT);
				rs = pstmt.executeQuery();

				while (rs.next()) {
					// empVO 也稱為 Domain objects
					hostelPicVO = new HostelPicVO();
					hostelPicVO.setHostelPicNo(rs.getInt("hostelPicNo"));
					hostelPicVO.setHostelNo(rs.getInt("hostelNo"));
					hostelPicVO.setHostelPhoto(rs.getBytes("hostelPhoto"));
					list.add(hostelPicVO); // Store the row in the list
				}

				// Handle any driver errors
			} catch (ClassNotFoundException e) {
				throw new RuntimeException("Couldn't load database driver. "
						+ e.getMessage());
				// Handle any SQL errors
			} catch (SQLException se) {
				throw new RuntimeException("A database error occured. "
						+ se.getMessage());
				// Clean up JDBC resources
			} finally {
				if (rs != null) {
					try {
						rs.close();
					} catch (SQLException se) {
						se.printStackTrace(System.err);
					}
				}
				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException se) {
						se.printStackTrace(System.err);
					}
				}
				if (con != null) {
					try {
						con.close();
					} catch (Exception e) {
						e.printStackTrace(System.err);
					}
				}
			}
			return list;
		}
		
		public static void main(String [] args){
			HostelPicJDBCDAO dao=new HostelPicJDBCDAO();
			
			//insert
			HostelPicVO hostelPicVO1=new HostelPicVO();
			hostelPicVO1.setHostelNo(2001);
			hostelPicVO1.setHostelPhoto(null);
			dao.insert(hostelPicVO1);
			
			//update
			HostelPicVO hostelPicVO2=new HostelPicVO();
			hostelPicVO2.setHostelPicNo(6001);
			hostelPicVO2.setHostelNo(2002);
			hostelPicVO2.setHostelPhoto(null);
			dao.update(hostelPicVO2);
			
			//delete
//			dao.delete(6001);
			
			//select
			System.out.println("first: ");
			HostelPicVO hostelVO3=dao.findByPrimaryKey(6001);
			System.out.print(hostelVO3.getHostelPicNo()+",");
			System.out.print(hostelVO3.getHostelNo()+",");
			System.out.println(hostelVO3.getHostelPhoto()+",");
			
			System.out.println("list: ");
			List<HostelPicVO> list =dao.getAll();
			for(HostelPicVO hostel:list){
				System.out.print(hostel.getHostelPicNo()+",");
				System.out.print(hostel.getHostelNo()+",");
				System.out.println(hostel.getHostelPhoto()+",");
			}
		}
		
		

}
