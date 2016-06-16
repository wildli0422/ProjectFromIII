/*本程式是使用DAO產生器自動產生 copyRight:wildli0422@gmail.com 我愛一條柴*/
package com.hostelNews.model;

import java.util.*;
import java.sql.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class HostelNewsDAO implements HostelNewsDAO_interface {
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
	private static final String INSERT_STMT = "INSERT INTO hostelNews (hostelNewsNo,hostelNo,newsContent,updateDate) VALUES (hostelNewsNo_seq.NEXTVAL, ?, ?, SYSDATE)";

	private static final String GET_ALL_STMT = "SELECT hostelNewsNo,hostelNo,newsContent,to_char(updateDate ,'yyyy-mm-dd') updateDate FROM hostelNews order by hostelNewsNo";

	private static final String GET_ONE_STMT = "SELECT hostelNewsNo,hostelNo,newsContent,to_char(updateDate ,'yyyy-mm-dd') updateDate FROM hostelNews where hostelNewsNo=?";

	private static final String DELETE = "DELETE FROM hostelNews where hostelNewsNo=?";

	private static final String UPDATE = "UPDATE hostelNews set hostelNo=?, newsContent=?, updateDate=? where hostelNewsNo=?";

	// start to add some regular method
	@Override
	public void insert(HostelNewsVO hostelNewsVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, hostelNewsVO.getHostelNo());
			pstmt.setString(2, hostelNewsVO.getNewsContent());
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
	public void update(HostelNewsVO hostelNewsVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1, hostelNewsVO.getHostelNo());
			pstmt.setString(2, hostelNewsVO.getNewsContent());
			pstmt.setDate(3, hostelNewsVO.getUpdateDate());
			pstmt.setInt(4, hostelNewsVO.getHostelNewsNo());
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
	public void delete(Integer hostelNewsNo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, hostelNewsNo);
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
	public HostelNewsVO findByPrimaryKey(Integer hostelNewsNo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		HostelNewsVO hostelNewsVO = null;
		ResultSet rs = null;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, hostelNewsNo);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				hostelNewsVO = new HostelNewsVO();
				hostelNewsVO.setHostelNewsNo(rs.getInt("hostelNewsNo"));
				hostelNewsVO.setHostelNo(rs.getInt("hostelNo"));
				hostelNewsVO.setNewsContent(rs.getString("newsContent"));
				hostelNewsVO.setUpdateDate(rs.getDate("updateDate"));
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
		return hostelNewsVO;
	}

	@Override
	public List<HostelNewsVO> getAll() {
		Connection con = null;
		PreparedStatement pstmt = null;

		HostelNewsVO hostelNewsVO = null;
		ResultSet rs = null;
		List<HostelNewsVO> list = new ArrayList<HostelNewsVO>();
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);

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
	public List<HostelNewsVO> likeByColumn(String columnName, String likeString) {
		Connection con = null;
		Statement pstmt = null;

		HostelNewsVO hostelNewsVO = null;
		ResultSet rs = null;
		List<HostelNewsVO> list = new ArrayList<HostelNewsVO>();
		String likeSql = "SELECT hostelNewsNo,hostelNo,newsContent,to_char(updateDate ,'yyyy-mm-dd') updateDate FROM hostelNews where "
				+ columnName + " like '" + likeString + "'";

		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		try {

			con = ds.getConnection();
			pstmt = con.createStatement();

			rs = pstmt.executeQuery(likeSql);
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
	public HostelNewsVO findByHostelNo(Integer hostelNo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		HostelNewsVO hostelNewsVO = null;
		ResultSet rs = null;
		try {

			con = ds.getConnection();
			String findByThisKey = "SELECT hostelNewsNo,hostelNo,newsContent,to_char(updateDate ,'yyyy-mm-dd') updateDate FROM hostelNews where hostelNo= ?";

			pstmt = con.prepareStatement(findByThisKey);
			pstmt.setInt(1, hostelNo);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				hostelNewsVO = new HostelNewsVO();
				hostelNewsVO.setHostelNewsNo(rs.getInt("hostelNewsNo"));
				hostelNewsVO.setHostelNo(rs.getInt("hostelNo"));
				hostelNewsVO.setNewsContent(rs.getString("newsContent"));
				hostelNewsVO.setUpdateDate(rs.getDate("updateDate"));
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
		return hostelNewsVO;
	}

	public List<HostelNewsVO> getAllByHostelNo(Integer hostelNo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		HostelNewsVO hostelNewsVO = null;
		List<HostelNewsVO> list = new ArrayList<HostelNewsVO>();
		ResultSet rs = null;
		try {

			con = ds.getConnection();
			String listByThisKey = "SELECT hostelNewsNo,hostelNo,newsContent,to_char(updateDate ,'yyyy-mm-dd') updateDate FROM hostelNews where hostelNo= ? order by hostelNo";

			pstmt = con.prepareStatement(listByThisKey);
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

	public HostelNewsVO findByNewsContent(String newsContent) {
		Connection con = null;
		PreparedStatement pstmt = null;

		HostelNewsVO hostelNewsVO = null;
		ResultSet rs = null;
		try {

			con = ds.getConnection();
			String findByThisKey = "SELECT hostelNewsNo,hostelNo,newsContent,to_char(updateDate ,'yyyy-mm-dd') updateDate FROM hostelNews where newsContent= ?";

			pstmt = con.prepareStatement(findByThisKey);
			pstmt.setString(1, newsContent);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				hostelNewsVO = new HostelNewsVO();
				hostelNewsVO.setHostelNewsNo(rs.getInt("hostelNewsNo"));
				hostelNewsVO.setHostelNo(rs.getInt("hostelNo"));
				hostelNewsVO.setNewsContent(rs.getString("newsContent"));
				hostelNewsVO.setUpdateDate(rs.getDate("updateDate"));
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
		return hostelNewsVO;
	}

	public List<HostelNewsVO> getAllByNewsContent(String newsContent) {
		Connection con = null;
		PreparedStatement pstmt = null;

		HostelNewsVO hostelNewsVO = null;
		List<HostelNewsVO> list = new ArrayList<HostelNewsVO>();
		ResultSet rs = null;
		try {

			con = ds.getConnection();
			String listByThisKey = "SELECT hostelNewsNo,hostelNo,newsContent,to_char(updateDate ,'yyyy-mm-dd') updateDate FROM hostelNews where newsContent= ? order by newsContent";

			pstmt = con.prepareStatement(listByThisKey);
			pstmt.setString(1, newsContent);
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

	public HostelNewsVO findByUpdateDate(java.sql.Date updateDate) {
		Connection con = null;
		PreparedStatement pstmt = null;

		HostelNewsVO hostelNewsVO = null;
		ResultSet rs = null;
		try {

			con = ds.getConnection();
			String findByThisKey = "SELECT hostelNewsNo,hostelNo,newsContent,to_char(updateDate ,'yyyy-mm-dd') updateDate FROM hostelNews where updateDate= ?";

			pstmt = con.prepareStatement(findByThisKey);
			pstmt.setDate(1, updateDate);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				hostelNewsVO = new HostelNewsVO();
				hostelNewsVO.setHostelNewsNo(rs.getInt("hostelNewsNo"));
				hostelNewsVO.setHostelNo(rs.getInt("hostelNo"));
				hostelNewsVO.setNewsContent(rs.getString("newsContent"));
				hostelNewsVO.setUpdateDate(rs.getDate("updateDate"));
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
		return hostelNewsVO;
	}

	public List<HostelNewsVO> getAllByUpdateDate(java.sql.Date updateDate) {
		Connection con = null;
		PreparedStatement pstmt = null;

		HostelNewsVO hostelNewsVO = null;
		List<HostelNewsVO> list = new ArrayList<HostelNewsVO>();
		ResultSet rs = null;
		try {

			con = ds.getConnection();
			String listByThisKey = "SELECT hostelNewsNo,hostelNo,newsContent,to_char(updateDate ,'yyyy-mm-dd') updateDate FROM hostelNews where updateDate= ? order by updateDate";

			pstmt = con.prepareStatement(listByThisKey);
			pstmt.setDate(1, updateDate);
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