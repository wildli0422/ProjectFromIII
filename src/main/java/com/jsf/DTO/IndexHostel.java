package com.jsf.DTO;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.hostel.model.HostelService;

@ManagedBean
public class IndexHostel {
	private StreamedContent images;

	@PostConstruct
	public void init() {
		HostelService hostelService = new HostelService();
		byte[] hostelPicture = hostelService.getAll().get(1).getHostelPicture();
		images = new DefaultStreamedContent(new ByteArrayInputStream(hostelPicture));
	}

	public StreamedContent getImages() {
		return images;
	}

	public void setImages(StreamedContent images) {
		this.images = images;
	}

}
