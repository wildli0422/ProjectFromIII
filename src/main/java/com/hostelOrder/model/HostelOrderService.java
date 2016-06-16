/*�蝔�雿輻Service��������� copyRight:wildli0422@gmail.com ����璇*/
package com.hostelOrder.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.cart.model.RoomTypeCartVO;
import com.hostelOrderDetail.model.HostelOrderDetailVO;

import tool.cart.model.OrderCartVO;

public class HostelOrderService {
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/ZA105G3");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	private HostelOrderDAO_interface dao;

	public HostelOrderService() {
		dao = new HostelOrderDAO();
	}

	private HostelOrderTransactionInvoke_interface tranDao;

	public int orderInvoke(HostelOrderVO hostelOrderVO,
			List<OrderCartVO> cartList) {

		int hostelOrderNo = 0;
		tranDao = new HostelOrderDAO();
		Connection con = null;
		try {
			con = ds.getConnection();
			con.setAutoCommit(false);
			hostelOrderNo = tranDao.doTransaction(con, hostelOrderVO, cartList);
			con.commit();
			con.setAutoCommit(true);

		} catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
		return hostelOrderNo;

	}
	
	public List<HostelOrderVO> getAll(Map<String,String[]> map){
		return dao.getAll(map);
	}

	// ************************************new
	public HostelOrderVO addNewOrder(Integer hostelNo, Integer tenantNo,
			java.sql.Date hostelOrderDate, Integer hostelScore,
			String hostelComment, Integer tenantScore,
			Integer customerQuantity, String paymentWay, String paymentState,
			String orderRemark, String orderState,
			Vector<RoomTypeCartVO> cartList) {

		HostelOrderVO hostelOrderVO = new HostelOrderVO();

		hostelOrderVO.setHostelNo(hostelNo);
		hostelOrderVO.setTenantNo(tenantNo);
		hostelOrderVO.setHostelOrderDate(hostelOrderDate);
		hostelOrderVO.setHostelScore(hostelScore);
		hostelOrderVO.setHostelComment(hostelComment);
		hostelOrderVO.setTenantScore(tenantScore);
		hostelOrderVO.setCustomerQuantity(customerQuantity);
		hostelOrderVO.setPaymentWay(paymentWay);
		hostelOrderVO.setPaymentState(paymentState);
		hostelOrderVO.setOrderState(orderState);
		hostelOrderVO.setOrderRemark(orderRemark);

		List<HostelOrderDetailVO> addList = new ArrayList<HostelOrderDetailVO>();
		for (RoomTypeCartVO roomTypeCartVO : cartList) {
			HostelOrderDetailVO detailVO = new HostelOrderDetailVO();
			detailVO.setRoomQuantity(roomTypeCartVO.getRoomQuantity());
			detailVO.setCheckInDate(roomTypeCartVO.getCheckInDate());
			detailVO.setCheckOutDate(roomTypeCartVO.getCheckOutDate());
			detailVO.setRoomTypeNo(roomTypeCartVO.getRoomTypeNo());

			addList.add(detailVO);
		}

		dao.insertWithDetail(hostelOrderVO, addList);

		return hostelOrderVO;
	}

	public List<HostelOrderDetailVO> getAllOrderDetail(Integer hostelOrderNo) {
		return dao.getHostelOrderDetail(hostelOrderNo);
	}

	// ****************************new
	
	public HostelOrderVO addHostelOrder(Integer hostelNo, Integer tenantNo,
			Integer hostelScore, String hostelComment, Integer tenantScore,
			Integer customerQuantity, String paymentWay, String paymentState,
			String orderRemark, String orderState) {
		HostelOrderVO hostelOrderVO = new HostelOrderVO();

		hostelOrderVO.setHostelNo(hostelNo);
		hostelOrderVO.setTenantNo(tenantNo);
		hostelOrderVO.setHostelScore(hostelScore);
		hostelOrderVO.setHostelComment(hostelComment);
		hostelOrderVO.setTenantScore(tenantScore);
		hostelOrderVO.setCustomerQuantity(customerQuantity);
		hostelOrderVO.setPaymentWay(paymentWay);
		hostelOrderVO.setPaymentState(paymentState);
		hostelOrderVO.setOrderRemark(orderRemark);
		hostelOrderVO.setOrderState(orderState);
		dao.insert(hostelOrderVO);

		return hostelOrderVO;
	}

