/*本程式是使用Service產生器自動產生 copyRight:wildli0422@gmail.com 我愛一條柴*/
package com.hostelOrderDetail.model;

import java.util.List;
import java.sql.*;

public class HostelOrderDetailService {

	private HostelOrderDetailDAO_interface dao;

	public HostelOrderDetailService() {
		dao = new HostelOrderDetailDAO();
	}

	public HostelOrderDetailVO addHostelOrderDetail(Integer hostelOrderNo,
			Integer roomTypeNo, Integer roomQuantity,
			java.sql.Timestamp checkInDate, java.sql.Timestamp checkOutDate,
			Integer totalPrice) {
		HostelOrderDetailVO hostelOrderDetailVO = new HostelOrderDetailVO();

		hostelOrderDetailVO.setHostelOrderNo(hostelOrderNo);
		hostelOrderDetailVO.setRoomTypeNo(roomTypeNo);
		hostelOrderDetailVO.setRoomQuantity(roomQuantity);
		hostelOrderDetailVO.setCheckInDate(checkInDate);
		hostelOrderDetailVO.setCheckOutDate(checkOutDate);
		hostelOrderDetailVO.setTotalPrice(totalPrice);
		dao.insert(hostelOrderDetailVO);

		return hostelOrderDetailVO;
	}

	public HostelOrderDetailVO updateHostelOrderDetail(
			Integer hostelOrderDetailNo, Integer hostelOrderNo,
			Integer roomTypeNo, Integer roomQuantity,
			java.sql.Timestamp checkInDate, java.sql.Timestamp checkOutDate,
			Integer totalPrice) {
		HostelOrderDetailVO hostelOrderDetailVO = new HostelOrderDetailVO();

		hostelOrderDetailVO.setHostelOrderDetailNo(hostelOrderDetailNo);
		hostelOrderDetailVO.setHostelOrderNo(hostelOrderNo);
		hostelOrderDetailVO.setRoomTypeNo(roomTypeNo);
		hostelOrderDetailVO.setRoomQuantity(roomQuantity);
		hostelOrderDetailVO.setCheckInDate(checkInDate);
		hostelOrderDetailVO.setCheckOutDate(checkOutDate);
		hostelOrderDetailVO.setTotalPrice(totalPrice);
		dao.update(hostelOrderDetailVO);

		return hostelOrderDetailVO;
	}

	public void deleteHostelOrderDetail(Integer hostelOrderDetailNo) {
		dao.delete(hostelOrderDetailNo);
	}

	public HostelOrderDetailVO getOneHostelOrderDetail(
			Integer hostelOrderDetailNo) {
		return dao.findByPrimaryKey(hostelOrderDetailNo);
	}

	public List<HostelOrderDetailVO> getAll() {
		return dao.getAll();
	}

	// from html input start to do some onDemand method
	public HostelOrderDetailVO getOneByHostelOrderNo(Integer hostelOrderNo) {
		return dao.findByHostelOrderNo(hostelOrderNo);
	}

	public List<HostelOrderDetailVO> getAllByHostelOrderNo(Integer hostelOrderNo) {
		return dao.getAllByHostelOrderNo(hostelOrderNo);
	}

	public HostelOrderDetailVO getOneByRoomTypeNo(Integer roomTypeNo) {
		return dao.findByRoomTypeNo(roomTypeNo);
	}

	public List<HostelOrderDetailVO> getAllByRoomTypeNo(Integer roomTypeNo) {
		return dao.getAllByRoomTypeNo(roomTypeNo);
	}

	public HostelOrderDetailVO getOneByRoomQuantity(Integer roomQuantity) {
		return dao.findByRoomQuantity(roomQuantity);
	}

	public List<HostelOrderDetailVO> getAllByRoomQuantity(Integer roomQuantity) {
		return dao.getAllByRoomQuantity(roomQuantity);
	}

	public HostelOrderDetailVO getOneByCheckInDate(
			java.sql.Timestamp checkInDate) {
		return dao.findByCheckInDate(checkInDate);
	}

	public List<HostelOrderDetailVO> getAllByCheckInDate(
			java.sql.Timestamp checkInDate) {
		return dao.getAllByCheckInDate(checkInDate);
	}

	public HostelOrderDetailVO getOneByCheckOutDate(
			java.sql.Timestamp checkOutDate) {
		return dao.findByCheckOutDate(checkOutDate);
	}

	public List<HostelOrderDetailVO> getAllByCheckOutDate(
			java.sql.Timestamp checkOutDate) {
		return dao.getAllByCheckOutDate(checkOutDate);
	}

	public HostelOrderDetailVO getOneByTotalPrice(Integer totalPrice) {
		return dao.findByTotalPrice(totalPrice);
	}

	public List<HostelOrderDetailVO> getAllByTotalPrice(Integer totalPrice) {
		return dao.getAllByTotalPrice(totalPrice);
	}

}
