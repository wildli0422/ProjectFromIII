package com.hostelPic.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class HostelPicDAO implements HostelPicDAO_interface{
	
	private static DataSource ds=null;
	
	static{
		try {
			Context ctx=new InitialContext();
			ds=(DataSource)ctx.lookup("java:comp/env/jdbc/ZA105G3");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
		private static final String INSERT_STMT = 
			"INSERT INTO hostelPic VALUES (hostelPicNo_seq.NEXTVAL, ?, ?)";
		private static final String GET_ALL_STMT = 
			"SELECT * FROM hostelPic order by hostelPicNo";
		private static final String GET_ONE_STMT = 
			"SELECT * FROM hostelPic where hostelPicNo = ?";
		private static final String DELETE = 
			"DELETE FROM hostelPic where hostelPicNo = ?";
		private static final String UPDATE = 
			"UPDATE hostelPic set hostelNo=?,hostelPhoto=?  where hostelPicNo = ?";

	@Override
	public void insert(HostelPicVO hostelPicVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {

			con=ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, hostelPicVO.getHostelNo());
			pstmt.setBytes(2, hostelPicVO.getHostelPhoto());

			pstmt.executeUpdate();

			// Handle any driver errors
		}catch (SQLException se) {
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
	public void update(HostelPicVO hostelPicVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			
			con=ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1, hostelPicVO.getHostelNo());
			pstmt.setBytes(2, hostelPicVO.getHostelPhoto());
			pstmt.setInt(3, hostelPicVO.getHostelPicNo());
			
			pstmt.executeUpdate();

			// Handle any driver errors
		}catch (SQLException se) {
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
	public void delete(Integer hostelPicNo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			
			con=ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, hostelPicNo);

			pstmt.executeUpdate();

			// Handle any driver errors
		}catch (SQLException se) {
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
	public HostelPicVO findByPrimaryKey(Integer hostelPicNo) {
		HostelPicVO hostelPicVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			
			con=ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, hostelPicNo);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo �]�٬� Domain objects
				hostelPicVO=new HostelPicVO();
				hostelPicVO.setHostelPicNo(rs.getInt("hostelPicNo"));
				hostelPicVO.setHostelNo(rs.getInt("hostelNo"));
				hostelPicVO.setHostelPhoto(rs.getBytes("hostelPhoto"));
			}

			// Handle any driver errors
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
		return hostelPicVO;
	}

	@Override
	public List<HostelPicVO> getAll() {
		List<HostelPicVO> list = new ArrayList<HostelPicVO>();
		HostelPicVO hostelPicVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			
			con=ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO �]�٬� Domain objects
				hostelPicVO = new HostelPicVO();
				hostelPicVO.setHostelPicNo(rs.getInt("hostelPicNo"));
				hostelPicVO.setHostelNo(rs.getInt("hostelNo"));
				hostelPicVO.setHostelPhoto(rs.getBytes("hostelPhoto"));
				list.add(hostelPicVO); // Store the row in the list
			}

			// Handle any driver errors
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
		return list;
	}

}
