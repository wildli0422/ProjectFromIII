package com.facility.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class FacilityDAO implements FacilityDAO_interface{
	
	private static DataSource ds=null;
	
	static{
		try {
			Context ctx =new InitialContext();
			ds=(DataSource)ctx.lookup("java:comp/env/jdbc/ZA105G3");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static final String INSERT_STMT = 
			"insert into facility values (facilityNo_seq.nextval,?,?,?,?,?,?,?,?,?,?)";
		private static final String GET_ALL_STMT = 
			"select * from facility order by facilityNo";
		private static final String GET_ONE_STMT = 
			"select * from facility where facilityNo=?";
		private static final String DELETE = 
			"DELETE FROM facility where facilityNo = ?";
		private static final String UPDATE = 
			"UPDATE facility set television=?,wifi=?,kitchen=?,"
			+ "parking=?,bathroom=?,airConditioning=?,pet=?,"
			+ "toiletries=?,roomPhone=?,roomBedService=? where facilityNo = ?";
		
		@Override
		public void insert(FacilityVO facilityVO) {
			
			Connection con=null;
			PreparedStatement pstmt=null;
			
			try {
				con=ds.getConnection();
				pstmt= con.prepareStatement(INSERT_STMT);
				
				pstmt.setInt(1, facilityVO.getTelevision());
				pstmt.setInt(2, facilityVO.getWifi());
				pstmt.setInt(3, facilityVO.getKitchen());
				pstmt.setInt(4, facilityVO.getParking());
				pstmt.setInt(5, facilityVO.getBathroom());
				pstmt.setInt(6, facilityVO.getAirConditioning());
				pstmt.setInt(7, facilityVO.getPet());
				pstmt.setInt(8, facilityVO.getToiletries());
				pstmt.setInt(9, facilityVO.getRoomPhone());
				pstmt.setInt(10, facilityVO.getRoomBedService());
				
				pstmt.executeUpdate();
				
			}catch (SQLException se) {
				throw new RuntimeException("A database error occured. "
						+ se.getMessage());
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
		}//insert-----------------------------------------
		
		@Override
		public void update(FacilityVO facilityVO) {
			// TODO Auto-generated method stub
			Connection con = null;
			PreparedStatement pstmt = null;
			
			
			try {
				con=ds.getConnection();
				pstmt = con.prepareStatement(UPDATE);
				
				pstmt.setInt(1, facilityVO.getTelevision());
				pstmt.setInt(2, facilityVO.getWifi());
				pstmt.setInt(3, facilityVO.getKitchen());
				pstmt.setInt(4, facilityVO.getParking());
				pstmt.setInt(5, facilityVO.getBathroom());
				pstmt.setInt(6, facilityVO.getAirConditioning());
				pstmt.setInt(7, facilityVO.getPet());
				pstmt.setInt(8, facilityVO.getToiletries());
				pstmt.setInt(9, facilityVO.getRoomPhone());
				pstmt.setInt(10, facilityVO.getRoomBedService());
				pstmt.setInt(11, facilityVO.getFacilityNo());
				
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
		}//update--------------------------------------------
		
		@Override
		public void delete(Integer facilityNo) {
			// TODO Auto-generated method stub
			Connection con = null;
			PreparedStatement pstmt = null;
			
			try{
				con=ds.getConnection();
				pstmt = con.prepareStatement(DELETE);
				
				pstmt.setInt(1, facilityNo);
				
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
			
		}//delete------------------------------------------
		
		@Override
		public FacilityVO findByPrimaryKey(Integer facilityNo) {
			
			FacilityVO facilityVO =null;
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			try{
				con=ds.getConnection();
				pstmt = con.prepareStatement(GET_ONE_STMT);
				
				pstmt.setInt(1, facilityNo);
				
				rs=pstmt.executeQuery();
				
				while(rs.next()){
					facilityVO =new FacilityVO();
					facilityVO.setFacilityNo(rs.getInt("facilityNo"));
					facilityVO.setTelevision(rs.getInt("television"));
					facilityVO.setWifi(rs.getInt("wifi"));
					facilityVO.setKitchen(rs.getInt("kitchen"));
					facilityVO.setParking(rs.getInt("parking"));
					facilityVO.setBathroom(rs.getInt("bathroom"));
					facilityVO.setAirConditioning(rs.getInt("airConditioning"));
					facilityVO.setPet(rs.getInt("pet"));
					facilityVO.setToiletries(rs.getInt("toiletries"));
					facilityVO.setRoomPhone(rs.getInt("roomPhone"));
					facilityVO.setRoomBedService(rs.getInt("roomBedService"));
					
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

			return facilityVO;
		}
		
		@Override
		public List<FacilityVO> getAll() {
			List<FacilityVO> list =new ArrayList<FacilityVO>();
			FacilityVO facilityVO=null;

			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			try{
				con=ds.getConnection();
				pstmt = con.prepareStatement(GET_ALL_STMT);
				rs = pstmt.executeQuery();
				
				while(rs.next()){
					facilityVO =new FacilityVO();
					facilityVO.setFacilityNo(rs.getInt("facilityNo"));
					facilityVO.setTelevision(rs.getInt("television"));
					facilityVO.setWifi(rs.getInt("wifi"));
					facilityVO.setKitchen(rs.getInt("kitchen"));
					facilityVO.setParking(rs.getInt("parking"));
					facilityVO.setBathroom(rs.getInt("bathroom"));
					facilityVO.setAirConditioning(rs.getInt("airConditioning"));
					facilityVO.setPet(rs.getInt("pet"));
					facilityVO.setToiletries(rs.getInt("toiletries"));
					facilityVO.setRoomPhone(rs.getInt("roomPhone"));
					facilityVO.setRoomBedService(rs.getInt("roomBedService"));
					list.add(facilityVO);
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
			
			return list;
		}
}
