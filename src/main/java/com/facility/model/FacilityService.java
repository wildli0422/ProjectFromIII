package com.facility.model;

import java.util.List;

public class FacilityService {
	private FacilityDAO_interface dao;
	
	public FacilityService(){
		dao=new FacilityDAO();
	}
	
	public FacilityVO addFacility(Integer television,Integer wifi,Integer kitchen,
			Integer parking,Integer bathroom,Integer airConditioning,Integer pet,
			Integer toiletries,Integer roomPhone,Integer roomBedService){
		
		FacilityVO facilityVO =new FacilityVO();
		
		facilityVO.setTelevision(television);
		facilityVO.setWifi(wifi);
		facilityVO.setKitchen(kitchen);
		facilityVO.setParking(parking);
		facilityVO.setBathroom(bathroom);
		facilityVO.setAirConditioning(airConditioning);
		facilityVO.setPet(pet);
		facilityVO.setToiletries(toiletries);
		facilityVO.setRoomPhone(roomPhone);
		facilityVO.setRoomBedService(roomBedService);
		
		dao.insert(facilityVO);
		
		return facilityVO;
	}
	
	public FacilityVO updateFacility(Integer facilityNo,Integer television,Integer wifi,Integer kitchen,
			Integer parking,Integer bathroom,Integer airConditioning,Integer pet,
			Integer toiletries,Integer roomPhone,Integer roomBedService){
		
		FacilityVO facilityVO =new FacilityVO();
		
		facilityVO.setFacilityNo(facilityNo);
		facilityVO.setTelevision(television);
		facilityVO.setWifi(wifi);
		facilityVO.setKitchen(kitchen);
		facilityVO.setParking(parking);
		facilityVO.setBathroom(bathroom);
		facilityVO.setAirConditioning(airConditioning);
		facilityVO.setPet(pet);
		facilityVO.setToiletries(toiletries);
		facilityVO.setRoomPhone(roomPhone);
		facilityVO.setRoomBedService(roomBedService);
		
		dao.update(facilityVO);
		
		return facilityVO;
	}
	
	public void deleteFacility(Integer facilityNo){
		dao.delete(facilityNo);
	}
	public FacilityVO getOneFacility(Integer facilityNo){
		return dao.findByPrimaryKey(facilityNo);
	}
	public List<FacilityVO> getAll(){
		return dao.getAll();
	}
	
	
}
