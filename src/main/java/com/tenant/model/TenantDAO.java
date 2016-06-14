/*���{���O�ϥ�DAO���;��۰ʲ��� copyRight:wildli0422@gmail.com �ڷR�@����*/
package com.tenant.model;

import java.util.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import jdbc.util.CompositeQuery.CompositeQuery_Tenant;

public class TenantDAO implements TenantDAO_interface {
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/ZA105G3");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	// add some static stmt
	private static final String INSERT_STMT = "INSERT INTO Tenant (tenantNo,tenantMail,tenantPassword,tenantName,tenantSex,tenantAddress,tenantPhone,tenantPic,registerDate) VALUES (tenantNo_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, SYSDATE)";

	private static final String GET_ALL_STMT = "SELECT tenantNo,tenantMail,tenantPassword,tenantName,tenantSex,tenantAddress,tenantPhone,tenantPic,to_char(registerDate ,'yyyy-mm-dd') registerDate FROM Tenant order by tenantNo";

	private static final String GET_ONE_STMT = "SELECT tenantNo,tenantMail,tenantPassword,tenantName,tenantSex,tenantAddress,tenantPhone,tenantPic,to_char(registerDate ,'yyyy-mm-dd') registerDate FROM Tenant where tenantNo=?";

	private static final String DELETE = "DELETE FROM Tenant where tenantNo=?";

	private static final String UPDATE = "UPDATE Tenant set tenantMail=?, tenantPassword=?, tenantName=?, tenantSex=?, tenantAddress=?, tenantPhone=?, tenantPic=?, registerDate=? where tenantNo=?";

	private static final String GET_NO_BY_MAIL = "select tenantNo from tenant where tenantMail=?";

	private static final String CHECK_STMT = "SELECT * FROM tenant WHERE tenantMail = ?";

	private static final String Score_STMT = "SELECT avg(tenantScore) from hostelOrder where tenantNo = ?";


