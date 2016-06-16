/*�蝔�雿輻DAO��������� copyRight:wildli0422@gmail.com ����璇*/
package com.hostelOrder.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.*;
import java.sql.Date;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.xml.stream.events.Comment;

import com.hostelOrderDetail.model.HostelOrderDetailDAO;
import com.hostelOrderDetail.model.HostelOrderDetailTransactionInvoke_interface;
import com.hostelOrderDetail.model.HostelOrderDetailVO;

import jdbc.util.CompositeQuery.CQforHostelOrder;
import tool.cart.model.OrderCartVO;

public class HostelOrderDAO implements HostelOrderDAO_interface,
		HostelOrderTransactionInvoke_interface {
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/ZA105G3");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	/* transactionInvoke is true need to override doTransaction method */
	@Override
	public int doTransaction(Connection con, HostelOrderVO hostelOrderVO,
			List<OrderCartVO> cartList) throws Exception {
		String sqlStr = "INSERT INTO HostelOrder(hostelorderno,hostelno,tenantno,hostelorderdate,customerquantity,paymentway) VALUES (hostelOrderNo_seq.NEXTVAL,?,?,SYSDATE,?,?)";
		PreparedStatement pstmt = null;
		String cols[] = { "hostelorderno" };

		pstmt = con.prepareStatement(sqlStr, cols);
		pstmt.setInt(1, hostelOrderVO.getHostelNo());
		pstmt.setInt(2, hostelOrderVO.getTenantNo());
		pstmt.setInt(3, hostelOrderVO.getCustomerQuantity());
		pstmt.setString(4, hostelOrderVO.getPaymentWay());
		pstmt.executeUpdate();
		int ordSeqNo = 0;
		ResultSet rs = pstmt.getGeneratedKeys();
		if (rs.next()) {
			ordSeqNo = rs.getInt(1);
			System.out.println("ordSeqNo = " + ordSeqNo);
		} else {
			System.out.println("can't get seqNo!");
		}
		rs.close();
		HostelOrderDetailTransactionInvoke_interface tranDao = new HostelOrderDetailDAO();
		SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		for (int i = 0; i < cartList.size(); i++) {
			OrderCartVO nowCart = cartList.get(i);
			HostelOrderDetailVO nowDetail = new HostelOrderDetailVO();
			nowDetail.setHostelOrderNo(ordSeqNo);
			nowDetail.setRoomTypeNo(nowCart.getRoomTypeNo());
			nowDetail.setRoomQuantity(nowCart.getRoomQty());
			Timestamp inStamp = new Timestamp(sm.parse(
					nowCart.getCheckInDate() + " 18:00").getTime());
			nowDetail.setCheckInDate(inStamp);
			Timestamp outStamp = new Timestamp(sm.parse(
					nowCart.getCheckOutDate() + " 12:00").getTime());
			nowDetail.setCheckOutDate(outStamp);
			nowDetail.setTotalPrice(nowCart.getTotalPrice());
			tranDao.doTransaction(con, nowDetail);
		}

		return ordSeqNo;
		// wait to override !
	}

	// add some static stmt
	private static final String INSERT_STMT = "INSERT INTO HostelOrder (hostelOrderNo,hostelNo,tenantNo,hostelOrderDate,hostelScore,hostelComment,tenantScore,customerQuantity,paymentWay,paymentState,orderRemark,orderState) VALUES (hostelOrderNo_seq.NEXTVAL, ?, ?, SYSDATE, ?, ?, ?, ?, ?, ?, ?, ?)";

	private static final String GET_ALL_STMT = "SELECT hostelOrderNo,hostelNo,tenantNo,to_char(hostelOrderDate ,'yyyy-mm-dd') hostelOrderDate,hostelScore,hostelComment,tenantScore,customerQuantity,paymentWay,paymentState,orderRemark,orderState FROM HostelOrder order by hostelOrderNo";

	private static final String GET_ONE_STMT = "SELECT hostelOrderNo,hostelNo,tenantNo,to_char(hostelOrderDate ,'yyyy-mm-dd') hostelOrderDate,hostelScore,hostelComment,tenantScore,customerQuantity,paymentWay,paymentState,orderRemark,orderState FROM HostelOrder where hostelOrderNo=?";

	private static final String DELETE = "DELETE FROM HostelOrder where hostelOrderNo=?";

	private static final String UPDATE = "UPDATE HostelOrder set hostelNo=?, tenantNo=?, hostelOrderDate=?, hostelScore=?, hostelComment=?, tenantScore=?, customerQuantity=?, paymentWay=?, paymentState=?, orderRemark=?, orderState=? where hostelOrderNo=?";
	//*******
	private static final String GET_DETAIL_STMT = "select hostelOrderDetailNo,hostelOrderNo,roomTypeNo,roomQuantity,to_char(checkinDate ,'yyyy-mm-dd HH:mm:ss') checkinDate,to_char(checkoutDate ,'yyyy-mm-dd HH:mm:ss') checkoutDate FROM hostelOrderDetail where hostelOrderNo=?";

	private static final String GET_ALL_ByDATE = // history
			"select hostelOrderNo,hostelNo,tenantNo,to_char(hostelOrderDate ,'yyyy-mm-dd') hostelOrderDate,hostelScore,hostelComment,tenantScore,customerQuantity,paymentWay,paymentState,orderRemark,orderState from hostelOrder where to_char(hostelOrderDate,'YYYY-MM-DD')=? order by hostelOrderNo";
	private static final String GET_Verify_ByDATE = // on verify
			"select hostelOrderNo,hostelNo,tenantNo,to_char(hostelOrderDate ,'yyyy-mm-dd') hostelOrderDate,hostelScore,hostelComment,tenantScore,customerQuantity,paymentWay,paymentState,orderRemark,orderState from hostelOrder where hostelOrderDate=? and orderState='審核' order by hostelOrderNo";
	//*******
	// start to add some regular method
	// ***********************
	@Override
	public HostelOrderVO insertWithDetail(HostelOrderVO hostelOrderVO,List<HostelOrderDetailVO> list) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			
			con.setAutoCommit(false);
			
			String cols[]={"hostelOrderNo"};
			
			pstmt = con.prepareStatement(INSERT_STMT,cols);

			pstmt.setInt(1, hostelOrderVO.getHostelNo());
			pstmt.setInt(2, hostelOrderVO.getTenantNo());
			pstmt.setDate(3, hostelOrderVO.getHostelOrderDate());
			pstmt.setInt(4, hostelOrderVO.getHostelScore());
			pstmt.setString(5, hostelOrderVO.getHostelComment());
			pstmt.setInt(6, hostelOrderVO.getTenantScore());
			pstmt.setInt(7, hostelOrderVO.getCustomerQuantity());
			pstmt.setString(8, hostelOrderVO.getPaymentWay());
			pstmt.setString(9, hostelOrderVO.getPaymentState());
			pstmt.setString(10, hostelOrderVO.getOrderRemark());
			pstmt.setString(11, hostelOrderVO.getOrderState());
			pstmt.executeUpdate();
			
			// 掘取對應的自增主鍵值
			Integer nextOrderNo = null;
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				nextOrderNo = rs.getInt(1);
				System.out.println("自增主鍵值= " + nextOrderNo + "(剛新增成功的訂單編號)");
			} else {
				System.out.println("未取得自增主鍵值");
			}
			rs.close();
			
			hostelOrderVO.setHostelOrderNo(nextOrderNo);
			System.out.println("00");
			// 再同時新增訂單明細
			SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			HostelOrderDetailDAO dao=new HostelOrderDetailDAO();
			for(HostelOrderDetailVO detailVO:list){
				System.out.println("11");
				detailVO.setHostelOrderNo(nextOrderNo);
				
				Timestamp inStamp=null;
				Timestamp outStamp=null;
				try {
					inStamp=new Timestamp(sm.parse(detailVO.getCheckInDate()+" 18:00").getTime());
					detailVO.setCheckInDate(inStamp);
					outStamp=new Timestamp(sm.parse(detailVO.getCheckOutDate()+" 12:00").getTime());
					detailVO.setCheckOutDate(outStamp);
				} catch (Exception e) {
					con.rollback();
					e.getMessage();
				}
				
				dao.insertDetail(detailVO, con);
			}
			
			// 2●設定於 pstm.executeUpdate()之後
			con.commit();
			con.setAutoCommit(true);
			System.out.println("list.size()-B=" + list.size());
			System.out.println("新增訂單編號" + nextOrderNo + "時訂單明細共有" + list.size() + "則同時被新增");
			
		}catch (SQLException se) {
			if (con != null) {
				try {
					// 3●設定於當有exception發生時之catch區塊內
					System.err.print("Transaction is being ");
					System.err.println("rolled back-由-ord");
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
		return hostelOrderVO;
	}
	
	@Override
	public List<HostelOrderDetailVO> getHostelOrderDetail(Integer hostelOrderNo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		HostelOrderDetailVO hostelOrderDetailVO = null;
		List<HostelOrderDetailVO> list = new ArrayList<HostelOrderDetailVO>();

		ResultSet rs = null;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_DETAIL_STMT);

			pstmt.setInt(1, hostelOrderNo);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				hostelOrderDetailVO = new HostelOrderDetailVO();
				hostelOrderDetailVO.setHostelOrderDetailNo(rs
						.getInt("hostelOrderDetailNo"));
				hostelOrderDetailVO
						.setHostelOrderNo(rs.getInt("hostelOrderNo"));
				hostelOrderDetailVO.setRoomTypeNo(rs.getInt("roomTypeNo"));
				hostelOrderDetailVO.setRoomQuantity(rs.getInt("roomQuantity"));
				hostelOrderDetailVO.setCheckInDate(rs.getTimestamp("checkinDate"));
				hostelOrderDetailVO.setCheckOutDate(rs.getTimestamp("checkoutDate"));
				list.add(hostelOrderDetailVO);
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
	
	@Override
	public List<HostelOrderVO> getAllByDate(String orderDate) {

		Connection con = null;
		PreparedStatement pstmt = null;

		HostelOrderVO hostelOrderVO = null;
		ResultSet rs = null;
		List<HostelOrderVO> list = new ArrayList<HostelOrderVO>();
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_ByDATE);
			pstmt.setString(1, orderDate);
			System.out.println("dao:~~~" + orderDate);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				hostelOrderVO = new HostelOrderVO();
				hostelOrderVO.setHostelOrderNo(rs.getInt("hostelOrderNo"));
				hostelOrderVO.setHostelNo(rs.getInt("hostelNo"));
				hostelOrderVO.setTenantNo(rs.getInt("tenantNo"));
				hostelOrderVO.setHostelOrderDate(rs.getDate("hostelOrderDate"));
				hostelOrderVO.setHostelScore(rs.getInt("hostelScore"));
				hostelOrderVO.setHostelComment(rs.getString("hostelComment"));
				hostelOrderVO.setTenantScore(rs.getInt("tenantScore"));
				hostelOrderVO
						.setCustomerQuantity(rs.getInt("customerQuantity"));
				hostelOrderVO.setPaymentWay(rs.getString("paymentWay"));
				hostelOrderVO.setPaymentState(rs.getString("paymentState"));
				hostelOrderVO.setOrderState(rs.getString("orderState"));
				list.add(hostelOrderVO);
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

	@Override
	public List<HostelOrderVO> getVerifyByDate(java.sql.Date orderDate) {

		Connection con = null;
		PreparedStatement pstmt = null;

		HostelOrderVO hostelOrderVO = null;
		ResultSet rs = null;
		List<HostelOrderVO> list = new ArrayList<HostelOrderVO>();
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_Verify_ByDATE);
			pstmt.setDate(1, orderDate);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				hostelOrderVO = new HostelOrderVO();
				hostelOrderVO.setHostelOrderNo(rs.getInt("hostelOrderNo"));
				hostelOrderVO.setHostelNo(rs.getInt("hostelNo"));
				hostelOrderVO.setTenantNo(rs.getInt("tenantNo"));
				hostelOrderVO.setHostelOrderDate(rs.getDate("hostelOrderDate"));
				hostelOrderVO.setHostelScore(rs.getInt("hostelScore"));
				hostelOrderVO.setHostelComment(rs.getString("hostelComment"));
				hostelOrderVO.setTenantScore(rs.getInt("tenantScore"));
				hostelOrderVO
						.setCustomerQuantity(rs.getInt("customerQuantity"));
				hostelOrderVO.setPaymentWay(rs.getString("paymentWay"));
				hostelOrderVO.setPaymentState(rs.getString("paymentState"));
				hostelOrderVO.setOrderState(rs.getString("orderState"));
				list.add(hostelOrderVO);
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

	// ************************************

	@Override
	public void insert(HostelOrderVO hostelOrderVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, hostelOrderVO.getHostelNo());
			pstmt.setInt(2, hostelOrderVO.getTenantNo());
			pstmt.setInt(3, hostelOrderVO.getHostelScore());
			pstmt.setString(4, hostelOrderVO.getHostelComment());
			pstmt.setInt(5, hostelOrderVO.getTenantScore());
			pstmt.setInt(6, hostelOrderVO.getCustomerQuantity());
			pstmt.setString(7, hostelOrderVO.getPaymentWay());
			pstmt.setString(8, hostelOrderVO.getPaymentState());
			pstmt.setString(9, hostelOrderVO.getOrderRemark());
			pstmt.setString(10, hostelOrderVO.getOrderState());
			pstmt.executeUpdate();
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
	}

	@Override
	public void update(HostelOrderVO hostelOrderVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1, hostelOrderVO.getHostelNo());
			pstmt.setInt(2, hostelOrderVO.getTenantNo());
			pstmt.setDate(3, hostelOrderVO.getHostelOrderDate());
			pstmt.setInt(4, hostelOrderVO.getHostelScore());
			pstmt.setString(5, hostelOrderVO.getHostelComment());
			pstmt.setInt(6, hostelOrderVO.getTenantScore());
			pstmt.setInt(7, hostelOrderVO.getCustomerQuantity());
			pstmt.setString(8, hostelOrderVO.getPaymentWay());
			pstmt.setString(9, hostelOrderVO.getPaymentState());
			pstmt.setString(10, hostelOrderVO.getOrderRemark());
			pstmt.setString(11, hostelOrderVO.getOrderState());
			pstmt.setInt(12, hostelOrderVO.getHostelOrderNo());
			pstmt.executeUpdate();
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
	}

	@Override
	public void delete(Integer hostelOrderNo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, hostelOrderNo);
			pstmt.executeUpdate();
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
	}

	@Override
	public HostelOrderVO findByPrimaryKey(Integer hostelOrderNo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		HostelOrderVO hostelOrderVO = null;
		ResultSet rs = null;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, hostelOrderNo);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				hostelOrderVO = new HostelOrderVO();
				hostelOrderVO.setHostelOrderNo(rs.getInt("hostelOrderNo"));
				hostelOrderVO.setHostelNo(rs.getInt("hostelNo"));
				hostelOrderVO.setTenantNo(rs.getInt("tenantNo"));
				hostelOrderVO.setHostelOrderDate(rs.getDate("hostelOrderDate"));
				hostelOrderVO.setHostelScore(rs.getInt("hostelScore"));
				hostelOrderVO.setHostelComment(rs.getString("hostelComment"));
				hostelOrderVO.setTenantScore(rs.getInt("tenantScore"));
				hostelOrderVO
						.setCustomerQuantity(rs.getInt("customerQuantity"));
				hostelOrderVO.setPaymentWay(rs.getString("paymentWay"));
				hostelOrderVO.setPaymentState(rs.getString("paymentState"));
				hostelOrderVO.setOrderRemark(rs.getString("orderRemark"));
				hostelOrderVO.setOrderState(rs.getString("orderState"));
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
		return hostelOrderVO;
	}

	@Override
	public List<HostelOrderVO> getAll() {
		Connection con = null;
		PreparedStatement pstmt = null;

		HostelOrderVO hostelOrderVO = null;
		ResultSet rs = null;
		List<HostelOrderVO> list = new ArrayList<HostelOrderVO>();
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				hostelOrderVO = new HostelOrderVO();
				hostelOrderVO.setHostelOrderNo(rs.getInt("hostelOrderNo"));
				hostelOrderVO.setHostelNo(rs.getInt("hostelNo"));
				hostelOrderVO.setTenantNo(rs.getInt("tenantNo"));
				hostelOrderVO.setHostelOrderDate(rs.getDate("hostelOrderDate"));
				hostelOrderVO.setHostelScore(rs.getInt("hostelScore"));
				hostelOrderVO.setHostelComment(rs.getString("hostelComment"));
				hostelOrderVO.setTenantScore(rs.getInt("tenantScore"));
				hostelOrderVO
						.setCustomerQuantity(rs.getInt("customerQuantity"));
				hostelOrderVO.setPaymentWay(rs.getString("paymentWay"));
				hostelOrderVO.setPaymentState(rs.getString("paymentState"));
				hostelOrderVO.setOrderRemark(rs.getString("orderRemark"));
				hostelOrderVO.setOrderState(rs.getString("orderState"));
				list.add(hostelOrderVO);
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

	@Override
	public List<HostelOrderVO> likeByColumn(String columnName, String likeString) {
		Connection con = null;
		Statement pstmt = null;

		HostelOrderVO hostelOrderVO = null;
		ResultSet rs = null;
		List<HostelOrderVO> list = new ArrayList<HostelOrderVO>();
		String likeSql = "SELECT hostelOrderNo,hostelNo,tenantNo,to_char(hostelOrderDate ,'yyyy-mm-dd') hostelOrderDate,hostelScore,hostelComment,tenantScore,customerQuantity,paymentWay,paymentState,orderRemark,orderState FROM HostelOrder where "
				+ columnName + " like '" + likeString + "'";

		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		try {

			con = ds.getConnection();
			pstmt = con.createStatement();

			rs = pstmt.executeQuery(likeSql);
			while (rs.next()) {
				hostelOrderVO = new HostelOrderVO();
				hostelOrderVO.setHostelOrderNo(rs.getInt("hostelOrderNo"));
				hostelOrderVO.setHostelNo(rs.getInt("hostelNo"));
				hostelOrderVO.setTenantNo(rs.getInt("tenantNo"));
				hostelOrderVO.setHostelOrderDate(rs.getDate("hostelOrderDate"));
				hostelOrderVO.setHostelScore(rs.getInt("hostelScore"));
				hostelOrderVO.setHostelComment(rs.getString("hostelComment"));
				hostelOrderVO.setTenantScore(rs.getInt("tenantScore"));
				hostelOrderVO
						.setCustomerQuantity(rs.getInt("customerQuantity"));
				hostelOrderVO.setPaymentWay(rs.getString("paymentWay"));
				hostelOrderVO.setPaymentState(rs.getString("paymentState"));
				hostelOrderVO.setOrderRemark(rs.getString("orderRemark"));
				hostelOrderVO.setOrderState(rs.getString("orderState"));
				list.add(hostelOrderVO);
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
	public HostelOrderVO findByHostelNo(Integer hostelNo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		HostelOrderVO hostelOrderVO = null;
		ResultSet rs = null;
		try {

			con = ds.getConnection();
			String findByThisKey = "SELECT hostelOrderNo,hostelNo,tenantNo,to_char(hostelOrderDate ,'yyyy-mm-dd') hostelOrderDate,hostelScore,hostelComment,tenantScore,customerQuantity,paymentWay,paymentState,orderRemark,orderState FROM HostelOrder where hostelNo= ?";

			pstmt = con.prepareStatement(findByThisKey);
			pstmt.setInt(1, hostelNo);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				hostelOrderVO = new HostelOrderVO();
				hostelOrderVO.setHostelOrderNo(rs.getInt("hostelOrderNo"));
				hostelOrderVO.setHostelNo(rs.getInt("hostelNo"));
				hostelOrderVO.setTenantNo(rs.getInt("tenantNo"));
				hostelOrderVO.setHostelOrderDate(rs.getDate("hostelOrderDate"));
				hostelOrderVO.setHostelScore(rs.getInt("hostelScore"));
				hostelOrderVO.setHostelComment(rs.getString("hostelComment"));
				hostelOrderVO.setTenantScore(rs.getInt("tenantScore"));
				hostelOrderVO
						.setCustomerQuantity(rs.getInt("customerQuantity"));
				hostelOrderVO.setPaymentWay(rs.getString("paymentWay"));
				hostelOrderVO.setPaymentState(rs.getString("paymentState"));
				hostelOrderVO.setOrderRemark(rs.getString("orderRemark"));
				hostelOrderVO.setOrderState(rs.getString("orderState"));
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
		return hostelOrderVO;
	}

	public List<HostelOrderVO> getAllByHostelNo(Integer hostelNo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		HostelOrderVO hostelOrderVO = null;
		List<HostelOrderVO> list = new ArrayList<HostelOrderVO>();
		ResultSet rs = null;
		try {

			con = ds.getConnection();
			String listByThisKey = "SELECT hostelOrderNo,hostelNo,tenantNo,to_char(hostelOrderDate ,'yyyy-mm-dd') hostelOrderDate,hostelScore,hostelComment,tenantScore,customerQuantity,paymentWay,paymentState,orderRemark,orderState FROM HostelOrder where hostelNo= ? order by hostelNo";

			pstmt = con.prepareStatement(listByThisKey);
			pstmt.setInt(1, hostelNo);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				hostelOrderVO = new HostelOrderVO();
				hostelOrderVO.setHostelOrderNo(rs.getInt("hostelOrderNo"));
				hostelOrderVO.setHostelNo(rs.getInt("hostelNo"));
				hostelOrderVO.setTenantNo(rs.getInt("tenantNo"));
				hostelOrderVO.setHostelOrderDate(rs.getDate("hostelOrderDate"));
				hostelOrderVO.setHostelScore(rs.getInt("hostelScore"));
				hostelOrderVO.setHostelComment(rs.getString("hostelComment"));
				hostelOrderVO.setTenantScore(rs.getInt("tenantScore"));
				hostelOrderVO
						.setCustomerQuantity(rs.getInt("customerQuantity"));
				hostelOrderVO.setPaymentWay(rs.getString("paymentWay"));
				hostelOrderVO.setPaymentState(rs.getString("paymentState"));
				hostelOrderVO.setOrderRemark(rs.getString("orderRemark"));
				hostelOrderVO.setOrderState(rs.getString("orderState"));
				list.add(hostelOrderVO);
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

	public HostelOrderVO findByTenantNo(Integer tenantNo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		HostelOrderVO hostelOrderVO = null;
		ResultSet rs = null;
		try {

			con = ds.getConnection();
			String findByThisKey = "SELECT hostelOrderNo,hostelNo,tenantNo,to_char(hostelOrderDate ,'yyyy-mm-dd') hostelOrderDate,hostelScore,hostelComment,tenantScore,customerQuantity,paymentWay,paymentState,orderRemark,orderState FROM HostelOrder where tenantNo= ?";

			pstmt = con.prepareStatement(findByThisKey);
			pstmt.setInt(1, tenantNo);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				hostelOrderVO = new HostelOrderVO();
				hostelOrderVO.setHostelOrderNo(rs.getInt("hostelOrderNo"));
				hostelOrderVO.setHostelNo(rs.getInt("hostelNo"));
				hostelOrderVO.setTenantNo(rs.getInt("tenantNo"));
				hostelOrderVO.setHostelOrderDate(rs.getDate("hostelOrderDate"));
				hostelOrderVO.setHostelScore(rs.getInt("hostelScore"));
				hostelOrderVO.setHostelComment(rs.getString("hostelComment"));
				hostelOrderVO.setTenantScore(rs.getInt("tenantScore"));
				hostelOrderVO
						.setCustomerQuantity(rs.getInt("customerQuantity"));
				hostelOrderVO.setPaymentWay(rs.getString("paymentWay"));
				hostelOrderVO.setPaymentState(rs.getString("paymentState"));
				hostelOrderVO.setOrderRemark(rs.getString("orderRemark"));
				hostelOrderVO.setOrderState(rs.getString("orderState"));
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
		return hostelOrderVO;
	}

	public List<HostelOrderVO> getAllByTenantNo(Integer tenantNo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		HostelOrderVO hostelOrderVO = null;
		List<HostelOrderVO> list = new ArrayList<HostelOrderVO>();
		ResultSet rs = null;
		try {

			con = ds.getConnection();
			String listByThisKey = "SELECT hostelOrderNo,hostelNo,tenantNo,to_char(hostelOrderDate ,'yyyy-mm-dd') hostelOrderDate,hostelScore,hostelComment,tenantScore,customerQuantity,paymentWay,paymentState,orderRemark,orderState FROM HostelOrder where tenantNo= ? order by hostelOrderNo desc";

			pstmt = con.prepareStatement(listByThisKey);
			pstmt.setInt(1, tenantNo);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				hostelOrderVO = new HostelOrderVO();
				hostelOrderVO.setHostelOrderNo(rs.getInt("hostelOrderNo"));
				hostelOrderVO.setHostelNo(rs.getInt("hostelNo"));
				hostelOrderVO.setTenantNo(rs.getInt("tenantNo"));
				hostelOrderVO.setHostelOrderDate(rs.getDate("hostelOrderDate"));
				hostelOrderVO.setHostelScore(rs.getInt("hostelScore"));
				hostelOrderVO.setHostelComment(rs.getString("hostelComment"));
				hostelOrderVO.setTenantScore(rs.getInt("tenantScore"));
				hostelOrderVO
						.setCustomerQuantity(rs.getInt("customerQuantity"));
				hostelOrderVO.setPaymentWay(rs.getString("paymentWay"));
				hostelOrderVO.setPaymentState(rs.getString("paymentState"));
				hostelOrderVO.setOrderRemark(rs.getString("orderRemark"));
				hostelOrderVO.setOrderState(rs.getString("orderState"));
				list.add(hostelOrderVO);
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

	public HostelOrderVO findByHostelOrderDate(java.sql.Date hostelOrderDate) {
		Connection con = null;
		PreparedStatement pstmt = null;

		HostelOrderVO hostelOrderVO = null;
		ResultSet rs = null;
		try {

			con = ds.getConnection();
			String findByThisKey = "SELECT hostelOrderNo,hostelNo,tenantNo,to_char(hostelOrderDate ,'yyyy-mm-dd') hostelOrderDate,hostelScore,hostelComment,tenantScore,customerQuantity,paymentWay,paymentState,orderRemark,orderState FROM HostelOrder where hostelOrderDate= ?";

			pstmt = con.prepareStatement(findByThisKey);
			pstmt.setDate(1, hostelOrderDate);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				hostelOrderVO = new HostelOrderVO();
				hostelOrderVO.setHostelOrderNo(rs.getInt("hostelOrderNo"));
				hostelOrderVO.setHostelNo(rs.getInt("hostelNo"));
				hostelOrderVO.setTenantNo(rs.getInt("tenantNo"));
				hostelOrderVO.setHostelOrderDate(rs.getDate("hostelOrderDate"));
				hostelOrderVO.setHostelScore(rs.getInt("hostelScore"));
				hostelOrderVO.setHostelComment(rs.getString("hostelComment"));
				hostelOrderVO.setTenantScore(rs.getInt("tenantScore"));
				hostelOrderVO
						.setCustomerQuantity(rs.getInt("customerQuantity"));
				hostelOrderVO.setPaymentWay(rs.getString("paymentWay"));
				hostelOrderVO.setPaymentState(rs.getString("paymentState"));
				hostelOrderVO.setOrderRemark(rs.getString("orderRemark"));
				hostelOrderVO.setOrderState(rs.getString("orderState"));
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
		return hostelOrderVO;
	}

	public List<HostelOrderVO> getAllByHostelOrderDate(
			java.sql.Date hostelOrderDate) {
		Connection con = null;
		PreparedStatement pstmt = null;

		HostelOrderVO hostelOrderVO = null;
		List<HostelOrderVO> list = new ArrayList<HostelOrderVO>();
		ResultSet rs = null;
		try {

			con = ds.getConnection();
			String listByThisKey = "SELECT hostelOrderNo,hostelNo,tenantNo,to_char(hostelOrderDate ,'yyyy-mm-dd') hostelOrderDate,hostelScore,hostelComment,tenantScore,customerQuantity,paymentWay,paymentState,orderRemark,orderState FROM HostelOrder where hostelOrderDate= ? order by hostelOrderDate";

			pstmt = con.prepareStatement(listByThisKey);
			pstmt.setDate(1, hostelOrderDate);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				hostelOrderVO = new HostelOrderVO();
				hostelOrderVO.setHostelOrderNo(rs.getInt("hostelOrderNo"));
				hostelOrderVO.setHostelNo(rs.getInt("hostelNo"));
				hostelOrderVO.setTenantNo(rs.getInt("tenantNo"));
				hostelOrderVO.setHostelOrderDate(rs.getDate("hostelOrderDate"));
				hostelOrderVO.setHostelScore(rs.getInt("hostelScore"));
				hostelOrderVO.setHostelComment(rs.getString("hostelComment"));
				hostelOrderVO.setTenantScore(rs.getInt("tenantScore"));
				hostelOrderVO
						.setCustomerQuantity(rs.getInt("customerQuantity"));
				hostelOrderVO.setPaymentWay(rs.getString("paymentWay"));
				hostelOrderVO.setPaymentState(rs.getString("paymentState"));
				hostelOrderVO.setOrderRemark(rs.getString("orderRemark"));
				hostelOrderVO.setOrderState(rs.getString("orderState"));
				list.add(hostelOrderVO);
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

	public HostelOrderVO findByHostelScore(Integer hostelScore) {
		Connection con = null;
		PreparedStatement pstmt = null;

		HostelOrderVO hostelOrderVO = null;
		ResultSet rs = null;
		try {

			con = ds.getConnection();
			String findByThisKey = "SELECT hostelOrderNo,hostelNo,tenantNo,to_char(hostelOrderDate ,'yyyy-mm-dd') hostelOrderDate,hostelScore,hostelComment,tenantScore,customerQuantity,paymentWay,paymentState,orderRemark,orderState FROM HostelOrder where hostelScore= ?";

			pstmt = con.prepareStatement(findByThisKey);
			pstmt.setInt(1, hostelScore);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				hostelOrderVO = new HostelOrderVO();
				hostelOrderVO.setHostelOrderNo(rs.getInt("hostelOrderNo"));
				hostelOrderVO.setHostelNo(rs.getInt("hostelNo"));
				hostelOrderVO.setTenantNo(rs.getInt("tenantNo"));
				hostelOrderVO.setHostelOrderDate(rs.getDate("hostelOrderDate"));
				hostelOrderVO.setHostelScore(rs.getInt("hostelScore"));
				hostelOrderVO.setHostelComment(rs.getString("hostelComment"));
				hostelOrderVO.setTenantScore(rs.getInt("tenantScore"));
				hostelOrderVO
						.setCustomerQuantity(rs.getInt("customerQuantity"));
				hostelOrderVO.setPaymentWay(rs.getString("paymentWay"));
				hostelOrderVO.setPaymentState(rs.getString("paymentState"));
				hostelOrderVO.setOrderRemark(rs.getString("orderRemark"));
				hostelOrderVO.setOrderState(rs.getString("orderState"));
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
		return hostelOrderVO;
	}

	public List<HostelOrderVO> getAllByHostelScore(Integer hostelScore) {
		Connection con = null;
		PreparedStatement pstmt = null;

		HostelOrderVO hostelOrderVO = null;
		List<HostelOrderVO> list = new ArrayList<HostelOrderVO>();
		ResultSet rs = null;
		try {

			con = ds.getConnection();
			String listByThisKey = "SELECT hostelOrderNo,hostelNo,tenantNo,to_char(hostelOrderDate ,'yyyy-mm-dd') hostelOrderDate,hostelScore,hostelComment,tenantScore,customerQuantity,paymentWay,paymentState,orderRemark,orderState FROM HostelOrder where hostelScore= ? order by hostelScore";

			pstmt = con.prepareStatement(listByThisKey);
			pstmt.setInt(1, hostelScore);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				hostelOrderVO = new HostelOrderVO();
				hostelOrderVO.setHostelOrderNo(rs.getInt("hostelOrderNo"));
				hostelOrderVO.setHostelNo(rs.getInt("hostelNo"));
				hostelOrderVO.setTenantNo(rs.getInt("tenantNo"));
				hostelOrderVO.setHostelOrderDate(rs.getDate("hostelOrderDate"));
				hostelOrderVO.setHostelScore(rs.getInt("hostelScore"));
				hostelOrderVO.setHostelComment(rs.getString("hostelComment"));
				hostelOrderVO.setTenantScore(rs.getInt("tenantScore"));
				hostelOrderVO
						.setCustomerQuantity(rs.getInt("customerQuantity"));
				hostelOrderVO.setPaymentWay(rs.getString("paymentWay"));
				hostelOrderVO.setPaymentState(rs.getString("paymentState"));
				hostelOrderVO.setOrderRemark(rs.getString("orderRemark"));
				hostelOrderVO.setOrderState(rs.getString("orderState"));
				list.add(hostelOrderVO);
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

	public HostelOrderVO findByHostelComment(String hostelComment) {
		Connection con = null;
		PreparedStatement pstmt = null;

		HostelOrderVO hostelOrderVO = null;
		ResultSet rs = null;
		try {

			con = ds.getConnection();
			String findByThisKey = "SELECT hostelOrderNo,hostelNo,tenantNo,to_char(hostelOrderDate ,'yyyy-mm-dd') hostelOrderDate,hostelScore,hostelComment,tenantScore,customerQuantity,paymentWay,paymentState,orderRemark,orderState FROM HostelOrder where hostelComment= ?";

			pstmt = con.prepareStatement(findByThisKey);
			pstmt.setString(1, hostelComment);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				hostelOrderVO = new HostelOrderVO();
				hostelOrderVO.setHostelOrderNo(rs.getInt("hostelOrderNo"));
				hostelOrderVO.setHostelNo(rs.getInt("hostelNo"));
				hostelOrderVO.setTenantNo(rs.getInt("tenantNo"));
				hostelOrderVO.setHostelOrderDate(rs.getDate("hostelOrderDate"));
				hostelOrderVO.setHostelScore(rs.getInt("hostelScore"));
				hostelOrderVO.setHostelComment(rs.getString("hostelComment"));
				hostelOrderVO.setTenantScore(rs.getInt("tenantScore"));
				hostelOrderVO
						.setCustomerQuantity(rs.getInt("customerQuantity"));
				hostelOrderVO.setPaymentWay(rs.getString("paymentWay"));
				hostelOrderVO.setPaymentState(rs.getString("paymentState"));
				hostelOrderVO.setOrderRemark(rs.getString("orderRemark"));
				hostelOrderVO.setOrderState(rs.getString("orderState"));
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
		return hostelOrderVO;
	}

	public List<HostelOrderVO> getAllByHostelComment(String hostelComment) {
		Connection con = null;
		PreparedStatement pstmt = null;

		HostelOrderVO hostelOrderVO = null;
		List<HostelOrderVO> list = new ArrayList<HostelOrderVO>();
		ResultSet rs = null;
		try {

			con = ds.getConnection();
			String listByThisKey = "SELECT hostelOrderNo,hostelNo,tenantNo,to_char(hostelOrderDate ,'yyyy-mm-dd') hostelOrderDate,hostelScore,hostelComment,tenantScore,customerQuantity,paymentWay,paymentState,orderRemark,orderState FROM HostelOrder where hostelComment= ? order by hostelComment";

			pstmt = con.prepareStatement(listByThisKey);
			pstmt.setString(1, hostelComment);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				hostelOrderVO = new HostelOrderVO();
				hostelOrderVO.setHostelOrderNo(rs.getInt("hostelOrderNo"));
				hostelOrderVO.setHostelNo(rs.getInt("hostelNo"));
				hostelOrderVO.setTenantNo(rs.getInt("tenantNo"));
				hostelOrderVO.setHostelOrderDate(rs.getDate("hostelOrderDate"));
				hostelOrderVO.setHostelScore(rs.getInt("hostelScore"));
				hostelOrderVO.setHostelComment(rs.getString("hostelComment"));
				hostelOrderVO.setTenantScore(rs.getInt("tenantScore"));
				hostelOrderVO
						.setCustomerQuantity(rs.getInt("customerQuantity"));
				hostelOrderVO.setPaymentWay(rs.getString("paymentWay"));
				hostelOrderVO.setPaymentState(rs.getString("paymentState"));
				hostelOrderVO.setOrderRemark(rs.getString("orderRemark"));
				hostelOrderVO.setOrderState(rs.getString("orderState"));
				list.add(hostelOrderVO);
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

	public HostelOrderVO findByTenantScore(Integer tenantScore) {
		Connection con = null;
		PreparedStatement pstmt = null;

		HostelOrderVO hostelOrderVO = null;
		ResultSet rs = null;
		try {

			con = ds.getConnection();
			String findByThisKey = "SELECT hostelOrderNo,hostelNo,tenantNo,to_char(hostelOrderDate ,'yyyy-mm-dd') hostelOrderDate,hostelScore,hostelComment,tenantScore,customerQuantity,paymentWay,paymentState,orderRemark,orderState FROM HostelOrder where tenantScore= ?";

			pstmt = con.prepareStatement(findByThisKey);
			pstmt.setInt(1, tenantScore);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				hostelOrderVO = new HostelOrderVO();
				hostelOrderVO.setHostelOrderNo(rs.getInt("hostelOrderNo"));
				hostelOrderVO.setHostelNo(rs.getInt("hostelNo"));
				hostelOrderVO.setTenantNo(rs.getInt("tenantNo"));
				hostelOrderVO.setHostelOrderDate(rs.getDate("hostelOrderDate"));
				hostelOrderVO.setHostelScore(rs.getInt("hostelScore"));
				hostelOrderVO.setHostelComment(rs.getString("hostelComment"));
				hostelOrderVO.setTenantScore(rs.getInt("tenantScore"));
				hostelOrderVO
						.setCustomerQuantity(rs.getInt("customerQuantity"));
				hostelOrderVO.setPaymentWay(rs.getString("paymentWay"));
				hostelOrderVO.setPaymentState(rs.getString("paymentState"));
				hostelOrderVO.setOrderRemark(rs.getString("orderRemark"));
				hostelOrderVO.setOrderState(rs.getString("orderState"));
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
		return hostelOrderVO;
	}

	public List<HostelOrderVO> getAllByTenantScore(Integer tenantScore) {
		Connection con = null;
		PreparedStatement pstmt = null;

		HostelOrderVO hostelOrderVO = null;
		List<HostelOrderVO> list = new ArrayList<HostelOrderVO>();
		ResultSet rs = null;
		try {

			con = ds.getConnection();
			String listByThisKey = "SELECT hostelOrderNo,hostelNo,tenantNo,to_char(hostelOrderDate ,'yyyy-mm-dd') hostelOrderDate,hostelScore,hostelComment,tenantScore,customerQuantity,paymentWay,paymentState,orderRemark,orderState FROM HostelOrder where tenantScore= ? order by tenantScore";

			pstmt = con.prepareStatement(listByThisKey);
			pstmt.setInt(1, tenantScore);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				hostelOrderVO = new HostelOrderVO();
				hostelOrderVO.setHostelOrderNo(rs.getInt("hostelOrderNo"));
				hostelOrderVO.setHostelNo(rs.getInt("hostelNo"));
				hostelOrderVO.setTenantNo(rs.getInt("tenantNo"));
				hostelOrderVO.setHostelOrderDate(rs.getDate("hostelOrderDate"));
				hostelOrderVO.setHostelScore(rs.getInt("hostelScore"));
				hostelOrderVO.setHostelComment(rs.getString("hostelComment"));
				hostelOrderVO.setTenantScore(rs.getInt("tenantScore"));
				hostelOrderVO
						.setCustomerQuantity(rs.getInt("customerQuantity"));
				hostelOrderVO.setPaymentWay(rs.getString("paymentWay"));
				hostelOrderVO.setPaymentState(rs.getString("paymentState"));
				hostelOrderVO.setOrderRemark(rs.getString("orderRemark"));
				hostelOrderVO.setOrderState(rs.getString("orderState"));
				list.add(hostelOrderVO);
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

	public HostelOrderVO findByCustomerQuantity(Integer customerQuantity) {
		Connection con = null;
		PreparedStatement pstmt = null;

		HostelOrderVO hostelOrderVO = null;
		ResultSet rs = null;
		try {

			con = ds.getConnection();
			String findByThisKey = "SELECT hostelOrderNo,hostelNo,tenantNo,to_char(hostelOrderDate ,'yyyy-mm-dd') hostelOrderDate,hostelScore,hostelComment,tenantScore,customerQuantity,paymentWay,paymentState,orderRemark,orderState FROM HostelOrder where customerQuantity= ?";

			pstmt = con.prepareStatement(findByThisKey);
			pstmt.setInt(1, customerQuantity);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				hostelOrderVO = new HostelOrderVO();
				hostelOrderVO.setHostelOrderNo(rs.getInt("hostelOrderNo"));
				hostelOrderVO.setHostelNo(rs.getInt("hostelNo"));
				hostelOrderVO.setTenantNo(rs.getInt("tenantNo"));
				hostelOrderVO.setHostelOrderDate(rs.getDate("hostelOrderDate"));
				hostelOrderVO.setHostelScore(rs.getInt("hostelScore"));
				hostelOrderVO.setHostelComment(rs.getString("hostelComment"));
				hostelOrderVO.setTenantScore(rs.getInt("tenantScore"));
				hostelOrderVO
						.setCustomerQuantity(rs.getInt("customerQuantity"));
				hostelOrderVO.setPaymentWay(rs.getString("paymentWay"));
				hostelOrderVO.setPaymentState(rs.getString("paymentState"));
				hostelOrderVO.setOrderRemark(rs.getString("orderRemark"));
				hostelOrderVO.setOrderState(rs.getString("orderState"));
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
		return hostelOrderVO;
	}

	public List<HostelOrderVO> getAllByCustomerQuantity(Integer customerQuantity) {
		Connection con = null;
		PreparedStatement pstmt = null;

		HostelOrderVO hostelOrderVO = null;
		List<HostelOrderVO> list = new ArrayList<HostelOrderVO>();
		ResultSet rs = null;
		try {

			con = ds.getConnection();
			String listByThisKey = "SELECT hostelOrderNo,hostelNo,tenantNo,to_char(hostelOrderDate ,'yyyy-mm-dd') hostelOrderDate,hostelScore,hostelComment,tenantScore,customerQuantity,paymentWay,paymentState,orderRemark,orderState FROM HostelOrder where customerQuantity= ? order by customerQuantity";

			pstmt = con.prepareStatement(listByThisKey);
			pstmt.setInt(1, customerQuantity);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				hostelOrderVO = new HostelOrderVO();
				hostelOrderVO.setHostelOrderNo(rs.getInt("hostelOrderNo"));
				hostelOrderVO.setHostelNo(rs.getInt("hostelNo"));
				hostelOrderVO.setTenantNo(rs.getInt("tenantNo"));
				hostelOrderVO.setHostelOrderDate(rs.getDate("hostelOrderDate"));
				hostelOrderVO.setHostelScore(rs.getInt("hostelScore"));
				hostelOrderVO.setHostelComment(rs.getString("hostelComment"));
				hostelOrderVO.setTenantScore(rs.getInt("tenantScore"));
				hostelOrderVO
						.setCustomerQuantity(rs.getInt("customerQuantity"));
				hostelOrderVO.setPaymentWay(rs.getString("paymentWay"));
				hostelOrderVO.setPaymentState(rs.getString("paymentState"));
				hostelOrderVO.setOrderRemark(rs.getString("orderRemark"));
				hostelOrderVO.setOrderState(rs.getString("orderState"));
				list.add(hostelOrderVO);
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

	public HostelOrderVO findByPaymentWay(String paymentWay) {
		Connection con = null;
		PreparedStatement pstmt = null;

		HostelOrderVO hostelOrderVO = null;
		ResultSet rs = null;
		try {

			con = ds.getConnection();
			String findByThisKey = "SELECT hostelOrderNo,hostelNo,tenantNo,to_char(hostelOrderDate ,'yyyy-mm-dd') hostelOrderDate,hostelScore,hostelComment,tenantScore,customerQuantity,paymentWay,paymentState,orderRemark,orderState FROM HostelOrder where paymentWay= ?";

			pstmt = con.prepareStatement(findByThisKey);
			pstmt.setString(1, paymentWay);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				hostelOrderVO = new HostelOrderVO();
				hostelOrderVO.setHostelOrderNo(rs.getInt("hostelOrderNo"));
				hostelOrderVO.setHostelNo(rs.getInt("hostelNo"));
				hostelOrderVO.setTenantNo(rs.getInt("tenantNo"));
				hostelOrderVO.setHostelOrderDate(rs.getDate("hostelOrderDate"));
				hostelOrderVO.setHostelScore(rs.getInt("hostelScore"));
				hostelOrderVO.setHostelComment(rs.getString("hostelComment"));
				hostelOrderVO.setTenantScore(rs.getInt("tenantScore"));
				hostelOrderVO
						.setCustomerQuantity(rs.getInt("customerQuantity"));
				hostelOrderVO.setPaymentWay(rs.getString("paymentWay"));
				hostelOrderVO.setPaymentState(rs.getString("paymentState"));
				hostelOrderVO.setOrderRemark(rs.getString("orderRemark"));
				hostelOrderVO.setOrderState(rs.getString("orderState"));
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
		return hostelOrderVO;
	}

	public List<HostelOrderVO> getAllByPaymentWay(String paymentWay) {
		Connection con = null;
		PreparedStatement pstmt = null;

		HostelOrderVO hostelOrderVO = null;
		List<HostelOrderVO> list = new ArrayList<HostelOrderVO>();
		ResultSet rs = null;
		try {

			con = ds.getConnection();
			String listByThisKey = "SELECT hostelOrderNo,hostelNo,tenantNo,to_char(hostelOrderDate ,'yyyy-mm-dd') hostelOrderDate,hostelScore,hostelComment,tenantScore,customerQuantity,paymentWay,paymentState,orderRemark,orderState FROM HostelOrder where paymentWay= ? order by paymentWay";

			pstmt = con.prepareStatement(listByThisKey);
			pstmt.setString(1, paymentWay);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				hostelOrderVO = new HostelOrderVO();
				hostelOrderVO.setHostelOrderNo(rs.getInt("hostelOrderNo"));
				hostelOrderVO.setHostelNo(rs.getInt("hostelNo"));
				hostelOrderVO.setTenantNo(rs.getInt("tenantNo"));
				hostelOrderVO.setHostelOrderDate(rs.getDate("hostelOrderDate"));
				hostelOrderVO.setHostelScore(rs.getInt("hostelScore"));
				hostelOrderVO.setHostelComment(rs.getString("hostelComment"));
				hostelOrderVO.setTenantScore(rs.getInt("tenantScore"));
				hostelOrderVO
						.setCustomerQuantity(rs.getInt("customerQuantity"));
				hostelOrderVO.setPaymentWay(rs.getString("paymentWay"));
				hostelOrderVO.setPaymentState(rs.getString("paymentState"));
				hostelOrderVO.setOrderRemark(rs.getString("orderRemark"));
				hostelOrderVO.setOrderState(rs.getString("orderState"));
				list.add(hostelOrderVO);
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

	public HostelOrderVO findByPaymentState(String paymentState) {
		Connection con = null;
		PreparedStatement pstmt = null;

		HostelOrderVO hostelOrderVO = null;
		ResultSet rs = null;
		try {

			con = ds.getConnection();
			String findByThisKey = "SELECT hostelOrderNo,hostelNo,tenantNo,to_char(hostelOrderDate ,'yyyy-mm-dd') hostelOrderDate,hostelScore,hostelComment,tenantScore,customerQuantity,paymentWay,paymentState,orderRemark,orderState FROM HostelOrder where paymentState= ?";

			pstmt = con.prepareStatement(findByThisKey);
			pstmt.setString(1, paymentState);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				hostelOrderVO = new HostelOrderVO();
				hostelOrderVO.setHostelOrderNo(rs.getInt("hostelOrderNo"));
				hostelOrderVO.setHostelNo(rs.getInt("hostelNo"));
				hostelOrderVO.setTenantNo(rs.getInt("tenantNo"));
				hostelOrderVO.setHostelOrderDate(rs.getDate("hostelOrderDate"));
				hostelOrderVO.setHostelScore(rs.getInt("hostelScore"));
				hostelOrderVO.setHostelComment(rs.getString("hostelComment"));
				hostelOrderVO.setTenantScore(rs.getInt("tenantScore"));
				hostelOrderVO
						.setCustomerQuantity(rs.getInt("customerQuantity"));
				hostelOrderVO.setPaymentWay(rs.getString("paymentWay"));
				hostelOrderVO.setPaymentState(rs.getString("paymentState"));
				hostelOrderVO.setOrderRemark(rs.getString("orderRemark"));
				hostelOrderVO.setOrderState(rs.getString("orderState"));
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
		return hostelOrderVO;
	}

	public List<HostelOrderVO> getAllByPaymentState(String paymentState) {
		Connection con = null;
		PreparedStatement pstmt = null;

		HostelOrderVO hostelOrderVO = null;
		List<HostelOrderVO> list = new ArrayList<HostelOrderVO>();
		ResultSet rs = null;
		try {

			con = ds.getConnection();
			String listByThisKey = "SELECT hostelOrderNo,hostelNo,tenantNo,to_char(hostelOrderDate ,'yyyy-mm-dd') hostelOrderDate,hostelScore,hostelComment,tenantScore,customerQuantity,paymentWay,paymentState,orderRemark,orderState FROM HostelOrder where paymentState= ? order by paymentState";

			pstmt = con.prepareStatement(listByThisKey);
			pstmt.setString(1, paymentState);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				hostelOrderVO = new HostelOrderVO();
				hostelOrderVO.setHostelOrderNo(rs.getInt("hostelOrderNo"));
				hostelOrderVO.setHostelNo(rs.getInt("hostelNo"));
				hostelOrderVO.setTenantNo(rs.getInt("tenantNo"));
				hostelOrderVO.setHostelOrderDate(rs.getDate("hostelOrderDate"));
				hostelOrderVO.setHostelScore(rs.getInt("hostelScore"));
				hostelOrderVO.setHostelComment(rs.getString("hostelComment"));
				hostelOrderVO.setTenantScore(rs.getInt("tenantScore"));
				hostelOrderVO
						.setCustomerQuantity(rs.getInt("customerQuantity"));
				hostelOrderVO.setPaymentWay(rs.getString("paymentWay"));
				hostelOrderVO.setPaymentState(rs.getString("paymentState"));
				hostelOrderVO.setOrderRemark(rs.getString("orderRemark"));
				hostelOrderVO.setOrderState(rs.getString("orderState"));
				list.add(hostelOrderVO);
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

	public HostelOrderVO findByOrderRemark(String orderRemark) {
		Connection con = null;
		PreparedStatement pstmt = null;

		HostelOrderVO hostelOrderVO = null;
		ResultSet rs = null;
		try {

			con = ds.getConnection();
			String findByThisKey = "SELECT hostelOrderNo,hostelNo,tenantNo,to_char(hostelOrderDate ,'yyyy-mm-dd') hostelOrderDate,hostelScore,hostelComment,tenantScore,customerQuantity,paymentWay,paymentState,orderRemark,orderState FROM HostelOrder where orderRemark= ?";

			pstmt = con.prepareStatement(findByThisKey);
			pstmt.setString(1, orderRemark);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				hostelOrderVO = new HostelOrderVO();
				hostelOrderVO.setHostelOrderNo(rs.getInt("hostelOrderNo"));
				hostelOrderVO.setHostelNo(rs.getInt("hostelNo"));
				hostelOrderVO.setTenantNo(rs.getInt("tenantNo"));
				hostelOrderVO.setHostelOrderDate(rs.getDate("hostelOrderDate"));
				hostelOrderVO.setHostelScore(rs.getInt("hostelScore"));
				hostelOrderVO.setHostelComment(rs.getString("hostelComment"));
				hostelOrderVO.setTenantScore(rs.getInt("tenantScore"));
				hostelOrderVO
						.setCustomerQuantity(rs.getInt("customerQuantity"));
				hostelOrderVO.setPaymentWay(rs.getString("paymentWay"));
				hostelOrderVO.setPaymentState(rs.getString("paymentState"));
				hostelOrderVO.setOrderRemark(rs.getString("orderRemark"));
				hostelOrderVO.setOrderState(rs.getString("orderState"));
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
		return hostelOrderVO;
	}

	public List<HostelOrderVO> getAllByOrderRemark(String orderRemark) {
		Connection con = null;
		PreparedStatement pstmt = null;

		HostelOrderVO hostelOrderVO = null;
		List<HostelOrderVO> list = new ArrayList<HostelOrderVO>();
		ResultSet rs = null;
		try {

			con = ds.getConnection();
			String listByThisKey = "SELECT hostelOrderNo,hostelNo,tenantNo,to_char(hostelOrderDate ,'yyyy-mm-dd') hostelOrderDate,hostelScore,hostelComment,tenantScore,customerQuantity,paymentWay,paymentState,orderRemark,orderState FROM HostelOrder where orderRemark= ? order by orderRemark";

			pstmt = con.prepareStatement(listByThisKey);
			pstmt.setString(1, orderRemark);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				hostelOrderVO = new HostelOrderVO();
				hostelOrderVO.setHostelOrderNo(rs.getInt("hostelOrderNo"));
				hostelOrderVO.setHostelNo(rs.getInt("hostelNo"));
				hostelOrderVO.setTenantNo(rs.getInt("tenantNo"));
				hostelOrderVO.setHostelOrderDate(rs.getDate("hostelOrderDate"));
				hostelOrderVO.setHostelScore(rs.getInt("hostelScore"));
				hostelOrderVO.setHostelComment(rs.getString("hostelComment"));
				hostelOrderVO.setTenantScore(rs.getInt("tenantScore"));
				hostelOrderVO
						.setCustomerQuantity(rs.getInt("customerQuantity"));
				hostelOrderVO.setPaymentWay(rs.getString("paymentWay"));
				hostelOrderVO.setPaymentState(rs.getString("paymentState"));
				hostelOrderVO.setOrderRemark(rs.getString("orderRemark"));
				hostelOrderVO.setOrderState(rs.getString("orderState"));
				list.add(hostelOrderVO);
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

	public HostelOrderVO findByOrderState(String orderState) {
		Connection con = null;
		PreparedStatement pstmt = null;

		HostelOrderVO hostelOrderVO = null;
		ResultSet rs = null;
		try {

			con = ds.getConnection();
			String findByThisKey = "SELECT hostelOrderNo,hostelNo,tenantNo,to_char(hostelOrderDate ,'yyyy-mm-dd') hostelOrderDate,hostelScore,hostelComment,tenantScore,customerQuantity,paymentWay,paymentState,orderRemark,orderState FROM HostelOrder where orderState= ?";

			pstmt = con.prepareStatement(findByThisKey);
			pstmt.setString(1, orderState);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				hostelOrderVO = new HostelOrderVO();
				hostelOrderVO.setHostelOrderNo(rs.getInt("hostelOrderNo"));
				hostelOrderVO.setHostelNo(rs.getInt("hostelNo"));
				hostelOrderVO.setTenantNo(rs.getInt("tenantNo"));
				hostelOrderVO.setHostelOrderDate(rs.getDate("hostelOrderDate"));
				hostelOrderVO.setHostelScore(rs.getInt("hostelScore"));
				hostelOrderVO.setHostelComment(rs.getString("hostelComment"));
				hostelOrderVO.setTenantScore(rs.getInt("tenantScore"));
				hostelOrderVO
						.setCustomerQuantity(rs.getInt("customerQuantity"));
				hostelOrderVO.setPaymentWay(rs.getString("paymentWay"));
				hostelOrderVO.setPaymentState(rs.getString("paymentState"));
				hostelOrderVO.setOrderRemark(rs.getString("orderRemark"));
				hostelOrderVO.setOrderState(rs.getString("orderState"));
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
		return hostelOrderVO;
	}

	public List<HostelOrderVO> getAllByOrderState(String orderState) {
		Connection con = null;
		PreparedStatement pstmt = null;

		HostelOrderVO hostelOrderVO = null;
		List<HostelOrderVO> list = new ArrayList<HostelOrderVO>();
		ResultSet rs = null;
		try {

			con = ds.getConnection();
			String listByThisKey = "SELECT hostelOrderNo,hostelNo,tenantNo,to_char(hostelOrderDate ,'yyyy-mm-dd') hostelOrderDate,hostelScore,hostelComment,tenantScore,customerQuantity,paymentWay,paymentState,orderRemark,orderState FROM HostelOrder where orderState= ? order by orderState";

			pstmt = con.prepareStatement(listByThisKey);
			pstmt.setString(1, orderState);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				hostelOrderVO = new HostelOrderVO();
				hostelOrderVO.setHostelOrderNo(rs.getInt("hostelOrderNo"));
				hostelOrderVO.setHostelNo(rs.getInt("hostelNo"));
				hostelOrderVO.setTenantNo(rs.getInt("tenantNo"));
				hostelOrderVO.setHostelOrderDate(rs.getDate("hostelOrderDate"));
				hostelOrderVO.setHostelScore(rs.getInt("hostelScore"));
				hostelOrderVO.setHostelComment(rs.getString("hostelComment"));
				hostelOrderVO.setTenantScore(rs.getInt("tenantScore"));
				hostelOrderVO
						.setCustomerQuantity(rs.getInt("customerQuantity"));
				hostelOrderVO.setPaymentWay(rs.getString("paymentWay"));
				hostelOrderVO.setPaymentState(rs.getString("paymentState"));
				hostelOrderVO.setOrderRemark(rs.getString("orderRemark"));
				hostelOrderVO.setOrderState(rs.getString("orderState"));
				list.add(hostelOrderVO);
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

	// /backend///
	public List<HostelOrderVO> getAll(Map<String, String[]> map) {

		Connection con = null;
		PreparedStatement pstmt = null;

		HostelOrderVO hostelOrderVO = null;
		ResultSet rs = null;
		List<HostelOrderVO> list = new ArrayList<HostelOrderVO>();
		try {

			con = ds.getConnection();
			String finalSQL = "select * from hostelOrder"
					+ CQforHostelOrder.get_WhereCondition(map)
					+ "order by hostelOrderNo";

			pstmt = con.prepareStatement(finalSQL);
			System.out.println("final sql(DAO) = " + finalSQL);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				hostelOrderVO = new HostelOrderVO();
				hostelOrderVO.setHostelOrderNo(rs.getInt("hostelOrderNo"));
				hostelOrderVO.setHostelNo(rs.getInt("hostelNo"));
				hostelOrderVO.setTenantNo(rs.getInt("tenantNo"));
				hostelOrderVO.setHostelOrderDate(rs.getDate("hostelOrderDate"));
				hostelOrderVO.setHostelScore(rs.getInt("hostelScore"));
				hostelOrderVO.setHostelComment(rs.getString("hostelComment"));
				hostelOrderVO.setTenantScore(rs.getInt("tenantScore"));
				hostelOrderVO
						.setCustomerQuantity(rs.getInt("customerQuantity"));
				hostelOrderVO.setPaymentWay(rs.getString("paymentWay"));
				hostelOrderVO.setPaymentState(rs.getString("paymentState"));
				hostelOrderVO.setOrderState(rs.getString("orderState"));
				list.add(hostelOrderVO);
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
	
	@Override
	public void Comment (Integer hostelOrderNo, String hostelComment, Integer hostelScore) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			String updateComment = "UPDATE hostelOrder set hostelComment=?, hostelScore=? where hostelOrderNo=?"; 
			pstmt = con.prepareStatement(updateComment);

			pstmt.setString(1, hostelComment);
			pstmt.setInt(2, hostelScore);
			pstmt.setInt(3, hostelOrderNo);
			
			pstmt.executeUpdate();
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
	}

}