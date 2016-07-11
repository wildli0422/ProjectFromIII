package com.jsf.controller;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.hostel.model.HostelService;
import com.hostel.model.HostelVO;
import com.jsf.DTO.IndexHostelDTO;

@RequestScoped
@ManagedBean(name = "indexHostelController")
public class IndexHostelController implements Serializable {

	private List<IndexHostelDTO> allRandomHostels;
	private List<IndexHostelDTO> nonRandomHostels;
	private String queryHostel;
	


	public List<String> suggestHostel(String query){
		System.out.println("query String = "+query);
		List<String> suggestList=new ArrayList<>();
		HostelService hostelService =new HostelService();
		List<HostelVO> hostels= hostelService.likeByColumn("hostelName", "%"+query+"%");
		for(int i=0;i<hostels.size();i++){
			suggestList.add(hostels.get(i).getHostelName());
		}
		return suggestList;
	}
	
	// to show pic method
	// http://stackoverflow.com/questions/8207325/display-dynamic-image-from-database-with-pgraphicimage-and-streamedcontent
	public StreamedContent getImage() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		if (facesContext.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			return new DefaultStreamedContent();
		} else {
			HostelService hostelService = new HostelService();
			int hostelNo = new Integer(facesContext.getExternalContext().getRequestParameterMap().get("hostelNo"));
			HostelVO hostelVO = hostelService.getOneHostel(hostelNo);
			return new DefaultStreamedContent(new ByteArrayInputStream(hostelVO.getHostelPicture()));
		}
	}

	@PostConstruct
	public void init() {
		allRandomHostels = new ArrayList<>();
		nonRandomHostels = new ArrayList<>();
		HostelService hostelService = new HostelService();
		List<HostelVO> allHostelVO = hostelService.getAll();
		for (int i = 0; i < 6; i++) {
			if (allHostelVO.size() == 0) {
				break;
			}
			int randomNumber = (int) (Math.random() * allHostelVO.size());
			HostelVO randomHostelVO = allHostelVO.remove(randomNumber);
			allRandomHostels.add(transformVOToDTO(randomHostelVO));
		}
		for (int i = 0; i < 6; i++) {
			if (allHostelVO.size() == 0) {
				break;
			}
			int randomNumber = (int) (Math.random() * allHostelVO.size());
			HostelVO randomHostelVO = allHostelVO.remove(randomNumber);
			nonRandomHostels.add(transformVOToDTO(randomHostelVO));
		}
	}

	private IndexHostelDTO transformVOToDTO(HostelVO hostelVO) {
		IndexHostelDTO indexHostelDTO = new IndexHostelDTO();
		indexHostelDTO.setHostelNo(hostelVO.getHostelNo().toString());
		indexHostelDTO.setHostelName(hostelVO.getHostelName());
		indexHostelDTO.setHostelAddress(hostelVO.getHostelAddress());
		indexHostelDTO.setHostelPhone(hostelVO.getHostelPhone());
		indexHostelDTO.setHostelContent(hostelVO.getHostelContent());
		return indexHostelDTO;
	}

	public List<IndexHostelDTO> getAllRandomHostels() {
		return allRandomHostels;
	}

	public void setAllRandomHostels(List<IndexHostelDTO> allRandomHostels) {
		this.allRandomHostels = allRandomHostels;
	}

	public List<IndexHostelDTO> getNonRandomHostels() {
		return nonRandomHostels;
	}

	public void setNonRandomHostels(List<IndexHostelDTO> nonRandomHostels) {
		nonRandomHostels = nonRandomHostels;
	}
	
	public String getQueryHostel() {
		return queryHostel;
	}

	public void setQueryHostel(String queryHostel) {
		this.queryHostel = queryHostel;
	}
}
