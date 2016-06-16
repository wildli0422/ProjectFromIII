/*?��程�?�是使用VO?��??�器?��??�產??? copyRight:wildli0422@gmail.com ??��?��?條柴*/
package com.hostelTrace.model;
import java.sql.Date;
public class HostelTraceVO implements java.io.Serializable{
  public HostelTraceVO(){}
//declare private variable 
  private Integer hostelTraceNo;
  private Integer hostelNo;
  private Integer tenantNo;
  private java.sql.Date viewDate;
//declare public getter method  
  public Integer getHostelTraceNo() {
    return hostelTraceNo;
  }
  public Integer getHostelNo() {
    return hostelNo;
  }
  public Integer getTenantNo() {
    return tenantNo;
  }
  public java.sql.Date getViewDate() {
    return viewDate;
  }
//declare public setter method  
  public void setHostelTraceNo(Integer hostelTraceNo) {
    this.hostelTraceNo = hostelTraceNo;
  }
  public void setHostelNo(Integer hostelNo) {
    this.hostelNo = hostelNo;
  }
  public void setTenantNo(Integer tenantNo) {
    this.tenantNo = tenantNo;
  }
  public void setViewDate(java.sql.Date viewDate) {
    this.viewDate = viewDate;
  }

}