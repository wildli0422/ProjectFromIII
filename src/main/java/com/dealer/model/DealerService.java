package com.dealer.model;

import java.util.List;
import java.util.Map;

import com.hostel.model.HostelVO;

public class DealerService {
	private DealerDAO_interface dao;
	
	
	
	public DealerService(){
		dao=new DealerDAO();
	}//����i�H��@���P�������A�phibernate�Bspring
	
	public DealerVO addDealer(String dealerPassword,String dealerName,String dealerSex,
			String dealerAddress,String dealerPhone,String dealerMail,
			Integer dealerState,String dealerAccount){
		
		DealerVO dealerVO =new DealerVO();
		
		dealerVO.setDealerPassword(dealerPassword);
		dealerVO.setDealerName(dealerName);
		dealerVO.setDealerSex(dealerSex);
		dealerVO.setDealerAddress(dealerAddress);
		dealerVO.setDealerPhone(dealerPhone);
		dealerVO.setDealerMail(dealerMail);
		dealerVO.setDealerState(dealerState);
		dealerVO.setDealerAccount(dealerAccount);
		
		dao.insert(dealerVO);
		return dealerVO;
	}
	
	public DealerVO updateDealer(Integer dealerNo,String dealerPassword,String dealerName,
			String dealerSex,String dealerAddress,String dealerPhone,
			String dealerMail,Integer dealerState,String dealerAccount){
		
		DealerVO dealerVO =new DealerVO();
		
		dealerVO.setDealerNo(dealerNo);
		dealerVO.setDealerPassword(dealerPassword);
		dealerVO.setDealerName(dealerName);
		dealerVO.setDealerSex(dealerSex);
		dealerVO.setDealerAddress(dealerAddress);
		dealerVO.setDealerPhone(dealerPhone);
		dealerVO.setDealerMail(dealerMail);
		dealerVO.setDealerState(dealerState);
		dealerVO.setDealerAccount(dealerAccount);
		
		dao.update(dealerVO);
		
		return dealerVO;
	}
	
	public void deleteDealer(Integer dealerNo){
		dao.delete(dealerNo);
	}
	
	public DealerVO getOneDealer(Integer dealerNo){
		return dao.findByPrimaryKey(dealerNo);
	}
	
	public List<DealerVO> getAll(){
		return dao.getAll();
	}
	
	public String getDealerPassword(String dealerMail){
		return dao.findDealerPassword(dealerMail);
	}
	public Integer getDealerNo(String dealerMail){
		return dao.findDealerNo(dealerMail);
	}
	
	public HostelVO getHostelByDealerNo(Integer dealerNo){
		return dao.findHostelByDealerNo(dealerNo);
	}
	
	
///backend///
public Integer countState0() {
return dao.countState0();
}

public List<DealerVO> getAll(Map<String, String[]> map) {
return dao.getAll(map);
}

public List<DealerVO> getState0() {
return dao.getState0();
} 
}
