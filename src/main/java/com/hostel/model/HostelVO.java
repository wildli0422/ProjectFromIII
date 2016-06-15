package com.hostel.model;

import java.io.Serializable;

public class HostelVO implements Serializable{
	private Integer hostelNo;
	private Integer dealerNo;
	private String hostelName;
	private String hostelPhone;
	private String hostelAddress;
	private String hostelWebPages;
	private Integer hostelState;
	private Integer hostelVerification;
	private Double hostelLat;
	private Double hostelLon;
	private byte[] hostelPicture;
	private byte[] dealerVerify;
	private String hostelCautions;
	private String hostelContent;

	public Integer getHostelNo() {
		return hostelNo;
	}

	public void setHostelNo(Integer hostelNo) {
		this.hostelNo = hostelNo;
	}

	public Integer getDealerNo() {
		return dealerNo;
	}

	public void setDealerNo(Integer dealerNo) {
		this.dealerNo = dealerNo;
	}

	public String getHostelName() {
		return hostelName;
	}

	public void setHostelName(String hostelName) {
		this.hostelName = hostelName;
	}

	public String getHostelPhone() {
		return hostelPhone;
	}

	public void setHostelPhone(String hostelPhone) {
		this.hostelPhone = hostelPhone;
	}

	public String getHostelAddress() {
		return hostelAddress;
	}

	public void setHostelAddress(String hostelAddress) {
		this.hostelAddress = hostelAddress;
	}

	public String getHostelWebPages() {
		return hostelWebPages;
	}

	public void setHostelWebPages(String hostelWebPages) {
		this.hostelWebPages = hostelWebPages;
	}

	public Integer getHostelState() {
		return hostelState;
	}

	public void setHostelState(Integer hostelState) {
		this.hostelState = hostelState;
	}

	public Integer getHostelVerification() {
		return hostelVerification;
	}

	public void setHostelVerification(Integer hostelVerification) {
		this.hostelVerification = hostelVerification;
	}

	public Double getHostelLat() {
		return hostelLat;
	}

	public void setHostelLat(Double hostelLat) {
		this.hostelLat = hostelLat;
	}

	public Double getHostelLon() {
		return hostelLon;
	}

	public void setHostelLon(Double hostelLon) {
		this.hostelLon = hostelLon;
	}

	public byte[] getHostelPicture() {
		return hostelPicture;
	}

	public void setHostelPicture(byte[] hostelPicture) {
		this.hostelPicture = hostelPicture;
	}

	public byte[] getDealerVerify() {
		return dealerVerify;
	}

	public void setDealerVerify(byte[] dealerVerify) {
		this.dealerVerify = dealerVerify;
	}

	public String getHostelCautions() {
		return hostelCautions;
	}

	public void setHostelCautions(String hostelCautions) {
		this.hostelCautions = hostelCautions;
	}

	public String getHostelContent() {
		return hostelContent;
	}

	public void setHostelContent(String hostelContent) {
		this.hostelContent = hostelContent;
	}

}
