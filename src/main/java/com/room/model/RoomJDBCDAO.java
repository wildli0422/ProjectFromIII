package com.room.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.roomType.model.RoomTypeVO;

public class RoomJDBCDAO implements RoomDAO_interface{
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "ZA105G3";
	String passwd = "ZA105G3";
	
		private static final String INSERT_STMT = 
			"INSERT INTO room VALUES (room_seq.NEXTVAL, ?, ?, ?)";
		private static final String GET_ALL_STMT = 
			"SELECT * FROM room order by roomNo";
		private static final String GET_ONE_STMT = 
			"SELECT * FROM room where roomNo = ?";
		private static final String DELETE = 
			"DELETE FROM room where roomNo = ?";
		private static final String UPDATE = 
			"UPDATE room set hostelNo=?,roomTypeNo=?,roomState=?"
			+ " where roomNo = ?";
		
		
		
		@Override
		public void insert(RoomVO roomVo) {
			
			Connection con = null;
			PreparedStatement pstmt = null;
			
			try {

				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(INSERT_STMT);

				pstmt.setInt(1, roomVo.getHostelNo());
				pstmt.setInt(2, roomVo.getRoomTypeNo());
				pstmt.setString(3, roomVo.getRoomState());

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
		}//insert----------------------------------------
		
		@Override
		public void update(RoomVO roomVo) {
			
			Connection con = null;
			PreparedStatement pstmt = null;

			try {

				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(UPDATE);

				pstmt.setInt(1, roomVo.getHostelNo());
				pstmt.setInt(2, roomVo.getRoomTypeNo());
				pstmt.setString(3, roomVo.getRoomState());
				pstmt.setInt(4, roomVo.getRoomNo());

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
		public void delete(Integer roomNo) {
			
			Connection con = null;
			PreparedStatement pstmt = null;

			try {

				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(DELETE);

				pstmt.setInt(1, roomNo);

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
		}//delete--------------------------------
		
		@Override
		public RoomVO findByPrimaryKey(Integer roomNo) {
			
			RoomVO roomVO = null;
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {

				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(GET_ONE_STMT);

				pstmt.setInt(1, roomNo);

				rs = pstmt.executeQuery();

				while (rs.next()) {
					// empVo �]�٬� Domain objects
					roomVO=new RoomVO();
					roomVO.setRoomNo(rs.getInt("roomNo"));
					roomVO.setRoomTypeNo(rs.getInt("roomTypeNo"));
					roomVO.setHostelNo(rs.getInt("hostelNo"));
					roomVO.setRoomState(rs.getString("roomState"));
					
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
			return roomVO;
		}
		@Override
		public List<RoomVO> getAll() {
			
			List<RoomVO> list = new ArrayList<RoomVO>();
			RoomVO roomVO = null;

			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {

				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(GET_ALL_STMT);
				rs = pstmt.executeQuery();

				while (rs.next()) {
					roomVO=new RoomVO();
					roomVO.setRoomNo(rs.getInt("roomNo"));
					roomVO.setRoomTypeNo(rs.getInt("roomTypeNo"));
					roomVO.setHostelNo(rs.getInt("hostelNo"));
					roomVO.setRoomState(rs.getString("roomState"));
					list.add(roomVO);
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
			RoomJDBCDAO dao =new RoomJDBCDAO();
			
			//insert
			RoomVO roomVO1=new RoomVO();
			roomVO1.setHostelNo(2001);
			roomVO1.setRoomTypeNo(3001);
			roomVO1.setRoomState("�ũ�");
			dao.insert(roomVO1);
			
			//update
			RoomVO roomVO2=new RoomVO();
			roomVO2.setRoomNo(5001);
			roomVO2.setHostelNo(2001);
			roomVO2.setRoomTypeNo(3001);
			roomVO2.setRoomState("�ũ�test");
			dao.update(roomVO2);
			
			//delete
//			dao.delete(5001);
			
			//select
			System.out.println("first: ");
			RoomVO roomVO3=dao.findByPrimaryKey(5001);
			System.out.print(roomVO3.getRoomNo()+",");
			System.out.print(roomVO3.getHostelNo()+",");
			System.out.print(roomVO3.getRoomTypeNo()+",");
			System.out.println(roomVO3.getRoomState()+",");
			
			System.out.println("list: ");
			List<RoomVO> list=dao.getAll();
			for(RoomVO room:list){
				System.out.print(room.getRoomNo()+",");
				System.out.print(room.getHostelNo()+",");
				System.out.print(room.getRoomTypeNo()+",");
				System.out.println(room.getRoomState()+",");
				
			}
		}

		@Override
		public void insertByRoomType(RoomVO roomVO, Connection con) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void deleteByRoomType(RoomVO roomVO, Connection con) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public List<RoomVO> getAllByRoomTypeNo(Integer roomTypeNo) {
			// TODO Auto-generated method stub
			return null;
		}
		
}
