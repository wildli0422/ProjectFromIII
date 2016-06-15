/*?��程�?�是使用Service?��??�器?��??�產??? copyRight:wildli0422@gmail.com ??��?��?條柴*/
package com.hostelTrace.model;
import java.util.List;
import java.sql.*;

public class HostelTraceService {

  private HostelTraceDAO_interface dao;

  public HostelTraceService() {
		dao = new HostelTraceDAO();
	}

	public HostelTraceVO addHostelTrace(Integer hostelNo,Integer tenantNo) {
HostelTraceVO hostelTraceVO = new HostelTraceVO();

  hostelTraceVO.setHostelNo(hostelNo);
  hostelTraceVO.setTenantNo(tenantNo);
		dao.insert(hostelTraceVO);

		return hostelTraceVO;
}

	public HostelTraceVO updateHostelTrace(Integer hostelTraceNo,Integer hostelNo,Integer tenantNo,java.sql.Date viewDate) {
HostelTraceVO hostelTraceVO = new HostelTraceVO();

  hostelTraceVO.setHostelTraceNo(hostelTraceNo);
  hostelTraceVO.setHostelNo(hostelNo);
  hostelTraceVO.setTenantNo(tenantNo);
  hostelTraceVO.setViewDate(viewDate);
		dao.update(hostelTraceVO);

		return hostelTraceVO;
}

	public void deleteHostelTrace(Integer hostelTraceNo) {
		dao.delete(hostelTraceNo);
	}
 public HostelTraceVO getOneHostelTrace(Integer hostelTraceNo) {
return dao.findByPrimaryKey(hostelTraceNo);
	}
 public List<HostelTraceVO> getAll() {
		return dao.getAll();
}
//from html input start to do some onDemand method 
    public HostelTraceVO getOneByHostelNo(Integer hostelNo) {
     return dao.findByHostelNo(hostelNo);
  }

    public List<HostelTraceVO> getAllByHostelNo(Integer hostelNo) {
     return dao.getAllByHostelNo(hostelNo);
  }

    public HostelTraceVO getOneByTenantNo(Integer tenantNo) {
     return dao.findByTenantNo(tenantNo);
  }

    public List<HostelTraceVO> getAllByTenantNo(Integer tenantNo) {
     return dao.getAllByTenantNo(tenantNo);
  }

    public HostelTraceVO getOneByViewDate(java.sql.Date viewDate) {
     return dao.findByViewDate(viewDate);
  }

    public List<HostelTraceVO> getAllByViewDate(java.sql.Date viewDate) {
     return dao.getAllByViewDate(viewDate);
  }


}
