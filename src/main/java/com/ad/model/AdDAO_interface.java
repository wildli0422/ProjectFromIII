/*本程式是使用Interface產生器自動產生 copyRight:wildli0422@gmail.com 我愛一條柴*/
package com.ad.model;
import java.sql.Date;
import java.sql.Date;
import java.sql.Date;
import java.util.*;

public interface AdDAO_interface {

    public void insert(AdVO adVO);
    public void update(AdVO adVO);
	public List<AdVO> likeByColumn(String columnName,String likeString);
    public void delete(Integer adNo);
    public AdVO findByPrimaryKey(Integer adNo);
    public List<AdVO> getAll();

//from html input start to do some onDemand method 
    public AdVO findByDealerNo(Integer dealerNo);
    public List<AdVO> getAllByDealerNo(Integer dealerNo);
    public AdVO findByOdStatus(Integer odStatus);
    public List<AdVO> getAllByOdStatus(Integer odStatus);
    public AdVO findByOdPrice(Integer odPrice);
    public List<AdVO> getAllByOdPrice(Integer odPrice);
    public AdVO findByAdStatus(Integer adStatus);
    public List<AdVO> getAllByAdStatus(Integer adStatus);

}