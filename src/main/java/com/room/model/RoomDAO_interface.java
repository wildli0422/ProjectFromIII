package com.room.model;

import java.sql.Connection;
import java.util.List;


public interface RoomDAO_interface {
	public void insert(RoomVO roomVO);
    public void update(RoomVO roomVO);
    public void delete(Integer roomNo);
    public RoomVO findByPrimaryKey(Integer roomNo);
    public List<RoomVO> getAll();
    
    public void insertByRoomType(RoomVO roomVO,Connection con);
    public void deleteByRoomType(RoomVO roomVO,Connection con);
    
	public List<RoomVO> getAllByRoomTypeNo(Integer roomTypeNo);
}
