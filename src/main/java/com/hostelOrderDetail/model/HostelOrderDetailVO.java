/*本程式是使用VO產生器自動產生 copyRight:wildli0422@gmail.com 我愛一條柴*/
package com.hostelOrderDetail.model;
import java.sql.Timestamp;
import java.sql.Timestamp;
public class HostelOrderDetailVO implements java.io.Serializable{
  public HostelOrderDetailVO(){}
//declare private variable 
  private Integer hostelOrderDetailNo;
  private Integer hostelOrderNo;
  private Integer roomTypeNo;
  private Integer roomQuantity;
  private java.sql.Timestamp checkInDate;
  private java.sql.Timestamp checkOutDate;
  private Integer totalPrice;
//declare public getter method  
  public Integer getHostelOrderDetailNo() {
    return hostelOrderDetailNo;
  }
  public Integer getHostelOrderNo() {
    return hostelOrderNo;
  }
  public Integer getRoomTypeNo() {
    return roomTypeNo;
  }
  public Integer getRoomQuantity() {
    return roomQuantity;
  }
  public java.sql.Timestamp getCheckInDate() {
    return checkInDate;
  }
  public java.sql.Timestamp getCheckOutDate() {
    return checkOutDate;
  }
  public Integer getTotalPrice() {
    return totalPrice;
  }
//declare public setter method  
  public void setHostelOrderDetailNo(Integer hostelOrderDetailNo) {
    this.hostelOrderDetailNo = hostelOrderDetailNo;
  }
  public void setHostelOrderNo(Integer hostelOrderNo) {
    this.hostelOrderNo = hostelOrderNo;
  }
  public void setRoomTypeNo(Integer roomTypeNo) {
    this.roomTypeNo = roomTypeNo;
  }
  public void setRoomQuantity(Integer roomQuantity) {
    this.roomQuantity = roomQuantity;
  }
  public void setCheckInDate(java.sql.Timestamp checkInDate) {
    this.checkInDate = checkInDate;
  }
  public void setCheckOutDate(java.sql.Timestamp checkOutDate) {
    this.checkOutDate = checkOutDate;
  }
  public void setTotalPrice(Integer totalPrice) {
    this.totalPrice = totalPrice;
  }

}