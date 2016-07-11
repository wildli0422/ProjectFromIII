package com.jsf.DTO;

import java.io.ByteArrayInputStream;
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.hostel.model.HostelService;
import com.hostel.model.HostelVO;


@ManagedBean(name="indexHostelDTO")
public class IndexHostelDTO implements Serializable{
	
	private String hostelName;
	private String hostelNo;
	private String hostelAddress;
	private String hostelContent;
	private String hostelPhone;
	
	public String getHostelName() {
		return hostelName;
	}
	public void setHostelName(String hostelName) {
		this.hostelName = hostelName;
	}
	public String getHostelNo() {
		return hostelNo;
	}
	public void setHostelNo(String hostelNo) {
		this.hostelNo = hostelNo;
	}
	public String getHostelAddress() {
		return hostelAddress;
	}
	public void setHostelAddress(String hostelAddress) {
		this.hostelAddress = hostelAddress;
	}
	public String getHostelContent() {
		return hostelContent;
	}
	public void setHostelContent(String hostelContent) {
		this.hostelContent = hostelContent;
	}
	public String getHostelPhone() {
		return hostelPhone;
	}
	public void setHostelPhone(String hostelPhone) {
		this.hostelPhone = hostelPhone;
	}


}
