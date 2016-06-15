/*本程式是使用DAO產生器自動產生 copyRight:wildli0422@gmail.com 我愛一條柴*/
package com.hostelOrderDetail.model;
import java.util.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class HostelOrderDetailDAO implements HostelOrderDetailDAO_interface,HostelOrderDetailTransactionInvoke_interface {
  private static DataSource ds = null;
  static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/ZA105G3");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

  /*transactionInvoke is true need to override doTransaction method */
  @Override
public void doTransaction (Connection con, HostelOrderDetailVO hostelOrderDetailVO)throws Exception {
	  PreparedStatement pstmt = null;
	  
	  pstmt = con.prepareStatement(INSERT_STMT);
	  pstmt.setInt(1, hostelOrderDetailVO.getHostelOrderNo());
	  pstmt.setInt(2, hostelOrderDetailVO.getRoomTypeNo());
	  pstmt.setInt(3, hostelOrderDetailVO.getRoomQuantity());
	  pstmt.setTimestamp(4, hostelOrderDetailVO.getCheckInDate());
	  pstmt.setTimestamp(5, hostelOrderDetailVO.getCheckOutDate());
	  pstmt.setInt(6, hostelOrderDetailVO.getTotalPrice());
	  pstmt.executeUpdate();
//wait to override !
}
//add some static stmt
	private static final String INSERT_STMT =
"INSERT INTO HostelOrderDetail (hostelOrderDetailNo,hostelOrderNo,roomTypeNo,roomQuantity,checkInDate,checkOutDate,totalPrice) VALUES (hostelOrderDetailNo_seq.NEXTVAL, ?, ?, ?, ?, ?, ?)";

	private static final String GET_ALL_STMT =
"SELECT hostelOrderDetailNo,hostelOrderNo,roomTypeNo,roomQuantity,checkInDate,checkOutDate,totalPrice FROM HostelOrderDetail order by hostelOrderDetailNo";

	private static final String GET_ONE_STMT =
"SELECT hostelOrderDetailNo,hostelOrderNo,roomTypeNo,roomQuantity,checkInDate,checkOutDate,totalPrice FROM HostelOrderDetail where hostelOrderDetailNo=?";

	private static final String DELETE =
"DELETE FROM HostelOrderDetail where hostelOrderDetailNo=?";

	private static final String UPDATE =
"UPDATE HostelOrderDetail set hostelOrderNo=?, roomTypeNo=?, roomQuantity=?, checkInDate=?, checkOutDate=?, totalPrice=? where hostelOrderDetailNo=?";

//start to add some regular method
	//********************************new
	@Override
	public void insertDetail(HostelOrderDetailVO hostelOrderDetailVO,Connection con) {
		PreparedStatement pstmt = null;
		try {

			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, hostelOrderDetailVO.getHostelOrderNo());
			pstmt.setInt(2, hostelOrderDetailVO.getRoomTypeNo());
			pstmt.setInt(3, hostelOrderDetailVO.getRoomQuantity());
			pstmt.setTimestamp(4, hostelOrderDetailVO.getCheckInDate());
			pstmt.setTimestamp(5, hostelOrderDetailVO.getCheckOutDate());
			pstmt.setInt(6, hostelOrderDetailVO.getTotalPrice());
			pstmt.executeUpdate();
		} catch (SQLException se) {
			if (con != null) {
				try {
					// 3●設定於當有exception發生時之catch區塊內
					System.err.print("Transaction is being ");
					System.err.println("rolled back-由-ord_list");
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
		}
	}
	//********************************new
	
	
  @Override
  public void insert(HostelOrderDetailVO hostelOrderDetailVO) {
  Connection con = null;
  PreparedStatement pstmt = null;

   try {

  con = ds.getConnection();
  pstmt = con.prepareStatement(INSERT_STMT);

  pstmt.setInt(1, hostelOrderDetailVO.getHostelOrderNo());
  pstmt.setInt(2, hostelOrderDetailVO.getRoomTypeNo());
  pstmt.setInt(3, hostelOrderDetailVO.getRoomQuantity());
  pstmt.setTimestamp(4, hostelOrderDetailVO.getCheckInDate());
  pstmt.setTimestamp(5, hostelOrderDetailVO.getCheckOutDate());
  pstmt.setInt(6, hostelOrderDetailVO.getTotalPrice());
  pstmt.executeUpdate();
  }catch (SQLException se) {
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
}

  @Override
  public void update(HostelOrderDetailVO hostelOrderDetailVO) {
  Connection con = null;
  PreparedStatement pstmt = null;

   try {

  con = ds.getConnection();
  pstmt = con.prepareStatement(UPDATE);

  pstmt.setInt(1, hostelOrderDetailVO.getHostelOrderNo());
  pstmt.setInt(2, hostelOrderDetailVO.getRoomTypeNo());
  pstmt.setInt(3, hostelOrderDetailVO.getRoomQuantity());
  pstmt.setTimestamp(4, hostelOrderDetailVO.getCheckInDate());
  pstmt.setTimestamp(5, hostelOrderDetailVO.getCheckOutDate());
  pstmt.setInt(6, hostelOrderDetailVO.getTotalPrice());
  pstmt.setInt(7, hostelOrderDetailVO.getHostelOrderDetailNo());
  pstmt.executeUpdate();
  }catch (SQLException se) {
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
}

  @Override
  public void delete(Integer hostelOrderDetailNo) {
  Connection con = null;
  PreparedStatement pstmt = null;

   try {

  con = ds.getConnection();
  pstmt = con.prepareStatement(DELETE);

  pstmt.setInt(1, hostelOrderDetailNo);
  pstmt.executeUpdate();
  }catch (SQLException se) {
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
}

  @Override
  public HostelOrderDetailVO findByPrimaryKey(Integer hostelOrderDetailNo) {
  Connection con = null;
  PreparedStatement pstmt = null;

  HostelOrderDetailVO hostelOrderDetailVO = null;
  ResultSet rs = null;
   try {

  con = ds.getConnection();
  pstmt = con.prepareStatement(GET_ONE_STMT);

  pstmt.setInt(1, hostelOrderDetailNo);
			rs = pstmt.executeQuery();
			while (rs.next()) {
  hostelOrderDetailVO = new HostelOrderDetailVO();
  hostelOrderDetailVO.setHostelOrderDetailNo(rs.getInt("hostelOrderDetailNo"));
  hostelOrderDetailVO.setHostelOrderNo(rs.getInt("hostelOrderNo"));
  hostelOrderDetailVO.setRoomTypeNo(rs.getInt("roomTypeNo"));
  hostelOrderDetailVO.setRoomQuantity(rs.getInt("roomQuantity"));
  hostelOrderDetailVO.setCheckInDate(rs.getTimestamp("checkInDate"));
  hostelOrderDetailVO.setCheckOutDate(rs.getTimestamp("checkOutDate"));
  hostelOrderDetailVO.setTotalPrice(rs.getInt("totalPrice"));
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
		return hostelOrderDetailVO;
}

  @Override
	public List<HostelOrderDetailVO> getAll() {
  Connection con = null;
  PreparedStatement pstmt = null;

  HostelOrderDetailVO hostelOrderDetailVO = null;
  ResultSet rs = null;
  List<HostelOrderDetailVO> list = new ArrayList<HostelOrderDetailVO>();
   try {

  con = ds.getConnection();
  pstmt = con.prepareStatement(GET_ALL_STMT);

			rs = pstmt.executeQuery();
			while (rs.next()) {
  hostelOrderDetailVO = new HostelOrderDetailVO();
  hostelOrderDetailVO.setHostelOrderDetailNo(rs.getInt("hostelOrderDetailNo"));
  hostelOrderDetailVO.setHostelOrderNo(rs.getInt("hostelOrderNo"));
  hostelOrderDetailVO.setRoomTypeNo(rs.getInt("roomTypeNo"));
  hostelOrderDetailVO.setRoomQuantity(rs.getInt("roomQuantity"));
  hostelOrderDetailVO.setCheckInDate(rs.getTimestamp("checkInDate"));
  hostelOrderDetailVO.setCheckOutDate(rs.getTimestamp("checkOutDate"));
  hostelOrderDetailVO.setTotalPrice(rs.getInt("totalPrice"));
  list.add(hostelOrderDetailVO);
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

  @Override
	public List<HostelOrderDetailVO> likeByColumn(String columnName,String likeString) {
  Connection con = null;
  Statement pstmt = null;

  HostelOrderDetailVO hostelOrderDetailVO = null;
  ResultSet rs = null;
  List<HostelOrderDetailVO> list = new ArrayList<HostelOrderDetailVO>();
  String likeSql="SELECT hostelOrderDetailNo,hostelOrderNo,roomTypeNo,roomQuantity,checkInDate,checkOutDate,totalPrice FROM HostelOrderDetail where "+columnName+" like '"+likeString+"'";

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   try {

  con = ds.getConnection();
  pstmt = con.createStatement();

			rs = pstmt.executeQuery(likeSql);
			while (rs.next()) {
  hostelOrderDetailVO = new HostelOrderDetailVO();
  hostelOrderDetailVO.setHostelOrderDetailNo(rs.getInt("hostelOrderDetailNo"));
  hostelOrderDetailVO.setHostelOrderNo(rs.getInt("hostelOrderNo"));
  hostelOrderDetailVO.setRoomTypeNo(rs.getInt("roomTypeNo"));
  hostelOrderDetailVO.setRoomQuantity(rs.getInt("roomQuantity"));
  hostelOrderDetailVO.setCheckInDate(rs.getTimestamp("checkInDate"));
  hostelOrderDetailVO.setCheckOutDate(rs.getTimestamp("checkOutDate"));
  hostelOrderDetailVO.setTotalPrice(rs.getInt("totalPrice"));
  list.add(hostelOrderDetailVO);
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
    public HostelOrderDetailVO findByHostelOrderNo(Integer hostelOrderNo) {
  Connection con = null;
  PreparedStatement pstmt = null;

  HostelOrderDetailVO hostelOrderDetailVO = null;
  ResultSet rs = null;
   try {

  con = ds.getConnection();
  String findByThisKey =
  "SELECT hostelOrderDetailNo,hostelOrderNo,roomTypeNo,roomQuantity,checkInDate,checkOutDate,totalPrice FROM HostelOrderDetail where hostelOrderNo= ?";

    pstmt = con.prepareStatement(findByThisKey);
  pstmt.setInt(1, hostelOrderNo);
			rs = pstmt.executeQuery();
			while (rs.next()) {
  hostelOrderDetailVO = new HostelOrderDetailVO();
  hostelOrderDetailVO.setHostelOrderDetailNo(  rs.getInt("hostelOrderDetailNo"));
  hostelOrderDetailVO.setHostelOrderNo(  rs.getInt("hostelOrderNo"));
  hostelOrderDetailVO.setRoomTypeNo(  rs.getInt("roomTypeNo"));
  hostelOrderDetailVO.setRoomQuantity(  rs.getInt("roomQuantity"));
  hostelOrderDetailVO.setCheckInDate(  rs.getTimestamp("checkInDate"));
  hostelOrderDetailVO.setCheckOutDate(  rs.getTimestamp("checkOutDate"));
  hostelOrderDetailVO.setTotalPrice(  rs.getInt("totalPrice"));
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
		return hostelOrderDetailVO;
}

    public List<HostelOrderDetailVO> getAllByHostelOrderNo(Integer hostelOrderNo) {
  Connection con = null;
  PreparedStatement pstmt = null;

  HostelOrderDetailVO hostelOrderDetailVO = null;
  List<HostelOrderDetailVO> list = new ArrayList<HostelOrderDetailVO>();
  ResultSet rs = null;
   try {

  con = ds.getConnection();
  String listByThisKey =
  "SELECT hostelOrderDetailNo,hostelOrderNo,roomTypeNo,roomQuantity,checkInDate,checkOutDate,totalPrice FROM HostelOrderDetail where hostelOrderNo= ? order by hostelOrderNo";

   pstmt = con.prepareStatement(listByThisKey);
   pstmt.setInt(1, hostelOrderNo);
		 rs = pstmt.executeQuery();
			while (rs.next()) {
  hostelOrderDetailVO = new HostelOrderDetailVO();
  hostelOrderDetailVO.setHostelOrderDetailNo(  rs.getInt("hostelOrderDetailNo"));
  hostelOrderDetailVO.setHostelOrderNo(  rs.getInt("hostelOrderNo"));
  hostelOrderDetailVO.setRoomTypeNo(  rs.getInt("roomTypeNo"));
  hostelOrderDetailVO.setRoomQuantity(  rs.getInt("roomQuantity"));
  hostelOrderDetailVO.setCheckInDate(  rs.getTimestamp("checkInDate"));
  hostelOrderDetailVO.setCheckOutDate(  rs.getTimestamp("checkOutDate"));
  hostelOrderDetailVO.setTotalPrice(  rs.getInt("totalPrice"));
  list.add(hostelOrderDetailVO);
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

    public HostelOrderDetailVO findByRoomTypeNo(Integer roomTypeNo) {
  Connection con = null;
  PreparedStatement pstmt = null;

  HostelOrderDetailVO hostelOrderDetailVO = null;
  ResultSet rs = null;
   try {

  con = ds.getConnection();
  String findByThisKey =
  "SELECT hostelOrderDetailNo,hostelOrderNo,roomTypeNo,roomQuantity,checkInDate,checkOutDate,totalPrice FROM HostelOrderDetail where roomTypeNo= ?";

    pstmt = con.prepareStatement(findByThisKey);
  pstmt.setInt(1, roomTypeNo);
			rs = pstmt.executeQuery();
			while (rs.next()) {
  hostelOrderDetailVO = new HostelOrderDetailVO();
  hostelOrderDetailVO.setHostelOrderDetailNo(  rs.getInt("hostelOrderDetailNo"));
  hostelOrderDetailVO.setHostelOrderNo(  rs.getInt("hostelOrderNo"));
  hostelOrderDetailVO.setRoomTypeNo(  rs.getInt("roomTypeNo"));
  hostelOrderDetailVO.setRoomQuantity(  rs.getInt("roomQuantity"));
  hostelOrderDetailVO.setCheckInDate(  rs.getTimestamp("checkInDate"));
  hostelOrderDetailVO.setCheckOutDate(  rs.getTimestamp("checkOutDate"));
  hostelOrderDetailVO.setTotalPrice(  rs.getInt("totalPrice"));
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
		return hostelOrderDetailVO;
}

    public List<HostelOrderDetailVO> getAllByRoomTypeNo(Integer roomTypeNo) {
  Connection con = null;
  PreparedStatement pstmt = null;

  HostelOrderDetailVO hostelOrderDetailVO = null;
  List<HostelOrderDetailVO> list = new ArrayList<HostelOrderDetailVO>();
  ResultSet rs = null;
   try {

  con = ds.getConnection();
  String listByThisKey =
  "SELECT hostelOrderDetailNo,hostelOrderNo,roomTypeNo,roomQuantity,checkInDate,checkOutDate,totalPrice FROM HostelOrderDetail where roomTypeNo= ? order by roomTypeNo";

   pstmt = con.prepareStatement(listByThisKey);
   pstmt.setInt(1, roomTypeNo);
		 rs = pstmt.executeQuery();
			while (rs.next()) {
  hostelOrderDetailVO = new HostelOrderDetailVO();
  hostelOrderDetailVO.setHostelOrderDetailNo(  rs.getInt("hostelOrderDetailNo"));
  hostelOrderDetailVO.setHostelOrderNo(  rs.getInt("hostelOrderNo"));
  hostelOrderDetailVO.setRoomTypeNo(  rs.getInt("roomTypeNo"));
  hostelOrderDetailVO.setRoomQuantity(  rs.getInt("roomQuantity"));
  hostelOrderDetailVO.setCheckInDate(  rs.getTimestamp("checkInDate"));
  hostelOrderDetailVO.setCheckOutDate(  rs.getTimestamp("checkOutDate"));
  hostelOrderDetailVO.setTotalPrice(  rs.getInt("totalPrice"));
  list.add(hostelOrderDetailVO);
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

    public HostelOrderDetailVO findByRoomQuantity(Integer roomQuantity) {
  Connection con = null;
  PreparedStatement pstmt = null;

  HostelOrderDetailVO hostelOrderDetailVO = null;
  ResultSet rs = null;
   try {

  con = ds.getConnection();
  String findByThisKey =
  "SELECT hostelOrderDetailNo,hostelOrderNo,roomTypeNo,roomQuantity,checkInDate,checkOutDate,totalPrice FROM HostelOrderDetail where roomQuantity= ?";

    pstmt = con.prepareStatement(findByThisKey);
  pstmt.setInt(1, roomQuantity);
			rs = pstmt.executeQuery();
			while (rs.next()) {
  hostelOrderDetailVO = new HostelOrderDetailVO();
  hostelOrderDetailVO.setHostelOrderDetailNo(  rs.getInt("hostelOrderDetailNo"));
  hostelOrderDetailVO.setHostelOrderNo(  rs.getInt("hostelOrderNo"));
  hostelOrderDetailVO.setRoomTypeNo(  rs.getInt("roomTypeNo"));
  hostelOrderDetailVO.setRoomQuantity(  rs.getInt("roomQuantity"));
  hostelOrderDetailVO.setCheckInDate(  rs.getTimestamp("checkInDate"));
  hostelOrderDetailVO.setCheckOutDate(  rs.getTimestamp("checkOutDate"));
  hostelOrderDetailVO.setTotalPrice(  rs.getInt("totalPrice"));
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
		return hostelOrderDetailVO;
}

    public List<HostelOrderDetailVO> getAllByRoomQuantity(Integer roomQuantity) {
  Connection con = null;
  PreparedStatement pstmt = null;

  HostelOrderDetailVO hostelOrderDetailVO = null;
  List<HostelOrderDetailVO> list = new ArrayList<HostelOrderDetailVO>();
  ResultSet rs = null;
   try {

  con = ds.getConnection();
  String listByThisKey =
  "SELECT hostelOrderDetailNo,hostelOrderNo,roomTypeNo,roomQuantity,checkInDate,checkOutDate,totalPrice FROM HostelOrderDetail where roomQuantity= ? order by roomQuantity";

   pstmt = con.prepareStatement(listByThisKey);
   pstmt.setInt(1, roomQuantity);
		 rs = pstmt.executeQuery();
			while (rs.next()) {
  hostelOrderDetailVO = new HostelOrderDetailVO();
  hostelOrderDetailVO.setHostelOrderDetailNo(  rs.getInt("hostelOrderDetailNo"));
  hostelOrderDetailVO.setHostelOrderNo(  rs.getInt("hostelOrderNo"));
  hostelOrderDetailVO.setRoomTypeNo(  rs.getInt("roomTypeNo"));
  hostelOrderDetailVO.setRoomQuantity(  rs.getInt("roomQuantity"));
  hostelOrderDetailVO.setCheckInDate(  rs.getTimestamp("checkInDate"));
  hostelOrderDetailVO.setCheckOutDate(  rs.getTimestamp("checkOutDate"));
  hostelOrderDetailVO.setTotalPrice(  rs.getInt("totalPrice"));
  list.add(hostelOrderDetailVO);
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

    public HostelOrderDetailVO findByCheckInDate(java.sql.Timestamp checkInDate) {
  Connection con = null;
  PreparedStatement pstmt = null;

  HostelOrderDetailVO hostelOrderDetailVO = null;
  ResultSet rs = null;
   try {

  con = ds.getConnection();
  String findByThisKey =
  "SELECT hostelOrderDetailNo,hostelOrderNo,roomTypeNo,roomQuantity,checkInDate,checkOutDate,totalPrice FROM HostelOrderDetail where checkInDate= ?";

    pstmt = con.prepareStatement(findByThisKey);
  pstmt.setTimestamp(1, checkInDate);
			rs = pstmt.executeQuery();
			while (rs.next()) {
  hostelOrderDetailVO = new HostelOrderDetailVO();
  hostelOrderDetailVO.setHostelOrderDetailNo(  rs.getInt("hostelOrderDetailNo"));
  hostelOrderDetailVO.setHostelOrderNo(  rs.getInt("hostelOrderNo"));
  hostelOrderDetailVO.setRoomTypeNo(  rs.getInt("roomTypeNo"));
  hostelOrderDetailVO.setRoomQuantity(  rs.getInt("roomQuantity"));
  hostelOrderDetailVO.setCheckInDate(  rs.getTimestamp("checkInDate"));
  hostelOrderDetailVO.setCheckOutDate(  rs.getTimestamp("checkOutDate"));
  hostelOrderDetailVO.setTotalPrice(  rs.getInt("totalPrice"));
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
		return hostelOrderDetailVO;
}

    public List<HostelOrderDetailVO> getAllByCheckInDate(java.sql.Timestamp checkInDate) {
  Connection con = null;
  PreparedStatement pstmt = null;

  HostelOrderDetailVO hostelOrderDetailVO = null;
  List<HostelOrderDetailVO> list = new ArrayList<HostelOrderDetailVO>();
  ResultSet rs = null;
   try {

  con = ds.getConnection();
  String listByThisKey =
  "SELECT hostelOrderDetailNo,hostelOrderNo,roomTypeNo,roomQuantity,checkInDate,checkOutDate,totalPrice FROM HostelOrderDetail where checkInDate= ? order by checkInDate";

   pstmt = con.prepareStatement(listByThisKey);
   pstmt.setTimestamp(1, checkInDate);
		 rs = pstmt.executeQuery();
			while (rs.next()) {
  hostelOrderDetailVO = new HostelOrderDetailVO();
  hostelOrderDetailVO.setHostelOrderDetailNo(  rs.getInt("hostelOrderDetailNo"));
  hostelOrderDetailVO.setHostelOrderNo(  rs.getInt("hostelOrderNo"));
  hostelOrderDetailVO.setRoomTypeNo(  rs.getInt("roomTypeNo"));
  hostelOrderDetailVO.setRoomQuantity(  rs.getInt("roomQuantity"));
  hostelOrderDetailVO.setCheckInDate(  rs.getTimestamp("checkInDate"));
  hostelOrderDetailVO.setCheckOutDate(  rs.getTimestamp("checkOutDate"));
  hostelOrderDetailVO.setTotalPrice(  rs.getInt("totalPrice"));
  list.add(hostelOrderDetailVO);
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

    public HostelOrderDetailVO findByCheckOutDate(java.sql.Timestamp checkOutDate) {
  Connection con = null;
  PreparedStatement pstmt = null;

  HostelOrderDetailVO hostelOrderDetailVO = null;
  ResultSet rs = null;
   try {

  con = ds.getConnection();
  String findByThisKey =
  "SELECT hostelOrderDetailNo,hostelOrderNo,roomTypeNo,roomQuantity,checkInDate,checkOutDate,totalPrice FROM HostelOrderDetail where checkOutDate= ?";

    pstmt = con.prepareStatement(findByThisKey);
  pstmt.setTimestamp(1, checkOutDate);
			rs = pstmt.executeQuery();
			while (rs.next()) {
  hostelOrderDetailVO = new HostelOrderDetailVO();
  hostelOrderDetailVO.setHostelOrderDetailNo(  rs.getInt("hostelOrderDetailNo"));
  hostelOrderDetailVO.setHostelOrderNo(  rs.getInt("hostelOrderNo"));
  hostelOrderDetailVO.setRoomTypeNo(  rs.getInt("roomTypeNo"));
  hostelOrderDetailVO.setRoomQuantity(  rs.getInt("roomQuantity"));
  hostelOrderDetailVO.setCheckInDate(  rs.getTimestamp("checkInDate"));
  hostelOrderDetailVO.setCheckOutDate(  rs.getTimestamp("checkOutDate"));
  hostelOrderDetailVO.setTotalPrice(  rs.getInt("totalPrice"));
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
		return hostelOrderDetailVO;
}

    public List<HostelOrderDetailVO> getAllByCheckOutDate(java.sql.Timestamp checkOutDate) {
  Connection con = null;
  PreparedStatement pstmt = null;

  HostelOrderDetailVO hostelOrderDetailVO = null;
  List<HostelOrderDetailVO> list = new ArrayList<HostelOrderDetailVO>();
  ResultSet rs = null;
   try {

  con = ds.getConnection();
  String listByThisKey =
  "SELECT hostelOrderDetailNo,hostelOrderNo,roomTypeNo,roomQuantity,checkInDate,checkOutDate,totalPrice FROM HostelOrderDetail where checkOutDate= ? order by checkOutDate";

   pstmt = con.prepareStatement(listByThisKey);
   pstmt.setTimestamp(1, checkOutDate);
		 rs = pstmt.executeQuery();
			while (rs.next()) {
  hostelOrderDetailVO = new HostelOrderDetailVO();
  hostelOrderDetailVO.setHostelOrderDetailNo(  rs.getInt("hostelOrderDetailNo"));
  hostelOrderDetailVO.setHostelOrderNo(  rs.getInt("hostelOrderNo"));
  hostelOrderDetailVO.setRoomTypeNo(  rs.getInt("roomTypeNo"));
  hostelOrderDetailVO.setRoomQuantity(  rs.getInt("roomQuantity"));
  hostelOrderDetailVO.setCheckInDate(  rs.getTimestamp("checkInDate"));
  hostelOrderDetailVO.setCheckOutDate(  rs.getTimestamp("checkOutDate"));
  hostelOrderDetailVO.setTotalPrice(  rs.getInt("totalPrice"));
  list.add(hostelOrderDetailVO);
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

    public HostelOrderDetailVO findByTotalPrice(Integer totalPrice) {
  Connection con = null;
  PreparedStatement pstmt = null;

  HostelOrderDetailVO hostelOrderDetailVO = null;
  ResultSet rs = null;
   try {

  con = ds.getConnection();
  String findByThisKey =
  "SELECT hostelOrderDetailNo,hostelOrderNo,roomTypeNo,roomQuantity,checkInDate,checkOutDate,totalPrice FROM HostelOrderDetail where totalPrice= ?";

    pstmt = con.prepareStatement(findByThisKey);
  pstmt.setInt(1, totalPrice);
			rs = pstmt.executeQuery();
			while (rs.next()) {
  hostelOrderDetailVO = new HostelOrderDetailVO();
  hostelOrderDetailVO.setHostelOrderDetailNo(  rs.getInt("hostelOrderDetailNo"));
  hostelOrderDetailVO.setHostelOrderNo(  rs.getInt("hostelOrderNo"));
  hostelOrderDetailVO.setRoomTypeNo(  rs.getInt("roomTypeNo"));
  hostelOrderDetailVO.setRoomQuantity(  rs.getInt("roomQuantity"));
  hostelOrderDetailVO.setCheckInDate(  rs.getTimestamp("checkInDate"));
  hostelOrderDetailVO.setCheckOutDate(  rs.getTimestamp("checkOutDate"));
  hostelOrderDetailVO.setTotalPrice(  rs.getInt("totalPrice"));
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
		return hostelOrderDetailVO;
}

    public List<HostelOrderDetailVO> getAllByTotalPrice(Integer totalPrice) {
  Connection con = null;
  PreparedStatement pstmt = null;

  HostelOrderDetailVO hostelOrderDetailVO = null;
  List<HostelOrderDetailVO> list = new ArrayList<HostelOrderDetailVO>();
  ResultSet rs = null;
   try {

  con = ds.getConnection();
  String listByThisKey =
  "SELECT hostelOrderDetailNo,hostelOrderNo,roomTypeNo,roomQuantity,checkInDate,checkOutDate,totalPrice FROM HostelOrderDetail where totalPrice= ? order by totalPrice";

   pstmt = con.prepareStatement(listByThisKey);
   pstmt.setInt(1, totalPrice);
		 rs = pstmt.executeQuery();
			while (rs.next()) {
  hostelOrderDetailVO = new HostelOrderDetailVO();
  hostelOrderDetailVO.setHostelOrderDetailNo(  rs.getInt("hostelOrderDetailNo"));
  hostelOrderDetailVO.setHostelOrderNo(  rs.getInt("hostelOrderNo"));
  hostelOrderDetailVO.setRoomTypeNo(  rs.getInt("roomTypeNo"));
  hostelOrderDetailVO.setRoomQuantity(  rs.getInt("roomQuantity"));
  hostelOrderDetailVO.setCheckInDate(  rs.getTimestamp("checkInDate"));
  hostelOrderDetailVO.setCheckOutDate(  rs.getTimestamp("checkOutDate"));
  hostelOrderDetailVO.setTotalPrice(  rs.getInt("totalPrice"));
  list.add(hostelOrderDetailVO);
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