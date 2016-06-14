package com.viewlist.model;

import java.util.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.viewlist.model.ViewListVO;


public class ViewListDAO implements ViewListDAO_interface {
	
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/ZA105G3");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String INSERT_STMT=
			"INSERT INTO viewlist (viewlistno, tenantno, viewno, viewlistdate) VALUES (VIEWLISTNo_SEQ.NEXTVAL, ?, ?, sysdate)";
	private static final String GET_ALL_STMT=
			"SELECT viewlistno, tenantno, viewno, to_char(viewlistdate,'yyyy-mm-dd')viewlistdate FROM viewlist order by tenantno";
	private static final String GET_ONE_STMT = 
			"SELECT viewlistno, tenantno, viewno, to_char(viewlistdate,'yyyy-mm-dd')viewlistdate from viewlist  where  viewlistno  = ?";
	
	//以房客編號查詢景點清單
	private static final String GET_Viewlists_ByTenantno_STMT=
			"SELECT viewlistno, tenantno, viewno, to_char(viewlistdate,'yyyy-mm-dd')viewlistdate from viewlist where tenantno=? ";
	
	private static final String DELETE = 
			"DELETE FROM viewlist where viewlistno = ?";
	private static final String UPDATE = 
			"UPDATE viewlist set tenantno=?, viewno=?, viewlistdate=? where viewlistno = ?";
	
	
	@Override
	public void insert(ViewListVO viewlistVO) {
		Connection con = null;
		PreparedStatement pstmt = null;	
		
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, viewlistVO.getTenantNo());
			pstmt.setInt(2, viewlistVO.getViewno());
			//pstmt.setDate(3, viewlistVO.getViewlistdate());

			pstmt.executeUpdate();

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
	public void update(ViewListVO viewlistVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1, viewlistVO.getTenantNo());
			pstmt.setInt(2, viewlistVO.getViewno());
			pstmt.setDate(3, viewlistVO.getViewlistdate());
			pstmt.setInt(4, viewlistVO.getViewlistno());
			
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
	public void delete(Integer viewlistno) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, viewlistno);

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
	public ViewListVO findByPrimaryKey(Integer viewlistno) {
		ViewListVO viewlistVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, viewlistno);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				viewlistVO = new ViewListVO();
				viewlistVO.setViewlistno(rs.getInt("viewlistno"));
				viewlistVO.setTenantNo(rs.getInt("tenantno"));
				viewlistVO.setViewno(rs.getInt("viewno"));
				viewlistVO.setViewlistdate(rs.getDate("viewlistdate"));
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
		return viewlistVO;
		
	}

	@Override
	public List<ViewListVO> getAll() {
		List<ViewListVO> list = new ArrayList<ViewListVO>();
		ViewListVO viewlistVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				
				viewlistVO = new ViewListVO();
				viewlistVO.setViewlistno(rs.getInt("viewlistno"));
				viewlistVO.setTenantNo(rs.getInt("tenantno"));
				viewlistVO.setViewno(rs.getInt("viewno"));
				viewlistVO.setViewlistdate(rs.getDate("viewlistdate"));
				
				list.add(viewlistVO); // Store the row in the list
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
	public Set<ViewListVO> getViewListByTenantno(Integer tenantNo) {
		Set<ViewListVO> set = new LinkedHashSet<ViewListVO>();
		ViewListVO viewlistVO = null;
	
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	
		try {
	
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_Viewlists_ByTenantno_STMT);
			pstmt.setInt(1, tenantNo);
			rs = pstmt.executeQuery();
	
			while (rs.next()) {
				viewlistVO = new ViewListVO();
				viewlistVO.setViewlistno(rs.getInt("viewlistno"));
				viewlistVO.setTenantNo(rs.getInt("tenantNo"));
				viewlistVO.setViewno(rs.getInt("viewno"));
				viewlistVO.setViewlistdate(rs.getDate("viewlistdate"));
				set.add(viewlistVO); // Store the row in the vector
			}
	
			// Handle any SQL errors
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
	
	}//getViewListByTenantno

}
