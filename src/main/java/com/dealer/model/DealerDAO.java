package com.dealer.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.sql.DataSource;
import javax.naming.Context;
import javax.naming.InitialContext;

import com.dealer.CompositeQuery_Dealer;
import com.hostel.model.HostelVO;



public class DealerDAO implements DealerDAO_interface{
	
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
			"insert into dealer values (dealerNo_seq.nextval,?,?,?,?,?,?,?,?)";
		private static final String GET_ALL_STMT = 
			"select * from dealer order by dealerNo";
		private static final String GET_ONE_STMT = 
			"select * from dealer where dealerNo=?";
		private static final String DELETE = 
			"DELETE FROM dealer where dealerNo = ?";
		private static final String UPDATE = 
			"UPDATE dealer set dealerPassword=?,dealerName=?,dealerSex=?,dealerAddress=?,"
			+ "dealerPhone=?,dealerMail=?,dealerState=?,dealerAccount=? where dealerNo = ?";
		private static final String GET_PSW_STMT=
				"select dealerPassword from dealer where dealerMail=?";
		private static final String GET_NO_ACCOUNT_STMT=
				"select dealerNo from dealer where dealerMail=?";
		private static final String GET_HOSTEL_STMT=
				"select * from hostel where dealerNo=? ";
		
		///backend///
		private static final String Count_STATE0 = 
				"select count(dealerNo) from dealer where dealerState = 0";
		
		
		
