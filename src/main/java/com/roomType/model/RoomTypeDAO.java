package com.roomType.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;


import com.room.model.RoomDAO;
import com.room.model.RoomService;
import com.room.model.RoomVO;
import com.roomTypePic.model.RoomTypePicDAO;
import com.roomTypePic.model.RoomTypePicVO;

import jdbc.util.CompositeQuery.*;

public class RoomTypeDAO implements RoomTypeDAO_interface{
	
	private static DataSource ds=null;
	
	static{
		try {
			Context ctx=new InitialContext();
			ds=(DataSource)ctx.lookup("java:comp/env/jdbc/ZA105G3");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private static final String INSERT_STMT = 
			"INSERT INTO roomType VALUES (roomTypeNo_seq.NEXTVAL, ?, ?, ?, ?, ?, ?,?)";
		private static final String GET_ALL_STMT = 
			"SELECT * FROM roomType order by roomTypeNo";
		private static final String GET_ONE_STMT = 
			"SELECT * FROM roomType where roomTypeNo = ?";
		private static final String DELETE = 
			"DELETE FROM roomType where roomTypeNo = ?";
		private static final String UPDATE = 
			"UPDATE roomType set hostelNo=?,facilityNo=?,roomTypeName=?,"
			+ "roomTypeContain=?,roomTypePrice=?,roomTypePicture=?,roomTypeContent=? where roomTypeNo = ?";
		private static final String GET_PIC_STMT=
			"select * from roomTypePic where roomTypeNo=?";
		private static final String GET_ROOM_STMT=
			"select * from room where roomTypeNo=?";
		
		@Override
		public void insertWithRoom(RoomTypeVO roomTypeVO,Integer roomQuantity){
			Connection con=null;
			PreparedStatement pstmt=null;
			InputStream picStream=new ByteArrayInputStream(roomTypeVO.getRoomTypePicture());
			System.out.println("22");
			try {
				con =ds.getConnection();
				con.setAutoCommit(false);
				System.out.println("33");
				String cols[]={"roomTypeNo"};
				pstmt=con.prepareStatement(INSERT_STMT,cols);
				pstmt.setInt(1, roomTypeVO.getHostelNo());
				pstmt.setInt(2, roomTypeVO.getFacilityNo());
				pstmt.setString(3, roomTypeVO.getRoomTypeName());
				pstmt.setDouble(4, roomTypeVO.getRoomTypeContain());
				pstmt.setDouble(5, roomTypeVO.getRoomTypePrice());
				pstmt.setString(6, roomTypeVO.getRoomTypeContent());
				pstmt.setBinaryStream(7, picStream);
				pstmt.executeUpdate();
				System.out.println("44");
				//GET PK
				Integer nextRoomTypeNo=null;
				ResultSet rs =pstmt.getGeneratedKeys();
				if(rs.next()){
					nextRoomTypeNo=rs.getInt(1);
					System.out.println("�憓蜓���= " + nextRoomTypeNo + "(��憓����蝺刻��)");
				}else{
					System.out.println("����憓蜓���");
				}
				rs.close();
				roomTypeVO.setRoomTypeNo(nextRoomTypeNo);
				
				RoomVO roomVO=null;
				RoomDAO dao=new RoomDAO();
				for(int i=0;i<roomQuantity;i++){
					roomVO=new RoomVO();
					roomVO.setRoomTypeNo(nextRoomTypeNo);
					roomVO.setHostelNo(roomTypeVO.getHostelNo());
					roomVO.setHostelOrderDetailNo(0);
					roomVO.setRoomState("蝛箸");
					dao.insertByRoomType(roomVO, con);
				}
				
				con.commit();
				picStream.close();
				con.setAutoCommit(true);
				System.out.println("�憓�蝺刻��" + nextRoomTypeNo + "�����敦����" + roomQuantity + "����◤�憓�");
				
			}catch(IOException ie){
				System.out.println("io error"+ie.getMessage());
			}catch (SQLException se) {
				if (con != null) {
					try {
						// 3��身摰���xception�����atch��憛
						System.err.print("Transaction is being ");
						System.err.println("rolled back-�-ord"+se.getMessage());
						con.rollback();
					} catch (SQLException excep) {
						throw new RuntimeException("rollback error occured. " + excep.getMessage());
					}
				}
				throw new RuntimeException("A database error occured. " + se.getMessage());
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
		public void insert(RoomTypeVO roomTypeVO) {
			
			Connection con = null;
			PreparedStatement pstmt = null;
			InputStream picStream=new ByteArrayInputStream(roomTypeVO.getRoomTypePicture());

			try {

				con = ds.getConnection();
				pstmt = con.prepareStatement(INSERT_STMT);

				pstmt.setInt(1, roomTypeVO.getHostelNo());
				pstmt.setInt(2, roomTypeVO.getFacilityNo());
				pstmt.setString(3, roomTypeVO.getRoomTypeName());
				pstmt.setDouble(4, roomTypeVO.getRoomTypeContain());
				pstmt.setDouble(5, roomTypeVO.getRoomTypePrice());
				pstmt.setBinaryStream(6, picStream);
				pstmt.setString(7, roomTypeVO.getRoomTypeContent());
				

				pstmt.executeUpdate();
				picStream.close();
				// Handle any driver errors
			}catch (SQLException se) {
				throw new RuntimeException("A database error occured. "
						+ se.getMessage());
				// Clean up JDBC resources
			}catch (IOException e) {
				
				e.printStackTrace();
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
			InputStream picStream=new ByteArrayInputStream(roomTypeVO.getRoomTypePicture());

			try {

				
				con = ds.getConnection();
				pstmt = con.prepareStatement(UPDATE);

				pstmt.setInt(1, roomTypeVO.getHostelNo());
				pstmt.setInt(2, roomTypeVO.getFacilityNo());
				pstmt.setString(3, roomTypeVO.getRoomTypeName());
				pstmt.setDouble(4, roomTypeVO.getRoomTypeContain());
				pstmt.setDouble(5, roomTypeVO.getRoomTypePrice());
				pstmt.setBinaryStream(6, picStream);
				pstmt.setString(7, roomTypeVO.getRoomTypeContent());
				pstmt.setInt(8, roomTypeVO.getRoomTypeNo());

				pstmt.executeUpdate();
				picStream.close();
				// Handle any driver errors
			}catch(IOException e){
				e.printStackTrace();
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
			RoomTypeDAO roomTypeDao=new RoomTypeDAO();
			RoomDAO roomDAO=new RoomDAO();
			RoomTypePicDAO roomTypePicDAO=new RoomTypePicDAO();
			List<RoomVO> list=new ArrayList<RoomVO>();
			List<RoomTypePicVO> picList=new ArrayList<RoomTypePicVO>();
			
			try {
				con = ds.getConnection();
				con.setAutoCommit(false);
				
				list=roomTypeDao.getRoomsByRoomType(roomTypeNo);
				for(RoomVO roomVO:list){
					roomDAO.deleteByRoomType(roomVO, con);
//					System.out.println("room"+roomVO.getRoomNo());
				}
				picList=roomTypeDao.findPicByRoomTypeNo(roomTypeNo);
				for(RoomTypePicVO roomTypePicVO:picList){
					roomTypePicDAO.deleteByRoomType(roomTypePicVO, con);
//					System.out.println("pic"+roomTypePicVO.getRoomTypeNo());
				}
				
				pstmt = con.prepareStatement(DELETE);

				pstmt.setInt(1, roomTypeNo);

				pstmt.executeUpdate();
//				System.out.println("3 ok");
				con.commit();
				con.setAutoCommit(true);

				// Handle any driver errors
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

				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_ONE_STMT);

				pstmt.setInt(1, roomTypeNo);

				rs = pstmt.executeQuery();

				while (rs.next()) {
					// empVo 銋迂� Domain objects
					roomTypeVO=new RoomTypeVO();
					roomTypeVO.setRoomTypeNo(rs.getInt("roomTypeNo"));
					roomTypeVO.setFacilityNo(rs.getInt("facilityNo"));
					roomTypeVO.setHostelNo(rs.getInt("hostelNo"));
					roomTypeVO.setRoomTypeName(rs.getString("roomTypeName"));
					roomTypeVO.setRoomTypeContain(rs.getInt("roomTypeContain"));
					roomTypeVO.setRoomTypePrice(rs.getInt("roomTypePrice"));
					roomTypeVO.setRoomTypeContent(rs.getString("roomTypeContent"));
					roomTypeVO.setRoomTypePicture(rs.getBytes("roomTypePicture"));
				}

				// Handle any driver errors
			}catch (SQLException se) {
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

				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_ALL_STMT);
				rs = pstmt.executeQuery();

				while (rs.next()) {
					// empVO 銋迂� Domain objects
					roomTypeVO = new RoomTypeVO();
					roomTypeVO.setRoomTypeNo(rs.getInt("roomTypeNo"));
					roomTypeVO.setFacilityNo(rs.getInt("facilityNo"));
					roomTypeVO.setHostelNo(rs.getInt("hostelNo"));
					roomTypeVO.setRoomTypeName(rs.getString("roomTypeName"));
					roomTypeVO.setRoomTypeContain(rs.getInt("roomTypeContain"));
					roomTypeVO.setRoomTypePrice(rs.getInt("roomTypePrice"));
					roomTypeVO.setRoomTypePicture(rs.getBytes("roomTypePicture"));
					roomTypeVO.setRoomTypeContent(rs.getString("roomTypeContent"));
					list.add(roomTypeVO); // Store the row in the list
				}

				// Handle any driver errors
			}catch (SQLException se) {
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
				con=ds.getConnection();
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
					roomTypeVO.setRoomTypePicture(rs.getBytes("roomTypePicture"));
					roomTypeVO.setRoomTypeContent(rs.getString("roomTypeContent"));
					
					list.add(roomTypeVO);
				}
				
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
		
		@Override
		public List<RoomTypePicVO> findPicByRoomTypeNo(Integer roomTypeNo) {
			
			List<RoomTypePicVO> list=new ArrayList<RoomTypePicVO>();
			RoomTypePicVO roomTypePicVO = null;
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {

				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_PIC_STMT);

				pstmt.setInt(1, roomTypeNo);

				rs = pstmt.executeQuery();

				while (rs.next()) {
					
					roomTypePicVO =new RoomTypePicVO();
					roomTypePicVO.setRoomTypeNo(rs.getInt("roomTypeNo"));
					roomTypePicVO.setRoomTypePicNo(rs.getInt("roomTypePicNo"));
					roomTypePicVO.setRoomTypePhoto(rs.getBytes("roomTypePhoto"));
					list.add(roomTypePicVO);
				}

				// Handle any driver errors
			}catch (SQLException se) {
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
		public List<RoomVO> getRoomsByRoomType(Integer roomTypeNo) {
			
			List<RoomVO> list=new ArrayList<RoomVO>();
			RoomVO roomVO = null;
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {

				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_ROOM_STMT);

				pstmt.setInt(1, roomTypeNo);

				rs = pstmt.executeQuery();

				while (rs.next()) {
					roomVO=new RoomVO();
					roomVO.setRoomNo(rs.getInt("roomNo"));
					roomVO.setRoomTypeNo(rs.getInt("roomTypeNo"));
					roomVO.setHostelNo(rs.getInt("hostelNo"));
					roomVO.setHostelOrderDetailNo(rs.getInt("hostelOrderDetailNo"));
					roomVO.setRoomState(rs.getString("roomState"));
					
					list.add(roomVO);
				}

				// Handle any driver errors
			}catch (SQLException se) {
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
			public List<RoomTypeVO> likeByColumn(String columnName,String likeString) {
		  Connection con = null;
		  Statement pstmt = null;

		  RoomTypeVO roomTypeVO = null;
		  ResultSet rs = null;
		  List<RoomTypeVO> list = new ArrayList<RoomTypeVO>();
		  String likeSql="SELECT RoomTypeNo,hostelNo,facilityNo,roomTypeName,roomTypeContain,roomTypePrice,roomTypeContent FROM RoomType where "+columnName+" like '"+likeString+"'";

		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		   try {

		  con = ds.getConnection();
		  pstmt = con.createStatement();

					rs = pstmt.executeQuery(likeSql);
					while (rs.next()) {
		  roomTypeVO = new RoomTypeVO();
		  roomTypeVO.setRoomTypeNo(rs.getInt("RoomTypeNo"));
		  roomTypeVO.setHostelNo(rs.getInt("hostelNo"));
		  roomTypeVO.setFacilityNo(rs.getInt("facilityNo"));
		  roomTypeVO.setRoomTypeName(rs.getString("roomTypeName"));
		  roomTypeVO.setRoomTypeContain(rs.getInt("roomTypeContain"));
		  roomTypeVO.setRoomTypePrice(rs.getInt("roomTypePrice"));
		  roomTypeVO.setRoomTypeContent(rs.getString("roomTypeContent"));
		  list.add(roomTypeVO);
					}

				}
		catch (SQLException se) {
		  throw new RuntimeException("A database error occured. "+ se.getMessage());
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
				return list;
		}

		//from html start to add some onDemand method 
		//from html input start to do some onDemand method 
		    public RoomTypeVO findByHostelNo(Integer hostelNo) {
		  Connection con = null;
		  PreparedStatement pstmt = null;

		  RoomTypeVO roomTypeVO = null;
		  ResultSet rs = null;
		   try {

		  con = ds.getConnection();
		  String findByThisKey =
		  "SELECT RoomTypeNo,hostelNo,facilityNo,roomTypeName,roomTypeContain,roomTypePrice,roomTypeContent FROM RoomType where hostelNo= ?";

		    pstmt = con.prepareStatement(findByThisKey);
		  pstmt.setInt(1, hostelNo);
					rs = pstmt.executeQuery();
					while (rs.next()) {
		  roomTypeVO = new RoomTypeVO();
		  roomTypeVO.setRoomTypeNo(  rs.getInt("RoomTypeNo"));
		  roomTypeVO.setHostelNo(  rs.getInt("hostelNo"));
		  roomTypeVO.setFacilityNo(  rs.getInt("facilityNo"));
		  roomTypeVO.setRoomTypeName(  rs.getString("roomTypeName"));
		  roomTypeVO.setRoomTypeContain(  rs.getInt("roomTypeContain"));
		  roomTypeVO.setRoomTypePrice(  rs.getInt("roomTypePrice"));
		  roomTypeVO.setRoomTypeContent(  rs.getString("roomTypeContent"));
					}

				}
		catch (SQLException se) {
		  throw new RuntimeException("A database error occured. "+ se.getMessage());
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
				return roomTypeVO;
		}

		    public List<RoomTypeVO> getAllByHostelNo(Integer hostelNo) {
		  Connection con = null;
		  PreparedStatement pstmt = null;

		  RoomTypeVO roomTypeVO = null;
		  List<RoomTypeVO> list = new ArrayList<RoomTypeVO>();
		  ResultSet rs = null;
		   try {

		  con = ds.getConnection();
		  String listByThisKey =
		  "SELECT RoomTypeNo,hostelNo,facilityNo,roomTypeName,roomTypeContain,roomTypePrice,roomTypeContent FROM RoomType where hostelNo= ? order by hostelNo";

		   pstmt = con.prepareStatement(listByThisKey);
		   pstmt.setInt(1, hostelNo);
				 rs = pstmt.executeQuery();
					while (rs.next()) {
		  roomTypeVO = new RoomTypeVO();
		  roomTypeVO.setRoomTypeNo(  rs.getInt("RoomTypeNo"));
		  roomTypeVO.setHostelNo(  rs.getInt("hostelNo"));
		  roomTypeVO.setFacilityNo(  rs.getInt("facilityNo"));
		  roomTypeVO.setRoomTypeName(  rs.getString("roomTypeName"));
		  roomTypeVO.setRoomTypeContain(  rs.getInt("roomTypeContain"));
		  roomTypeVO.setRoomTypePrice(  rs.getInt("roomTypePrice"));
		  roomTypeVO.setRoomTypeContent(  rs.getString("roomTypeContent"));
		  list.add(roomTypeVO);
					}

				}
		catch (SQLException se) {
		  throw new RuntimeException("A database error occured. "+ se.getMessage());
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
				return list;
		}

		    public RoomTypeVO findByFacilityNo(Integer facilityNo) {
		  Connection con = null;
		  PreparedStatement pstmt = null;

		  RoomTypeVO roomTypeVO = null;
		  ResultSet rs = null;
		   try {

		  con = ds.getConnection();
		  String findByThisKey =
		  "SELECT RoomTypeNo,hostelNo,facilityNo,roomTypeName,roomTypeContain,roomTypePrice,roomTypeContent FROM RoomType where facilityNo= ?";

		    pstmt = con.prepareStatement(findByThisKey);
		  pstmt.setInt(1, facilityNo);
					rs = pstmt.executeQuery();
					while (rs.next()) {
		  roomTypeVO = new RoomTypeVO();
		  roomTypeVO.setRoomTypeNo(  rs.getInt("RoomTypeNo"));
		  roomTypeVO.setHostelNo(  rs.getInt("hostelNo"));
		  roomTypeVO.setFacilityNo(  rs.getInt("facilityNo"));
		  roomTypeVO.setRoomTypeName(  rs.getString("roomTypeName"));
		  roomTypeVO.setRoomTypeContain(  rs.getInt("roomTypeContain"));
		  roomTypeVO.setRoomTypePrice(  rs.getInt("roomTypePrice"));
		  roomTypeVO.setRoomTypeContent(  rs.getString("roomTypeContent"));
					}

				}
		catch (SQLException se) {
		  throw new RuntimeException("A database error occured. "+ se.getMessage());
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
				return roomTypeVO;
		}

		    public List<RoomTypeVO> getAllByFacilityNo(Integer facilityNo) {
		  Connection con = null;
		  PreparedStatement pstmt = null;

		  RoomTypeVO roomTypeVO = null;
		  List<RoomTypeVO> list = new ArrayList<RoomTypeVO>();
		  ResultSet rs = null;
		   try {

		  con = ds.getConnection();
		  String listByThisKey =
		  "SELECT RoomTypeNo,hostelNo,facilityNo,roomTypeName,roomTypeContain,roomTypePrice,roomTypeContent FROM RoomType where facilityNo= ? order by facilityNo";

		   pstmt = con.prepareStatement(listByThisKey);
		   pstmt.setInt(1, facilityNo);
				 rs = pstmt.executeQuery();
					while (rs.next()) {
		  roomTypeVO = new RoomTypeVO();
		  roomTypeVO.setRoomTypeNo(  rs.getInt("RoomTypeNo"));
		  roomTypeVO.setHostelNo(  rs.getInt("hostelNo"));
		  roomTypeVO.setFacilityNo(  rs.getInt("facilityNo"));
		  roomTypeVO.setRoomTypeName(  rs.getString("roomTypeName"));
		  roomTypeVO.setRoomTypeContain(  rs.getInt("roomTypeContain"));
		  roomTypeVO.setRoomTypePrice(  rs.getInt("roomTypePrice"));
		  roomTypeVO.setRoomTypeContent(  rs.getString("roomTypeContent"));
		  list.add(roomTypeVO);
					}

				}
		catch (SQLException se) {
		  throw new RuntimeException("A database error occured. "+ se.getMessage());
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
				return list;
		}

		    public RoomTypeVO findByRoomTypeName(String roomTypeName) {
		  Connection con = null;
		  PreparedStatement pstmt = null;

		  RoomTypeVO roomTypeVO = null;
		  ResultSet rs = null;
		   try {

		  con = ds.getConnection();
		  String findByThisKey =
		  "SELECT RoomTypeNo,hostelNo,facilityNo,roomTypeName,roomTypeContain,roomTypePrice,roomTypeContent FROM RoomType where roomTypeName= ?";

		    pstmt = con.prepareStatement(findByThisKey);
		  pstmt.setString(1, roomTypeName);
					rs = pstmt.executeQuery();
					while (rs.next()) {
		  roomTypeVO = new RoomTypeVO();
		  roomTypeVO.setRoomTypeNo(  rs.getInt("RoomTypeNo"));
		  roomTypeVO.setHostelNo(  rs.getInt("hostelNo"));
		  roomTypeVO.setFacilityNo(  rs.getInt("facilityNo"));
		  roomTypeVO.setRoomTypeName(  rs.getString("roomTypeName"));
		  roomTypeVO.setRoomTypeContain(  rs.getInt("roomTypeContain"));
		  roomTypeVO.setRoomTypePrice(  rs.getInt("roomTypePrice"));
		  roomTypeVO.setRoomTypeContent(  rs.getString("roomTypeContent"));
					}

				}
		catch (SQLException se) {
		  throw new RuntimeException("A database error occured. "+ se.getMessage());
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
				return roomTypeVO;
		}

		    public List<RoomTypeVO> getAllByRoomTypeName(String roomTypeName) {
		  Connection con = null;
		  PreparedStatement pstmt = null;

		  RoomTypeVO roomTypeVO = null;
		  List<RoomTypeVO> list = new ArrayList<RoomTypeVO>();
		  ResultSet rs = null;
		   try {

		  con = ds.getConnection();
		  String listByThisKey =
		  "SELECT RoomTypeNo,hostelNo,facilityNo,roomTypeName,roomTypeContain,roomTypePrice,roomTypeContent FROM RoomType where roomTypeName= ? order by roomTypeName";

		   pstmt = con.prepareStatement(listByThisKey);
		   pstmt.setString(1, roomTypeName);
				 rs = pstmt.executeQuery();
					while (rs.next()) {
		  roomTypeVO = new RoomTypeVO();
		  roomTypeVO.setRoomTypeNo(  rs.getInt("RoomTypeNo"));
		  roomTypeVO.setHostelNo(  rs.getInt("hostelNo"));
		  roomTypeVO.setFacilityNo(  rs.getInt("facilityNo"));
		  roomTypeVO.setRoomTypeName(  rs.getString("roomTypeName"));
		  roomTypeVO.setRoomTypeContain(  rs.getInt("roomTypeContain"));
		  roomTypeVO.setRoomTypePrice(  rs.getInt("roomTypePrice"));
		  roomTypeVO.setRoomTypeContent(  rs.getString("roomTypeContent"));
		  list.add(roomTypeVO);
					}

				}
		catch (SQLException se) {
		  throw new RuntimeException("A database error occured. "+ se.getMessage());
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
				return list;
		}

		    public RoomTypeVO findByRoomTypeContain(Integer roomTypeContain) {
		  Connection con = null;
		  PreparedStatement pstmt = null;

		  RoomTypeVO roomTypeVO = null;
		  ResultSet rs = null;
		   try {

		  con = ds.getConnection();
		  String findByThisKey =
		  "SELECT RoomTypeNo,hostelNo,facilityNo,roomTypeName,roomTypeContain,roomTypePrice,roomTypeContent FROM RoomType where roomTypeContain= ?";

		    pstmt = con.prepareStatement(findByThisKey);
		  pstmt.setInt(1, roomTypeContain);
					rs = pstmt.executeQuery();
					while (rs.next()) {
		  roomTypeVO = new RoomTypeVO();
		  roomTypeVO.setRoomTypeNo(  rs.getInt("RoomTypeNo"));
		  roomTypeVO.setHostelNo(  rs.getInt("hostelNo"));
		  roomTypeVO.setFacilityNo(  rs.getInt("facilityNo"));
		  roomTypeVO.setRoomTypeName(  rs.getString("roomTypeName"));
		  roomTypeVO.setRoomTypeContain(  rs.getInt("roomTypeContain"));
		  roomTypeVO.setRoomTypePrice(  rs.getInt("roomTypePrice"));
		  roomTypeVO.setRoomTypeContent(  rs.getString("roomTypeContent"));
					}

				}
		catch (SQLException se) {
		  throw new RuntimeException("A database error occured. "+ se.getMessage());
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
				return roomTypeVO;
		}

		    public List<RoomTypeVO> getAllByRoomTypeContain(Integer roomTypeContain) {
		  Connection con = null;
		  PreparedStatement pstmt = null;

		  RoomTypeVO roomTypeVO = null;
		  List<RoomTypeVO> list = new ArrayList<RoomTypeVO>();
		  ResultSet rs = null;
		   try {

		  con = ds.getConnection();
		  String listByThisKey =
		  "SELECT RoomTypeNo,hostelNo,facilityNo,roomTypeName,roomTypeContain,roomTypePrice,roomTypeContent FROM RoomType where roomTypeContain= ? order by roomTypeContain";

		   pstmt = con.prepareStatement(listByThisKey);
		   pstmt.setInt(1, roomTypeContain);
				 rs = pstmt.executeQuery();
					while (rs.next()) {
		  roomTypeVO = new RoomTypeVO();
		  roomTypeVO.setRoomTypeNo(  rs.getInt("RoomTypeNo"));
		  roomTypeVO.setHostelNo(  rs.getInt("hostelNo"));
		  roomTypeVO.setFacilityNo(  rs.getInt("facilityNo"));
		  roomTypeVO.setRoomTypeName(  rs.getString("roomTypeName"));
		  roomTypeVO.setRoomTypeContain(  rs.getInt("roomTypeContain"));
		  roomTypeVO.setRoomTypePrice(  rs.getInt("roomTypePrice"));
		  roomTypeVO.setRoomTypeContent(  rs.getString("roomTypeContent"));
		  list.add(roomTypeVO);
					}

				}
		catch (SQLException se) {
		  throw new RuntimeException("A database error occured. "+ se.getMessage());
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
				return list;
		}

		    public RoomTypeVO findByRoomTypePrice(Integer roomTypePrice) {
		  Connection con = null;
		  PreparedStatement pstmt = null;

		  RoomTypeVO roomTypeVO = null;
		  ResultSet rs = null;
		   try {

		  con = ds.getConnection();
		  String findByThisKey =
		  "SELECT RoomTypeNo,hostelNo,facilityNo,roomTypeName,roomTypeContain,roomTypePrice,roomTypeContent FROM RoomType where roomTypePrice= ?";

		    pstmt = con.prepareStatement(findByThisKey);
		  pstmt.setInt(1, roomTypePrice);
					rs = pstmt.executeQuery();
					while (rs.next()) {
		  roomTypeVO = new RoomTypeVO();
		  roomTypeVO.setRoomTypeNo(  rs.getInt("RoomTypeNo"));
		  roomTypeVO.setHostelNo(  rs.getInt("hostelNo"));
		  roomTypeVO.setFacilityNo(  rs.getInt("facilityNo"));
		  roomTypeVO.setRoomTypeName(  rs.getString("roomTypeName"));
		  roomTypeVO.setRoomTypeContain(  rs.getInt("roomTypeContain"));
		  roomTypeVO.setRoomTypePrice(  rs.getInt("roomTypePrice"));
		  roomTypeVO.setRoomTypeContent(  rs.getString("roomTypeContent"));
					}

				}
		catch (SQLException se) {
		  throw new RuntimeException("A database error occured. "+ se.getMessage());
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
				return roomTypeVO;
		}

		    public List<RoomTypeVO> getAllByRoomTypePrice(Integer roomTypePrice) {
		  Connection con = null;
		  PreparedStatement pstmt = null;

		  RoomTypeVO roomTypeVO = null;
		  List<RoomTypeVO> list = new ArrayList<RoomTypeVO>();
		  ResultSet rs = null;
		   try {

		  con = ds.getConnection();
		  String listByThisKey =
		  "SELECT RoomTypeNo,hostelNo,facilityNo,roomTypeName,roomTypeContain,roomTypePrice,roomTypeContent FROM RoomType where roomTypePrice= ? order by roomTypePrice";

		   pstmt = con.prepareStatement(listByThisKey);
		   pstmt.setInt(1, roomTypePrice);
				 rs = pstmt.executeQuery();
					while (rs.next()) {
		  roomTypeVO = new RoomTypeVO();
		  roomTypeVO.setRoomTypeNo(  rs.getInt("RoomTypeNo"));
		  roomTypeVO.setHostelNo(  rs.getInt("hostelNo"));
		  roomTypeVO.setFacilityNo(  rs.getInt("facilityNo"));
		  roomTypeVO.setRoomTypeName(  rs.getString("roomTypeName"));
		  roomTypeVO.setRoomTypeContain(  rs.getInt("roomTypeContain"));
		  roomTypeVO.setRoomTypePrice(  rs.getInt("roomTypePrice"));
		  roomTypeVO.setRoomTypeContent(  rs.getString("roomTypeContent"));
		  list.add(roomTypeVO);
					}

				}
		catch (SQLException se) {
		  throw new RuntimeException("A database error occured. "+ se.getMessage());
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
				return list;
		}

		    public RoomTypeVO findByRoomTypeContent(String roomTypeContent) {
		  Connection con = null;
		  PreparedStatement pstmt = null;

		  RoomTypeVO roomTypeVO = null;
		  ResultSet rs = null;
		   try {

		  con = ds.getConnection();
		  String findByThisKey =
		  "SELECT RoomTypeNo,hostelNo,facilityNo,roomTypeName,roomTypeContain,roomTypePrice,roomTypeContent FROM RoomType where roomTypeContent= ?";

		    pstmt = con.prepareStatement(findByThisKey);
		  pstmt.setString(1, roomTypeContent);
					rs = pstmt.executeQuery();
					while (rs.next()) {
		  roomTypeVO = new RoomTypeVO();
		  roomTypeVO.setRoomTypeNo(  rs.getInt("RoomTypeNo"));
		  roomTypeVO.setHostelNo(  rs.getInt("hostelNo"));
		  roomTypeVO.setFacilityNo(  rs.getInt("facilityNo"));
		  roomTypeVO.setRoomTypeName(  rs.getString("roomTypeName"));
		  roomTypeVO.setRoomTypeContain(  rs.getInt("roomTypeContain"));
		  roomTypeVO.setRoomTypePrice(  rs.getInt("roomTypePrice"));
		  roomTypeVO.setRoomTypeContent(  rs.getString("roomTypeContent"));
					}

				}
		catch (SQLException se) {
		  throw new RuntimeException("A database error occured. "+ se.getMessage());
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
				return roomTypeVO;
		}

		    public List<RoomTypeVO> getAllByRoomTypeContent(String roomTypeContent) {
		  Connection con = null;
		  PreparedStatement pstmt = null;

		  RoomTypeVO roomTypeVO = null;
		  List<RoomTypeVO> list = new ArrayList<RoomTypeVO>();
		  ResultSet rs = null;
		   try {

		  con = ds.getConnection();
		  String listByThisKey =
		  "SELECT RoomTypeNo,hostelNo,facilityNo,roomTypeName,roomTypeContain,roomTypePrice,roomTypeContent FROM RoomType where roomTypeContent= ? order by roomTypeContent";

		   pstmt = con.prepareStatement(listByThisKey);
		   pstmt.setString(1, roomTypeContent);
				 rs = pstmt.executeQuery();
					while (rs.next()) {
		  roomTypeVO = new RoomTypeVO();
		  roomTypeVO.setRoomTypeNo(  rs.getInt("RoomTypeNo"));
		  roomTypeVO.setHostelNo(  rs.getInt("hostelNo"));
		  roomTypeVO.setFacilityNo(  rs.getInt("facilityNo"));
		  roomTypeVO.setRoomTypeName(  rs.getString("roomTypeName"));
		  roomTypeVO.setRoomTypeContain(  rs.getInt("roomTypeContain"));
		  roomTypeVO.setRoomTypePrice(  rs.getInt("roomTypePrice"));
		  roomTypeVO.setRoomTypeContent(  rs.getString("roomTypeContent"));
		  list.add(roomTypeVO);
					}

				}
		catch (SQLException se) {
		  throw new RuntimeException("A database error occured. "+ se.getMessage());
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
				return list;
		}

}

