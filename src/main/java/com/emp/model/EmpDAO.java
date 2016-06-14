package com.emp.model;

import java.util.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import com.emp.CompositeQuery_Emp;

public class EmpDAO implements EmpDAO_interface{

	private static final String CHECK = "SELECT * FROM emp WHERE empAccount = ?";
	private static final String GET_ONE_STMT = 
			"select empNo, empAccount, empPassword, empName, empSex, empAddress, empMail, empPhone, empPic, TO_CHAR(registerDate, 'YYYY/MM/DD') \"registerDate\" from emp where empNo = ?";
	private static final String GET_NO_STMT = 
			"select empNo from emp where empAccount = ?";
	private static final String UPDATE = 
			"UPDATE emp set empPassword = ?, empName = ?, empSex = ?, "
			+ "empAddress = ?, empMail = ?, empPhone = ? where empNo = ?";
	private static final String DELETE = 
			"DELETE FROM emp where empNo = ?";
	private static final String INSERT = 
			"insert into emp  (empNo, empAccount, empPassword, empName, empSex, empAddress, empMail, empPhone, empPic, registerDate)"
			+ "values (empNo_seq.nextval,?,?,?,?,?,?,?,?,sysdate)";
	
	
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/ZA105G3");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	public EmpVO findByPrimaryKey(Integer empNo) {
		EmpVO empVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet res = null;

		try {
			empVO = new EmpVO();
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setInt(1, empNo);
			res = pstmt.executeQuery();
			while (res.next()) {
				empVO.setEmpNo(res.getInt("empNo"));
				empVO.setEmpAccount(res.getString("empAccount"));
				empVO.setEmpPassword(res.getString("empPassword"));
				empVO.setEmpName(res.getString("empName"));
				empVO.setEmpSex(res.getString("empSex"));
				empVO.setEmpAddress(res.getString("empAddress"));
				empVO.setEmpMail(res.getString("empMail"));
				empVO.setEmpPhone(res.getString("empPhone"));				
				empVO.setEmpPic(res.getBytes("empPic"));
				empVO.setRegisterDate(res.getString("registerDate"));
			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
		return empVO;
	};

	public Integer findEmpNo(String empAccount) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		Integer empNo = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_NO_STMT);
			pstmt.setString(1, empAccount);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				empNo = rs.getInt("empNo");

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
		return empNo;
	}

	public String findEmpPassword(String empAccount) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet res = null;

		String password = null;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(CHECK);
			pstmt.setString(1, empAccount);

			res = pstmt.executeQuery();

			while (res.next()) {

				password = res.getString("empPassword");

			}

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
		return password;

	}

	public List<EmpVO> getListByName(String empName) {
		List<EmpVO> list = new ArrayList<EmpVO>();
		String GET_LIST = "select * FROM tenant where tenantName Like " + " '%" + empName + "%'  order by tenantNo";
		EmpVO empVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet res = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_LIST);
			res = pstmt.executeQuery();

			while (res.next()) {
				empVO = new EmpVO();
				empVO.setEmpAccount(res.getString("empAccount"));
				empVO.setEmpPassword(res.getString("empPassword"));
				empVO.setEmpName(res.getString("empName"));
				empVO.setEmpSex(res.getString("empSex"));
				empVO.setEmpAddress(res.getString("empAddress"));
				empVO.setEmpMail(res.getString("empMail"));
				empVO.setEmpPhone(res.getString("empPhone"));
				list.add(empVO); // Store the row in the vector
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

	public List<EmpVO> getAll(Map<String, String[]> map) {
		List<EmpVO> list = new ArrayList<EmpVO>();
		EmpVO empVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet res = null;

		try {

			con = ds.getConnection();
			String finalSQL = "select emp.* from emp join access_emp on emp.empno = access_emp.empno"
					+ CompositeQuery_Emp.getWhere(map) + "order by emp.empno";
			pstmt = con.prepareStatement(finalSQL);
			System.out.println("empSQL = " + finalSQL);
			res = pstmt.executeQuery();

			while (res.next()) {
				empVO = new EmpVO();
				empVO.setEmpNo(res.getInt("empNo"));
				empVO.setEmpAccount(res.getString("empAccount"));
				empVO.setEmpPassword(res.getString("empPassword"));
				empVO.setEmpName(res.getString("empName"));
				empVO.setEmpSex(res.getString("empSex"));
				empVO.setEmpAddress(res.getString("empAddress"));
				empVO.setEmpMail(res.getString("empMail"));
				empVO.setEmpPhone(res.getString("empPhone"));				
				list.add(empVO);
			}

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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

	public void update(EmpVO empVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, empVO.getEmpPassword());
			pstmt.setString(2, empVO.getEmpName());
			pstmt.setString(3, empVO.getEmpSex());
			pstmt.setString(4, empVO.getEmpAddress());
			pstmt.setString(5, empVO.getEmpMail());
			pstmt.setString(6, empVO.getEmpPhone());
			pstmt.setInt(7, empVO.getEmpNo());

			pstmt.executeUpdate();

			// Handle any driver errors
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
	
	public EmpVO insert(EmpVO empVO) {

		Connection con = null;
		PreparedStatement pstmt = null;
		InputStream empPicStream=new ByteArrayInputStream(empVO.getEmpPic());
		try {
			
			con = ds.getConnection();
			con.setAutoCommit(false);
			String cols[] = { "empNo" };
			pstmt = con.prepareStatement(INSERT,cols);
			
			pstmt.setString(1, empVO.getEmpAccount());
			pstmt.setString(2, empVO.getEmpPassword());
			pstmt.setString(3, empVO.getEmpName());
			pstmt.setString(4, empVO.getEmpSex());
			pstmt.setString(5, empVO.getEmpAddress());			
			pstmt.setString(6, empVO.getEmpMail());
			pstmt.setString(7, empVO.getEmpPhone());
			pstmt.setBinaryStream(8, empPicStream);
			System.out.println("set");
			pstmt.executeUpdate();
			System.out.println("eU");
			String next_emp_no = null;
			ResultSet rs = pstmt.getGeneratedKeys();
			System.out.println("rs");
			if (rs.next()) {
				next_emp_no = rs.getString(1);
				System.out.println("PK:" + next_emp_no );
			} else {
				System.out.println("PK fail");
			}
			rs.close();
			empPicStream.close();
			Integer no = Integer.parseInt(next_emp_no);
			empVO.setEmpNo(no);
			
			con.commit();
			con.setAutoCommit(true);

			// Handle any driver errors
		}catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		} catch (IOException e) {
			
			e.printStackTrace();
		}finally{
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
		return empVO;
	}

	
	public void delete(Integer empNo) {

		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, empNo);

			pstmt.executeUpdate();

			// Handle any driver errors
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