		@Override
		public void insert(DealerVO dealerVO) {
			
			Connection con=null;
			PreparedStatement pstmt=null;
			
			try{
				con =ds.getConnection();
				pstmt =con.prepareStatement(INSERT_STMT);
				
				pstmt.setString(1, dealerVO.getDealerPassword());
				pstmt.setString(2, dealerVO.getDealerName());
				pstmt.setString(3, dealerVO.getDealerSex() );
				pstmt.setString(4,dealerVO.getDealerAddress() );
				pstmt.setString(5,dealerVO.getDealerPhone());
				pstmt.setString(6,dealerVO.getDealerMail() );
				pstmt.setInt(7,dealerVO.getDealerState() );
				pstmt.setString(8,dealerVO.getDealerAccount() );
				
				pstmt.executeUpdate();
				
				
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
		//insert----------------------------------------------
		
		@Override
		public void update(DealerVO dealerVO) {

			Connection con=null;
			PreparedStatement pstmt=null;
			
			
			try{
				
				con=ds.getConnection();
				pstmt =con.prepareStatement(UPDATE);
				
				pstmt.setString(1, dealerVO.getDealerPassword());
				pstmt.setString(2, dealerVO.getDealerName());
				pstmt.setString(3, dealerVO.getDealerSex() );
				pstmt.setString(4,dealerVO.getDealerAddress() );
				pstmt.setString(5,dealerVO.getDealerPhone());
				pstmt.setString(6,dealerVO.getDealerMail() );
				pstmt.setInt(7,dealerVO.getDealerState() );
				pstmt.setString(8,dealerVO.getDealerAccount() );
				pstmt.setInt(9, dealerVO.getDealerNo());
				
				pstmt.executeUpdate();
				
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
		}//update-------------------------------------------
		
		@Override
		public void delete(Integer dealerNo) {
			Connection con=null;
			PreparedStatement pstmt=null;
			
			try{
				con=ds.getConnection();
				pstmt =con.prepareStatement(DELETE);
				
				pstmt.setInt(1, dealerNo);
				pstmt.executeUpdate();
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
		}//delete----------------------------------------
		
		@Override
		public DealerVO findByPrimaryKey(Integer dealerNo) {
			
			DealerVO dealerVO=null;
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			
			try{
				con=ds.getConnection();
				pstmt = con.prepareStatement(GET_ONE_STMT);
				
				pstmt.setInt(1, dealerNo);
				
				rs=pstmt.executeQuery();
				
				while(rs.next()){
					dealerVO =new DealerVO();
					dealerVO.setDealerNo(rs.getInt("dealerNo"));
					dealerVO.setDealerPassword(rs.getString("dealerPassword"));
					dealerVO.setDealerName(rs.getString("dealerName"));
					dealerVO.setDealerSex(rs.getString("dealerSex"));
					dealerVO.setDealerAddress(rs.getString("dealerAddress"));
					dealerVO.setDealerPhone(rs.getString("dealerPhone"));
					dealerVO.setDealerMail(rs.getString("dealerMail"));
					dealerVO.setDealerState(rs.getInt("dealerState"));
					dealerVO.setDealerAccount(rs.getString("dealerAccount"));
				}
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
			return dealerVO;
		}
		//select------------------------------------------
		@Override
		public List<DealerVO> getAll() {
			
			List<DealerVO> list =new ArrayList<DealerVO>();
			DealerVO dealerVO =new DealerVO();
			
			Connection con=null;
			PreparedStatement pstmt=null;
			ResultSet rs=null;
			
			
			try{
				
				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_ALL_STMT);
				rs = pstmt.executeQuery();
				
				while(rs.next()){
					
					dealerVO =new DealerVO();
					dealerVO.setDealerNo(rs.getInt("dealerNo"));
					dealerVO.setDealerPassword(rs.getString("dealerPassword"));
					dealerVO.setDealerName(rs.getString("dealerName"));
					dealerVO.setDealerSex(rs.getString("dealerSex"));
					dealerVO.setDealerAddress(rs.getString("dealerAddress"));
					dealerVO.setDealerPhone(rs.getString("dealerPhone"));
					dealerVO.setDealerMail(rs.getString("dealerMail"));
					dealerVO.setDealerState(rs.getInt("dealerState"));
					dealerVO.setDealerAccount(rs.getString("dealerAccount"));
					list.add(dealerVO);
				}//while
				
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
		@Override
		public String findDealerPassword(String dealerMail) {
			
			Connection con=null;
			PreparedStatement pstmt=null;
			ResultSet rs=null;
			
			String password=null;
			
			try{
				
				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_PSW_STMT);
				pstmt.setString(1, dealerMail);
				rs = pstmt.executeQuery();
				
				while(rs.next()){
					
					password=rs.getString("dealerPassword");

				}//while
				
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
			return password;
		}
		
		@Override
		public Integer findDealerNo(String dealerMail) {
			
			Connection con=null;
			PreparedStatement pstmt=null;
			ResultSet rs=null;
			
			Integer dealerNo=null;
			
			try{
				
				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_NO_ACCOUNT_STMT);
				pstmt.setString(1, dealerMail);
				rs = pstmt.executeQuery();
				
				while(rs.next()){
					
					dealerNo=rs.getInt("dealerNo");

				}//while
				
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
			return dealerNo;
		}//find dealer No
		
		@Override
		public HostelVO findHostelByDealerNo(Integer dealerNo){
			HostelVO hostelVO=null;
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			try {
				con=ds.getConnection();
				pstmt = con.prepareStatement(GET_HOSTEL_STMT);
				
				pstmt.setInt(1, dealerNo);
				
				rs=pstmt.executeQuery();
				
				while(rs.next()){
					hostelVO=new HostelVO();
					hostelVO.setHostelNo(rs.getInt("hostelNo"));
					hostelVO.setDealerNo(rs.getInt("dealerNo"));
					hostelVO.setHostelName(rs.getString("hostelName"));
					hostelVO.setHostelPhone(rs.getString("hostelPhone"));
					hostelVO.setHostelAddress(rs.getString("hostelAddress"));
					hostelVO.setHostelWebPages(rs.getString("hostelWebPages")) ;
					hostelVO.setHostelState(rs.getInt("hostelState")) ;
					hostelVO.setHostelVerification(rs.getInt("hostelVerification"));
					hostelVO.setHostelLat(rs.getDouble("hostelLat"));
					hostelVO.setHostelLon(rs.getDouble("hostelLon"));
					hostelVO.setHostelPicture(rs.getBytes("hostelPicture"));
					hostelVO.setDealerVerify(rs.getBytes("dealerVerify"));
					hostelVO.setHostelCautions(rs.getString("hostelCautions"));
					hostelVO.setHostelContent(rs.getString("hostelContent"));
					
				}
				
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
			return hostelVO;
		}
		
		
		///backend///
		public Integer countState0() {
			Connection con=null;
			PreparedStatement pstmt=null;
			ResultSet rs=null;
			int count = 0;
			
			try{
				
				con = ds.getConnection();
				pstmt = con.prepareStatement(Count_STATE0);
				rs = pstmt.executeQuery();
				while(rs.next()){
					count = rs.getInt("COUNT(DEALERNO)");
				}//while		
				
				
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
			
			
			
			
			return count;
		}
		
		
		public List<DealerVO> getAll(Map<String, String[]> map) {
			List<DealerVO> list = new ArrayList<DealerVO>();
			DealerVO dealerVO = null;
			
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			try {
				con = ds.getConnection();
				String SQL = "select * from dealer"
						+ CompositeQuery_Dealer.getWhere(map) +" order by dealerNo";
				
				pstmt = con.prepareStatement(SQL);
				System.out.println("dealerSQL = " + SQL);
				rs = pstmt.executeQuery();
				
				while (rs.next()) {
					dealerVO = new DealerVO();
					dealerVO.setDealerNo(rs.getInt("dealerNo"));
					dealerVO.setDealerName(rs.getString("dealerName"));
					dealerVO.setDealerMail(rs.getString("dealerMail"));
					dealerVO.setDealerSex(rs.getString("dealerSex"));
					dealerVO.setDealerAddress(rs.getString("dealerAddress"));
					dealerVO.setDealerPhone(rs.getString("dealerPhone"));
					dealerVO.setDealerState(rs.getInt("dealerState"));
					list.add(dealerVO);
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
		
		
		@Override
		public List<DealerVO> getState0() {
			List<DealerVO> list = new ArrayList<DealerVO>();
			DealerVO dealerVO = null;
			String[] dS= {"0"};
			Map<String, String[]> map = new TreeMap<>();
			map.put("dealerState", dS);
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			try {
				con = ds.getConnection();
				String SQL = "select * from dealer"
						+ CompositeQuery_Dealer.getWhere(map) +" order by dealerNo";
				
				pstmt = con.prepareStatement(SQL);
				rs = pstmt.executeQuery();
				
				while (rs.next()) {
					dealerVO = new DealerVO();
					dealerVO.setDealerNo(rs.getInt("dealerNo"));
					dealerVO.setDealerName(rs.getString("dealerName"));
					dealerVO.setDealerMail(rs.getString("dealerMail"));
					
					list.add(dealerVO);
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
