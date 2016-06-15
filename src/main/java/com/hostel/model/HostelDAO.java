package com.hostel.model;

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
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import jdbc.util.CompositeQuery.CQforHostel;

import com.hostel.CompositeQuery_Hostel;
import com.hostelNews.model.HostelNewsVO;
import com.hostelPic.model.HostelPicVO;
import com.room.model.RoomVO;
import com.roomType.model.RoomTypeVO;

public class HostelDAO implements HostelDAO_interface {

	private static DataSource ds = null;

	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/ZA105G3");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "insert into hostel values (hostelNo_seq.nextval,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	private static final String GET_ALL_STMT = "select * from hostel order by hostelNo";
	private static final String GET_ONE_STMT = "select * from hostel where hostelNo=?";
	// new sql
	private static final String GET_RoomTypes_ByHostelNo_STMT = "select * from roomtype where hostelNo=? order by roomTypeNo";
	// new sql
	private static final String GET_PICS_ByHostelNo_STMT = "select * from hostelPic where hostelNo=? order by hostelNo";
	// new sql
	private static final String GET_ROOMS_ByHostelNo_STMT = "select * from room where hostelNo=? order by roomNo";
	// new sql
	private static final String GET_NEWS_ByHostelNo_STMT = "select * from hostelNews where hostelNo=?";

	private static final String DELETE = "DELETE FROM hostel where hostelNo = ?";
	private static final String UPDATE = "UPDATE hostel set dealerNo=?,hostelName=?,hostelPhone=?,"
			+ "hostelAddress=?,hostelWebPages=?,hostelState=?,hostelVerification=?,"
			+ "hostelLat=?,hostelLon=?,hostelPicture=?,dealerVerify=?,"
			+ "hostelCautions=?,hostelContent=? where hostelNo = ?";
	
	///backend///
	private static final String GET_BY_DEALER = 
			"select * from hostel where dealerNo = ?";
	
	private static final String UPDATE_bk = 
			"UPDATE hostel set hostelName=?,hostelPhone=?,"
			+ "hostelAddress=?,hostelWebPages=?,hostelState=?,hostelVerification=?,"
			+ "hostelLat=?,hostelLon=?,"
			+ "hostelCautions=?,hostelContent=? where hostelNo = ?";
	
	private static final String UPDATE_img_bk = 
			"UPDATE hostel set hostelName=?,"
					+ "hostelPicture=?,dealerVerify=? where hostelNo = ?";
	
	
	@Override
	public void insert(HostelVO hostelVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		InputStream hostelPicStream = new ByteArrayInputStream(
				hostelVO.getHostelPicture());
		InputStream dealerVerifyStream = new ByteArrayInputStream(
				hostelVO.getDealerVerify());

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, hostelVO.getDealerNo());
			pstmt.setString(2, hostelVO.getHostelName());
			pstmt.setString(3, hostelVO.getHostelPhone());
			pstmt.setString(4, hostelVO.getHostelAddress());
			pstmt.setString(5, hostelVO.getHostelWebPages());
			pstmt.setInt(6, hostelVO.getHostelState());
			pstmt.setInt(7, hostelVO.getHostelVerification());
			pstmt.setDouble(8, hostelVO.getHostelLat());
			pstmt.setDouble(9, hostelVO.getHostelLon());
			pstmt.setBinaryStream(10, hostelPicStream);
			pstmt.setBinaryStream(11, dealerVerifyStream);
			pstmt.setString(12, hostelVO.getHostelCautions());
			pstmt.setString(13, hostelVO.getHostelContent());

			pstmt.executeUpdate();

			hostelPicStream.close();
			dealerVerifyStream.close();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		} catch (IOException e) {

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

	}// insert ------------------------------------------------

	@Override
	public void update(HostelVO hostelVO) {

		Connection con = null;
		PreparedStatement pstmt = null;
		InputStream hostelPicStream = new ByteArrayInputStream(
				hostelVO.getHostelPicture());
		InputStream dealerVerifyStream = new ByteArrayInputStream(
				hostelVO.getDealerVerify());

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1, hostelVO.getDealerNo());
			pstmt.setString(2, hostelVO.getHostelName());
			pstmt.setString(3, hostelVO.getHostelPhone());
			pstmt.setString(4, hostelVO.getHostelAddress());
			pstmt.setString(5, hostelVO.getHostelWebPages());
			pstmt.setInt(6, hostelVO.getHostelState());
			pstmt.setInt(7, hostelVO.getHostelVerification());
			pstmt.setDouble(8, hostelVO.getHostelLat());
			pstmt.setDouble(9, hostelVO.getHostelLon());
			pstmt.setBinaryStream(10, hostelPicStream);
			pstmt.setBinaryStream(11, dealerVerifyStream);
			pstmt.setString(12, hostelVO.getHostelCautions());
			pstmt.setString(13, hostelVO.getHostelContent());
			pstmt.setInt(14, hostelVO.getHostelNo());

			pstmt.executeUpdate();

