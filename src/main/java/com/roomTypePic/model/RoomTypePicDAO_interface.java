package com.roomTypePic.model;

import java.sql.Connection;
import java.util.List;

public interface RoomTypePicDAO_interface {
	public void insert(RoomTypePicVO roomTypePicVO);
    public void update(RoomTypePicVO roomTypePicVO);
    public void delete(Integer roomTypePicNo);
    public RoomTypePicVO findByPrimaryKey(Integer roomTypePicNo);
    public List<RoomTypePicVO> getAll();
    
  //from html input start to do some onDemand method 
    public RoomTypePicVO findByRoomTypeNo(Integer roomTypeNo);
    public List<RoomTypePicVO> getAllByRoomTypeNo(Integer roomTypeNo);
	public void deleteByRoomType(RoomTypePicVO roomTypePicVO, Connection con);
    
}
