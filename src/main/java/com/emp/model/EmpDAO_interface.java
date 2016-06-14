package com.emp.model;

import java.util.*;

public interface EmpDAO_interface {
	public EmpVO insert(EmpVO empVo);
	public void update(EmpVO empVO);
	public void delete(Integer empNo);
	public EmpVO findByPrimaryKey(Integer empNo);
	
	

}
