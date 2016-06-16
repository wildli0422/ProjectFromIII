package com.accessemp.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


public class AccessEmpDAO {

	private static DataSource ds = null;
	private static final String GET_ACCESS_EMP = "select accessNo from emp e join access_emp a on e.empNo = a.empNo where e.empNo = ?";
	private static final String INSERT_STMT = "insert into access_emp values (?, ?)";
	private static final String DELETE = "delete from access_emp where empno= ? and accessno = ?";
	private static final String DELETEALL = "delete from access_emp where empno= ?";
	
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/ZA105G3");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	public List<Integer> getEmpAccess(Integer empNo) {
		List<Integer> list = new LinkedList<Integer>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet res = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ACCESS_EMP);
			pstmt.setInt(1, empNo);
			res = pstmt.executeQuery();

			while (res.next()) {
				list.add(res.getInt("accessNo"));

			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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

	public void insert(AccessEmpVO accessEmpVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setInt(1, accessEmpVO.getEmpNo());
			pstmt.setInt(2, accessEmpVO.getAccessNo());
			pstmt.executeUpdate();
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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

	public void delete(AccessEmpVO accessEmpVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);
			pstmt.setInt(1, accessEmpVO.getEmpNo());
			pstmt.setInt(2, accessEmpVO.getAccessNo());
			pstmt.executeUpdate();

		} catch (SQLException se) {
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
	public void deleteAll(Integer empNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETEALL);
			pstmt.setInt(1, empNo);
			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
}
