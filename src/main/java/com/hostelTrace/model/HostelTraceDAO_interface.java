/*?��程�?�是使用Interface?��??�器?��??�產??? copyRight:wildli0422@gmail.com ??��?��?條柴*/
package com.hostelTrace.model;
import java.sql.Date;
import java.util.*;

public interface HostelTraceDAO_interface {

    public void insert(HostelTraceVO hostelTraceVO);
    public void update(HostelTraceVO hostelTraceVO);
	public List<HostelTraceVO> likeByColumn(String columnName,String likeString);
    public void delete(Integer hostelTraceNo);
    public HostelTraceVO findByPrimaryKey(Integer hostelTraceNo);
    public List<HostelTraceVO> getAll();

//from html input start to do some onDemand method 
    public HostelTraceVO findByHostelNo(Integer hostelNo);
    public List<HostelTraceVO> getAllByHostelNo(Integer hostelNo);
    public HostelTraceVO findByTenantNo(Integer tenantNo);
    public List<HostelTraceVO> getAllByTenantNo(Integer tenantNo);
    public HostelTraceVO findByViewDate(java.sql.Date viewDate);
    public List<HostelTraceVO> getAllByViewDate(java.sql.Date viewDate);

}