package com.umesh.crawler.service;

import org.springframework.http.ResponseEntity;

import com.umesh.crawler.rest.model.BaseResponse;
import com.umesh.crawler.rest.model.StatusResponse;

public interface CrawlingService {
	
	ResponseEntity<StatusResponse> getStatus(final String url);
	
	ResponseEntity<BaseResponse> crawlPage(final String url, final int depth);
	
	ResponseEntity<?> getDetailByUrl(final String url);

}
