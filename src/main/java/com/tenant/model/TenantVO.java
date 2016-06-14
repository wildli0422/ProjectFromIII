package com.tenant.model;

import java.sql.Date;

public class TenantVO implements java.io.Serializable {
	public TenantVO() {
	}

	// declare private variable
	private Integer tenantNo;
	private String tenantMail;
	private String tenantPassword;
	private String tenantName;
	private String tenantSex;
	private String tenantAddress;
	private String tenantPhone;
	private byte[] tenantPic;
	private java.sql.Date registerDate;

	// declare public getter method
	public Integer getTenantNo() {
		return tenantNo;
	}

	public String getTenantMail() {
		return tenantMail;
	}

	public String getTenantPassword() {
		return tenantPassword;
	}

	public String getTenantName() {
		return tenantName;
	}

	public String getTenantSex() {
		return tenantSex;
	}

	public String getTenantAddress() {
		return tenantAddress;
	}

	public String getTenantPhone() {
		return tenantPhone;
	}

	public byte[] getTenantPic() {
		return tenantPic;
	}

	public java.sql.Date getRegisterDate() {
		return registerDate;
	}

	// declare public setter method
	public void setTenantNo(Integer tenantNo) {
		this.tenantNo = tenantNo;
	}

	public void setTenantMail(String tenantMail) {
		this.tenantMail = tenantMail;
	}

	public void setTenantPassword(String tenantPassword) {
		this.tenantPassword = tenantPassword;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}

	public void setTenantSex(String tenantSex) {
		this.tenantSex = tenantSex;
	}

	public void setTenantAddress(String tenantAddress) {
		this.tenantAddress = tenantAddress;
	}

	public void setTenantPhone(String tenantPhone) {
		this.tenantPhone = tenantPhone;
	}

	public void setTenantPic(byte[] tenantPic) {
		this.tenantPic = tenantPic;
	}

	public void setRegisterDate(java.sql.Date registerDate) {
		this.registerDate = registerDate;
	}

}