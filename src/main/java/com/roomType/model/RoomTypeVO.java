package com.roomType.model;

public class RoomTypeVO {
	private Integer roomTypeNo;
	private Integer hostelNo;
	private Integer facilityNo;
	private String roomTypeName;
	private Integer roomTypeContain;
	private Integer roomTypePrice;
	private String roomTypeContent;
	private byte[] roomTypePicture;
	
	public byte[] getRoomTypePicture() {
		return roomTypePicture;
	}
	public void setRoomTypePicture(byte[] roomTypePicture) {
		this.roomTypePicture = roomTypePicture;
	}
	public Integer getRoomTypeNo() {
		return roomTypeNo;
	}
	public void setRoomTypeNo(Integer roomTypeNo) {
		this.roomTypeNo = roomTypeNo;
	}
	public Integer getHostelNo() {
		return hostelNo;
	}
	public void setHostelNo(Integer hostelNo) {
		this.hostelNo = hostelNo;
	}
	public Integer getFacilityNo() {
		return facilityNo;
	}
	public void setFacilityNo(Integer facilityNo) {
		this.facilityNo = facilityNo;
	}
	public String getRoomTypeName() {
		return roomTypeName;
	}
	public void setRoomTypeName(String roomTypeName) {
		this.roomTypeName = roomTypeName;
	}
	public Integer getRoomTypeContain() {
		return roomTypeContain;
	}
	public void setRoomTypeContain(Integer roomTypeContain) {
		this.roomTypeContain = roomTypeContain;
	}
	public Integer getRoomTypePrice() {
		return roomTypePrice;
	}
	public void setRoomTypePrice(Integer roomTypePrice) {
		this.roomTypePrice = roomTypePrice;
	}
	public String getRoomTypeContent() {
		return roomTypeContent;
	}
	public void setRoomTypeContent(String roomTypeContent) {
		this.roomTypeContent = roomTypeContent;
	}
	
	
}
