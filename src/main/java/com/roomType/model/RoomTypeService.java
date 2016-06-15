package com.roomType.model;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.hostel.model.HostelVO;
import com.room.model.RoomVO;
import com.roomTypePic.model.RoomTypePicVO;

public class RoomTypeService {
	private RoomTypeDAO_interface dao;

	public RoomTypeService(){
		dao=new RoomTypeDAO();
	}
	
	public RoomTypeVO addRoomTypeWithRoom(Integer hostelNo,Integer facilityNo
			,String roomTypeName,Integer roomTypeContain,
			Integer roomTypePrice,byte[] roomTypePicture,
			String roomTypeContent,Integer roomQuantity){	
		System.out.println("11");
		RoomTypeVO roomTypeVO =new RoomTypeVO();
		
		roomTypeVO.setHostelNo(hostelNo);
		roomTypeVO.setFacilityNo(facilityNo);
		roomTypeVO.setRoomTypeName(roomTypeName);
		roomTypeVO.setRoomTypeContain(roomTypeContain);
		roomTypeVO.setRoomTypePrice(roomTypePrice);
		roomTypeVO.setRoomTypeContent(roomTypeContent);
		roomTypeVO.setRoomTypePicture(roomTypePicture);
		//data base and sql
		dao.insertWithRoom(roomTypeVO,roomQuantity);

		return roomTypeVO;
	}
	
	public RoomTypeVO addRoomType(Integer hostelNo,Integer facilityNo
			,String roomTypeName,Integer roomTypeContain,
			Integer roomTypePrice,byte[] roomTypePicture,
			String roomTypeContent){	
		
		RoomTypeVO roomTypeVO =new RoomTypeVO();
		
		roomTypeVO.setHostelNo(hostelNo);
		roomTypeVO.setFacilityNo(facilityNo);
		roomTypeVO.setRoomTypeName(roomTypeName);
		roomTypeVO.setRoomTypeContain(roomTypeContain);
		roomTypeVO.setRoomTypePrice(roomTypePrice);
		roomTypeVO.setRoomTypeContent(roomTypeContent);
		roomTypeVO.setRoomTypePicture(roomTypePicture);
		//data base and sql
		dao.insert(roomTypeVO);
		
		return roomTypeVO;
	}
	
	public RoomTypeVO updateRoomType(Integer roomTypeNo,Integer hostelNo
			,Integer facilityNo,String roomTypeName,Integer roomTypeContain,
			Integer roomTypePrice,byte[] roomTypePicture,
			String roomTypeContent){
		RoomTypeVO roomTypeVO =new RoomTypeVO();
		
		roomTypeVO.setRoomTypeNo(roomTypeNo);
		roomTypeVO.setHostelNo(hostelNo);
		roomTypeVO.setFacilityNo(facilityNo);
		roomTypeVO.setRoomTypeName(roomTypeName);
		roomTypeVO.setRoomTypeContain(roomTypeContain);
		roomTypeVO.setRoomTypePrice(roomTypePrice);
		roomTypeVO.setRoomTypeContent(roomTypeContent);
		roomTypeVO.setRoomTypePicture(roomTypePicture);
		dao.update(roomTypeVO);
		
		return roomTypeVO;
	}
	
	public void deleteRoomType(Integer roomTypeNo){
		dao.delete(roomTypeNo);
	}
	
	public List<RoomTypeVO> getAll(){
		return dao.getAll();
	}
	
	public RoomTypeVO getOneRoomType(Integer roomTypeNo){
		return dao.findByPrimaryKey(roomTypeNo);
	}
	
	public List<RoomTypeVO> getAll(Map<String,String[]> map){
		return dao.getAll(map);
	}
	
	//用房型編號找民宿，但不會用到
//	public Set<HostelVO> getHostelsByRoomTypeNo(Integer roomTypeNo){
//		return null;
//	}
	public List<RoomTypePicVO> getPicByRoomTypeNo(Integer roomTypeNo){
		return dao.findPicByRoomTypeNo(roomTypeNo);
	}
	
	public List<RoomVO> getRoomsByRoomTypeNo(Integer roomTypeNo){
		return dao.getRoomsByRoomType(roomTypeNo);
	}
	
	public RoomTypeVO getOneByHostelNo(Integer hostelNo) {
		return dao.findByHostelNo(hostelNo);
	}
	
	public List<RoomTypeVO> getAllByHostelNo(Integer hostelNo) {
		return dao.getAllByHostelNo(hostelNo);
	}
	
}
