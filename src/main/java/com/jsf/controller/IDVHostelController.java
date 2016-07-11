package com.jsf.controller;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.hostel.model.HostelService;
import com.hostel.model.HostelVO;
import com.hostelPic.model.HostelPicService;
import com.hostelPic.model.HostelPicVO;

@RequestScoped
@ManagedBean(name = "IDVHostelController")
public class IDVHostelController implements Serializable {

	private String IDVHostelNo ="2002";
	private HostelService hostelService;
	private HostelPicService hostelPicService;
	private List<Integer> hostelPicNos;

	@PostConstruct
	public void init() {
		hostelPicService = new HostelPicService();
		hostelService = new HostelService();
	}

	public StreamedContent getImage() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		if (facesContext.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			return new DefaultStreamedContent();
		} else {
			int hostelPicNo = new Integer(
					facesContext.getExternalContext().getRequestParameterMap().get("hostelPicNo"));
			System.out.println("wanna output image no is "+ hostelPicNo);
			HostelPicVO hoPicVO = hostelPicService.getOneHostelPic(hostelPicNo);
			System.out.println("hostelPicVO = "+hoPicVO);
			return new DefaultStreamedContent(new ByteArrayInputStream(hoPicVO.getHostelPhoto()));
		}
	}

	public List<Integer> getHostelPicNos() {

		List<HostelPicVO> hostelPicVOs = hostelPicService.getAll();
		hostelPicNos = new ArrayList<>();
		for (HostelPicVO hostelPicVO : hostelPicVOs) {
			String hostelNo = hostelPicVO.getHostelNo().toString();
			System.out.println("is " + hostelNo + " equals " + IDVHostelNo + "  ? ");
//			if (hostelNo.equals(IDVHostelNo)) {
				System.out.println("yes , put it into list ");
				hostelPicNos.add(new Integer(hostelPicVO.getHostelPicNo()));
//			}
		}
		System.out.println("hostelPicNos.size = "+hostelPicNos.size());
		return hostelPicNos;

	}

	public void setHostelPicNos(List<Integer> hostelPicNos) {
		this.hostelPicNos = hostelPicNos;
	}

	public String getIDVHostelNo() {
		return IDVHostelNo;
	}

	public void setIDVHostelNo(String iDVHostelNo) {
		IDVHostelNo = iDVHostelNo;
	}

}
