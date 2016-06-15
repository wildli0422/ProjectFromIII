package com.facility.model;

import java.util.List;


public interface FacilityDAO_interface {
	
	public void insert(FacilityVO facilityVO);
    public void update(FacilityVO facilityVO);
    public void delete(Integer facilityNo);
    public FacilityVO findByPrimaryKey(Integer facilityNo);
    public List<FacilityVO> getAll();
}
