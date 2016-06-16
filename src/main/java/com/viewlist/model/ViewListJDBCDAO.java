package com.viewlist.model;

import java.util.*;
import java.sql.*;

import com.viewlist.model.ViewListVO;

public class ViewListJDBCDAO implements ViewListDAO_interface{
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "za105G3";
	String passwd = "gogoG3";
	
	private static final String INSERT_STMT=
			"INSERT INTO viewlist (viewlistno, tenantno, viewno, viewlistdate) VALUES (VIEWLIST_SEQ.NEXTVAL, ?, ?, ?)";
	private static final String GET_ALL_STMT=
			"SELECT viewlistno, tenantno, viewno, to_char(viewlistdate,'yyyy-mm-dd')viewlistdate FROM viewlist order by tenantno";
	private static final String GET_ONE_STMT = 
			"SELECT viewlistno, tenantno, viewno, to_char(viewlistdate,'yyyy-mm-dd')viewlistdate FROM viewlist where viewlistno = ?";
	
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

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, viewlistVO.getTenantNo());
			pstmt.setInt(2, viewlistVO.getViewno());
			pstmt.setDate(3, viewlistVO.getViewlistdate());

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
	public void update(ViewListVO viewlistVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1, viewlistVO.getTenantNo());
			pstmt.setInt(2, viewlistVO.getViewno());
			pstmt.setDate(3, viewlistVO.getViewlistdate());
			pstmt.setInt(4, viewlistVO.getViewlistno());
			
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
	public void delete(Integer viewlistno) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, viewlistno);

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
	public ViewListVO findByPrimaryKey(Integer viewlistno) {
		ViewListVO viewlistVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
	
	//查詢某房客收藏的景點清單
	@Override
	public Set<ViewListVO> getViewListByTenantno(Integer tenantno) {
		Set<ViewListVO> set = new LinkedHashSet<ViewListVO>();
		ViewListVO viewlistVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_Viewlists_ByTenantno_STMT);
			pstmt.setInt(1, tenantno);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				viewlistVO = new ViewListVO();
				viewlistVO.setViewlistno(rs.getInt("viewlistno"));
				viewlistVO.setTenantNo(rs.getInt("tenantno"));
				viewlistVO.setViewno(rs.getInt("viewno"));
				viewlistVO.setViewlistdate(rs.getDate("viewlistdate"));
				set.add(viewlistVO); // Store the row in the vector
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
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
	
	
	public static void main(String[] args) {

		ViewListJDBCDAO dao = new ViewListJDBCDAO();

//		
//		// 新增 ok
//		
//		ViewListVO viewlistVO1 = new ViewListVO();
//		viewlistVO1.setTenantno(new Integer(8000));
//		viewlistVO1.setViewno(new Integer(1234));
//		viewlistVO1.setViewlistdate(java.sql.Date.valueOf("2005-01-01"));
//		dao.insert(viewlistVO1);
//		
//	
		
		
		
		// 修改 ok
//		ViewListVO viewlistVO2 = new ViewListVO();
//		viewlistVO2.setViewlistno(9004);
//		viewlistVO2.setTenantno(500);
//		viewlistVO2.setViewno(500);
//		viewlistVO2.setViewlistdate(java.sql.Date.valueOf("2002-01-01"));
//		dao.update(viewlistVO2);
		
		
		
		// 刪除 ok
		//dao.delete(9008);
		
		
////		// 查詢
//		ViewListVO viewlistVO3 = dao.findByPrimaryKey(9001);
//		System.out.print(viewlistVO3.getViewlistno() + ",");
//		System.out.print(viewlistVO3.getTenantno() + ",");
//		System.out.print(viewlistVO3.getViewno() + ",");
//		System.out.print(viewlistVO3.getViewlistdate() + ",");
//		System.out.println("---------------------");
//		

		// 查詢 ok
		
//		List<ViewListVO> list = dao.getAll();
//		for (ViewListVO aViewList : list) {
//			System.out.print(aViewList.getViewlistno() + ",");
//			System.out.print(aViewList.getTenantno() + ",");
//			System.out.print(aViewList.getViewno() + ",");
//			System.out.print(aViewList.getViewlistdate() + ",");
//			System.out.println();
//		}
		
		// 查詢房客收藏的景點清單
		Set<ViewListVO> set1 = dao.getViewListByTenantno(2003);
		for (ViewListVO aViewList : set1) {
			System.out.print(aViewList.getViewlistno() + ",");
			System.out.print(aViewList.getTenantNo() + ",");
			System.out.print(aViewList.getViewno() + ",");
			System.out.print(aViewList.getViewlistdate());
			System.out.println();

		}

		
	}


}
