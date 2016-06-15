package com.hostel.model;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.hostelNews.model.HostelNewsVO;
import com.hostelPic.model.HostelPicVO;
import com.room.model.RoomVO;
import com.roomType.model.RoomTypeVO;

public class HostelService {
	private HostelDAO_interface dao;
	
	public HostelService(){
		dao=new HostelDAO();
	}
	
	public HostelVO addHostel(Integer dealerNo,String hostelName,
			String hostelPhone,String hostelAddress,String hostelWebPages
			,Integer hostelState,Integer hostelVerification,Double hostelLat
			,Double hostelLon,byte[] hostelPicture,byte[] dealerVerify
			,String hostelCautions,String hostelContent){
		
		HostelVO hostelVO =new HostelVO();
		
		hostelVO.setDealerNo(dealerNo);
		hostelVO.setHostelName(hostelName);
		hostelVO.setHostelPhone(hostelPhone);
		hostelVO.setHostelAddress(hostelAddress);
		hostelVO.setHostelWebPages(hostelWebPages);
		hostelVO.setHostelState(hostelState);
		hostelVO.setHostelVerification(hostelVerification);
		hostelVO.setHostelLat(hostelLat);
		hostelVO.setHostelLon(hostelLon);
		hostelVO.setHostelPicture(hostelPicture);
		hostelVO.setDealerVerify(dealerVerify);
		hostelVO.setHostelCautions(hostelCautions);
		hostelVO.setHostelContent(hostelContent);
			
		dao.insert(hostelVO);
		
		return hostelVO;
	}
	
	public HostelVO updateHostel(Integer hostelNo,Integer dealerNo,String hostelName,
			String hostelPhone,String hostelAddress,String hostelWebPages
			,Integer hostelState,Integer hostelVerification,Double hostelLat
			,Double hostelLon,byte[] hostelPicture,byte[] dealerVerify
			,String hostelCautions,String hostelContent){
		
		HostelVO hostelVO =new HostelVO();
		
		hostelVO.setHostelNo(hostelNo);
		hostelVO.setDealerNo(dealerNo);
		hostelVO.setHostelName(hostelName);
		hostelVO.setHostelPhone(hostelPhone);
		hostelVO.setHostelAddress(hostelAddress);
		hostelVO.setHostelWebPages(hostelWebPages);
		hostelVO.setHostelState(hostelState);
		hostelVO.setHostelVerification(hostelVerification);
		hostelVO.setHostelLat(hostelLat);
		hostelVO.setHostelLon(hostelLon);
		hostelVO.setHostelPicture(hostelPicture);
		hostelVO.setDealerVerify(dealerVerify);
		hostelVO.setHostelCautions(hostelCautions);
		hostelVO.setHostelContent(hostelContent);
		
		dao.update(hostelVO);
	
		return hostelVO;
	}
	
	public void deleteHostel(Integer hostelNo){
		dao.delete(hostelNo);
	}
	
	public HostelVO getOneHostel(Integer hostelNo){
		return dao.findByPrimaryKey(hostelNo);
	}
	
	public Set<RoomTypeVO> getRoomTypesByHostelNo(Integer hostelNo){
		return dao.getRoomTypesByHostelNo(hostelNo);
	}
	
	public Set<HostelPicVO> getHostelPicsByHostelNo(Integer hostelNo){
		return dao.getPicsByHostelNo(hostelNo);
	}
	
	public List<HostelNewsVO> getNewsByHostelNo(Integer hostelNo){
		return dao.getNewsByHostelNo(hostelNo);
	}
	
	public List<RoomVO> getRoomsByHostelNo(Integer hostelNo){
		return dao.getRoomsByHostelNo(hostelNo);
	}
	
	public List<HostelVO> getAll(){
		return dao.getAll();
	}
	
	
	
	public List<HostelVO> likeByColumn(String columnName,String likeString){
		 return dao.likeByColumn(columnName, likeString);
	}
	
	
	///backend///
	
	public HostelVO findByDealer(Integer dealerNo) {
		return dao.findByDealer(dealerNo);
	}
	
	public HostelVO updateHostel_bk(Integer hostelNo,String hostelName,
			String hostelPhone,String hostelAddress,String hostelWebPages
			,Integer hostelState,Integer hostelVerification,Double hostelLat
			,Double hostelLon,String hostelCautions,String hostelContent){
		HostelVO hostelVO =new HostelVO();
		
		hostelVO.setHostelNo(hostelNo);
		hostelVO.setHostelName(hostelName);
		hostelVO.setHostelPhone(hostelPhone);
		hostelVO.setHostelAddress(hostelAddress);
		hostelVO.setHostelWebPages(hostelWebPages);
		hostelVO.setHostelState(hostelState);
		hostelVO.setHostelVerification(hostelVerification);
		hostelVO.setHostelLat(hostelLat);
		hostelVO.setHostelLon(hostelLon);
		hostelVO.setHostelCautions(hostelCautions);
		hostelVO.setHostelContent(hostelContent);
		dao.update_bk(hostelVO);
		return hostelVO;
	}
	
	public HostelVO updateHostel_Img(Integer hostelNo,Integer dealerNo,
			byte[] hostelPicture,byte[] dealerVerify){
		
		HostelVO hostelVO =new HostelVO();
		
		hostelVO.setHostelNo(hostelNo);
		hostelVO.setDealerNo(dealerNo);
		hostelVO.setHostelPicture(hostelPicture);
		hostelVO.setDealerVerify(dealerVerify);
		
		dao.update_img_bk(hostelVO);
	
		return hostelVO;
	}
	public List<HostelVO> getAllbk(Map<String, String[]> map) {
		return dao.getAllbk(map);
	}
	
}
