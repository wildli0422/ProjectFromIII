/*�蝔�雿輻Interface��������� copyRight:wildli0422@gmail.com ����璇*/
package com.hostelOrder.model;
import java.sql.Date;
import java.util.*;

import com.hostelOrderDetail.model.HostelOrderDetailVO;

public interface HostelOrderDAO_interface {

    public void insert(HostelOrderVO hostelOrderVO);
    public void update(HostelOrderVO hostelOrderVO);
	public List<HostelOrderVO> likeByColumn(String columnName,String likeString);
    public void delete(Integer hostelOrderNo);
    public HostelOrderVO findByPrimaryKey(Integer hostelOrderNo);
    public List<HostelOrderVO> getAll();
    
    //NEW**************************
    public List<HostelOrderVO> getAll(Map<String,String[]> map);
    public List<HostelOrderVO> getAllByDate(String orderDate);
    public List<HostelOrderVO> getVerifyByDate(Date orderDate);
    
    public List<HostelOrderDetailVO> getHostelOrderDetail(Integer hostelOrderNo);
    public HostelOrderVO insertWithDetail(HostelOrderVO hostelOrderVO,List<HostelOrderDetailVO> list);
    //NEW*************************
    
//from html input start to do some onDemand method 
    public HostelOrderVO findByHostelNo(Integer hostelNo);
    public List<HostelOrderVO> getAllByHostelNo(Integer hostelNo);
    public HostelOrderVO findByTenantNo(Integer tenantNo);
    public List<HostelOrderVO> getAllByTenantNo(Integer tenantNo);
    public HostelOrderVO findByHostelOrderDate(java.sql.Date hostelOrderDate);
    public List<HostelOrderVO> getAllByHostelOrderDate(java.sql.Date hostelOrderDate);
    public HostelOrderVO findByHostelScore(Integer hostelScore);
    public List<HostelOrderVO> getAllByHostelScore(Integer hostelScore);
    public HostelOrderVO findByHostelComment(String hostelComment);
    public List<HostelOrderVO> getAllByHostelComment(String hostelComment);
    public HostelOrderVO findByTenantScore(Integer tenantScore);
    public List<HostelOrderVO> getAllByTenantScore(Integer tenantScore);
    public HostelOrderVO findByCustomerQuantity(Integer customerQuantity);
    public List<HostelOrderVO> getAllByCustomerQuantity(Integer customerQuantity);
    public HostelOrderVO findByPaymentWay(String paymentWay);
    public List<HostelOrderVO> getAllByPaymentWay(String paymentWay);
    public HostelOrderVO findByPaymentState(String paymentState);
    public List<HostelOrderVO> getAllByPaymentState(String paymentState);
    public HostelOrderVO findByOrderRemark(String orderRemark);
    public List<HostelOrderVO> getAllByOrderRemark(String orderRemark);
    public HostelOrderVO findByOrderState(String orderState);
    public List<HostelOrderVO> getAllByOrderState(String orderState);

    
    public void Comment (Integer hostelOrderNo, String hostelComment, Integer hostelScore);
    
}