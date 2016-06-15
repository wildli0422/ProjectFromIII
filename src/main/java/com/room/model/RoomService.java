package com.room.model;

import java.util.List;

import com.hostelOrderDetail.model.HostelOrderDetailVO;

public class RoomService {

	private RoomDAO_interface dao;

	public RoomService() {
		dao = new RoomDAO();
	}

	public RoomVO updateRoom(Integer roomNo, Integer hostelNo,
			Integer roomTypeNo, Integer hostelOrderDetailNo, String roomState) {

		RoomVO roomVO = new RoomVO();
		roomVO.setRoomNo(roomNo);
		roomVO.setHostelNo(hostelNo);
		roomVO.setRoomTypeNo(roomTypeNo);
		roomVO.setHostelOrderDetailNo(hostelOrderDetailNo);
		roomVO.setRoomState(roomState);

		dao.update(roomVO);

		return roomVO;

	}

	public RoomVO getOneRoom(Integer roomNo) {
		return dao.findByPrimaryKey(roomNo);
	}

	public List<RoomVO> getAllByRoomTypeNo(Integer roomTypeNo) {
		return dao.getAllByRoomTypeNo(roomTypeNo);
	}

}
