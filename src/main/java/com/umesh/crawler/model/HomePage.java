package com.umesh.crawler.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.GenerationType;

@Entity
@Table(name="home_page")
public class HomePage {
	
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Id
	long id;
	
	String url;
	
	int depth;
	String status;
	int totalLinks;
	int totalImages;
	
	@OneToMany( cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="pageId")
	Set<PageDetail> pageDetail = new HashSet<>();
	
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getTotalLinks() {
		return totalLinks;
	}
	public void setTotalLinks(int totalLinks) {
		this.totalLinks = totalLinks;
	}
	public int getTotalImages() {
		return totalImages;
	}
	public void setTotalImages(int totalImgages) {
		this.totalImages = totalImgages;
	}
	public Set<PageDetail> getPageDetail() {
		return pageDetail;
	}
	public void setPageDetail(Set<PageDetail> pageDetail) {
		this.pageDetail = pageDetail;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	

}
