package com.emp.model;

import java.util.List;
import java.util.Map;

import com.tenant.model.TenantVO;



public class EmpService {
	private EmpDAO dao;
	public EmpService() {
		dao = new EmpDAO();
	}

	public Integer getEmpNo(String empAccount){
		return dao.findEmpNo(empAccount);
	}
	public EmpVO getOneEmp(Integer empNo){
		return dao.findByPrimaryKey(empNo);
	}
	
	public String getEmpPassword(String empAccount){
		return dao.findEmpPassword(empAccount);
	}
	
	public List<EmpVO> getListByName(String empName) {
		return dao.getListByName(empName);
	}
	
	public List<EmpVO> getAll(Map<String, String[]> map) {
		return dao.getAll(map);
	}
	
	public EmpVO updateEmp(String empPassword, String empName, String empSex,
			String empAddress, String empMail, String empPhone, Integer empNo) {

		EmpVO empVO = new EmpVO();

		empVO.setEmpPassword(empPassword);
		empVO.setEmpName(empName);
		empVO.setEmpSex(empSex);
		empVO.setEmpAddress(empAddress);
		empVO.setEmpMail(empMail);
		empVO.setEmpPhone(empPhone);
		
		empVO.setEmpNo(empNo);
		dao.update(empVO);

		return empVO;
	}

	public void deleteEmp(Integer empNo) {
		dao.delete(empNo);
	}
	
	public EmpVO addEmp(String empAccount, String empPassword, String empName, String empSex,
			String empAddress, String empMail, String empPhone, byte[] empPic) {
		EmpVO empVO = new EmpVO();
		empVO.setEmpAccount(empAccount);
		empVO.setEmpPassword(empPassword);
		empVO.setEmpName(empName);
		empVO.setEmpSex(empSex);
		empVO.setEmpAddress(empAddress);
		empVO.setEmpMail(empMail);
		empVO.setEmpPhone(empPhone);		
		empVO.setEmpPic(empPic);
		
		
		
		return dao.insert(empVO);
	}

	
}
