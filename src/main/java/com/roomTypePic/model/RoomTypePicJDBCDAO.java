package com.roomTypePic.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;




public class RoomTypePicJDBCDAO implements RoomTypePicDAO_interface{
	
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "ZA105G3";
	String passwd = "ZA105G3";
	
		private static final String INSERT_STMT = 
			"INSERT INTO roomTypePic VALUES (roomTypePic_seq.NEXTVAL, ?, ?)";
		private static final String GET_ALL_STMT = 
			"SELECT * FROM roomTypePic order by roomTypePicNo";
		private static final String GET_ONE_STMT = 
			"SELECT * FROM roomTypePic where roomTypePicNo = ?";
		private static final String DELETE = 
			"DELETE FROM roomTypePic where roomTypePicNo = ?";
		private static final String UPDATE = 
			"UPDATE roomTypePic set roomTypeNo=?,roomTypePhoto=?  where roomTypePicNo = ?";
		
		@Override
		public void insert(RoomTypePicVO roomTypePicVO) {
			
			Connection con = null;
			PreparedStatement pstmt = null;
			
			try {

				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(INSERT_STMT);

				pstmt.setInt(1, roomTypePicVO.getRoomTypeNo());
				pstmt.setBytes(2, roomTypePicVO.getRoomTypePhoto());

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
		public void update(RoomTypePicVO roomTypePicVO) {
			
			Connection con = null;
			PreparedStatement pstmt = null;

			try {

				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(UPDATE);

				pstmt.setInt(1, roomTypePicVO.getRoomTypeNo());
				pstmt.setBytes(2, roomTypePicVO.getRoomTypePhoto());
				pstmt.setInt(3, roomTypePicVO.getRoomTypePicNo());
				
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
		public void delete(Integer roomTypePicNo) {
			
			Connection con = null;
			PreparedStatement pstmt = null;

			try {

				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(DELETE);

				pstmt.setInt(1, roomTypePicNo);

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
		public RoomTypePicVO findByPrimaryKey(Integer roomTypePicNo) {
			
			RoomTypePicVO roomTypePicVO = null;
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {

				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(GET_ONE_STMT);

				pstmt.setInt(1, roomTypePicNo);

				rs = pstmt.executeQuery();

				while (rs.next()) {
					// empVo �]�٬� Domain objects
					roomTypePicVO=new RoomTypePicVO();
					roomTypePicVO.setRoomTypePicNo(rs.getInt("roomTypePicNo"));
					roomTypePicVO.setRoomTypeNo(rs.getInt("roomTypeNo"));
					roomTypePicVO.setRoomTypePhoto(rs.getBytes("roomTypePhoto"));
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
			return roomTypePicVO;
		}
		@Override
		public List<RoomTypePicVO> getAll() {
			
			List<RoomTypePicVO> list = new ArrayList<RoomTypePicVO>();
			RoomTypePicVO roomTypePicVO = null;

			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {

				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(GET_ALL_STMT);
				rs = pstmt.executeQuery();

				while (rs.next()) {
					// empVO �]�٬� Domain objects
					roomTypePicVO = new RoomTypePicVO();
					roomTypePicVO.setRoomTypePicNo(rs.getInt("roomTypePicNo"));
					roomTypePicVO.setRoomTypeNo(rs.getInt("roomTypeNo"));
					roomTypePicVO.setRoomTypePhoto(rs.getBytes("roomTypePhoto"));
					list.add(roomTypePicVO); // Store the row in the list
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
			RoomTypePicJDBCDAO dao=new RoomTypePicJDBCDAO();
			
			//insert
			RoomTypePicVO roomTypePicVO1=new RoomTypePicVO();
			roomTypePicVO1.setRoomTypeNo(3002);
			roomTypePicVO1.setRoomTypePhoto(null);
			dao.insert(roomTypePicVO1);
			
			//update
//			RoomTypePicVO roomTypePicVO2=new RoomTypePicVO();
//			roomTypePicVO2.setRoomTypePicNo(7001);
//			roomTypePicVO2.setRoomTypeNo(3002);
//			roomTypePicVO2.setRoomTypePhoto(null);
//			dao.update(roomTypePicVO2);
			
			//delete
//			dao.delete(7001);
			
			//select
			System.out.println("first: ");
			RoomTypePicVO roomTypePicVO3=dao.findByPrimaryKey(7001);
			System.out.print(roomTypePicVO3.getRoomTypePicNo()+",");
			System.out.print(roomTypePicVO3.getRoomTypeNo()+",");
			System.out.println(roomTypePicVO3.getRoomTypePhoto()+",");
			
			System.out.println("list: ");
			List<RoomTypePicVO> list =dao.getAll();
			for(RoomTypePicVO roomTypePic:list){
				System.out.print(roomTypePic.getRoomTypePicNo()+",");
				System.out.print(roomTypePic.getRoomTypeNo()+",");
				System.out.println(roomTypePic.getRoomTypePhoto()+",");
			}
		}

		@Override
		public RoomTypePicVO findByRoomTypeNo(Integer roomTypeNo) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<RoomTypePicVO> getAllByRoomTypeNo(Integer roomTypeNo) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void deleteByRoomType(RoomTypePicVO roomTypePicVO, Connection con) {
			// TODO Auto-generated method stub
			
		}
		
		

}
