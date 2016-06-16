package tool.cart.model;

public class OrderCartVO {
	private Integer hostelOrderNo;
	private Integer roomTypeNo;
	private Integer roomQty;
	private Integer totalPrice;
	private String checkInDate;
	private String checkOutDate;
	private String roomTypeName;
	
	public Integer getHostelOrderNo() {
		return hostelOrderNo;
	}
	public void setHostelOrderNo(Integer hostelOrderNo) {
		this.hostelOrderNo = hostelOrderNo;
	}
	public String getRoomTypeName() {
		return roomTypeName;
	}
	public void setRoomTypeName(String roomTypeName) {
		this.roomTypeName = roomTypeName;
	}
	public Integer getRoomTypeNo() {
		return roomTypeNo;
	}
	public void setRoomTypeNo(Integer roomTypeNo) {
		this.roomTypeNo = roomTypeNo;
	}
	public Integer getRoomQty() {
		return roomQty;
	}
	public void setRoomQty(Integer roomQty) {
		this.roomQty = roomQty;
	}
	public Integer getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getCheckInDate() {
		return checkInDate;
	}
	public void setCheckInDate(String checkInDate) {
		this.checkInDate = checkInDate;
	}
	public String getCheckOutDate() {
		return checkOutDate;
	}
	public void setCheckOutDate(String checkOutDate) {
		this.checkOutDate = checkOutDate;
	}
	
	
	
	
}
