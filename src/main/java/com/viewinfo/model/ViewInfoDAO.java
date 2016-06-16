package com.viewinfo.model;

import java.util.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import jdbc.util.CompositeQuery.CompositeQuery_ViewInfo;

import com.dealer.CompositeQuery_Dealer;
import com.viewlist.model.ViewListVO;
import com.viewphoto.model.ViewPhotoVO;



public class ViewInfoDAO implements ViewInfoDAO_interface {
	
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/ZA105G3");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String INSERT_STMT = 
			"INSERT INTO viewinfo (viewno, viewname, viewmanager, viewphone, viewaddress, viewweb, viewlon, viewlat, viewopen, viewticket, viewequi, viewcontent) VALUES (VIEWNO_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	
	private static final String GET_ALL_STMT = 
			"SELECT viewno, viewname, viewmanager, viewphone, viewaddress, viewweb, viewlon, viewlat, viewopen, viewticket, viewequi, viewcontent FROM viewinfo order by viewno";
	private static final String GET_ONE_STMT = 
			"SELECT viewno, viewname, viewmanager, viewphone, viewaddress, viewweb, viewlon, viewlat, viewopen, viewticket, viewequi, viewcontent FROM viewinfo where viewno = ?";
	private static final String GET_ViewPhoto_ByViewno_STMT=
			"SELECT viewpicno, viewno,viewpic FROM viewphoto WHERE viewno=? order by viewno ";
	private static final String GET_ViewList_ByViewno_STMT=
			"SELECT viewlistno, tenantno, viewno, to_char(viewlistdate,'yyyy-mm-dd')viewlistdate FROM viewlist WHERE viewno=? order by viewno ";
	
	//���o�̫�s�W�����I��T
	private static final String GET_FinalAddView_ByViewno_STMT=
			"select *  from viewinfo where viewno in  (select max(viewno) from viewinfo)";
	
	private static final String DELETE_ViewPhoto="DELETE FROM viewphoto WHERE viewno=?";
	private static final String DELETE_ViewList="DELETE FROM viewlist WHERE viewno=?";
	private static final String DELETE_ViewInfo="DELETE FROM viewinfo WHERE viewno=?";
	
	private static final String UPDATE = 
			"UPDATE viewinfo set viewname=?, viewmanager=?, viewphone=?, viewaddress=?, viewweb=?, viewlon=?, viewlat=?, viewopen=?, viewticket=?, viewequi=?, viewcontent=? where viewno = ?";
	
