package com.cart.model;

import java.sql.Date;
import java.sql.Timestamp;

public class RoomTypeCartVO implements java.io.Serializable {

	// declare private variable
	private Integer roomTypeNo;	//�Ы��s��
	private String roomTypeName;	//�Ы��W��
	private Integer roomQuantity;	//�Ы��ƶq
	private Integer roomTypePrice;	//����
	private Timestamp checkInDate;
	private Timestamp checkOutDate;
	
	// declare public getter method
	
	
	
	public Integer getRoomTypeNo() {
		return roomTypeNo;
	}

	public Timestamp getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(Timestamp checkInDate) {
		this.checkInDate = checkInDate;
	}

	public Timestamp getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(Timestamp checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	public String getRoomTypeName() {
		return roomTypeName;
	}

	public void setRoomTypeName(String roomTypeName) {
		this.roomTypeName = roomTypeName;
	}

	public Integer getRoomTypePrice() {
		return roomTypePrice;
	}

	public void setRoomTypePrice(Integer roomTypePrice) {
		this.roomTypePrice = roomTypePrice;
	}

	public Integer getRoomQuantity() {
		return roomQuantity;
	}


	public void setRoomTypeNo(Integer roomTypeNo) {
		this.roomTypeNo = roomTypeNo;
	}

	public void setRoomQuantity(Integer roomQuantity) {
		this.roomQuantity = roomQuantity;
	}


}
