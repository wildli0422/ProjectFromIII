/*本程式是使用VO產生器自動產生 copyRight:wildli0422@gmail.com 我愛一條柴*/
package com.hostelNews.model;
import java.sql.Date;
public class HostelNewsVO implements java.io.Serializable{
	
	  public HostelNewsVO(){}
	//declare private variable 
	  private Integer hostelNewsNo;
	  private Integer hostelNo;
	  private String newsContent;
	  private java.sql.Date updateDate;
	//declare public getter method  
	  public Integer getHostelNewsNo() {
	    return hostelNewsNo;
	  }
	  public Integer getHostelNo() {
	    return hostelNo;
	  }
	  public String getNewsContent() {
	    return newsContent;
	  }
	  public java.sql.Date getUpdateDate() {
	    return updateDate;
	  }
	//declare public setter method  
	  public void setHostelNewsNo(Integer hostelNewsNo) {
	    this.hostelNewsNo = hostelNewsNo;
	  }
	  public void setHostelNo(Integer hostelNo) {
	    this.hostelNo = hostelNo;
	  }
	  public void setNewsContent(String newsContent) {
	    this.newsContent = newsContent;
	  }
	  public void setUpdateDate(java.sql.Date updateDate) {
	    this.updateDate = updateDate;
	  }

	}