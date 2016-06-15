/*本程式是使用VO產生器自動產生 copyRight:wildli0422@gmail.com 我愛一條柴*/
package com.hostelOrder.model;
import java.sql.Date;
public class HostelOrderVO implements java.io.Serializable{
  public HostelOrderVO(){}
//declare private variable 
  private Integer hostelOrderNo;
  private Integer hostelNo;
  private Integer tenantNo;
  private java.sql.Date hostelOrderDate;
  private Integer hostelScore;
  private String hostelComment;
  private Integer tenantScore;
  private Integer customerQuantity;
  private String paymentWay;
  private String paymentState;
  private String orderRemark;
  private String orderState;
//declare public getter method  
  public Integer getHostelOrderNo() {
    return hostelOrderNo;
  }
  public Integer getHostelNo() {
    return hostelNo;
  }
  public Integer getTenantNo() {
    return tenantNo;
  }
  public java.sql.Date getHostelOrderDate() {
    return hostelOrderDate;
  }
  public Integer getHostelScore() {
    return hostelScore;
  }
  public String getHostelComment() {
    return hostelComment;
  }
  public Integer getTenantScore() {
    return tenantScore;
  }
  public Integer getCustomerQuantity() {
    return customerQuantity;
  }
  public String getPaymentWay() {
    return paymentWay;
  }
  public String getPaymentState() {
    return paymentState;
  }
  public String getOrderRemark() {
    return orderRemark;
  }
  public String getOrderState() {
    return orderState;
  }
//declare public setter method  
  public void setHostelOrderNo(Integer hostelOrderNo) {
    this.hostelOrderNo = hostelOrderNo;
  }
  public void setHostelNo(Integer hostelNo) {
    this.hostelNo = hostelNo;
  }
  public void setTenantNo(Integer tenantNo) {
    this.tenantNo = tenantNo;
  }
  public void setHostelOrderDate(java.sql.Date hostelOrderDate) {
    this.hostelOrderDate = hostelOrderDate;
  }
  public void setHostelScore(Integer hostelScore) {
    this.hostelScore = hostelScore;
  }
  public void setHostelComment(String hostelComment) {
    this.hostelComment = hostelComment;
  }
  public void setTenantScore(Integer tenantScore) {
    this.tenantScore = tenantScore;
  }
  public void setCustomerQuantity(Integer customerQuantity) {
    this.customerQuantity = customerQuantity;
  }
  public void setPaymentWay(String paymentWay) {
    this.paymentWay = paymentWay;
  }
  public void setPaymentState(String paymentState) {
    this.paymentState = paymentState;
  }
  public void setOrderRemark(String orderRemark) {
    this.orderRemark = orderRemark;
  }
  public void setOrderState(String orderState) {
    this.orderState = orderState;
  }

}