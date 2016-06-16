package com.roomTypePic.model;

import java.util.List;

public class RoomTypePicService {
	private RoomTypePicDAO_interface dao;

	public RoomTypePicService() {
		dao = new RoomTypePicDAO();
	}

	public RoomTypePicVO addRoomTypePic(Integer roomTypeNo, byte[] roomTypePhoto) {

		RoomTypePicVO roomTypePicVO = new RoomTypePicVO();

		roomTypePicVO.setRoomTypeNo(roomTypeNo);
		roomTypePicVO.setRoomTypePhoto(roomTypePhoto);

		dao.insert(roomTypePicVO);

		return roomTypePicVO;
	}

	public RoomTypePicVO updateRoomTypePic(Integer roomTypePicNo,
			Integer roomTypeNo, byte[] roomTypePhoto) {

		RoomTypePicVO roomTypePicVO = new RoomTypePicVO();

		roomTypePicVO.setRoomTypePicNo(roomTypePicNo);
		roomTypePicVO.setRoomTypeNo(roomTypeNo);
		roomTypePicVO.setRoomTypePhoto(roomTypePhoto);

		dao.update(roomTypePicVO);

		return roomTypePicVO;
	}

	public void deleteRoomTypePic(Integer roomTypePicNo) {
		dao.delete(roomTypePicNo);
	}

	public List<RoomTypePicVO> getAll() {
		return dao.getAll();
	}

	public RoomTypePicVO getOneRoomTypePic(Integer roomTypePicNo) {
		return dao.findByPrimaryKey(roomTypePicNo);
	}

	public RoomTypePicVO getOneByRoomTypeNo(Integer roomTypeNo) {
		return dao.findByRoomTypeNo(roomTypeNo);
	}

	public List<RoomTypePicVO> getAllByRoomTypeNo(Integer roomTypeNo) {
		return dao.getAllByRoomTypeNo(roomTypeNo);
	}

}
