package com.dealer.model;

import java.util.List;
import java.util.Map;

import com.hostel.model.HostelVO;


public interface DealerDAO_interface {
	
	public void insert(DealerVO dealerVO);
    public void update(DealerVO dealerVO);
    public void delete(Integer dealerNo);
    public DealerVO findByPrimaryKey(Integer dealerNo);
    public List<DealerVO> getAll();
    public String findDealerPassword(String dealerMail);
    public Integer findDealerNo(String dealerMail);
    //�U�νƦX�d��(�ǤJ�Ѽƫ��AMap)(�^�� List)
//  public List<EmpVO> getAll(Map<String, String[]> map); 
    public HostelVO findHostelByDealerNo(Integer dealerNo);
    
    
    ///backend///
    public Integer countState0();
    public List<DealerVO> getAll(Map<String, String[]> map);
    public List<DealerVO> getState0();
    
}
