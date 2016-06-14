package com.viewphoto.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.viewphoto.model.ViewPhotoVO;


public class ViewPhotoDAO implements ViewPhotoDAO_interface 
{

	// 一個應用程式中,針對一個資料庫 ,共用一個DataSource即可
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
		"INSERT INTO viewphoto (viewpicno, viewno, viewpic) VALUES (VIEWPICNO_SEQ.NEXTVAL, ?, ?) ";
	private static final String GET_ALL_STMT =
		"SELECT viewpicno,viewno,viewpic FROM viewphoto order by viewno";
	private static final String GET_ONE_STMT = 
		"SELECT viewpicno, viewno, viewpic FROM viewphoto where viewpicno = ?";
	private static final String GET_ONEVIEWPIC_STMT=
		"SELECT * FROM viewphoto WHERE viewpicno IN (SELECT MIN(viewpicno) FROM viewphoto GROUP BY viewno)";


	
	private static final String DELETE = 
		"DELETE FROM viewphoto where viewpicno = ?";
	private static final String UPDATE = 
		"UPDATE viewphoto set viewno=?, viewpic=? where viewpicno = ?";
	
	@Override
	public void insert(ViewPhotoVO viewphotoVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, viewphotoVO.getViewno());
			pstmt.setBytes(2, viewphotoVO.getViewpic());

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
	public void update(ViewPhotoVO viewphotoVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1, viewphotoVO.getViewno());
			pstmt.setBytes(2, viewphotoVO.getViewpic());
			pstmt.setInt(3, viewphotoVO.getViewpicno());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
//			// Clean up JDBC resources
		}
			finally {
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
	public void delete(Integer viewpicno) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, viewpicno);

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
	public ViewPhotoVO findByPrimaryKey(Integer viewpicno) {
		ViewPhotoVO viewphotoVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, viewpicno);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				viewphotoVO = new ViewPhotoVO();
				viewphotoVO.setViewpicno(rs.getInt("viewpicno"));
				viewphotoVO.setViewno(rs.getInt("viewno"));
				viewphotoVO.setViewpic(rs.getBytes("viewpic"));
				
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
		return viewphotoVO;		
	}

	@Override
	public List<ViewPhotoVO> getAll() {
		List<ViewPhotoVO> list = new ArrayList<ViewPhotoVO>();
		ViewPhotoVO viewphotoVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				viewphotoVO = new ViewPhotoVO();
				viewphotoVO.setViewpicno(rs.getInt("viewpicno"));
				viewphotoVO.setViewno(rs.getInt("viewno"));
				viewphotoVO.setViewpic(rs.getBytes("viewpic"));
				list.add(viewphotoVO); // Store the row in the list
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
	//找每景點的第一張照片
	@Override
	public List<ViewPhotoVO> getOneViewpic() {
		List<ViewPhotoVO> list = new ArrayList<ViewPhotoVO>();
		ViewPhotoVO viewphotoVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONEVIEWPIC_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				viewphotoVO = new ViewPhotoVO();
				viewphotoVO.setViewpicno(rs.getInt("viewpicno"));
				viewphotoVO.setViewno(rs.getInt("viewno"));
				viewphotoVO.setViewpic(rs.getBytes("viewpic"));
				list.add(viewphotoVO); // Store the row in the list
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
    public ViewPhotoVO findByViewNo(Integer viewNo) {
  Connection con = null;
  PreparedStatement pstmt = null;

  ViewPhotoVO viewPhotoVO = null;
  ResultSet rs = null;
   try {

  con = ds.getConnection();
  String findByThisKey =
  "SELECT viewpicNo,viewNo,viewPic FROM ViewPhoto where viewNo= ?";

    pstmt = con.prepareStatement(findByThisKey);
  pstmt.setInt(1, viewNo);
			rs = pstmt.executeQuery();
			while (rs.next()) {
  viewPhotoVO = new ViewPhotoVO();
  viewPhotoVO.setViewpicno(  rs.getInt("viewpicNo"));
  viewPhotoVO.setViewno(  rs.getInt("viewNo"));
  viewPhotoVO.setViewpic(  rs.getBytes("viewPic"));
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
		return viewPhotoVO;
}
}
