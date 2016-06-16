/*本程式是使用DAO產生器自動產生 copyRight:wildli0422@gmail.com 我愛一條柴*/
package com.ad.model;
import java.util.*;
import java.sql.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class AdDAO implements AdDAO_interface {
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
"INSERT INTO Ad (adNo,dealerNo,adOdDate,odStatus,adPic,odPrice,adStatus,adStartline,adDeadline) VALUES (adNo_seq.NEXTVAL, ?, SYSDATE, ?, ?, ?, ?, ?, ?)";

	private static final String GET_ALL_STMT =
"SELECT adNo,dealerNo,to_char(adOdDate ,'yyyy-mm-dd') adOdDate,odStatus,adPic,odPrice,adStatus,to_char(adStartline ,'yyyy-mm-dd') adStartline,to_char(adDeadline ,'yyyy-mm-dd') adDeadline FROM Ad order by adNo";

	private static final String GET_ONE_STMT =
"SELECT adNo,dealerNo,to_char(adOdDate ,'yyyy-mm-dd') adOdDate,odStatus,adPic,odPrice,adStatus,to_char(adStartline ,'yyyy-mm-dd') adStartline,to_char(adDeadline ,'yyyy-mm-dd') adDeadline FROM Ad where adNo=?";

	private static final String DELETE =
"DELETE FROM Ad where adNo=?";

	private static final String UPDATE =
"UPDATE Ad set dealerNo=?, adOdDate=?, odStatus=?, adPic=?, odPrice=?, adStatus=?, adStartline=?, adDeadline=? where adNo=?";

//start to add some regular method
  @Override
  public void insert(AdVO adVO) {
  Connection con = null;
  PreparedStatement pstmt = null;

   try {

  con = ds.getConnection();
  pstmt = con.prepareStatement(INSERT_STMT);

  pstmt.setInt(1, adVO.getDealerNo());
  pstmt.setInt(2, adVO.getOdStatus());
  pstmt.setBytes(3, adVO.getAdPic());
  pstmt.setInt(4, adVO.getOdPrice());
  pstmt.setInt(5, adVO.getAdStatus());
  pstmt.setDate(6, adVO.getAdStartline());
  pstmt.setDate(7, adVO.getAdDeadline());
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
  public void update(AdVO adVO) {
  Connection con = null;
  PreparedStatement pstmt = null;

   try {

  con = ds.getConnection();
  pstmt = con.prepareStatement(UPDATE);

  pstmt.setInt(1, adVO.getDealerNo());
  pstmt.setDate(2, adVO.getAdOdDate());
  pstmt.setInt(3, adVO.getOdStatus());
  pstmt.setBytes(4, adVO.getAdPic());
  pstmt.setInt(5, adVO.getOdPrice());
  pstmt.setInt(6, adVO.getAdStatus());
  pstmt.setDate(7, adVO.getAdStartline());
  pstmt.setDate(8, adVO.getAdDeadline());
  pstmt.setInt(9, adVO.getAdNo());
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
  public void delete(Integer adNo) {
  Connection con = null;
  PreparedStatement pstmt = null;

   try {

  con = ds.getConnection();
  pstmt = con.prepareStatement(DELETE);

  pstmt.setInt(1, adNo);
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
  public AdVO findByPrimaryKey(Integer adNo) {
  Connection con = null;
  PreparedStatement pstmt = null;

  AdVO adVO = null;
  ResultSet rs = null;
   try {

  con = ds.getConnection();
  pstmt = con.prepareStatement(GET_ONE_STMT);

  pstmt.setInt(1, adNo);
			rs = pstmt.executeQuery();
			while (rs.next()) {
  adVO = new AdVO();
  adVO.setAdNo(rs.getInt("adNo"));
  adVO.setDealerNo(rs.getInt("dealerNo"));
  adVO.setAdOdDate(rs.getDate("adOdDate"));
  adVO.setOdStatus(rs.getInt("odStatus"));
  adVO.setAdPic(rs.getBytes("adPic"));
  adVO.setOdPrice(rs.getInt("odPrice"));
  adVO.setAdStatus(rs.getInt("adStatus"));
  adVO.setAdStartline(rs.getDate("adStartline"));
  adVO.setAdDeadline(rs.getDate("adDeadline"));
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
		return adVO;
}

  @Override
	public List<AdVO> getAll() {
  Connection con = null;
  PreparedStatement pstmt = null;

  AdVO adVO = null;
  ResultSet rs = null;
  List<AdVO> list = new ArrayList<AdVO>();
   try {

  con = ds.getConnection();
  pstmt = con.prepareStatement(GET_ALL_STMT);

			rs = pstmt.executeQuery();
			while (rs.next()) {
  adVO = new AdVO();
  adVO.setAdNo(rs.getInt("adNo"));
  adVO.setDealerNo(rs.getInt("dealerNo"));
  adVO.setAdOdDate(rs.getDate("adOdDate"));
  adVO.setOdStatus(rs.getInt("odStatus"));
  adVO.setAdPic(rs.getBytes("adPic"));
  adVO.setOdPrice(rs.getInt("odPrice"));
  adVO.setAdStatus(rs.getInt("adStatus"));
  adVO.setAdStartline(rs.getDate("adStartline"));
  adVO.setAdDeadline(rs.getDate("adDeadline"));
  list.add(adVO);
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
	public List<AdVO> likeByColumn(String columnName,String likeString) {
  Connection con = null;
  Statement pstmt = null;

  AdVO adVO = null;
  ResultSet rs = null;
  List<AdVO> list = new ArrayList<AdVO>();
  String likeSql="SELECT adNo,dealerNo,to_char(adOdDate ,'yyyy-mm-dd') adOdDate,odStatus,adPic,odPrice,adStatus,to_char(adStartline ,'yyyy-mm-dd') adStartline,to_char(adDeadline ,'yyyy-mm-dd') adDeadline FROM Ad where "+columnName+" like '"+likeString+"'";

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   try {

  con = ds.getConnection();
  pstmt = con.createStatement();

			rs = pstmt.executeQuery(likeSql);
			while (rs.next()) {
  adVO = new AdVO();
  adVO.setAdNo(rs.getInt("adNo"));
  adVO.setDealerNo(rs.getInt("dealerNo"));
  adVO.setAdOdDate(rs.getDate("adOdDate"));
  adVO.setOdStatus(rs.getInt("odStatus"));
  adVO.setAdPic(rs.getBytes("adPic"));
  adVO.setOdPrice(rs.getInt("odPrice"));
  adVO.setAdStatus(rs.getInt("adStatus"));
  adVO.setAdStartline(rs.getDate("adStartline"));
  adVO.setAdDeadline(rs.getDate("adDeadline"));
  list.add(adVO);
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
    public AdVO findByDealerNo(Integer dealerNo) {
  Connection con = null;
  PreparedStatement pstmt = null;

  AdVO adVO = null;
  ResultSet rs = null;
   try {

  con = ds.getConnection();
  String findByThisKey =
  "SELECT adNo,dealerNo,to_char(adOdDate ,'yyyy-mm-dd') adOdDate,odStatus,adPic,odPrice,adStatus,to_char(adStartline ,'yyyy-mm-dd') adStartline,to_char(adDeadline ,'yyyy-mm-dd') adDeadline FROM Ad where dealerNo= ?";

    pstmt = con.prepareStatement(findByThisKey);
  pstmt.setInt(1, dealerNo);
			rs = pstmt.executeQuery();
			while (rs.next()) {
  adVO = new AdVO();
  adVO.setAdNo(  rs.getInt("adNo"));
  adVO.setDealerNo(  rs.getInt("dealerNo"));
  adVO.setAdOdDate(  rs.getDate("adOdDate"));
  adVO.setOdStatus(  rs.getInt("odStatus"));
  adVO.setAdPic(  rs.getBytes("adPic"));
  adVO.setOdPrice(  rs.getInt("odPrice"));
  adVO.setAdStatus(  rs.getInt("adStatus"));
  adVO.setAdStartline(  rs.getDate("adStartline"));
  adVO.setAdDeadline(  rs.getDate("adDeadline"));
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
		return adVO;
}

    public List<AdVO> getAllByDealerNo(Integer dealerNo) {
  Connection con = null;
  PreparedStatement pstmt = null;

  AdVO adVO = null;
  List<AdVO> list = new ArrayList<AdVO>();
  ResultSet rs = null;
   try {

  con = ds.getConnection();
  String listByThisKey =
  "SELECT adNo,dealerNo,to_char(adOdDate ,'yyyy-mm-dd') adOdDate,odStatus,adPic,odPrice,adStatus,to_char(adStartline ,'yyyy-mm-dd') adStartline,to_char(adDeadline ,'yyyy-mm-dd') adDeadline FROM Ad where dealerNo= ? order by dealerNo";

   pstmt = con.prepareStatement(listByThisKey);
   pstmt.setInt(1, dealerNo);
		 rs = pstmt.executeQuery();
			while (rs.next()) {
  adVO = new AdVO();
  adVO.setAdNo(  rs.getInt("adNo"));
  adVO.setDealerNo(  rs.getInt("dealerNo"));
  adVO.setAdOdDate(  rs.getDate("adOdDate"));
  adVO.setOdStatus(  rs.getInt("odStatus"));
  adVO.setAdPic(  rs.getBytes("adPic"));
  adVO.setOdPrice(  rs.getInt("odPrice"));
  adVO.setAdStatus(  rs.getInt("adStatus"));
  adVO.setAdStartline(  rs.getDate("adStartline"));
  adVO.setAdDeadline(  rs.getDate("adDeadline"));
  list.add(adVO);
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

    public AdVO findByOdStatus(Integer odStatus) {
  Connection con = null;
  PreparedStatement pstmt = null;

  AdVO adVO = null;
  ResultSet rs = null;
   try {

  con = ds.getConnection();
  String findByThisKey =
  "SELECT adNo,dealerNo,to_char(adOdDate ,'yyyy-mm-dd') adOdDate,odStatus,adPic,odPrice,adStatus,to_char(adStartline ,'yyyy-mm-dd') adStartline,to_char(adDeadline ,'yyyy-mm-dd') adDeadline FROM Ad where odStatus= ?";

    pstmt = con.prepareStatement(findByThisKey);
  pstmt.setInt(1, odStatus);
			rs = pstmt.executeQuery();
			while (rs.next()) {
  adVO = new AdVO();
  adVO.setAdNo(  rs.getInt("adNo"));
  adVO.setDealerNo(  rs.getInt("dealerNo"));
  adVO.setAdOdDate(  rs.getDate("adOdDate"));
  adVO.setOdStatus(  rs.getInt("odStatus"));
  adVO.setAdPic(  rs.getBytes("adPic"));
  adVO.setOdPrice(  rs.getInt("odPrice"));
  adVO.setAdStatus(  rs.getInt("adStatus"));
  adVO.setAdStartline(  rs.getDate("adStartline"));
  adVO.setAdDeadline(  rs.getDate("adDeadline"));
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
		return adVO;
}

    public List<AdVO> getAllByOdStatus(Integer odStatus) {
  Connection con = null;
  PreparedStatement pstmt = null;

  AdVO adVO = null;
  List<AdVO> list = new ArrayList<AdVO>();
  ResultSet rs = null;
   try {

  con = ds.getConnection();
  String listByThisKey =
  "SELECT adNo,dealerNo,to_char(adOdDate ,'yyyy-mm-dd') adOdDate,odStatus,adPic,odPrice,adStatus,to_char(adStartline ,'yyyy-mm-dd') adStartline,to_char(adDeadline ,'yyyy-mm-dd') adDeadline FROM Ad where odStatus= ? order by odStatus";

   pstmt = con.prepareStatement(listByThisKey);
   pstmt.setInt(1, odStatus);
		 rs = pstmt.executeQuery();
			while (rs.next()) {
  adVO = new AdVO();
  adVO.setAdNo(  rs.getInt("adNo"));
  adVO.setDealerNo(  rs.getInt("dealerNo"));
  adVO.setAdOdDate(  rs.getDate("adOdDate"));
  adVO.setOdStatus(  rs.getInt("odStatus"));
  adVO.setAdPic(  rs.getBytes("adPic"));
  adVO.setOdPrice(  rs.getInt("odPrice"));
  adVO.setAdStatus(  rs.getInt("adStatus"));
  adVO.setAdStartline(  rs.getDate("adStartline"));
  adVO.setAdDeadline(  rs.getDate("adDeadline"));
  list.add(adVO);
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

    public AdVO findByOdPrice(Integer odPrice) {
  Connection con = null;
  PreparedStatement pstmt = null;

  AdVO adVO = null;
  ResultSet rs = null;
   try {

  con = ds.getConnection();
  String findByThisKey =
  "SELECT adNo,dealerNo,to_char(adOdDate ,'yyyy-mm-dd') adOdDate,odStatus,adPic,odPrice,adStatus,to_char(adStartline ,'yyyy-mm-dd') adStartline,to_char(adDeadline ,'yyyy-mm-dd') adDeadline FROM Ad where odPrice= ?";

    pstmt = con.prepareStatement(findByThisKey);
  pstmt.setInt(1, odPrice);
			rs = pstmt.executeQuery();
			while (rs.next()) {
  adVO = new AdVO();
  adVO.setAdNo(  rs.getInt("adNo"));
  adVO.setDealerNo(  rs.getInt("dealerNo"));
  adVO.setAdOdDate(  rs.getDate("adOdDate"));
  adVO.setOdStatus(  rs.getInt("odStatus"));
  adVO.setAdPic(  rs.getBytes("adPic"));
  adVO.setOdPrice(  rs.getInt("odPrice"));
  adVO.setAdStatus(  rs.getInt("adStatus"));
  adVO.setAdStartline(  rs.getDate("adStartline"));
  adVO.setAdDeadline(  rs.getDate("adDeadline"));
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
		return adVO;
}

    public List<AdVO> getAllByOdPrice(Integer odPrice) {
  Connection con = null;
  PreparedStatement pstmt = null;

  AdVO adVO = null;
  List<AdVO> list = new ArrayList<AdVO>();
  ResultSet rs = null;
   try {

  con = ds.getConnection();
  String listByThisKey =
  "SELECT adNo,dealerNo,to_char(adOdDate ,'yyyy-mm-dd') adOdDate,odStatus,adPic,odPrice,adStatus,to_char(adStartline ,'yyyy-mm-dd') adStartline,to_char(adDeadline ,'yyyy-mm-dd') adDeadline FROM Ad where odPrice= ? order by odPrice";

   pstmt = con.prepareStatement(listByThisKey);
   pstmt.setInt(1, odPrice);
		 rs = pstmt.executeQuery();
			while (rs.next()) {
  adVO = new AdVO();
  adVO.setAdNo(  rs.getInt("adNo"));
  adVO.setDealerNo(  rs.getInt("dealerNo"));
  adVO.setAdOdDate(  rs.getDate("adOdDate"));
  adVO.setOdStatus(  rs.getInt("odStatus"));
  adVO.setAdPic(  rs.getBytes("adPic"));
  adVO.setOdPrice(  rs.getInt("odPrice"));
  adVO.setAdStatus(  rs.getInt("adStatus"));
  adVO.setAdStartline(  rs.getDate("adStartline"));
  adVO.setAdDeadline(  rs.getDate("adDeadline"));
  list.add(adVO);
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

    public AdVO findByAdStatus(Integer adStatus) {
  Connection con = null;
  PreparedStatement pstmt = null;

  AdVO adVO = null;
  ResultSet rs = null;
   try {

  con = ds.getConnection();
  String findByThisKey =
  "SELECT adNo,dealerNo,to_char(adOdDate ,'yyyy-mm-dd') adOdDate,odStatus,adPic,odPrice,adStatus,to_char(adStartline ,'yyyy-mm-dd') adStartline,to_char(adDeadline ,'yyyy-mm-dd') adDeadline FROM Ad where adStatus= ?";

    pstmt = con.prepareStatement(findByThisKey);
  pstmt.setInt(1, adStatus);
			rs = pstmt.executeQuery();
			while (rs.next()) {
  adVO = new AdVO();
  adVO.setAdNo(  rs.getInt("adNo"));
  adVO.setDealerNo(  rs.getInt("dealerNo"));
  adVO.setAdOdDate(  rs.getDate("adOdDate"));
  adVO.setOdStatus(  rs.getInt("odStatus"));
  adVO.setAdPic(  rs.getBytes("adPic"));
  adVO.setOdPrice(  rs.getInt("odPrice"));
  adVO.setAdStatus(  rs.getInt("adStatus"));
  adVO.setAdStartline(  rs.getDate("adStartline"));
  adVO.setAdDeadline(  rs.getDate("adDeadline"));
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
		return adVO;
}

    public List<AdVO> getAllByAdStatus(Integer adStatus) {
  Connection con = null;
  PreparedStatement pstmt = null;

  AdVO adVO = null;
  List<AdVO> list = new ArrayList<AdVO>();
  ResultSet rs = null;
   try {

  con = ds.getConnection();
  String listByThisKey =
  "SELECT adNo,dealerNo,to_char(adOdDate ,'yyyy-mm-dd') adOdDate,odStatus,adPic,odPrice,adStatus,to_char(adStartline ,'yyyy-mm-dd') adStartline,to_char(adDeadline ,'yyyy-mm-dd') adDeadline FROM Ad where adStatus= ? order by adStatus";

   pstmt = con.prepareStatement(listByThisKey);
   pstmt.setInt(1, adStatus);
		 rs = pstmt.executeQuery();
			while (rs.next()) {
  adVO = new AdVO();
  adVO.setAdNo(  rs.getInt("adNo"));
  adVO.setDealerNo(  rs.getInt("dealerNo"));
  adVO.setAdOdDate(  rs.getDate("adOdDate"));
  adVO.setOdStatus(  rs.getInt("odStatus"));
  adVO.setAdPic(  rs.getBytes("adPic"));
  adVO.setOdPrice(  rs.getInt("odPrice"));
  adVO.setAdStatus(  rs.getInt("adStatus"));
  adVO.setAdStartline(  rs.getDate("adStartline"));
  adVO.setAdDeadline(  rs.getDate("adDeadline"));
  list.add(adVO);
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