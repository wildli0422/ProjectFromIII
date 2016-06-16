package com.tenant.model;
import java.util.*;

public interface TenantDAO_interface {

    public void insert(TenantVO tenantVO);
    public void update(TenantVO tenantVO);
    public void delete(Integer TenantNo);
    public TenantVO findByPrimaryKey(Integer TenantNo);
    public List<TenantVO> getAll();
    public List<TenantVO> getAll(Map<String, String[]> map);
    
	public List<TenantVO> likeByColumn(String columnName,String likeString);
	public String findTenantPassword(String tenantMail); //login
	public Integer findTenantNo(String tenantMail);
	
	
	//from html input start to do some onDemand method 
	public List<TenantVO> addThisMonth();

	public Integer getScoreAvg(Integer tenantNo);
	public Integer countScore(Integer tenantNo, Integer tenantScore);
	public Integer toDate(Integer tenantNo);
	public Integer travelThisMonth(Integer tenantNo);
	public List<TenantVO> getListByAddress(String tenantAddress);
	public List<TenantVO> getListByName(String tenantName);
	public void update_pic_bk(TenantVO tenantVo);
}