package com.viewlist.model;

import java.sql.Date;

public class ViewListVO  {
	private Integer viewlistno;
	private Integer tenantNo;
	private Integer viewno;
	private Date viewlistdate;
	public Integer getViewlistno() {
		return viewlistno;
	}
	public void setViewlistno(Integer viewlistno) {
		this.viewlistno = viewlistno;
	}
	public Integer getTenantNo() {
		return tenantNo;
	}
	public void setTenantNo(Integer tenantNo) {
		this.tenantNo = tenantNo;
	}
	public  Integer getViewno() {
		return viewno;
	}
	public void setViewno(Integer viewno) {
		this.viewno = viewno;
	}
	public Date getViewlistdate() {
		return viewlistdate;
	}
	public void setViewlistdate(Date viewlistdate) {
		this.viewlistdate = viewlistdate;
	}

}