			hostelPicStream.close();
			dealerVerifyStream.close();

		} catch (IOException e) {
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
	}// update----------------------------------------------------

	@Override
	public void delete(Integer hostelNo) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, hostelNo);

			pstmt.executeUpdate();

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
	}// delete---------------------------------------

	@Override
	public HostelVO findByPrimaryKey(Integer hostelNo) {

		HostelVO hostelVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, hostelNo);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				hostelVO = new HostelVO();
				hostelVO.setHostelNo(rs.getInt("hostelNo"));
				hostelVO.setDealerNo(rs.getInt("dealerNo"));
				hostelVO.setHostelName(rs.getString("hostelName"));
				hostelVO.setHostelPhone(rs.getString("hostelPhone"));
				hostelVO.setHostelAddress(rs.getString("hostelAddress"));
				hostelVO.setHostelWebPages(rs.getString("hostelWebPages"));
				hostelVO.setHostelState(rs.getInt("hostelState"));
				hostelVO.setHostelVerification(rs.getInt("hostelVerification"));
				hostelVO.setHostelLat(rs.getDouble("hostelLat"));
				hostelVO.setHostelLon(rs.getDouble("hostelLon"));
				hostelVO.setHostelPicture(rs.getBytes("hostelPicture"));
				hostelVO.setDealerVerify(rs.getBytes("dealerVerify"));
				hostelVO.setHostelCautions(rs.getString("hostelCautions"));
				hostelVO.setHostelContent(rs.getString("hostelContent"));

			}

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

		return hostelVO;
	}

	@Override
	public List<HostelVO> getAll() {

		List<HostelVO> list = new ArrayList<HostelVO>();
		HostelVO hostelVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				hostelVO = new HostelVO();
				hostelVO.setHostelNo(rs.getInt("hostelNo"));
				hostelVO.setDealerNo(rs.getInt("dealerNo"));
				hostelVO.setHostelName(rs.getString("hostelName"));
				hostelVO.setHostelPhone(rs.getString("hostelPhone"));
				hostelVO.setHostelAddress(rs.getString("hostelAddress"));
				hostelVO.setHostelWebPages(rs.getString("hostelWebPages"));
				hostelVO.setHostelState(rs.getInt("hostelState"));
				hostelVO.setHostelVerification(rs.getInt("hostelVerification"));
				hostelVO.setHostelLat(rs.getDouble("hostelLat"));
				hostelVO.setHostelLon(rs.getDouble("hostelLon"));
				hostelVO.setHostelPicture(rs.getBytes("hostelPicture"));
				hostelVO.setDealerVerify(rs.getBytes("dealerVerify"));
				hostelVO.setHostelCautions(rs.getString("hostelCautions"));
				hostelVO.setHostelContent(rs.getString("hostelContent"));

				list.add(hostelVO);
			}

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
	}// get all

	public List<RoomVO> getRoomsByHostelNo(Integer hostelNo) {

		List<RoomVO> list = new ArrayList<RoomVO>();
		RoomVO roomVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ROOMS_ByHostelNo_STMT);
			pstmt.setInt(1, hostelNo);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				roomVO = new RoomVO();
				roomVO.setRoomNo(rs.getInt("roomNo"));
				roomVO.setRoomTypeNo(rs.getInt("roomTypeNo"));
				roomVO.setHostelNo(rs.getInt("hostelNo"));
				roomVO.setRoomState(rs.getString("roomState"));
				list.add(roomVO);
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
		return list;
	}

	@Override
	public Set<RoomTypeVO> getRoomTypesByHostelNo(Integer hostelNo) {
		Set<RoomTypeVO> set = new LinkedHashSet<RoomTypeVO>();
		RoomTypeVO roomTypeVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_RoomTypes_ByHostelNo_STMT);
			pstmt.setInt(1, hostelNo);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				roomTypeVO = new RoomTypeVO();
				roomTypeVO.setRoomTypeNo(rs.getInt("roomTypeNo"));
				roomTypeVO.setHostelNo(rs.getInt("hostelNo"));
				roomTypeVO.setFacilityNo(rs.getInt("facilityNo"));
				roomTypeVO.setRoomTypeName(rs.getString("roomTypeName"));
				roomTypeVO.setRoomTypeContain(rs.getInt("roomTypeContain"));
				roomTypeVO.setRoomTypePrice(rs.getInt("roomTypePrice"));
				roomTypeVO.setRoomTypeContent(rs.getString("roomTypeContent"));
				set.add(roomTypeVO);
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

		return set;
	}

	@Override
	public Set<HostelPicVO> getPicsByHostelNo(Integer hostelNo) {

		Set<HostelPicVO> set = new LinkedHashSet<HostelPicVO>();
		HostelPicVO hostelPicVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_PICS_ByHostelNo_STMT);
			pstmt.setInt(1, hostelNo);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				hostelPicVO = new HostelPicVO();

				hostelPicVO.setHostelPicNo(rs.getInt("hostelPicNo"));
				hostelPicVO.setHostelNo(rs.getInt("hostelNo"));
				hostelPicVO.setHostelPhoto(rs.getBytes("hostelPhoto"));

				set.add(hostelPicVO);
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

		return set;
	}

	@Override
	public List<HostelNewsVO> getNewsByHostelNo(Integer hostelNo) {

		List<HostelNewsVO> list = new ArrayList<HostelNewsVO>();
		HostelNewsVO hostelNewsVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_NEWS_ByHostelNo_STMT);
			pstmt.setInt(1, hostelNo);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				hostelNewsVO = new HostelNewsVO();
				hostelNewsVO.setHostelNewsNo(rs.getInt("hostelNewsNo"));
				hostelNewsVO.setHostelNo(rs.getInt("hostelNo"));
				hostelNewsVO.setNewsContent(rs.getString("newsContent"));
				hostelNewsVO.setUpdateDate(rs.getDate("updateDate"));
				list.add(hostelNewsVO);
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
	public List<HostelVO> getAll(Map<String, String[]> map) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		HostelVO hostelVO = null;
		List<HostelVO> list = new ArrayList<HostelVO>();

		try {
			con = ds.getConnection();
			String finalSQL = "select * from hostel"
					+ CQforHostel.get_WhereCondition(map) + "order by hostelNo";
			pstmt = con.prepareStatement(finalSQL);
			System.out.println("final sql(DAO) = " + finalSQL);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				hostelVO = new HostelVO();
				hostelVO.setHostelNo(rs.getInt("hostelNo"));
				hostelVO.setDealerNo(rs.getInt("dealerNo"));
				hostelVO.setHostelName(rs.getString("hostelName"));
				hostelVO.setHostelPhone(rs.getString("hostelPhone"));
				hostelVO.setHostelAddress(rs.getString("hostelAddress"));
				hostelVO.setHostelWebPages(rs.getString("hostelWebPages"));
				hostelVO.setHostelState(rs.getInt("hostelState"));
				hostelVO.setHostelVerification(rs.getInt("hostelVerification"));
				hostelVO.setHostelLat(rs.getDouble("hostelLat"));
				hostelVO.setHostelLon(rs.getDouble("hostelLon"));
				hostelVO.setHostelPicture(rs.getBytes("hostelPicture"));
				hostelVO.setDealerVerify(rs.getBytes("dealerVerify"));
				hostelVO.setHostelCautions(rs.getString("hostelCautions"));
				hostelVO.setHostelContent(rs.getString("hostelContent"));
				list.add(hostelVO);

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
	public List<HostelVO> likeByColumn(String columnName, String likeString) {
		Connection con = null;
		Statement pstmt = null;

		HostelVO hostelVO = null;
		ResultSet rs = null;
		List<HostelVO> list = new ArrayList<HostelVO>();
		String likeSql = "SELECT hostelNo,dealerNo,hostelName,hostelPhone,hostelAddress,hostelWebPages,hostelState,hostelVerification,hostelLat,hostelLon,hostelpicture,dealerverify,hostelCautions,hostelContent FROM Hostel where "
				+ columnName + " like '" + likeString + "'";

		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		try {

			con = ds.getConnection();
			pstmt = con.createStatement();

			rs = pstmt.executeQuery(likeSql);
			while (rs.next()) {
				hostelVO = new HostelVO();
				hostelVO.setHostelNo(rs.getInt("hostelNo"));
				hostelVO.setDealerNo(rs.getInt("dealerNo"));
				hostelVO.setHostelName(rs.getString("hostelName"));
				hostelVO.setHostelPhone(rs.getString("hostelPhone"));
				hostelVO.setHostelAddress(rs.getString("hostelAddress"));
				hostelVO.setHostelWebPages(rs.getString("hostelWebPages"));
				hostelVO.setHostelState(rs.getInt("hostelState"));
				hostelVO.setHostelVerification(rs.getInt("hostelVerification"));
				hostelVO.setHostelLat(rs.getDouble("hostelLat"));
				hostelVO.setHostelLon(rs.getDouble("hostelLon"));
				hostelVO.setHostelPicture(rs.getBytes("hostelpicture"));
				hostelVO.setDealerVerify(rs.getBytes("dealerverify"));
				hostelVO.setHostelCautions(rs.getString("hostelCautions"));
				hostelVO.setHostelContent(rs.getString("hostelContent"));
				list.add(hostelVO);
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

	// from html start to add some onDemand method
	// from html input start to do some onDemand method
	public HostelVO findByDealerNo(Integer dealerNo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		HostelVO hostelVO = null;
		ResultSet rs = null;
		try {

			con = ds.getConnection();
			String findByThisKey = "SELECT hostelNo,dealerNo,hostelName,hostelPhone,hostelAddress,hostelWebPages,hostelState,hostelVerification,hostelLat,hostelLon,hostelpicture,dealerverify,hostelCautions,hostelContent FROM Hostel where dealerNo= ?";

			pstmt = con.prepareStatement(findByThisKey);
			pstmt.setInt(1, dealerNo);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				hostelVO = new HostelVO();
				hostelVO.setHostelNo(rs.getInt("hostelNo"));
				hostelVO.setDealerNo(rs.getInt("dealerNo"));
				hostelVO.setHostelName(rs.getString("hostelName"));
				hostelVO.setHostelPhone(rs.getString("hostelPhone"));
				hostelVO.setHostelAddress(rs.getString("hostelAddress"));
				hostelVO.setHostelWebPages(rs.getString("hostelWebPages"));
				hostelVO.setHostelState(rs.getInt("hostelState"));
				hostelVO.setHostelVerification(rs.getInt("hostelVerification"));
				hostelVO.setHostelLat(rs.getDouble("hostelLat"));
				hostelVO.setHostelLon(rs.getDouble("hostelLon"));
				hostelVO.setHostelPicture(rs.getBytes("hostelpicture"));
				hostelVO.setDealerVerify(rs.getBytes("dealerverify"));
				hostelVO.setHostelCautions(rs.getString("hostelCautions"));
				hostelVO.setHostelContent(rs.getString("hostelContent"));
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
		return hostelVO;
	}

	public List<HostelVO> getAllByDealerNo(Integer dealerNo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		HostelVO hostelVO = null;
		List<HostelVO> list = new ArrayList<HostelVO>();
		ResultSet rs = null;
		try {

			con = ds.getConnection();
			String listByThisKey = "SELECT hostelNo,dealerNo,hostelName,hostelPhone,hostelAddress,hostelWebPages,hostelState,hostelVerification,hostelLat,hostelLon,hostelpicture,dealerverify,hostelCautions,hostelContent FROM Hostel where dealerNo= ? order by dealerNo";

			pstmt = con.prepareStatement(listByThisKey);
			pstmt.setInt(1, dealerNo);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				hostelVO = new HostelVO();
				hostelVO.setHostelNo(rs.getInt("hostelNo"));
				hostelVO.setDealerNo(rs.getInt("dealerNo"));
				hostelVO.setHostelName(rs.getString("hostelName"));
				hostelVO.setHostelPhone(rs.getString("hostelPhone"));
				hostelVO.setHostelAddress(rs.getString("hostelAddress"));
				hostelVO.setHostelWebPages(rs.getString("hostelWebPages"));
				hostelVO.setHostelState(rs.getInt("hostelState"));
				hostelVO.setHostelVerification(rs.getInt("hostelVerification"));
				hostelVO.setHostelLat(rs.getDouble("hostelLat"));
				hostelVO.setHostelLon(rs.getDouble("hostelLon"));
				hostelVO.setHostelPicture(rs.getBytes("hostelpicture"));
				hostelVO.setDealerVerify(rs.getBytes("dealerverify"));
				hostelVO.setHostelCautions(rs.getString("hostelCautions"));
				hostelVO.setHostelContent(rs.getString("hostelContent"));
				list.add(hostelVO);
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

	public HostelVO findByHostelName(String hostelName) {
		Connection con = null;
		PreparedStatement pstmt = null;

		HostelVO hostelVO = null;
		ResultSet rs = null;
		try {

			con = ds.getConnection();
			String findByThisKey = "SELECT hostelNo,dealerNo,hostelName,hostelPhone,hostelAddress,hostelWebPages,hostelState,hostelVerification,hostelLat,hostelLon,hostelpicture,dealerverify,hostelCautions,hostelContent FROM Hostel where hostelName= ?";

			pstmt = con.prepareStatement(findByThisKey);
			pstmt.setString(1, hostelName);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				hostelVO = new HostelVO();
				hostelVO.setHostelNo(rs.getInt("hostelNo"));
				hostelVO.setDealerNo(rs.getInt("dealerNo"));
				hostelVO.setHostelName(rs.getString("hostelName"));
				hostelVO.setHostelPhone(rs.getString("hostelPhone"));
				hostelVO.setHostelAddress(rs.getString("hostelAddress"));
				hostelVO.setHostelWebPages(rs.getString("hostelWebPages"));
				hostelVO.setHostelState(rs.getInt("hostelState"));
				hostelVO.setHostelVerification(rs.getInt("hostelVerification"));
				hostelVO.setHostelLat(rs.getDouble("hostelLat"));
				hostelVO.setHostelLon(rs.getDouble("hostelLon"));
				hostelVO.setHostelPicture(rs.getBytes("hostelpicture"));
				hostelVO.setDealerVerify(rs.getBytes("dealerverify"));
				hostelVO.setHostelCautions(rs.getString("hostelCautions"));
				hostelVO.setHostelContent(rs.getString("hostelContent"));
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
		return hostelVO;
	}

	public List<HostelVO> getAllByHostelName(String hostelName) {
		Connection con = null;
		PreparedStatement pstmt = null;

		HostelVO hostelVO = null;
		List<HostelVO> list = new ArrayList<HostelVO>();
		ResultSet rs = null;
		try {

			con = ds.getConnection();
			String listByThisKey = "SELECT hostelNo,dealerNo,hostelName,hostelPhone,hostelAddress,hostelWebPages,hostelState,hostelVerification,hostelLat,hostelLon,hostelpicture,dealerverify,hostelCautions,hostelContent FROM Hostel where hostelName= ? order by hostelName";

			pstmt = con.prepareStatement(listByThisKey);
			pstmt.setString(1, hostelName);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				hostelVO = new HostelVO();
				hostelVO.setHostelNo(rs.getInt("hostelNo"));
				hostelVO.setDealerNo(rs.getInt("dealerNo"));
				hostelVO.setHostelName(rs.getString("hostelName"));
				hostelVO.setHostelPhone(rs.getString("hostelPhone"));
				hostelVO.setHostelAddress(rs.getString("hostelAddress"));
				hostelVO.setHostelWebPages(rs.getString("hostelWebPages"));
				hostelVO.setHostelState(rs.getInt("hostelState"));
				hostelVO.setHostelVerification(rs.getInt("hostelVerification"));
				hostelVO.setHostelLat(rs.getDouble("hostelLat"));
				hostelVO.setHostelLon(rs.getDouble("hostelLon"));
				hostelVO.setHostelPicture(rs.getBytes("hostelpicture"));
				hostelVO.setDealerVerify(rs.getBytes("dealerverify"));
				hostelVO.setHostelCautions(rs.getString("hostelCautions"));
				hostelVO.setHostelContent(rs.getString("hostelContent"));
				list.add(hostelVO);
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

	public HostelVO findByHostelPhone(String hostelPhone) {
		Connection con = null;
		PreparedStatement pstmt = null;

		HostelVO hostelVO = null;
		ResultSet rs = null;
		try {

			con = ds.getConnection();
			String findByThisKey = "SELECT hostelNo,dealerNo,hostelName,hostelPhone,hostelAddress,hostelWebPages,hostelState,hostelVerification,hostelLat,hostelLon,hostelpicture,dealerverify,hostelCautions,hostelContent FROM Hostel where hostelPhone= ?";

			pstmt = con.prepareStatement(findByThisKey);
			pstmt.setString(1, hostelPhone);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				hostelVO = new HostelVO();
				hostelVO.setHostelNo(rs.getInt("hostelNo"));
				hostelVO.setDealerNo(rs.getInt("dealerNo"));
				hostelVO.setHostelName(rs.getString("hostelName"));
				hostelVO.setHostelPhone(rs.getString("hostelPhone"));
				hostelVO.setHostelAddress(rs.getString("hostelAddress"));
				hostelVO.setHostelWebPages(rs.getString("hostelWebPages"));
				hostelVO.setHostelState(rs.getInt("hostelState"));
				hostelVO.setHostelVerification(rs.getInt("hostelVerification"));
				hostelVO.setHostelLat(rs.getDouble("hostelLat"));
				hostelVO.setHostelLon(rs.getDouble("hostelLon"));
				hostelVO.setHostelPicture(rs.getBytes("hostelpicture"));
				hostelVO.setDealerVerify(rs.getBytes("dealerverify"));
				hostelVO.setHostelCautions(rs.getString("hostelCautions"));
				hostelVO.setHostelContent(rs.getString("hostelContent"));
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
		return hostelVO;
	}

	public List<HostelVO> getAllByHostelPhone(String hostelPhone) {
		Connection con = null;
		PreparedStatement pstmt = null;

		HostelVO hostelVO = null;
		List<HostelVO> list = new ArrayList<HostelVO>();
		ResultSet rs = null;
		try {

			con = ds.getConnection();
			String listByThisKey = "SELECT hostelNo,dealerNo,hostelName,hostelPhone,hostelAddress,hostelWebPages,hostelState,hostelVerification,hostelLat,hostelLon,hostelpicture,dealerverify,hostelCautions,hostelContent FROM Hostel where hostelPhone= ? order by hostelPhone";

			pstmt = con.prepareStatement(listByThisKey);
			pstmt.setString(1, hostelPhone);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				hostelVO = new HostelVO();
				hostelVO.setHostelNo(rs.getInt("hostelNo"));
				hostelVO.setDealerNo(rs.getInt("dealerNo"));
				hostelVO.setHostelName(rs.getString("hostelName"));
				hostelVO.setHostelPhone(rs.getString("hostelPhone"));
				hostelVO.setHostelAddress(rs.getString("hostelAddress"));
				hostelVO.setHostelWebPages(rs.getString("hostelWebPages"));
				hostelVO.setHostelState(rs.getInt("hostelState"));
				hostelVO.setHostelVerification(rs.getInt("hostelVerification"));
				hostelVO.setHostelLat(rs.getDouble("hostelLat"));
				hostelVO.setHostelLon(rs.getDouble("hostelLon"));
				hostelVO.setHostelPicture(rs.getBytes("hostelpicture"));
				hostelVO.setDealerVerify(rs.getBytes("dealerverify"));
				hostelVO.setHostelCautions(rs.getString("hostelCautions"));
				hostelVO.setHostelContent(rs.getString("hostelContent"));
				list.add(hostelVO);
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

	public HostelVO findByHostelAddress(String hostelAddress) {
		Connection con = null;
		PreparedStatement pstmt = null;

		HostelVO hostelVO = null;
		ResultSet rs = null;
		try {

			con = ds.getConnection();
			String findByThisKey = "SELECT hostelNo,dealerNo,hostelName,hostelPhone,hostelAddress,hostelWebPages,hostelState,hostelVerification,hostelLat,hostelLon,hostelpicture,dealerverify,hostelCautions,hostelContent FROM Hostel where hostelAddress= ?";

			pstmt = con.prepareStatement(findByThisKey);
			pstmt.setString(1, hostelAddress);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				hostelVO = new HostelVO();
				hostelVO.setHostelNo(rs.getInt("hostelNo"));
				hostelVO.setDealerNo(rs.getInt("dealerNo"));
				hostelVO.setHostelName(rs.getString("hostelName"));
				hostelVO.setHostelPhone(rs.getString("hostelPhone"));
				hostelVO.setHostelAddress(rs.getString("hostelAddress"));
				hostelVO.setHostelWebPages(rs.getString("hostelWebPages"));
				hostelVO.setHostelState(rs.getInt("hostelState"));
				hostelVO.setHostelVerification(rs.getInt("hostelVerification"));
				hostelVO.setHostelLat(rs.getDouble("hostelLat"));
				hostelVO.setHostelLon(rs.getDouble("hostelLon"));
				hostelVO.setHostelPicture(rs.getBytes("hostelpicture"));
				hostelVO.setDealerVerify(rs.getBytes("dealerverify"));
				hostelVO.setHostelCautions(rs.getString("hostelCautions"));
				hostelVO.setHostelContent(rs.getString("hostelContent"));
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
		return hostelVO;
	}

	public List<HostelVO> getAllByHostelAddress(String hostelAddress) {
		Connection con = null;
		PreparedStatement pstmt = null;

		HostelVO hostelVO = null;
		List<HostelVO> list = new ArrayList<HostelVO>();
		ResultSet rs = null;
		try {

			con = ds.getConnection();
			String listByThisKey = "SELECT hostelNo,dealerNo,hostelName,hostelPhone,hostelAddress,hostelWebPages,hostelState,hostelVerification,hostelLat,hostelLon,hostelpicture,dealerverify,hostelCautions,hostelContent FROM Hostel where hostelAddress= ? order by hostelAddress";

			pstmt = con.prepareStatement(listByThisKey);
			pstmt.setString(1, hostelAddress);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				hostelVO = new HostelVO();
				hostelVO.setHostelNo(rs.getInt("hostelNo"));
				hostelVO.setDealerNo(rs.getInt("dealerNo"));
				hostelVO.setHostelName(rs.getString("hostelName"));
				hostelVO.setHostelPhone(rs.getString("hostelPhone"));
				hostelVO.setHostelAddress(rs.getString("hostelAddress"));
				hostelVO.setHostelWebPages(rs.getString("hostelWebPages"));
				hostelVO.setHostelState(rs.getInt("hostelState"));
				hostelVO.setHostelVerification(rs.getInt("hostelVerification"));
				hostelVO.setHostelLat(rs.getDouble("hostelLat"));
				hostelVO.setHostelLon(rs.getDouble("hostelLon"));
				hostelVO.setHostelPicture(rs.getBytes("hostelpicture"));
				hostelVO.setDealerVerify(rs.getBytes("dealerverify"));
				hostelVO.setHostelCautions(rs.getString("hostelCautions"));
				hostelVO.setHostelContent(rs.getString("hostelContent"));
				list.add(hostelVO);
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

	public HostelVO findByHostelState(Integer hostelState) {
		Connection con = null;
		PreparedStatement pstmt = null;

		HostelVO hostelVO = null;
		ResultSet rs = null;
		try {

			con = ds.getConnection();
			String findByThisKey = "SELECT hostelNo,dealerNo,hostelName,hostelPhone,hostelAddress,hostelWebPages,hostelState,hostelVerification,hostelLat,hostelLon,hostelpicture,dealerverify,hostelCautions,hostelContent FROM Hostel where hostelState= ?";

			pstmt = con.prepareStatement(findByThisKey);
			pstmt.setInt(1, hostelState);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				hostelVO = new HostelVO();
				hostelVO.setHostelNo(rs.getInt("hostelNo"));
				hostelVO.setDealerNo(rs.getInt("dealerNo"));
				hostelVO.setHostelName(rs.getString("hostelName"));
				hostelVO.setHostelPhone(rs.getString("hostelPhone"));
				hostelVO.setHostelAddress(rs.getString("hostelAddress"));
				hostelVO.setHostelWebPages(rs.getString("hostelWebPages"));
				hostelVO.setHostelState(rs.getInt("hostelState"));
				hostelVO.setHostelVerification(rs.getInt("hostelVerification"));
				hostelVO.setHostelLat(rs.getDouble("hostelLat"));
				hostelVO.setHostelLon(rs.getDouble("hostelLon"));
				hostelVO.setHostelPicture(rs.getBytes("hostelpicture"));
				hostelVO.setDealerVerify(rs.getBytes("dealerverify"));
				hostelVO.setHostelCautions(rs.getString("hostelCautions"));
				hostelVO.setHostelContent(rs.getString("hostelContent"));
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
		return hostelVO;
	}

	public List<HostelVO> getAllByHostelState(Integer hostelState) {
		Connection con = null;
		PreparedStatement pstmt = null;

		HostelVO hostelVO = null;
		List<HostelVO> list = new ArrayList<HostelVO>();
		ResultSet rs = null;
		try {

			con = ds.getConnection();
			String listByThisKey = "SELECT hostelNo,dealerNo,hostelName,hostelPhone,hostelAddress,hostelWebPages,hostelState,hostelVerification,hostelLat,hostelLon,hostelpicture,dealerverify,hostelCautions,hostelContent FROM Hostel where hostelState= ? order by hostelState";

			pstmt = con.prepareStatement(listByThisKey);
			pstmt.setInt(1, hostelState);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				hostelVO = new HostelVO();
				hostelVO.setHostelNo(rs.getInt("hostelNo"));
				hostelVO.setDealerNo(rs.getInt("dealerNo"));
				hostelVO.setHostelName(rs.getString("hostelName"));
				hostelVO.setHostelPhone(rs.getString("hostelPhone"));
				hostelVO.setHostelAddress(rs.getString("hostelAddress"));
				hostelVO.setHostelWebPages(rs.getString("hostelWebPages"));
				hostelVO.setHostelState(rs.getInt("hostelState"));
				hostelVO.setHostelVerification(rs.getInt("hostelVerification"));
				hostelVO.setHostelLat(rs.getDouble("hostelLat"));
				hostelVO.setHostelLon(rs.getDouble("hostelLon"));
				hostelVO.setHostelPicture(rs.getBytes("hostelpicture"));
				hostelVO.setDealerVerify(rs.getBytes("dealerverify"));
				hostelVO.setHostelCautions(rs.getString("hostelCautions"));
				hostelVO.setHostelContent(rs.getString("hostelContent"));
				list.add(hostelVO);
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

	public HostelVO findByHostelVerification(Integer hostelVerification) {
		Connection con = null;
		PreparedStatement pstmt = null;

		HostelVO hostelVO = null;
		ResultSet rs = null;
		try {

			con = ds.getConnection();
			String findByThisKey = "SELECT hostelNo,dealerNo,hostelName,hostelPhone,hostelAddress,hostelWebPages,hostelState,hostelVerification,hostelLat,hostelLon,hostelpicture,dealerverify,hostelCautions,hostelContent FROM Hostel where hostelVerification= ?";

			pstmt = con.prepareStatement(findByThisKey);
			pstmt.setInt(1, hostelVerification);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				hostelVO = new HostelVO();
				hostelVO.setHostelNo(rs.getInt("hostelNo"));
				hostelVO.setDealerNo(rs.getInt("dealerNo"));
				hostelVO.setHostelName(rs.getString("hostelName"));
				hostelVO.setHostelPhone(rs.getString("hostelPhone"));
				hostelVO.setHostelAddress(rs.getString("hostelAddress"));
				hostelVO.setHostelWebPages(rs.getString("hostelWebPages"));
				hostelVO.setHostelState(rs.getInt("hostelState"));
				hostelVO.setHostelVerification(rs.getInt("hostelVerification"));
				hostelVO.setHostelLat(rs.getDouble("hostelLat"));
				hostelVO.setHostelLon(rs.getDouble("hostelLon"));
				hostelVO.setHostelPicture(rs.getBytes("hostelpicture"));
				hostelVO.setDealerVerify(rs.getBytes("dealerverify"));
				hostelVO.setHostelCautions(rs.getString("hostelCautions"));
				hostelVO.setHostelContent(rs.getString("hostelContent"));
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
		return hostelVO;
	}

	public List<HostelVO> getAllByHostelVerification(Integer hostelVerification) {
		Connection con = null;
		PreparedStatement pstmt = null;

		HostelVO hostelVO = null;
		List<HostelVO> list = new ArrayList<HostelVO>();
		ResultSet rs = null;
		try {

			con = ds.getConnection();
			String listByThisKey = "SELECT hostelNo,dealerNo,hostelName,hostelPhone,hostelAddress,hostelWebPages,hostelState,hostelVerification,hostelLat,hostelLon,hostelpicture,dealerverify,hostelCautions,hostelContent FROM Hostel where hostelVerification= ? order by hostelVerification";

			pstmt = con.prepareStatement(listByThisKey);
			pstmt.setInt(1, hostelVerification);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				hostelVO = new HostelVO();
				hostelVO.setHostelNo(rs.getInt("hostelNo"));
				hostelVO.setDealerNo(rs.getInt("dealerNo"));
				hostelVO.setHostelName(rs.getString("hostelName"));
				hostelVO.setHostelPhone(rs.getString("hostelPhone"));
				hostelVO.setHostelAddress(rs.getString("hostelAddress"));
				hostelVO.setHostelWebPages(rs.getString("hostelWebPages"));
				hostelVO.setHostelState(rs.getInt("hostelState"));
				hostelVO.setHostelVerification(rs.getInt("hostelVerification"));
				hostelVO.setHostelLat(rs.getDouble("hostelLat"));
				hostelVO.setHostelLon(rs.getDouble("hostelLon"));
				hostelVO.setHostelPicture(rs.getBytes("hostelpicture"));
				hostelVO.setDealerVerify(rs.getBytes("dealerverify"));
				hostelVO.setHostelCautions(rs.getString("hostelCautions"));
				hostelVO.setHostelContent(rs.getString("hostelContent"));
				list.add(hostelVO);
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

	public HostelVO findByHostelLat(Double hostelLat) {
		Connection con = null;
		PreparedStatement pstmt = null;

		HostelVO hostelVO = null;
		ResultSet rs = null;
		try {

			con = ds.getConnection();
			String findByThisKey = "SELECT hostelNo,dealerNo,hostelName,hostelPhone,hostelAddress,hostelWebPages,hostelState,hostelVerification,hostelLat,hostelLon,hostelpicture,dealerverify,hostelCautions,hostelContent FROM Hostel where hostelLat= ?";

			pstmt = con.prepareStatement(findByThisKey);
			pstmt.setDouble(1, hostelLat);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				hostelVO = new HostelVO();
				hostelVO.setHostelNo(rs.getInt("hostelNo"));
				hostelVO.setDealerNo(rs.getInt("dealerNo"));
				hostelVO.setHostelName(rs.getString("hostelName"));
				hostelVO.setHostelPhone(rs.getString("hostelPhone"));
				hostelVO.setHostelAddress(rs.getString("hostelAddress"));
				hostelVO.setHostelWebPages(rs.getString("hostelWebPages"));
				hostelVO.setHostelState(rs.getInt("hostelState"));
				hostelVO.setHostelVerification(rs.getInt("hostelVerification"));
				hostelVO.setHostelLat(rs.getDouble("hostelLat"));
				hostelVO.setHostelLon(rs.getDouble("hostelLon"));
				hostelVO.setHostelPicture(rs.getBytes("hostelpicture"));
				hostelVO.setDealerVerify(rs.getBytes("dealerverify"));
				hostelVO.setHostelCautions(rs.getString("hostelCautions"));
				hostelVO.setHostelContent(rs.getString("hostelContent"));
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
		return hostelVO;
	}

	public List<HostelVO> getAllByHostelLat(Double hostelLat) {
		Connection con = null;
		PreparedStatement pstmt = null;

		HostelVO hostelVO = null;
		List<HostelVO> list = new ArrayList<HostelVO>();
		ResultSet rs = null;
		try {

			con = ds.getConnection();
			String listByThisKey = "SELECT hostelNo,dealerNo,hostelName,hostelPhone,hostelAddress,hostelWebPages,hostelState,hostelVerification,hostelLat,hostelLon,hostelpicture,dealerverify,hostelCautions,hostelContent FROM Hostel where hostelLat= ? order by hostelLat";

			pstmt = con.prepareStatement(listByThisKey);
			pstmt.setDouble(1, hostelLat);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				hostelVO = new HostelVO();
				hostelVO.setHostelNo(rs.getInt("hostelNo"));
				hostelVO.setDealerNo(rs.getInt("dealerNo"));
				hostelVO.setHostelName(rs.getString("hostelName"));
				hostelVO.setHostelPhone(rs.getString("hostelPhone"));
				hostelVO.setHostelAddress(rs.getString("hostelAddress"));
				hostelVO.setHostelWebPages(rs.getString("hostelWebPages"));
				hostelVO.setHostelState(rs.getInt("hostelState"));
				hostelVO.setHostelVerification(rs.getInt("hostelVerification"));
				hostelVO.setHostelLat(rs.getDouble("hostelLat"));
				hostelVO.setHostelLon(rs.getDouble("hostelLon"));
				hostelVO.setHostelPicture(rs.getBytes("hostelpicture"));
				hostelVO.setDealerVerify(rs.getBytes("dealerverify"));
				hostelVO.setHostelCautions(rs.getString("hostelCautions"));
				hostelVO.setHostelContent(rs.getString("hostelContent"));
				list.add(hostelVO);
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

	public HostelVO findByHostelLon(Double hostelLon) {
		Connection con = null;
		PreparedStatement pstmt = null;

		HostelVO hostelVO = null;
		ResultSet rs = null;
		try {

			con = ds.getConnection();
			String findByThisKey = "SELECT hostelNo,dealerNo,hostelName,hostelPhone,hostelAddress,hostelWebPages,hostelState,hostelVerification,hostelLat,hostelLon,hostelpicture,dealerverify,hostelCautions,hostelContent FROM Hostel where hostelLon= ?";

			pstmt = con.prepareStatement(findByThisKey);
			pstmt.setDouble(1, hostelLon);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				hostelVO = new HostelVO();
				hostelVO.setHostelNo(rs.getInt("hostelNo"));
				hostelVO.setDealerNo(rs.getInt("dealerNo"));
				hostelVO.setHostelName(rs.getString("hostelName"));
				hostelVO.setHostelPhone(rs.getString("hostelPhone"));
				hostelVO.setHostelAddress(rs.getString("hostelAddress"));
				hostelVO.setHostelWebPages(rs.getString("hostelWebPages"));
				hostelVO.setHostelState(rs.getInt("hostelState"));
				hostelVO.setHostelVerification(rs.getInt("hostelVerification"));
				hostelVO.setHostelLat(rs.getDouble("hostelLat"));
				hostelVO.setHostelLon(rs.getDouble("hostelLon"));
				hostelVO.setHostelPicture(rs.getBytes("hostelpicture"));
				hostelVO.setDealerVerify(rs.getBytes("dealerverify"));
				hostelVO.setHostelCautions(rs.getString("hostelCautions"));
				hostelVO.setHostelContent(rs.getString("hostelContent"));
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
		return hostelVO;
	}

	public List<HostelVO> getAllByHostelLon(Double hostelLon) {
		Connection con = null;
		PreparedStatement pstmt = null;

		HostelVO hostelVO = null;
		List<HostelVO> list = new ArrayList<HostelVO>();
		ResultSet rs = null;
		try {

			con = ds.getConnection();
			String listByThisKey = "SELECT hostelNo,dealerNo,hostelName,hostelPhone,hostelAddress,hostelWebPages,hostelState,hostelVerification,hostelLat,hostelLon,hostelpicture,dealerverify,hostelCautions,hostelContent FROM Hostel where hostelLon= ? order by hostelLon";

			pstmt = con.prepareStatement(listByThisKey);
			pstmt.setDouble(1, hostelLon);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				hostelVO = new HostelVO();
				hostelVO.setHostelNo(rs.getInt("hostelNo"));
				hostelVO.setDealerNo(rs.getInt("dealerNo"));
				hostelVO.setHostelName(rs.getString("hostelName"));
				hostelVO.setHostelPhone(rs.getString("hostelPhone"));
				hostelVO.setHostelAddress(rs.getString("hostelAddress"));
				hostelVO.setHostelWebPages(rs.getString("hostelWebPages"));
				hostelVO.setHostelState(rs.getInt("hostelState"));
				hostelVO.setHostelVerification(rs.getInt("hostelVerification"));
				hostelVO.setHostelLat(rs.getDouble("hostelLat"));
				hostelVO.setHostelLon(rs.getDouble("hostelLon"));
				hostelVO.setHostelPicture(rs.getBytes("hostelpicture"));
				hostelVO.setDealerVerify(rs.getBytes("dealerverify"));
				hostelVO.setHostelCautions(rs.getString("hostelCautions"));
				hostelVO.setHostelContent(rs.getString("hostelContent"));
				list.add(hostelVO);
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

	public HostelVO findByHostelCautions(String hostelCautions) {
		Connection con = null;
		PreparedStatement pstmt = null;

		HostelVO hostelVO = null;
		ResultSet rs = null;
		try {

			con = ds.getConnection();
			String findByThisKey = "SELECT hostelNo,dealerNo,hostelName,hostelPhone,hostelAddress,hostelWebPages,hostelState,hostelVerification,hostelLat,hostelLon,hostelpicture,dealerverify,hostelCautions,hostelContent FROM Hostel where hostelCautions= ?";

			pstmt = con.prepareStatement(findByThisKey);
			pstmt.setString(1, hostelCautions);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				hostelVO = new HostelVO();
				hostelVO.setHostelNo(rs.getInt("hostelNo"));
				hostelVO.setDealerNo(rs.getInt("dealerNo"));
				hostelVO.setHostelName(rs.getString("hostelName"));
				hostelVO.setHostelPhone(rs.getString("hostelPhone"));
				hostelVO.setHostelAddress(rs.getString("hostelAddress"));
				hostelVO.setHostelWebPages(rs.getString("hostelWebPages"));
				hostelVO.setHostelState(rs.getInt("hostelState"));
				hostelVO.setHostelVerification(rs.getInt("hostelVerification"));
				hostelVO.setHostelLat(rs.getDouble("hostelLat"));
				hostelVO.setHostelLon(rs.getDouble("hostelLon"));
				hostelVO.setHostelPicture(rs.getBytes("hostelpicture"));
				hostelVO.setDealerVerify(rs.getBytes("dealerverify"));
				hostelVO.setHostelCautions(rs.getString("hostelCautions"));
				hostelVO.setHostelContent(rs.getString("hostelContent"));
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
		return hostelVO;
	}

	public List<HostelVO> getAllByHostelCautions(String hostelCautions) {
		Connection con = null;
		PreparedStatement pstmt = null;

		HostelVO hostelVO = null;
		List<HostelVO> list = new ArrayList<HostelVO>();
		ResultSet rs = null;
		try {

			con = ds.getConnection();
			String listByThisKey = "SELECT hostelNo,dealerNo,hostelName,hostelPhone,hostelAddress,hostelWebPages,hostelState,hostelVerification,hostelLat,hostelLon,hostelpicture,dealerverify,hostelCautions,hostelContent FROM Hostel where hostelCautions= ? order by hostelCautions";

			pstmt = con.prepareStatement(listByThisKey);
			pstmt.setString(1, hostelCautions);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				hostelVO = new HostelVO();
				hostelVO.setHostelNo(rs.getInt("hostelNo"));
				hostelVO.setDealerNo(rs.getInt("dealerNo"));
				hostelVO.setHostelName(rs.getString("hostelName"));
				hostelVO.setHostelPhone(rs.getString("hostelPhone"));
				hostelVO.setHostelAddress(rs.getString("hostelAddress"));
				hostelVO.setHostelWebPages(rs.getString("hostelWebPages"));
				hostelVO.setHostelState(rs.getInt("hostelState"));
				hostelVO.setHostelVerification(rs.getInt("hostelVerification"));
				hostelVO.setHostelLat(rs.getDouble("hostelLat"));
				hostelVO.setHostelLon(rs.getDouble("hostelLon"));
				hostelVO.setHostelPicture(rs.getBytes("hostelpicture"));
				hostelVO.setDealerVerify(rs.getBytes("dealerverify"));
				hostelVO.setHostelCautions(rs.getString("hostelCautions"));
				hostelVO.setHostelContent(rs.getString("hostelContent"));
				list.add(hostelVO);
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

	public HostelVO findByHostelContent(String hostelContent) {
		Connection con = null;
		PreparedStatement pstmt = null;

		HostelVO hostelVO = null;
		ResultSet rs = null;
		try {

			con = ds.getConnection();
			String findByThisKey = "SELECT hostelNo,dealerNo,hostelName,hostelPhone,hostelAddress,hostelWebPages,hostelState,hostelVerification,hostelLat,hostelLon,hostelpicture,dealerverify,hostelCautions,hostelContent FROM Hostel where hostelContent= ?";

			pstmt = con.prepareStatement(findByThisKey);
			pstmt.setString(1, hostelContent);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				hostelVO = new HostelVO();
				hostelVO.setHostelNo(rs.getInt("hostelNo"));
				hostelVO.setDealerNo(rs.getInt("dealerNo"));
				hostelVO.setHostelName(rs.getString("hostelName"));
				hostelVO.setHostelPhone(rs.getString("hostelPhone"));
				hostelVO.setHostelAddress(rs.getString("hostelAddress"));
				hostelVO.setHostelWebPages(rs.getString("hostelWebPages"));
				hostelVO.setHostelState(rs.getInt("hostelState"));
				hostelVO.setHostelVerification(rs.getInt("hostelVerification"));
				hostelVO.setHostelLat(rs.getDouble("hostelLat"));
				hostelVO.setHostelLon(rs.getDouble("hostelLon"));
				hostelVO.setHostelPicture(rs.getBytes("hostelpicture"));
				hostelVO.setDealerVerify(rs.getBytes("dealerverify"));
				hostelVO.setHostelCautions(rs.getString("hostelCautions"));
				hostelVO.setHostelContent(rs.getString("hostelContent"));
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
		return hostelVO;
	}

	public List<HostelVO> getAllByHostelContent(String hostelContent) {
		Connection con = null;
		PreparedStatement pstmt = null;

		HostelVO hostelVO = null;
		List<HostelVO> list = new ArrayList<HostelVO>();
		ResultSet rs = null;
		try {

			con = ds.getConnection();
			String listByThisKey = "SELECT hostelNo,dealerNo,hostelName,hostelPhone,hostelAddress,hostelWebPages,hostelState,hostelVerification,hostelLat,hostelLon,hostelpicture,dealerverify,hostelCautions,hostelContent FROM Hostel where hostelContent= ? order by hostelContent";

			pstmt = con.prepareStatement(listByThisKey);
			pstmt.setString(1, hostelContent);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				hostelVO = new HostelVO();
				hostelVO.setHostelNo(rs.getInt("hostelNo"));
				hostelVO.setDealerNo(rs.getInt("dealerNo"));
				hostelVO.setHostelName(rs.getString("hostelName"));
				hostelVO.setHostelPhone(rs.getString("hostelPhone"));
				hostelVO.setHostelAddress(rs.getString("hostelAddress"));
				hostelVO.setHostelWebPages(rs.getString("hostelWebPages"));
				hostelVO.setHostelState(rs.getInt("hostelState"));
				hostelVO.setHostelVerification(rs.getInt("hostelVerification"));
				hostelVO.setHostelLat(rs.getDouble("hostelLat"));
				hostelVO.setHostelLon(rs.getDouble("hostelLon"));
				hostelVO.setHostelPicture(rs.getBytes("hostelpicture"));
				hostelVO.setDealerVerify(rs.getBytes("dealerverify"));
				hostelVO.setHostelCautions(rs.getString("hostelCautions"));
				hostelVO.setHostelContent(rs.getString("hostelContent"));
				list.add(hostelVO);
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
	
	///backend///
	@Override
	public void update_bk(HostelVO hostelVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		
		try{
			con=ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_bk);
			pstmt.setString(1, hostelVO.getHostelName());
			pstmt.setString(2, hostelVO.getHostelPhone() );
			pstmt.setString(3,hostelVO.getHostelAddress());
			pstmt.setString(4,hostelVO.getHostelWebPages() );
			pstmt.setInt(5,hostelVO.getHostelState() );
			pstmt.setInt(6,hostelVO.getHostelVerification());
			pstmt.setDouble(7,hostelVO.getHostelLat());
			pstmt.setDouble(8,hostelVO.getHostelLon());
			pstmt.setString(9, hostelVO.getHostelCautions());
			pstmt.setString(10, hostelVO.getHostelContent());
			pstmt.setInt(11, hostelVO.getHostelNo());
			pstmt.executeUpdate();
		}catch (SQLException se) {
			System.out.println(se);
			System.out.println(se.getMessage());
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
	public void update_img_bk(HostelVO hostelVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		InputStream hostelPicStream=new ByteArrayInputStream(hostelVO.getHostelPicture());
		InputStream dealerVerifyStream=new ByteArrayInputStream(hostelVO.getDealerVerify());
		
		try{
			con=ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_bk);
			pstmt.setBinaryStream(1, hostelPicStream );
			pstmt.setBinaryStream(2, dealerVerifyStream);
			pstmt.setInt(3, hostelVO.getHostelNo());
			pstmt.executeUpdate();
			hostelPicStream.close();
			dealerVerifyStream.close();
			
		}catch(IOException e){
			e.printStackTrace();
		}catch (SQLException se) {
			System.out.println(se);
			System.out.println(se.getMessage());
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
	public HostelVO findByDealer(Integer dealerNo) {

		HostelVO hostelVO=null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con=ds.getConnection();
			pstmt = con.prepareStatement(GET_BY_DEALER);
			
			pstmt.setInt(1, dealerNo);
			
			rs=pstmt.executeQuery();
			
			while(rs.next()){
				hostelVO=new HostelVO();
				hostelVO.setHostelNo(rs.getInt("hostelNo"));
				hostelVO.setDealerNo(rs.getInt("dealerNo"));
				hostelVO.setHostelName(rs.getString("hostelName"));
				hostelVO.setHostelPhone(rs.getString("hostelPhone"));
				hostelVO.setHostelAddress(rs.getString("hostelAddress"));
				hostelVO.setHostelWebPages(rs.getString("hostelWebPages")) ;
				hostelVO.setHostelState(rs.getInt("hostelState")) ;
				hostelVO.setHostelVerification(rs.getInt("hostelVerification"));
				hostelVO.setHostelLat(rs.getDouble("hostelLat"));
				hostelVO.setHostelLon(rs.getDouble("hostelLon"));
				hostelVO.setHostelPicture(rs.getBytes("hostelPicture"));
				hostelVO.setDealerVerify(rs.getBytes("dealerVerify"));
				hostelVO.setHostelCautions(rs.getString("hostelCautions"));
				hostelVO.setHostelContent(rs.getString("hostelContent"));
			
			}
			
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
		
		return hostelVO;
	}
	
	public List<HostelVO> getAllbk(Map<String, String[]> map) {
		List<HostelVO> list = new ArrayList<HostelVO>();
		HostelVO hostelVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			String SQL = "select hostelNo ,hostelName,dealerNo, hostelAddress, hostelState, hostelVERIFICATION"
					+" from hostel "
					+ CompositeQuery_Hostel.getWhere(map) +" order by hostelNo";
			
			pstmt = con.prepareStatement(SQL);
			System.out.println("hostelSQL = " + SQL);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				hostelVO=new HostelVO();
				hostelVO.setHostelNo(rs.getInt("hostelNo"));
				hostelVO.setDealerNo(rs.getInt("dealerNo"));
				hostelVO.setHostelName(rs.getString("hostelName"));
				hostelVO.setHostelAddress(rs.getString("hostelAddress"));
				hostelVO.setHostelState(rs.getInt("hostelState")) ;
				hostelVO.setHostelVerification(rs.getInt("hostelVerification"));
				list.add(hostelVO);
			}
			
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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

}
