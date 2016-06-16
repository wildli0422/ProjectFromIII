package com.emp.model;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;


public class EmpVO implements Serializable{


	private Integer empNo;
	private String empAccount;
	private String empPassword;
	private String empName;
	private String empSex;
	private String empAddress;
	private String empPhone;
	private String empMail;
	private byte[] empPic;
	private String registerDate;
	

	
	public Integer getEmpNo() {
		return empNo;
	}
	public void setEmpNo(Integer empNo) {
		this.empNo = empNo;
	}
	public String getEmpAccount() {
		return empAccount;
	}
	public void setEmpAccount(String empAccount) {
		this.empAccount = empAccount;
	}
	public String getEmpPassword() {
		return empPassword;
	}
	public void setEmpPassword(String empPassword) {
		this.empPassword = empPassword;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getEmpSex() {
		return empSex;
	}
	public void setEmpSex(String empSex) {
		this.empSex = empSex;
	}
	public String getEmpAddress() {
		return empAddress;
	}
	public void setEmpAddress(String empAddress) {
		this.empAddress = empAddress;
	}
	public String getEmpPhone() {
		return empPhone;
	}
	public void setEmpPhone(String empPhone) {
		this.empPhone = empPhone;
	}
	public String getEmpMail() {
		return empMail;
	}
	public void setEmpMail(String empMail) {
		this.empMail = empMail;
	}
	public byte[] getEmpPic() {
		return empPic;
	}
	public void setEmpPic(byte[] empPic) {
		this.empPic = empPic;
	}	
	public String getRegisterDate() {
		return registerDate;
	}
	public void setRegisterDate(String registerDate) {
		this.registerDate = registerDate;
	}
	

	
		
	
}
