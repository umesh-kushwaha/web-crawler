package com.umesh.crawler.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="page_detail")
public class PageDetail {

	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Id
	long id;
	
	long pageId;
	String pageTitle;
	
	String pageUrl;
	
	int imageCount;

	/*@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="pageId", insertable = false, updatable = false)
    private HomePage homePage;*/
	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPageTitle() {
		return pageTitle;
	}

	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}

	public String getPageUrl() {
		return pageUrl;
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}

	public int getImageCount() {
		return imageCount;
	}

	public void setImageCount(int
			imageCount) {
		this.imageCount = imageCount;
	}

	public long getPageId() {
		return pageId;
	}

	public void setPageId(long pageId) {
		this.pageId = pageId;
	}
	
	
}
