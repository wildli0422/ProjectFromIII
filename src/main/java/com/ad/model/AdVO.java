/*本程式是使用VO產生器自動產生 copyRight:wildli0422@gmail.com 我愛一條柴*/
package com.ad.model;
import java.sql.Date;
import java.sql.Date;
import java.sql.Date;
public class AdVO implements java.io.Serializable{
  public AdVO(){}
//declare private variable 
  private Integer adNo;
  private Integer dealerNo;
  private Date adOdDate;
  private Integer odStatus;
  private byte[] adPic;
  private Integer odPrice;
  private Integer adStatus;
  private Date adStartline;
  private Date adDeadline;
//declare public getter method  
  public Integer getAdNo() {
    return adNo;
  }
  public Integer getDealerNo() {
    return dealerNo;
  }
  public Date getAdOdDate() {
    return adOdDate;
  }
  public Integer getOdStatus() {
    return odStatus;
  }
  public byte[] getAdPic() {
    return adPic;
  }
  public Integer getOdPrice() {
    return odPrice;
  }
  public Integer getAdStatus() {
    return adStatus;
  }
  public Date getAdStartline() {
    return adStartline;
  }
  public Date getAdDeadline() {
    return adDeadline;
  }
//declare public setter method  
  public void setAdNo(Integer adNo) {
    this.adNo = adNo;
  }
  public void setDealerNo(Integer dealerNo) {
    this.dealerNo = dealerNo;
  }
  public void setAdOdDate(Date adOdDate) {
    this.adOdDate = adOdDate;
  }
  public void setOdStatus(Integer odStatus) {
    this.odStatus = odStatus;
  }
  public void setAdPic(byte[] adPic) {
    this.adPic = adPic;
  }
  public void setOdPrice(Integer odPrice) {
    this.odPrice = odPrice;
  }
  public void setAdStatus(Integer adStatus) {
    this.adStatus = adStatus;
  }
  public void setAdStartline(Date adStartline) {
    this.adStartline = adStartline;
  }
  public void setAdDeadline(Date adDeadline) {
    this.adDeadline = adDeadline;
  }

}