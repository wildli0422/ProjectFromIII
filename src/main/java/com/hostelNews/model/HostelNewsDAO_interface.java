/*���{���O�ϥ�Interface���;��۰ʲ��� copyRight:wildli0422@gmail.com �ڷR�@����*/
package com.hostelNews.model;
import java.sql.Date;
import java.util.*;

public interface HostelNewsDAO_interface {

    public void insert(HostelNewsVO hostelNewsVO);
    public void update(HostelNewsVO hostelNewsVO);
	public List<HostelNewsVO> likeByColumn(String columnName,String likeString);
    public void delete(Integer hostelnewsNo);
    public HostelNewsVO findByPrimaryKey(Integer hostelnewsNo);
    public List<HostelNewsVO> getAll();

    	
//from html input start to do some onDemand method 
    public HostelNewsVO findByHostelNo(Integer hostelNo);
    public List<HostelNewsVO> getAllByHostelNo(Integer hostelNo);
    public HostelNewsVO findByNewsContent(String newsContent);
    public List<HostelNewsVO> getAllByNewsContent(String newsContent);
    public HostelNewsVO findByUpdateDate(java.sql.Date updateDate);
    public List<HostelNewsVO> getAllByUpdateDate(java.sql.Date updateDate);

}