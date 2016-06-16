package com.room.model;

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

public class RoomDAO implements RoomDAO_interface {

	private static DataSource ds = null;

	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/ZA105G3");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO room VALUES (roomNo_seq.NEXTVAL, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT * FROM room order by roomNo";
	private static final String GET_ONE_STMT = "SELECT * FROM room where roomNo = ?";
	private static final String DELETE = "DELETE FROM room where roomNo = ?";
	private static final String UPDATE = "UPDATE room set hostelNo=?,roomTypeNo=?,hostelOrderDetailNo=?,roomState=?"
			+ " where roomNo = ?";

	@Override
	public void insertByRoomType(RoomVO roomVO, Connection con) {
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, roomVO.getHostelNo());
			pstmt.setInt(2, roomVO.getRoomTypeNo());
			pstmt.setInt(3, roomVO.getHostelOrderDetailNo());
			pstmt.setString(4, roomVO.getRoomState());

			pstmt.executeUpdate();

		} catch (SQLException se) {
			if (con != null) {
				try {
					// 3���]�w���exception�o�ͮɤ�catch�϶���
					System.err.print("Transaction is being ");
					System.err.println("rolled back-��-room");
					System.err.println(se.getMessage());
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
	public void deleteByRoomType(RoomVO roomVO, Connection con) {

		PreparedStatement pstmt = null;
		System.out.println("delete !!!");
		try {

			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, roomVO.getRoomNo());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (SQLException se) {
			if (con != null) {
				try {
					// 3���]�w���exception�o�ͮɤ�catch�϶���
					System.err.print("Transaction is being ");
					System.err.println("rolled back-��-room");
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
	public void delete(Integer roomNo) {

	}

	@Override
	public void insert(RoomVO roomVo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(RoomVO roomVo) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1, roomVo.getHostelNo());
			pstmt.setInt(2, roomVo.getRoomTypeNo());
			pstmt.setInt(3, roomVo.getHostelOrderDetailNo());
			pstmt.setString(4, roomVo.getRoomState());
			pstmt.setInt(5, roomVo.getRoomNo());

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

	}

	@Override
	public RoomVO findByPrimaryKey(Integer roomNo) {

		RoomVO roomVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, roomNo);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo �]�٬� Domain objects
				roomVO = new RoomVO();
				roomVO.setRoomNo(rs.getInt("roomNo"));
				roomVO.setRoomTypeNo(rs.getInt("roomTypeNo"));
				roomVO.setHostelNo(rs.getInt("hostelNo"));
				roomVO.setHostelOrderDetailNo(rs.getInt("hostelOrderDetailNo"));
				roomVO.setRoomState(rs.getString("roomState"));

			}

			// Handle any driver errors
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
		// TODO Auto-generated method stub
		return null;
	}

	public List<RoomVO> getAllByRoomTypeNo(Integer roomTypeNo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		RoomVO roomVO = null;
		List<RoomVO> list = new ArrayList<RoomVO>();
		ResultSet rs = null;
		try {

			con = ds.getConnection();
			String listByThisKey = "SELECT roomNo,hostelNo,roomTypeNo,roomState FROM Room where roomTypeNo= ? order by roomTypeNo";

			pstmt = con.prepareStatement(listByThisKey);
			pstmt.setInt(1, roomTypeNo);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				roomVO = new RoomVO();
				roomVO.setRoomNo(rs.getInt("roomNo"));
				roomVO.setHostelNo(rs.getInt("hostelNo"));
				roomVO.setRoomTypeNo(rs.getInt("roomTypeNo"));
				roomVO.setRoomState(rs.getString("roomState"));
				list.add(roomVO);
			}

		} catch (SQLException se) {
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
