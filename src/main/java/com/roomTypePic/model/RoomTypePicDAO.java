package com.roomTypePic.model;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.room.model.RoomVO;

public class RoomTypePicDAO implements RoomTypePicDAO_interface{
	
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
			"INSERT INTO roomTypePic VALUES (roomTypePicNo_seq.NEXTVAL, ?, ?)";
		private static final String GET_ALL_STMT = 
			"SELECT * FROM roomTypePic order by roomTypePicNo";
		private static final String GET_ONE_STMT = 
			"SELECT * FROM roomTypePic where roomTypePicNo = ?";
		private static final String DELETE = 
			"DELETE FROM roomTypePic where roomTypePicNo = ?";
		private static final String UPDATE = 
			"UPDATE roomTypePic set roomTypeNo=?,roomTypePhoto=?  where roomTypePicNo = ?";
		
		@Override
		public void deleteByRoomType(RoomTypePicVO roomTypePicVO, Connection con) {

			PreparedStatement pstmt = null;
			System.out.println("delete !!!");
			try {

				pstmt = con.prepareStatement(DELETE);

				pstmt.setInt(1, roomTypePicVO.getRoomTypePicNo());

				pstmt.executeUpdate();

				// Handle any driver errors
			} catch (SQLException se) {
				if (con != null) {
					try {
						System.err.print("Transaction is being ");
						System.err.println("rolled back-��-roomPic");
						con.rollback();
					} catch (SQLException excep) {
						throw new RuntimeException("rollback error occured. "
								+ excep.getMessage());
					}
				}
				throw new RuntimeException("A database error occured. "
						+ se.getMessage());
			} finally {
				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException se) {
						se.printStackTrace(System.err);
					}
				}
			}// finally

		}
	
	@Override
	public void insert(RoomTypePicVO roomTypePicVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		InputStream roomTypePicStream=new ByteArrayInputStream(roomTypePicVO.getRoomTypePhoto());
		
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, roomTypePicVO.getRoomTypeNo());
			pstmt.setBinaryStream(2, roomTypePicStream);

			pstmt.executeUpdate();

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
	}//insert-------------------------------
	
	@Override
	public void update(RoomTypePicVO roomTypePicVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		InputStream roomTypePicStream=new ByteArrayInputStream(roomTypePicVO.getRoomTypePhoto());
		
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1, roomTypePicVO.getRoomTypeNo());
			pstmt.setBinaryStream(2, roomTypePicStream);
			pstmt.setInt(3, roomTypePicVO.getRoomTypePicNo());
			
			pstmt.executeUpdate();

			// Handle any driver errors
		}catch (SQLException se) {
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

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, roomTypePicNo);

			pstmt.executeUpdate();

			// Handle any driver errors
		}catch (SQLException se) {
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

			con = ds.getConnection();
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

			con = ds.getConnection();
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
	
	 public RoomTypePicVO findByRoomTypeNo(Integer roomTypeNo) {
		  Connection con = null;
		  PreparedStatement pstmt = null;

		  RoomTypePicVO roomTypePicVO = null;
		  ResultSet rs = null;
		   try {

		  con = ds.getConnection();
		  String findByThisKey =
		  "SELECT roomTypePicNo,roomTypeNo,roomTypePhoto FROM RoomTypePic where roomTypeNo= ?";

		    pstmt = con.prepareStatement(findByThisKey);
		  pstmt.setInt(1, roomTypeNo);
					rs = pstmt.executeQuery();
					while (rs.next()) {
		  roomTypePicVO = new RoomTypePicVO();
		  roomTypePicVO.setRoomTypePicNo(  rs.getInt("roomTypePicNo"));
		  roomTypePicVO.setRoomTypeNo(  rs.getInt("roomTypeNo"));
		  roomTypePicVO.setRoomTypePhoto(  rs.getBytes("roomTypePhoto"));
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
				return roomTypePicVO;
		}
	
	public List<RoomTypePicVO> getAllByRoomTypeNo(Integer roomTypeNo) {
		  Connection con = null;
		  PreparedStatement pstmt = null;

		  RoomTypePicVO roomTypePicVO = null;
		  List<RoomTypePicVO> list = new ArrayList<RoomTypePicVO>();
		  ResultSet rs = null;
		   try {

		  con = ds.getConnection();
		  String listByThisKey =
		  "SELECT roomTypePicNo,roomTypeNo,roomTypePhoto FROM RoomTypePic where roomTypeNo= ? order by roomTypeNo";

		   pstmt = con.prepareStatement(listByThisKey);
		   pstmt.setInt(1, roomTypeNo);
				 rs = pstmt.executeQuery();
					while (rs.next()) {
		  roomTypePicVO = new RoomTypePicVO();
		  roomTypePicVO.setRoomTypePicNo(  rs.getInt("roomTypePicNo"));
		  roomTypePicVO.setRoomTypeNo(  rs.getInt("roomTypeNo"));
		  roomTypePicVO.setRoomTypePhoto(  rs.getBytes("roomTypePhoto"));
		  list.add(roomTypePicVO);
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
