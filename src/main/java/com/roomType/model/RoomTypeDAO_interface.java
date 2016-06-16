package com.roomType.model;

import java.util.List;
import java.util.Map;

import com.room.model.RoomVO;
import com.roomTypePic.model.RoomTypePicVO;



public interface RoomTypeDAO_interface {
	public void insert(RoomTypeVO roomTypeVO);
    public void update(RoomTypeVO roomTypeVO);
    public void delete(Integer roomTypeNo);
    public RoomTypeVO findByPrimaryKey(Integer roomTypeNo);
    public List<RoomTypeVO> getAll(); 
    //萬用複合查詢(傳入參數型態Map)(回傳 List)
    public List<RoomTypeVO> getAll(Map<String, String[]> map); 
    
    public List<RoomTypePicVO> findPicByRoomTypeNo(Integer roomTypeNo);
    public void insertWithRoom(RoomTypeVO roomTypeVO,Integer roomQuantity);
    public List<RoomVO> getRoomsByRoomType(Integer roomTypeNo);
    
    public List<RoomTypeVO> likeByColumn(String columnName,String likeString);
  //from html input start to do some onDemand method 
    public RoomTypeVO findByHostelNo(Integer hostelNo);
    public List<RoomTypeVO> getAllByHostelNo(Integer hostelNo);
    public RoomTypeVO findByFacilityNo(Integer facilityNo);
    public List<RoomTypeVO> getAllByFacilityNo(Integer facilityNo);
    public RoomTypeVO findByRoomTypeName(String roomTypeName);
    public List<RoomTypeVO> getAllByRoomTypeName(String roomTypeName);
    public RoomTypeVO findByRoomTypeContain(Integer roomTypeContain);
    public List<RoomTypeVO> getAllByRoomTypeContain(Integer roomTypeContain);
    public RoomTypeVO findByRoomTypePrice(Integer roomTypePrice);
    public List<RoomTypeVO> getAllByRoomTypePrice(Integer roomTypePrice);
    public RoomTypeVO findByRoomTypeContent(String roomTypeContent);
    public List<RoomTypeVO> getAllByRoomTypeContent(String roomTypeContent);

}
