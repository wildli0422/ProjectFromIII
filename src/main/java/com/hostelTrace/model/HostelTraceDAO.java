/*?��程�?�是使用DAO?��??�器?��??�產??? copyRight:wildli0422@gmail.com ??��?��?條柴*/
package com.hostelTrace.model;
import java.util.*;
import java.sql.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class HostelTraceDAO implements HostelTraceDAO_interface {
  private static DataSource ds = null;
  static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/ZA105G3");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

//add some static stmt
	private static final String INSERT_STMT =
"INSERT INTO HostelTrace (hostelTraceNo,hostelNo,tenantNo,viewDate) VALUES (hostelTraceNo_seq.NEXTVAL, ?, ?, SYSDATE)";

	private static final String GET_ALL_STMT =
"SELECT hostelTraceNo,hostelNo,tenantNo,to_char(viewDate ,'yyyy-mm-dd') viewDate FROM HostelTrace order by hostelTraceNo";

	private static final String GET_ONE_STMT =
"SELECT hostelTraceNo,hostelNo,tenantNo,to_char(viewDate ,'yyyy-mm-dd') viewDate FROM HostelTrace where hostelTraceNo=?";

	private static final String DELETE =
"DELETE FROM HostelTrace where hostelTraceNo=?";

	private static final String UPDATE =
"UPDATE HostelTrace set hostelNo=?, tenantNo=?, viewDate=? where hostelTraceNo=?";

//start to add some regular method
  @Override
  public void insert(HostelTraceVO hostelTraceVO) {
  Connection con = null;
  PreparedStatement pstmt = null;

   try {

  con = ds.getConnection();
  pstmt = con.prepareStatement(INSERT_STMT);

  pstmt.setInt(1, hostelTraceVO.getHostelNo());
  pstmt.setInt(2, hostelTraceVO.getTenantNo());
  pstmt.executeUpdate();
  }catch (SQLException se) {
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
}

  @Override
  public void update(HostelTraceVO hostelTraceVO) {
  Connection con = null;
  PreparedStatement pstmt = null;

   try {

  con = ds.getConnection();
  pstmt = con.prepareStatement(UPDATE);

  pstmt.setInt(1, hostelTraceVO.getHostelNo());
  pstmt.setInt(2, hostelTraceVO.getTenantNo());
  pstmt.setDate(3, hostelTraceVO.getViewDate());
  pstmt.setInt(4, hostelTraceVO.getHostelTraceNo());
  pstmt.executeUpdate();
  }catch (SQLException se) {
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
}

  @Override
  public void delete(Integer hostelTraceNo) {
  Connection con = null;
  PreparedStatement pstmt = null;

   try {

  con = ds.getConnection();
  pstmt = con.prepareStatement(DELETE);

  pstmt.setInt(1, hostelTraceNo);
  pstmt.executeUpdate();
  }catch (SQLException se) {
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
}

  @Override
  public HostelTraceVO findByPrimaryKey(Integer hostelTraceNo) {
  Connection con = null;
  PreparedStatement pstmt = null;

  HostelTraceVO hostelTraceVO = null;
  ResultSet rs = null;
   try {

  con = ds.getConnection();
  pstmt = con.prepareStatement(GET_ONE_STMT);

  pstmt.setInt(1, hostelTraceNo);
			rs = pstmt.executeQuery();
			while (rs.next()) {
  hostelTraceVO = new HostelTraceVO();
  hostelTraceVO.setHostelTraceNo(rs.getInt("hostelTraceNo"));
  hostelTraceVO.setHostelNo(rs.getInt("hostelNo"));
  hostelTraceVO.setTenantNo(rs.getInt("tenantNo"));
  hostelTraceVO.setViewDate(rs.getDate("viewDate"));
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
		return hostelTraceVO;
}

  @Override
	public List<HostelTraceVO> getAll() {
  Connection con = null;
  PreparedStatement pstmt = null;

  HostelTraceVO hostelTraceVO = null;
  ResultSet rs = null;
  List<HostelTraceVO> list = new ArrayList<HostelTraceVO>();
   try {

  con = ds.getConnection();
  pstmt = con.prepareStatement(GET_ALL_STMT);

			rs = pstmt.executeQuery();
			while (rs.next()) {
  hostelTraceVO = new HostelTraceVO();
  hostelTraceVO.setHostelTraceNo(rs.getInt("hostelTraceNo"));
  hostelTraceVO.setHostelNo(rs.getInt("hostelNo"));
  hostelTraceVO.setTenantNo(rs.getInt("tenantNo"));
  hostelTraceVO.setViewDate(rs.getDate("viewDate"));
  list.add(hostelTraceVO);
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
	public List<HostelTraceVO> likeByColumn(String columnName,String likeString) {
  Connection con = null;
  Statement pstmt = null;

  HostelTraceVO hostelTraceVO = null;
  ResultSet rs = null;
  List<HostelTraceVO> list = new ArrayList<HostelTraceVO>();
  String likeSql="SELECT hostelTraceNo,hostelNo,tenantNo,to_char(viewDate ,'yyyy-mm-dd') viewDate FROM HostelTrace where "+columnName+" like '"+likeString+"'";

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   try {

  con = ds.getConnection();
  pstmt = con.createStatement();

			rs = pstmt.executeQuery(likeSql);
			while (rs.next()) {
  hostelTraceVO = new HostelTraceVO();
  hostelTraceVO.setHostelTraceNo(rs.getInt("hostelTraceNo"));
  hostelTraceVO.setHostelNo(rs.getInt("hostelNo"));
  hostelTraceVO.setTenantNo(rs.getInt("tenantNo"));
  hostelTraceVO.setViewDate(rs.getDate("viewDate"));
  list.add(hostelTraceVO);
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

//from html start to add some onDemand method 
//from html input start to do some onDemand method 
    public HostelTraceVO findByHostelNo(Integer hostelNo) {
  Connection con = null;
  PreparedStatement pstmt = null;

  HostelTraceVO hostelTraceVO = null;
  ResultSet rs = null;
   try {

  con = ds.getConnection();
  String findByThisKey =
  "SELECT hostelTraceNo,hostelNo,tenantNo,to_char(viewDate ,'yyyy-mm-dd') viewDate FROM HostelTrace where hostelNo= ?";

    pstmt = con.prepareStatement(findByThisKey);
  pstmt.setInt(1, hostelNo);
			rs = pstmt.executeQuery();
			while (rs.next()) {
  hostelTraceVO = new HostelTraceVO();
  hostelTraceVO.setHostelTraceNo(  rs.getInt("hostelTraceNo"));
  hostelTraceVO.setHostelNo(  rs.getInt("hostelNo"));
  hostelTraceVO.setTenantNo(  rs.getInt("tenantNo"));
  hostelTraceVO.setViewDate(  rs.getDate("viewDate"));
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
		return hostelTraceVO;
}

    public List<HostelTraceVO> getAllByHostelNo(Integer hostelNo) {
  Connection con = null;
  PreparedStatement pstmt = null;

  HostelTraceVO hostelTraceVO = null;
  List<HostelTraceVO> list = new ArrayList<HostelTraceVO>();
  ResultSet rs = null;
   try {

  con = ds.getConnection();
  String listByThisKey =
  "SELECT hostelTraceNo,hostelNo,tenantNo,to_char(viewDate ,'yyyy-mm-dd') viewDate FROM HostelTrace where hostelNo= ? order by hostelNo";

   pstmt = con.prepareStatement(listByThisKey);
   pstmt.setInt(1, hostelNo);
		 rs = pstmt.executeQuery();
			while (rs.next()) {
  hostelTraceVO = new HostelTraceVO();
  hostelTraceVO.setHostelTraceNo(  rs.getInt("hostelTraceNo"));
  hostelTraceVO.setHostelNo(  rs.getInt("hostelNo"));
  hostelTraceVO.setTenantNo(  rs.getInt("tenantNo"));
  hostelTraceVO.setViewDate(  rs.getDate("viewDate"));
  list.add(hostelTraceVO);
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

    public HostelTraceVO findByTenantNo(Integer tenantNo) {
  Connection con = null;
  PreparedStatement pstmt = null;

  HostelTraceVO hostelTraceVO = null;
  ResultSet rs = null;
   try {

  con = ds.getConnection();
  String findByThisKey =
  "SELECT hostelTraceNo,hostelNo,tenantNo,to_char(viewDate ,'yyyy-mm-dd') viewDate FROM HostelTrace where tenantNo= ?";

    pstmt = con.prepareStatement(findByThisKey);
  pstmt.setInt(1, tenantNo);
			rs = pstmt.executeQuery();
			while (rs.next()) {
  hostelTraceVO = new HostelTraceVO();
  hostelTraceVO.setHostelTraceNo(  rs.getInt("hostelTraceNo"));
  hostelTraceVO.setHostelNo(  rs.getInt("hostelNo"));
  hostelTraceVO.setTenantNo(  rs.getInt("tenantNo"));
  hostelTraceVO.setViewDate(  rs.getDate("viewDate"));
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
		return hostelTraceVO;
}

    public List<HostelTraceVO> getAllByTenantNo(Integer tenantNo) {
  Connection con = null;
  PreparedStatement pstmt = null;

  HostelTraceVO hostelTraceVO = null;
  List<HostelTraceVO> list = new ArrayList<HostelTraceVO>();
  ResultSet rs = null;
   try {

  con = ds.getConnection();
  String listByThisKey =
  "SELECT hostelTraceNo,hostelNo,tenantNo,to_char(viewDate ,'yyyy-mm-dd') viewDate FROM HostelTrace where tenantNo= ? order by tenantNo";

   pstmt = con.prepareStatement(listByThisKey);
   pstmt.setInt(1, tenantNo);
		 rs = pstmt.executeQuery();
			while (rs.next()) {
  hostelTraceVO = new HostelTraceVO();
  hostelTraceVO.setHostelTraceNo(  rs.getInt("hostelTraceNo"));
  hostelTraceVO.setHostelNo(  rs.getInt("hostelNo"));
  hostelTraceVO.setTenantNo(  rs.getInt("tenantNo"));
  hostelTraceVO.setViewDate(  rs.getDate("viewDate"));
  list.add(hostelTraceVO);
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

    public HostelTraceVO findByViewDate(java.sql.Date viewDate) {
  Connection con = null;
  PreparedStatement pstmt = null;

  HostelTraceVO hostelTraceVO = null;
  ResultSet rs = null;
   try {

  con = ds.getConnection();
  String findByThisKey =
  "SELECT hostelTraceNo,hostelNo,tenantNo,to_char(viewDate ,'yyyy-mm-dd') viewDate FROM HostelTrace where viewDate= ?";

    pstmt = con.prepareStatement(findByThisKey);
  pstmt.setDate(1, viewDate);
			rs = pstmt.executeQuery();
			while (rs.next()) {
  hostelTraceVO = new HostelTraceVO();
  hostelTraceVO.setHostelTraceNo(  rs.getInt("hostelTraceNo"));
  hostelTraceVO.setHostelNo(  rs.getInt("hostelNo"));
  hostelTraceVO.setTenantNo(  rs.getInt("tenantNo"));
  hostelTraceVO.setViewDate(  rs.getDate("viewDate"));
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
		return hostelTraceVO;
}

    public List<HostelTraceVO> getAllByViewDate(java.sql.Date viewDate) {
  Connection con = null;
  PreparedStatement pstmt = null;

  HostelTraceVO hostelTraceVO = null;
  List<HostelTraceVO> list = new ArrayList<HostelTraceVO>();
  ResultSet rs = null;
   try {

  con = ds.getConnection();
  String listByThisKey =
  "SELECT hostelTraceNo,hostelNo,tenantNo,to_char(viewDate ,'yyyy-mm-dd') viewDate FROM HostelTrace where viewDate= ? order by viewDate";

   pstmt = con.prepareStatement(listByThisKey);
   pstmt.setDate(1, viewDate);
		 rs = pstmt.executeQuery();
			while (rs.next()) {
  hostelTraceVO = new HostelTraceVO();
  hostelTraceVO.setHostelTraceNo(  rs.getInt("hostelTraceNo"));
  hostelTraceVO.setHostelNo(  rs.getInt("hostelNo"));
  hostelTraceVO.setTenantNo(  rs.getInt("tenantNo"));
  hostelTraceVO.setViewDate(  rs.getDate("viewDate"));
  list.add(hostelTraceVO);
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

}