	// start to add some regular method
	@Override
	public void insert(TenantVO tenantVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, tenantVO.getTenantMail());
			pstmt.setString(2, tenantVO.getTenantPassword());
			pstmt.setString(3, tenantVO.getTenantName());
			pstmt.setString(4, tenantVO.getTenantSex());
			pstmt.setString(5, tenantVO.getTenantAddress());
			pstmt.setString(6, tenantVO.getTenantPhone());
			pstmt.setBytes(7, tenantVO.getTenantPic());
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
	public void update(TenantVO tenantVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, tenantVO.getTenantMail());
			pstmt.setString(2, tenantVO.getTenantPassword());
			pstmt.setString(3, tenantVO.getTenantName());
			pstmt.setString(4, tenantVO.getTenantSex());
			pstmt.setString(5, tenantVO.getTenantAddress());
			pstmt.setString(6, tenantVO.getTenantPhone());
			pstmt.setBytes(7, tenantVO.getTenantPic());
			pstmt.setDate(8, tenantVO.getRegisterDate());
			pstmt.setInt(9, tenantVO.getTenantNo());
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
	public void delete(Integer tenantNo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, tenantNo);
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
	public TenantVO findByPrimaryKey(Integer tenantNo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		TenantVO tenantVO = null;
		ResultSet rs = null;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, tenantNo);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				tenantVO = new TenantVO();
				tenantVO.setTenantNo(rs.getInt("tenantNo"));
				tenantVO.setTenantMail(rs.getString("tenantMail"));
				tenantVO.setTenantPassword(rs.getString("tenantPassword"));
				tenantVO.setTenantName(rs.getString("tenantName"));
				tenantVO.setTenantSex(rs.getString("tenantSex"));
				tenantVO.setTenantAddress(rs.getString("tenantAddress"));
				tenantVO.setTenantPhone(rs.getString("tenantPhone"));
				tenantVO.setTenantPic(rs.getBytes("tenantPic"));
				tenantVO.setRegisterDate(rs.getDate("registerDate"));
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
		return tenantVO;
	}

	@Override
	public List<TenantVO> getAll() {
		Connection con = null;
		PreparedStatement pstmt = null;

		TenantVO tenantVO = null;
		ResultSet rs = null;
		List<TenantVO> list = new ArrayList<TenantVO>();
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				tenantVO = new TenantVO();
				tenantVO.setTenantNo(rs.getInt("tenantNo"));
				tenantVO.setTenantMail(rs.getString("tenantMail"));
				tenantVO.setTenantPassword(rs.getString("tenantPassword"));
				tenantVO.setTenantName(rs.getString("tenantName"));
				tenantVO.setTenantSex(rs.getString("tenantSex"));
				tenantVO.setTenantAddress(rs.getString("tenantAddress"));
				tenantVO.setTenantPhone(rs.getString("tenantPhone"));
				tenantVO.setTenantPic(rs.getBytes("tenantPic"));
				tenantVO.setRegisterDate(rs.getDate("registerDate"));
				list.add(tenantVO);
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
	public List<TenantVO> likeByColumn(String columnName, String likeString) {
		Connection con = null;
		Statement pstmt = null;

		TenantVO tenantVO = null;
		ResultSet rs = null;
		List<TenantVO> list = new ArrayList<TenantVO>();
		String likeSql = "SELECT tenantNo,tenantMail,tenantPassword,tenantName,tenantSex,tenantAddress,tenantPhone,tenantPic,to_char(registerDate ,'yyyy-mm-dd') registerDate FROM Tenant where "
				+ columnName + " like '" + likeString + "'";

		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		try {

			con = ds.getConnection();
			pstmt = con.createStatement();

			rs = pstmt.executeQuery(likeSql);
			while (rs.next()) {
				tenantVO = new TenantVO();
				tenantVO.setTenantNo(rs.getInt("tenantNo"));
				tenantVO.setTenantMail(rs.getString("tenantMail"));
				tenantVO.setTenantPassword(rs.getString("tenantPassword"));
				tenantVO.setTenantName(rs.getString("tenantName"));
				tenantVO.setTenantSex(rs.getString("tenantSex"));
				tenantVO.setTenantAddress(rs.getString("tenantAddress"));
				tenantVO.setTenantPhone(rs.getString("tenantPhone"));
				tenantVO.setTenantPic(rs.getBytes("tenantPic"));
				tenantVO.setRegisterDate(rs.getDate("registerDate"));
				list.add(tenantVO);
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

	public Integer findTenantNo(String tenantMail) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		Integer tenantNo = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_NO_BY_MAIL);
			pstmt.setString(1, tenantMail);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				tenantNo = rs.getInt("tenantNo");

			}// while

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
		return tenantNo;
	}

	public String findTenantPassword(String TenantMail) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String pw = null;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(CHECK_STMT);
			pstmt.setString(1, TenantMail);

			ResultSet res = pstmt.executeQuery();

			while (res.next()) {

				pw = res.getString("tenantPassword");

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
		return pw;

	}

	public List<TenantVO> getAll(Map<String, String[]> map) {
		List<TenantVO> list = new ArrayList<TenantVO>();
		TenantVO tenantVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet res = null;

		try {

			con = ds.getConnection();
			String SQL = "select * from tenant"
					+ CompositeQuery_Tenant.getWhere(map)
					+ " order by tenantNo";
			pstmt = con.prepareStatement(SQL);
			System.out.println("tenantSQL = " + SQL);
			res = pstmt.executeQuery();

			while (res.next()) {
				tenantVO = new TenantVO();
				tenantVO.setTenantNo(res.getInt("tenantNo"));
				tenantVO.setTenantMail(res.getString("tenantMail"));
				tenantVO.setTenantName(res.getString("tenantName"));
				tenantVO.setTenantSex(res.getString("tenantSex"));
				tenantVO.setTenantAddress(res.getString("tenantAddress"));
				tenantVO.setTenantPhone(res.getString("tenantPhone"));

				list.add(tenantVO);
			}

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		} finally {
			if (res != null) {
				try {
					res.close();
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

	// new
	// //////////Something Cool///////////
	public List<TenantVO> addThisMonth() {
		List<TenantVO> list = new ArrayList<TenantVO>();
		TenantVO tenantVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			final String month = "select tenantNo, tenantName, tenantMail from tenant where registerDate >= trunc(sysdate, 'month')";
			pstmt = con.prepareStatement(month);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				tenantVO = new TenantVO();
				tenantVO.setTenantNo(rs.getInt("tenantNo"));
				tenantVO.setTenantName(rs.getString("tenantName"));
				tenantVO.setTenantMail(rs.getString("tenantMail"));
				list.add(tenantVO);
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

	public Integer getScoreAvg(Integer tenantNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int score = 0;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(Score_STMT);
			pstmt.setInt(1, tenantNo);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				score = rs.getInt("AVG(TENANTSCORE)");
			}// while

		} catch (Exception se) {
			score = 0;
			return score;
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
		return score;
	}

	public Integer countScore(Integer tenantNo, Integer tenantScore) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0;

		try {
			String countScore = "SELECT count(*) c from hostelOrder where tenantNo = ? and tenantScore = ?";
			con = ds.getConnection();
			pstmt = con.prepareStatement(countScore);
			pstmt.setInt(1, tenantNo);
			pstmt.setInt(2, tenantScore);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				count = rs.getInt("c");
			}// while

		} catch (Exception se) {
			count = 0;
			return count;
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
		return count;
	}

	public Integer travelThisMonth(Integer tenantNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String tTM = "select count(*) times"
				+ " from tenant t join hostelorder ho on t.tenantNo = ho.tenantNo"
				+ " where t.tenantNo= ? and ho.hostelOrderDate >= trunc(sysdate, 'month')";
		int day = 0;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(tTM);
			pstmt.setInt(1, tenantNo);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				day = rs.getInt("times");
			}// while

		} catch (Exception se) {
			day = 0;
			return day;
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
		return day;
	}

	public Integer toDate(Integer tenantNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String toDate = "select sysdate-registerDate day from tenant  where tenantNo= ?";
		int day = 0;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(toDate);
			pstmt.setInt(1, tenantNo);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				day = rs.getInt("day");
			}// while

		} catch (Exception se) {
			day = 0;
			return day;
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
		return day;
	}

	public void update_pic_bk(TenantVO tenantVO) {

		Connection con = null;
		PreparedStatement pstmt = null;
		System.out.println(tenantVO.getTenantPic());

		InputStream tenantPicStream = new ByteArrayInputStream(
				tenantVO.getTenantPic());
		String update_pic = "UPDATE tenant SET  tenantPic = ?  WHERE tenantNo = ?";
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(update_pic);
			pstmt.setBinaryStream(1, tenantPicStream);
//			System.out.println(tenantVO.getTenantNo());
			pstmt.setInt(2, tenantVO.getTenantNo());
			pstmt.executeUpdate();
			tenantPicStream.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException se) {
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
	public List<TenantVO> getListByName(String tenantName) {
		List<TenantVO> list = new ArrayList<TenantVO>();
		String GET_LIST = 
				"select * FROM tenant where tenantName Like "+" '%"+tenantName+"%'  order by tenantNo";	
		TenantVO tenantVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet res = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_LIST);
			res = pstmt.executeQuery();

			while (res.next()) {				
				tenantVO = new TenantVO();				
				tenantVO.setTenantNo(res.getInt("tenantNo"));
				tenantVO.setTenantMail(res.getString("tenantMail"));
				tenantVO.setTenantPassword(res.getString("tenantPassword"));
				tenantVO.setTenantName(res.getString("tenantName"));
				tenantVO.setTenantSex(res.getString("tenantSex"));
				tenantVO.setTenantAddress(res.getString("tenantAddress"));
				tenantVO.setTenantPhone(res.getString("tenantPhone"));
				list.add(tenantVO); // Store the row in the vector
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (res != null) {
				try {
					res.close();
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
	
	public List<TenantVO> getListByAddress(String tenantAddress) {
		List<TenantVO> list = new ArrayList<TenantVO>();
		String GET_LIST = 
				"select * FROM tenant where tenantAddress Like "+" '%"+tenantAddress+"%'  order by tenantNo";	
		TenantVO tenantVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet res = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_LIST);
			res = pstmt.executeQuery();

			while (res.next()) {				
				tenantVO = new TenantVO();				
				tenantVO.setTenantNo(res.getInt("tenantNo"));
				tenantVO.setTenantMail(res.getString("tenantMail"));
				tenantVO.setTenantPassword(res.getString("tenantPassword"));
				tenantVO.setTenantName(res.getString("tenantName"));
				tenantVO.setTenantSex(res.getString("tenantSex"));
				tenantVO.setTenantAddress(res.getString("tenantAddress"));
				tenantVO.setTenantPhone(res.getString("tenantPhone"));
				list.add(tenantVO); // Store the row in the vector
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (res != null) {
				try {
					res.close();
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
	
	
	// from html start to add some onDemand method
	// from html input start to do some onDemand method
}