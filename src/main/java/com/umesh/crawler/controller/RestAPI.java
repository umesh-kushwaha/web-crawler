package com.umesh.crawler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.umesh.crawler.rest.model.BaseResponse;
import com.umesh.crawler.rest.model.StatusResponse;
import com.umesh.crawler.service.CrawlingService;

@RestController
public class RestAPI {
	

	
	@Autowired
	CrawlingService crawlingService;
	

	
	@GetMapping("/crawl")
	public ResponseEntity<BaseResponse> performCrawl(@RequestParam String baseUrl, @RequestParam int depth){
	
		return crawlingService.crawlPage(baseUrl, depth);
	}
	
	@GetMapping("/crawl/status")
	public ResponseEntity<StatusResponse> getStatus(@RequestParam String baseUrl){
		return crawlingService.getStatus(baseUrl);
	}

	@GetMapping("/crawl/data")
	public ResponseEntity<?> getCrawlData(@RequestParam String baseUrl){
		return crawlingService.getDetailByUrl(baseUrl);
	} 
}
