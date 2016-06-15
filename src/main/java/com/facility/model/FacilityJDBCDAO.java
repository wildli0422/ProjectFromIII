package com.facility.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FacilityJDBCDAO implements FacilityDAO_interface{
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "ZA105G3";
	String passwd = "ZA105G3";
	
		private static final String INSERT_STMT = 
			"insert into facility values (facility_seq.nextval,?,?,?,?,?,?,?,?,?,?)";
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
				Class.forName(driver);
				con = DriverManager.getConnection(url,userid,passwd);
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
				
			} catch (ClassNotFoundException e) {
				throw new RuntimeException("Couldn't load database driver. "
						+ e.getMessage());
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
				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
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
		}//update--------------------------------------------
		
		@Override
		public void delete(Integer facilityNo) {
			// TODO Auto-generated method stub
			Connection con = null;
			PreparedStatement pstmt = null;
			
			try{
				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(DELETE);
				
				pstmt.setInt(1, facilityNo);
				
				pstmt.executeUpdate();
				
			}catch (ClassNotFoundException e) {
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
			
		}//delete------------------------------------------
		
		@Override
		public FacilityVO findByPrimaryKey(Integer facilityNo) {
			
			FacilityVO facilityVO =null;
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			try{
				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
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
				
			}catch (ClassNotFoundException e) {
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
				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
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
				
			}catch (ClassNotFoundException e) {
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

		public static void main(String [] args){
			
			FacilityJDBCDAO dao=new FacilityJDBCDAO();
			//insert
			FacilityVO facilityVO1 =new FacilityVO();
			facilityVO1.setTelevision(0);
			facilityVO1.setAirConditioning(0);
			facilityVO1.setBathroom(0);
			facilityVO1.setKitchen(0);
			facilityVO1.setParking(0);
			facilityVO1.setPet(0);
			facilityVO1.setRoomBedService(0);
			facilityVO1.setRoomPhone(0);
			facilityVO1.setToiletries(0);
			facilityVO1.setWifi(0);
			dao.insert(facilityVO1);
			
			//update
			FacilityVO facilityVO2=new FacilityVO();
			facilityVO2.setFacilityNo(4001);
			facilityVO2.setTelevision(0);
			facilityVO2.setAirConditioning(0);
			facilityVO2.setBathroom(0);
			facilityVO2.setKitchen(0);
			facilityVO2.setParking(0);
			facilityVO2.setPet(0);
			facilityVO2.setRoomBedService(0);
			facilityVO2.setRoomPhone(0);
			facilityVO2.setToiletries(0);
			facilityVO2.setWifi(0);
			dao.update(facilityVO2);
			
			//delete
//			dao.delete(4001);
			System.out.println("first: ");
			FacilityVO facilityVO3=dao.findByPrimaryKey(4001);
			System.out.print(facilityVO3.getFacilityNo()+",");
			System.out.print(facilityVO3.getTelevision()+",");
			System.out.print(facilityVO3.getAirConditioning()+",");
			System.out.print(facilityVO3.getBathroom()+",");
			System.out.print(facilityVO3.getKitchen()+",");
			System.out.print(facilityVO3.getParking()+",");
			System.out.print(facilityVO3.getPet()+",");
			System.out.print(facilityVO3.getRoomPhone()+",");
			System.out.print(facilityVO3.getRoomBedService()+",");
			System.out.print(facilityVO3.getWifi()+",");
			System.out.println(facilityVO3.getToiletries()+",");
			
			System.out.println("list");
			List<FacilityVO> list =dao.getAll();
			for(FacilityVO facility:list){
				System.out.print(facility.getFacilityNo()+",");
				System.out.print(facility.getTelevision()+",");
				System.out.print(facility.getAirConditioning()+",");
				System.out.print(facility.getBathroom()+",");
				System.out.print(facility.getKitchen()+",");
				System.out.print(facility.getParking()+",");
				System.out.print(facility.getPet()+",");
				System.out.print(facility.getRoomPhone()+",");
				System.out.print(facility.getRoomBedService()+",");
				System.out.print(facility.getWifi()+",");
				System.out.println(facility.getToiletries()+",");
			}
		}
}
