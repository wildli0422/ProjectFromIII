/*本程式是使用Interface產生器自動產生 copyRight:wildli0422@gmail.com 我愛一條柴*/
package com.hostelOrderDetail.model;
import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.Timestamp;
import java.util.*;

public interface HostelOrderDetailDAO_interface {

    public void insert(HostelOrderDetailVO hostelOrderDetailVO);
    public void update(HostelOrderDetailVO hostelOrderDetailVO);
	public List<HostelOrderDetailVO> likeByColumn(String columnName,String likeString);
    public void delete(Integer hostelOrderDetailNo);
    public HostelOrderDetailVO findByPrimaryKey(Integer hostelOrderDetailNo);
    public List<HostelOrderDetailVO> getAll();
    public void insertDetail(HostelOrderDetailVO hostelOrderDetailVO,Connection con);

//from html input start to do some onDemand method 
    public HostelOrderDetailVO findByHostelOrderNo(Integer hostelOrderNo);
    public List<HostelOrderDetailVO> getAllByHostelOrderNo(Integer hostelOrderNo);
    public HostelOrderDetailVO findByRoomTypeNo(Integer roomTypeNo);
    public List<HostelOrderDetailVO> getAllByRoomTypeNo(Integer roomTypeNo);
    public HostelOrderDetailVO findByRoomQuantity(Integer roomQuantity);
    public List<HostelOrderDetailVO> getAllByRoomQuantity(Integer roomQuantity);
    public HostelOrderDetailVO findByCheckInDate(java.sql.Timestamp checkInDate);
    public List<HostelOrderDetailVO> getAllByCheckInDate(java.sql.Timestamp checkInDate);
    public HostelOrderDetailVO findByCheckOutDate(java.sql.Timestamp checkOutDate);
    public List<HostelOrderDetailVO> getAllByCheckOutDate(java.sql.Timestamp checkOutDate);
    public HostelOrderDetailVO findByTotalPrice(Integer totalPrice);
    public List<HostelOrderDetailVO> getAllByTotalPrice(Integer totalPrice);

}