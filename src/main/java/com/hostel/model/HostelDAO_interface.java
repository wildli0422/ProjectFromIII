package com.hostel.model;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.hostelNews.model.HostelNewsVO;
import com.hostelPic.model.HostelPicVO;
import com.room.model.RoomVO;
import com.roomType.model.RoomTypeVO;



public interface HostelDAO_interface {
	
	public void insert(HostelVO hostelVO);
    public void update(HostelVO hostelVO);
    public void delete(Integer hostelNo);
    public HostelVO findByPrimaryKey(Integer hostelNo);
    public List<HostelVO> getAll();
    //�d�߬Y���J���Ы�(�@��h)(�^�� Set)
    public Set<RoomTypeVO> getRoomTypesByHostelNo(Integer hostelNo);
    public Set<HostelPicVO> getPicsByHostelNo(Integer hostelNo);
    public List<HostelNewsVO> getNewsByHostelNo(Integer hostelNo);
    public List<RoomVO> getRoomsByHostelNo(Integer hostelNo);
    
    public List<HostelVO> getAll(Map<String, String[]> map);
    
	public List<HostelVO> likeByColumn(String columnName,String likeString);

  //from html input start to do some onDemand method 
    public HostelVO findByDealerNo(Integer dealerNo);
    public List<HostelVO> getAllByDealerNo(Integer dealerNo);
    public HostelVO findByHostelName(String hostelName);
    public List<HostelVO> getAllByHostelName(String hostelName);
    public HostelVO findByHostelPhone(String hostelPhone);
    public List<HostelVO> getAllByHostelPhone(String hostelPhone);
    public HostelVO findByHostelAddress(String hostelAddress);
    public List<HostelVO> getAllByHostelAddress(String hostelAddress);
    public HostelVO findByHostelState(Integer hostelState);
    public List<HostelVO> getAllByHostelState(Integer hostelState);
    public HostelVO findByHostelVerification(Integer hostelVerification);
    public List<HostelVO> getAllByHostelVerification(Integer hostelVerification);
    public HostelVO findByHostelLat(Double hostelLat);
    public List<HostelVO> getAllByHostelLat(Double hostelLat);
    public HostelVO findByHostelLon(Double hostelLon);
    public List<HostelVO> getAllByHostelLon(Double hostelLon);
    public HostelVO findByHostelCautions(String hostelCautions);
    public List<HostelVO> getAllByHostelCautions(String hostelCautions);
    public HostelVO findByHostelContent(String hostelContent);
    public List<HostelVO> getAllByHostelContent(String hostelContent);
    
    ///backend///
    public void update_bk(HostelVO hostelVO);
    public void update_img_bk(HostelVO hostelVO);
    public HostelVO findByDealer(Integer dealerNo);
    public List<HostelVO> getAllbk(Map<String, String[]> map);
}
