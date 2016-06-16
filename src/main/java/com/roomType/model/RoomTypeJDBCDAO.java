package com.roomType.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.room.model.RoomVO;
import com.roomTypePic.model.RoomTypePicVO;

import jdbc.util.CompositeQuery.CompositeQuery;




public class RoomTypeJDBCDAO implements RoomTypeDAO_interface{
	
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "ZA105G3";
	String passwd = "ZA105G3";
	
		private static final String INSERT_STMT = 
			"INSERT INTO roomType VALUES (roomType_seq.NEXTVAL, ?, ?, ?, ?, ?, ?)";
		private static final String GET_ALL_STMT = 
			"SELECT * FROM roomType order by roomTypeNo";
		private static final String GET_ONE_STMT = 
			"SELECT * FROM roomType where roomTypeNo = ?";
		private static final String DELETE = 
			"DELETE FROM roomType where roomTypeNo = ?";
		private static final String UPDATE = 
			"UPDATE roomType set hostelNo=?,facilityNo=?,roomTypeName=?,"
			+ "roomTypeContain=?,roomTypePrice=?,unBookNumber=?,"
			+ "maxUnBookNumber=?,roomTypeContent=? where roomTypeNo = ?";
		
		@Override
		public void insert(RoomTypeVO roomTypeVO) {
			
			Connection con = null;
			PreparedStatement pstmt = null;
			
			try {

				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(INSERT_STMT);

				pstmt.setInt(1, roomTypeVO.getHostelNo());
				pstmt.setInt(2, roomTypeVO.getFacilityNo());
				pstmt.setString(3, roomTypeVO.getRoomTypeName());
				pstmt.setDouble(4, roomTypeVO.getRoomTypeContain());
				pstmt.setDouble(5, roomTypeVO.getRoomTypePrice());
				pstmt.setString(6, roomTypeVO.getRoomTypeContent());
				

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
		public void update(RoomTypeVO roomTypeVO) {
			
			Connection con = null;
			PreparedStatement pstmt = null;

			try {

				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(UPDATE);

				pstmt.setInt(1, roomTypeVO.getHostelNo());
				pstmt.setInt(2, roomTypeVO.getFacilityNo());
				pstmt.setString(3, roomTypeVO.getRoomTypeName());
				pstmt.setDouble(4, roomTypeVO.getRoomTypeContain());
				pstmt.setDouble(5, roomTypeVO.getRoomTypePrice());
				pstmt.setString(6, roomTypeVO.getRoomTypeContent());
				pstmt.setInt(7, roomTypeVO.getRoomTypeNo());
				
				
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
		public void delete(Integer roomTypeNo) {
			
			Connection con = null;
			PreparedStatement pstmt = null;

			try {

				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(DELETE);

				pstmt.setInt(1, roomTypeNo);

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
		public RoomTypeVO findByPrimaryKey(Integer roomTypeNo) {
			
			RoomTypeVO roomTypeVO = null;
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {

				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(GET_ONE_STMT);

				pstmt.setInt(1, roomTypeNo);

				rs = pstmt.executeQuery();

				while (rs.next()) {
					// empVo 也稱為 Domain objects
					roomTypeVO=new RoomTypeVO();
					roomTypeVO.setRoomTypeNo(rs.getInt("roomTypeNo"));
					roomTypeVO.setFacilityNo(rs.getInt("facilityNo"));
					roomTypeVO.setHostelNo(rs.getInt("hostelNo"));
					roomTypeVO.setRoomTypeName(rs.getString("roomTypeName"));
					roomTypeVO.setRoomTypeContain(rs.getInt("roomTypeContain"));
					roomTypeVO.setRoomTypePrice(rs.getInt("roomTypePrice"));
					roomTypeVO.setRoomTypeContent(rs.getString("roomTypeContent"));
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
			return roomTypeVO;
		}
		@Override
		public List<RoomTypeVO> getAll() {
			
			List<RoomTypeVO> list = new ArrayList<RoomTypeVO>();
			RoomTypeVO roomTypeVO = null;

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
					roomTypeVO = new RoomTypeVO();
					roomTypeVO.setRoomTypeNo(rs.getInt("roomTypeNo"));
					roomTypeVO.setFacilityNo(rs.getInt("facilityNo"));
					roomTypeVO.setHostelNo(rs.getInt("hostelNo"));
					roomTypeVO.setRoomTypeName(rs.getString("roomTypeName"));
					roomTypeVO.setRoomTypeContain(rs.getInt("roomTypeContain"));
					roomTypeVO.setRoomTypePrice(rs.getInt("roomTypePrice"));
					roomTypeVO.setRoomTypeContent(rs.getString("roomTypeContent"));
					list.add(roomTypeVO); // Store the row in the list
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
		
		@Override
		public List<RoomTypeVO> getAll(Map<String, String[]> map) {
			Connection con=null;
			PreparedStatement pstmt=null;
			ResultSet rs=null;
			
			List<RoomTypeVO> list=new ArrayList<RoomTypeVO>();
			RoomTypeVO roomTypeVO=null;
			
			try {
				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				String finalSQL="select * from roomType"
						+CompositeQuery.get_WhereCondition(map)
						+"order by roomTypeNo";
				pstmt=con.prepareStatement(finalSQL);
				System.out.println("final sql(DAO) = "+finalSQL);
				rs=pstmt.executeQuery();
				while(rs.next()){
					roomTypeVO=new RoomTypeVO();
					roomTypeVO.setRoomTypeNo(rs.getInt("roomTypeNo"));
					roomTypeVO.setHostelNo(rs.getInt("hostelNo"));
					roomTypeVO.setFacilityNo(rs.getInt("facilityNo"));
					roomTypeVO.setRoomTypeContain(rs.getInt("roomTypeContain"));
					roomTypeVO.setRoomTypePrice(rs.getInt("roomTypePrice"));
					roomTypeVO.setRoomTypeName(rs.getString("roomTypeName"));
					roomTypeVO.setRoomTypeContent(rs.getString("roomTypeContent"));
					
					list.add(roomTypeVO);
				}
				
			} catch(ClassNotFoundException e){
				throw new RuntimeException("Couldn't load database driver. "
						+ e.getMessage());
			} catch (SQLException se) {
				throw new RuntimeException("A database error occured. "
						+ se.getMessage());
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
			RoomTypeJDBCDAO dao=new RoomTypeJDBCDAO();
			
			//insert
			RoomTypeVO roomTypeVO1=new RoomTypeVO();
			roomTypeVO1.setFacilityNo(4001);
			roomTypeVO1.setHostelNo(2001);
			roomTypeVO1.setRoomTypeName("name test");
			roomTypeVO1.setRoomTypeContain(5);
			roomTypeVO1.setRoomTypePrice(6666);
			roomTypeVO1.setRoomTypeContent("content test");
			
			dao.insert(roomTypeVO1);
			
			//update
			RoomTypeVO roomTypeVO2=new RoomTypeVO();
			roomTypeVO2.setRoomTypeNo(3001);
			roomTypeVO2.setFacilityNo(4001);
			roomTypeVO2.setHostelNo(2001);
			roomTypeVO2.setRoomTypeName("name test2");
			roomTypeVO2.setRoomTypeContain(0);
			roomTypeVO2.setRoomTypePrice(0);
			roomTypeVO2.setRoomTypeContent("content test");
			
			dao.update(roomTypeVO2);
			
			//delete
//			dao.delete(3001);
			
			//select
			System.out.println("first: ");
			RoomTypeVO roomTypeVO3=dao.findByPrimaryKey(3001);
			System.out.print(roomTypeVO3.getRoomTypeNo()+",");
			System.out.print(roomTypeVO3.getFacilityNo()+",");
			System.out.print(roomTypeVO3.getHostelNo()+",");
			System.out.print(roomTypeVO3.getRoomTypeName()+",");
			System.out.print(roomTypeVO3.getRoomTypeContain()+",");
			System.out.print(roomTypeVO3.getRoomTypePrice()+",");
			System.out.println(roomTypeVO3.getRoomTypeContent()+",");
			System.out.println("list: ");
			List<RoomTypeVO> list =dao.getAll();
			for(RoomTypeVO roomType:list){
				System.out.print(roomType.getRoomTypeNo()+",");
				System.out.print(roomType.getFacilityNo()+",");
				System.out.print(roomType.getHostelNo()+",");
				System.out.print(roomType.getRoomTypeName()+",");
				System.out.print(roomType.getRoomTypeContain()+",");
				System.out.print(roomType.getRoomTypePrice()+",");
				System.out.println(roomType.getRoomTypeContent()+",");
			}
			
			Map<String,String[]> map =new TreeMap<String, String[]>();
			map.put("roomTypeNo", new String[]{""});
			map.put("roomTypeName",new String[]{"name"});
			map.put("hostelNo", new String[]{"2001"});
			map.put("facilityNo", new String[]{"4001"});
			map.put("roomTypeContain", new String[]{"5"});
			map.put("roomTypePrice", new String[]{"0"});
			map.put("unBookNumber", new String[]{"2"});
			map.put("maxUnBookNumber", new String[]{"5"});
			map.put("roomTypeContent", new String[]{"content"});
			
			String finalSQL="select * from roomType"
					+CompositeQuery.get_WhereCondition(map)
					+"order by roomTypeNo";
			System.out.println("●●finalSQL = " + finalSQL);
			List<RoomTypeVO> list2 =dao.getAll(map);
			System.out.println("list2:"+list2.size());
			for(RoomTypeVO roomType:list2){
				System.out.print(roomType.getRoomTypeNo()+",");
				System.out.print(roomType.getFacilityNo()+",");
				System.out.print(roomType.getHostelNo()+",");
				System.out.print(roomType.getRoomTypeName()+",");
				System.out.print(roomType.getRoomTypeContain()+",");
				System.out.print(roomType.getRoomTypePrice()+",");
				System.out.println(roomType.getRoomTypeContent());
			}
			
		}



		@Override
		public List<RoomTypePicVO> findPicByRoomTypeNo(Integer roomTypeNo) {
			// TODO Auto-generated method stub
			return null;
		}


		@Override
		public void insertWithRoom(RoomTypeVO roomTypeVO, Integer roomQuantity) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public List<RoomVO> getRoomsByRoomType(Integer roomTypeNo) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<RoomTypeVO> likeByColumn(String columnName,
				String likeString) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public RoomTypeVO findByHostelNo(Integer hostelNo) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<RoomTypeVO> getAllByHostelNo(Integer hostelNo) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public RoomTypeVO findByFacilityNo(Integer facilityNo) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<RoomTypeVO> getAllByFacilityNo(Integer facilityNo) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public RoomTypeVO findByRoomTypeName(String roomTypeName) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<RoomTypeVO> getAllByRoomTypeName(String roomTypeName) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public RoomTypeVO findByRoomTypeContain(Integer roomTypeContain) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<RoomTypeVO> getAllByRoomTypeContain(Integer roomTypeContain) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public RoomTypeVO findByRoomTypePrice(Integer roomTypePrice) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<RoomTypeVO> getAllByRoomTypePrice(Integer roomTypePrice) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public RoomTypeVO findByRoomTypeContent(String roomTypeContent) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<RoomTypeVO> getAllByRoomTypeContent(String roomTypeContent) {
			// TODO Auto-generated method stub
			return null;
		}

		
}