	public HostelOrderVO updateHostelOrder(Integer hostelOrderNo,
			Integer hostelNo, Integer tenantNo, java.sql.Date hostelOrderDate,
			Integer hostelScore, String hostelComment, Integer tenantScore,
			Integer customerQuantity, String paymentWay, String paymentState,
			String orderRemark, String orderState) {
		HostelOrderVO hostelOrderVO = new HostelOrderVO();

		hostelOrderVO.setHostelOrderNo(hostelOrderNo);
		hostelOrderVO.setHostelNo(hostelNo);
		hostelOrderVO.setTenantNo(tenantNo);
		hostelOrderVO.setHostelOrderDate(hostelOrderDate);
		hostelOrderVO.setHostelScore(hostelScore);
		hostelOrderVO.setHostelComment(hostelComment);
		hostelOrderVO.setTenantScore(tenantScore);
		hostelOrderVO.setCustomerQuantity(customerQuantity);
		hostelOrderVO.setPaymentWay(paymentWay);
		hostelOrderVO.setPaymentState(paymentState);
		hostelOrderVO.setOrderRemark(orderRemark);
		hostelOrderVO.setOrderState(orderState);
		dao.update(hostelOrderVO);

		return hostelOrderVO;
	}

	public void deleteHostelOrder(Integer hostelOrderNo) {
		dao.delete(hostelOrderNo);
	}

	public HostelOrderVO getOneHostelOrder(Integer hostelOrderNo) {
		return dao.findByPrimaryKey(hostelOrderNo);
	}

	public List<HostelOrderVO> getAll() {
		return dao.getAll();
	}

	// from html input start to do some onDemand method
	public HostelOrderVO getOneByHostelNo(Integer hostelNo) {
		return dao.findByHostelNo(hostelNo);
	}

	public List<HostelOrderVO> getAllByHostelNo(Integer hostelNo) {
		return dao.getAllByHostelNo(hostelNo);
	}

	public HostelOrderVO getOneByTenantNo(Integer tenantNo) {
		return dao.findByTenantNo(tenantNo);
	}

	public List<HostelOrderVO> getAllByTenantNo(Integer tenantNo) {
		return dao.getAllByTenantNo(tenantNo);
	}

	public HostelOrderVO getOneByHostelOrderDate(java.sql.Date hostelOrderDate) {
		return dao.findByHostelOrderDate(hostelOrderDate);
	}

	public List<HostelOrderVO> getAllByHostelOrderDate(
			java.sql.Date hostelOrderDate) {
		return dao.getAllByHostelOrderDate(hostelOrderDate);
	}

	public HostelOrderVO getOneByHostelScore(Integer hostelScore) {
		return dao.findByHostelScore(hostelScore);
	}

	public List<HostelOrderVO> getAllByHostelScore(Integer hostelScore) {
		return dao.getAllByHostelScore(hostelScore);
	}

	public HostelOrderVO getOneByHostelComment(String hostelComment) {
		return dao.findByHostelComment(hostelComment);
	}

	public List<HostelOrderVO> getAllByHostelComment(String hostelComment) {
		return dao.getAllByHostelComment(hostelComment);
	}

	public HostelOrderVO getOneByTenantScore(Integer tenantScore) {
		return dao.findByTenantScore(tenantScore);
	}

	public List<HostelOrderVO> getAllByTenantScore(Integer tenantScore) {
		return dao.getAllByTenantScore(tenantScore);
	}

	public HostelOrderVO getOneByCustomerQuantity(Integer customerQuantity) {
		return dao.findByCustomerQuantity(customerQuantity);
	}

	public List<HostelOrderVO> getAllByCustomerQuantity(Integer customerQuantity) {
		return dao.getAllByCustomerQuantity(customerQuantity);
	}

	public HostelOrderVO getOneByPaymentWay(String paymentWay) {
		return dao.findByPaymentWay(paymentWay);
	}

	public List<HostelOrderVO> getAllByPaymentWay(String paymentWay) {
		return dao.getAllByPaymentWay(paymentWay);
	}

	public HostelOrderVO getOneByPaymentState(String paymentState) {
		return dao.findByPaymentState(paymentState);
	}

	public List<HostelOrderVO> getAllByPaymentState(String paymentState) {
		return dao.getAllByPaymentState(paymentState);
	}

	public HostelOrderVO getOneByOrderRemark(String orderRemark) {
		return dao.findByOrderRemark(orderRemark);
	}

	public List<HostelOrderVO> getAllByOrderRemark(String orderRemark) {
		return dao.getAllByOrderRemark(orderRemark);
	}

	public HostelOrderVO getOneByOrderState(String orderState) {
		return dao.findByOrderState(orderState);
	}

	public List<HostelOrderVO> getAllByOrderState(String orderState) {
		return dao.getAllByOrderState(orderState);
	}


	
	public void updateComment (Integer hostelOrderNo, String hostelComment, Integer hostelScore) {
		dao.Comment(hostelOrderNo, hostelComment, hostelScore);
	}
}
