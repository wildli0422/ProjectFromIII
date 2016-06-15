package com.accessemp.model;

import java.util.List;



public class AccessEmpService {
	private AccessEmpDAO dao;
	public AccessEmpService() {
		dao = new AccessEmpDAO();
	}
	
	public List<Integer> getEmpAccess(Integer empNo) {
		return dao.getEmpAccess(empNo);
	}
	
	public AccessEmpVO grantAccess(Integer empNo, Integer accessNo) {
		AccessEmpVO accessEmpVO = new AccessEmpVO();
		accessEmpVO.setEmpNo(empNo);
		accessEmpVO.setAccessNo(accessNo);
		dao.insert(accessEmpVO);
		return accessEmpVO;
	}
	
	public void revokeAccess(AccessEmpVO accessEmpVO) {
		dao.delete(accessEmpVO);
	}
	
	public void revokeAccessAll(Integer empNo) {
		dao.deleteAll(empNo);
	}
}
