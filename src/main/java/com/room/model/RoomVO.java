package com.room.model;

public class RoomVO {
	private Integer roomNo;
	private Integer hostelNo;
	private Integer roomTypeNo;
	private Integer hostelOrderDetailNo;
	private String roomState;
	
	
	public Integer getHostelOrderDetailNo() {
		return hostelOrderDetailNo;
	}
	public void setHostelOrderDetailNo(Integer hostelOrderDetailNo) {
		this.hostelOrderDetailNo = hostelOrderDetailNo;
	}
	public Integer getRoomNo() {
		return roomNo;
	}
	public void setRoomNo(Integer roomNo) {
		this.roomNo = roomNo;
	}
	public Integer getHostelNo() {
		return hostelNo;
	}
	public void setHostelNo(Integer hostelNo) {
		this.hostelNo = hostelNo;
	}
	public Integer getRoomTypeNo() {
		return roomTypeNo;
	}
	public void setRoomTypeNo(Integer roomTypeNo) {
		this.roomTypeNo = roomTypeNo;
	}
	public String getRoomState() {
		return roomState;
	}
	public void setRoomState(String roomState) {
		this.roomState = roomState;
	}
	
	
}
