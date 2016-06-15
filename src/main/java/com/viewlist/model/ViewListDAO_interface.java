package com.viewlist.model;

import java.util.*;



public interface ViewListDAO_interface {
	
	public void insert(ViewListVO viewlistVO); 
    public void update(ViewListVO viewlistVO);
    public void delete(Integer viewlistno);
    public ViewListVO findByPrimaryKey(Integer viewlistno);
    public List<ViewListVO> getAll();
  
    //查詢某房客收藏的的景點清單(一對多)(回傳 Set)
    public Set<ViewListVO> getViewListByTenantno(Integer tenantNo);
    
    //萬用複合查詢(傳入參數型態Map)(回傳 List)
//  public List<EmpVO> getAll(Map<String, String[]> map); 

}