	//private static final String DELETE = 
			//"DELETE FROM viewinfo where viewno = ?";
	//new
	 @Override
		public List<ViewInfoVO> likeByColumn(String columnName,String likeString) {
	  Connection con = null;
	  Statement pstmt = null;

	  ViewInfoVO viewInfoVO = null;
	  ResultSet rs = null;
	  List<ViewInfoVO> list = new ArrayList<ViewInfoVO>();
	  String likeSql="SELECT viewno,viewname,viewmanager,viewphone,viewaddress,viewweb,viewlon,viewlat,viewopen,viewticket,viewequi,viewcontent FROM ViewInfo where "+columnName+" like '"+likeString+"'";

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	   try {

	  con = ds.getConnection();
	  pstmt = con.createStatement();

				rs = pstmt.executeQuery(likeSql);
				while (rs.next()) {
	  viewInfoVO = new ViewInfoVO();
	  viewInfoVO.setViewno(rs.getInt("viewno"));
	  viewInfoVO.setViewname(rs.getString("viewname"));
	  viewInfoVO.setViewmanager(rs.getString("viewmanager"));
	  viewInfoVO.setViewphone(rs.getString("viewphone"));
	  viewInfoVO.setViewaddress(rs.getString("viewaddress"));
	  viewInfoVO.setViewweb(rs.getString("viewweb"));
	  viewInfoVO.setViewlon(rs.getDouble("viewlon"));
	  viewInfoVO.setViewlat(rs.getDouble("viewlat"));
	  viewInfoVO.setViewopen(rs.getString("viewopen"));
	  viewInfoVO.setViewticket(rs.getString("viewticket"));
	  viewInfoVO.setViewequi(rs.getString("viewequi"));
	  viewInfoVO.setViewcontent(rs.getString("viewcontent"));
	  list.add(viewInfoVO);
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
	public void insert(ViewInfoVO viewinfoVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, viewinfoVO.getViewname());
			pstmt.setString(2, viewinfoVO.getViewmanager());
			pstmt.setString(3, viewinfoVO.getViewphone());
			pstmt.setString(4, viewinfoVO.getViewaddress());
			pstmt.setString(5, viewinfoVO.getViewweb());
			pstmt.setDouble(6, viewinfoVO.getViewlon());
			pstmt.setDouble(7, viewinfoVO.getViewlat());
			pstmt.setString(8, viewinfoVO.getViewopen());
			pstmt.setString(9, viewinfoVO.getViewticket());
			pstmt.setString(10, viewinfoVO.getViewequi());
			pstmt.setString(11, viewinfoVO.getViewcontent());
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
	public void update(ViewInfoVO viewinfoVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, viewinfoVO.getViewname());
			pstmt.setString(2, viewinfoVO.getViewmanager());
			pstmt.setString(3, viewinfoVO.getViewphone());
			pstmt.setString(4, viewinfoVO.getViewaddress());
			pstmt.setString(5, viewinfoVO.getViewweb());
			pstmt.setDouble(6, viewinfoVO.getViewlon());
			pstmt.setDouble(7, viewinfoVO.getViewlat());
			pstmt.setString(8, viewinfoVO.getViewopen());
			pstmt.setString(9, viewinfoVO.getViewticket());
			pstmt.setString(10, viewinfoVO.getViewequi());
			pstmt.setString(11, viewinfoVO.getViewcontent());
			pstmt.setInt(12, viewinfoVO.getViewno());

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
	public void delete(Integer viewno) {
		int updateCount_ViewPhoto=0;
		int updateCount_ViewList=0;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {

			con = ds.getConnection();
			
			// 1���]�w�� pstm.executeUpdate()���e
			con.setAutoCommit(false);
			
			// ���R�����I�Ӥ�
			
			pstmt = con.prepareStatement(DELETE_ViewList);
			pstmt.setInt(1, viewno);
			updateCount_ViewPhoto = pstmt.executeUpdate();
			pstmt = con.prepareStatement(DELETE_ViewPhoto);
			pstmt.setInt(1, viewno);
			pstmt.executeUpdate();
			//�A�R�����I
			pstmt=con.prepareStatement(DELETE_ViewInfo);
			pstmt.setInt(1, viewno);
			pstmt.executeUpdate();
			
			// 2���]�w�� pstm.executeUpdate()����
			con.commit();
			con.setAutoCommit(true);
			System.out.println("�R�����I�s��" + viewno + "��,�@�����I�Ӥ�" + updateCount_ViewPhoto
								+ "�i�P�ɳQ�R��");
			System.out.println("�R�����I�s��" + viewno + "��,�@�����I���òM��" + updateCount_ViewList
					+ "�i�P�ɳQ�R��");			
			
			
			// Handle any driver errors
		}  catch (SQLException se) {
			if (con != null) {
				try {
					// 3���]�w���exception�o�ͮɤ�catch�϶���
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. "
							+ excep.getMessage());
				}
			}
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
	public ViewInfoVO findByPrimaryKey(Integer viewno) {
		ViewInfoVO viewinfoVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, viewno);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo �]�٬� Domain objects
				
				viewinfoVO = new ViewInfoVO();
				viewinfoVO.setViewno(rs.getInt("viewno"));
				viewinfoVO.setViewname(rs.getString("viewname"));
				viewinfoVO.setViewmanager(rs.getString("viewmanager"));
				viewinfoVO.setViewphone(rs.getString("viewphone"));
				viewinfoVO.setViewaddress(rs.getString("viewaddress"));
				viewinfoVO.setViewweb(rs.getString("viewweb"));
				viewinfoVO.setViewlon(rs.getDouble("viewlon"));
				viewinfoVO.setViewlat(rs.getDouble("viewlat"));
				viewinfoVO.setViewopen(rs.getString("viewopen"));
				viewinfoVO.setViewticket(rs.getString("viewticket"));
				viewinfoVO.setViewequi(rs.getString("viewequi"));
				viewinfoVO.setViewcontent(rs.getString("viewcontent"));
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
		return viewinfoVO;
	}
	@Override
	public List<ViewInfoVO> getAll() {
		List<ViewInfoVO> list = new ArrayList<ViewInfoVO>();
		ViewInfoVO viewinfoVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO �]�٬� Domain objects
				viewinfoVO = new ViewInfoVO();
				viewinfoVO.setViewno(rs.getInt("viewno"));
				viewinfoVO.setViewname(rs.getString("viewname"));
				viewinfoVO.setViewmanager(rs.getString("viewmanager"));
				viewinfoVO.setViewphone(rs.getString("viewphone"));
				viewinfoVO.setViewaddress(rs.getString("viewaddress"));
				viewinfoVO.setViewweb(rs.getString("viewweb"));
				viewinfoVO.setViewlon(rs.getDouble("viewlon"));
				viewinfoVO.setViewlat(rs.getDouble("viewlat"));
				viewinfoVO.setViewopen(rs.getString("viewopen"));
				viewinfoVO.setViewticket(rs.getString("viewticket"));
				viewinfoVO.setViewequi(rs.getString("viewequi"));
				viewinfoVO.setViewcontent(rs.getString("viewcontent"));
				
				list.add(viewinfoVO); // Store the row in the list
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
	public Set<ViewPhotoVO> getViewPhotoByViewno(Integer viewno) {
		Set<ViewPhotoVO> set = new LinkedHashSet<ViewPhotoVO>();
		ViewPhotoVO viewphotoVO = null;
	
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	
		try {
	
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ViewPhoto_ByViewno_STMT);
			pstmt.setInt(1, viewno);
			rs = pstmt.executeQuery();
	
			while (rs.next()) {
				viewphotoVO = new ViewPhotoVO();
				viewphotoVO.setViewpicno(rs.getInt("viewpicno"));
				viewphotoVO.setViewno(rs.getInt("viewno"));
				viewphotoVO.setViewpic(rs.getBytes("viewpic"));

				set.add(viewphotoVO); // Store the row in the vector
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
	}
	@Override
	public Set<ViewListVO> getViewListByViewno(Integer viewno) {
		Set<ViewListVO> set = new LinkedHashSet<ViewListVO>();
		ViewListVO viewlistVO = null;
	
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	
		try {
	
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ViewList_ByViewno_STMT);
			pstmt.setInt(1, viewno);
			rs = pstmt.executeQuery();
	
			while (rs.next()) {
				viewlistVO = new ViewListVO();
				viewlistVO.setViewlistno(rs.getInt("viewlistno"));
				viewlistVO.setTenantNo(rs.getInt("tenantno"));
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
	
	}
	@Override
	public ViewInfoVO getLastOneViewInfo() {
		ViewInfoVO viewinfoVO = new ViewInfoVO();
		

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_FinalAddView_ByViewno_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// viewinfoVO �]�٬� Domain objects
				viewinfoVO = new ViewInfoVO();
				viewinfoVO.setViewno(rs.getInt("viewno"));
				viewinfoVO.setViewname(rs.getString("viewname"));
				viewinfoVO.setViewmanager(rs.getString("viewmanager"));
				viewinfoVO.setViewphone(rs.getString("viewphone"));
				viewinfoVO.setViewaddress(rs.getString("viewaddress"));
				viewinfoVO.setViewweb(rs.getString("viewweb"));
				viewinfoVO.setViewlon(rs.getDouble("viewlon"));
				viewinfoVO.setViewlat(rs.getDouble("viewlat"));
				viewinfoVO.setViewopen(rs.getString("viewopen"));
				viewinfoVO.setViewticket(rs.getString("viewticket"));
				viewinfoVO.setViewequi(rs.getString("viewequi"));
				viewinfoVO.setViewcontent(rs.getString("viewcontent"));
				
				
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
		return viewinfoVO;
	

	}
	
	  public List<ViewInfoVO> getAll(Map<String, String[]> map) {
		List<ViewInfoVO> list = new ArrayList<ViewInfoVO>();
		ViewInfoVO viewinfoVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			String SQL = "select * from viewinfo"
					+ CompositeQuery_ViewInfo.getWhere(map)+" order by viewno";
			
			pstmt = con.prepareStatement(SQL);
			System.out.println("viewSQL = " + SQL);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				viewinfoVO = new ViewInfoVO();
				viewinfoVO.setViewno(rs.getInt("viewno"));
				viewinfoVO.setViewname(rs.getString("viewname"));
				viewinfoVO.setViewmanager(rs.getString("viewmanager"));
				viewinfoVO.setViewphone(rs.getString("viewphone"));
				viewinfoVO.setViewaddress(rs.getString("viewaddress"));
				viewinfoVO.setViewweb(rs.getString("viewweb"));
				viewinfoVO.setViewlon(rs.getDouble("viewlon"));
				viewinfoVO.setViewlat(rs.getDouble("viewlat"));
				viewinfoVO.setViewopen(rs.getString("viewopen"));
				viewinfoVO.setViewticket(rs.getString("viewticket"));
				viewinfoVO.setViewequi(rs.getString("viewequi"));
				viewinfoVO.setViewcontent(rs.getString("viewcontent"));
				list.add(viewinfoVO);
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
		
		return list;
	}
	
	

}
