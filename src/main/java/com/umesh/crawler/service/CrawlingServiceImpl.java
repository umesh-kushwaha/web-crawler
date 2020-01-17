package com.umesh.crawler.service;

import java.util.concurrent.ExecutorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.umesh.crawler.concurrency.CrawlPage;
import com.umesh.crawler.model.HomePage;
import com.umesh.crawler.repository.HomePageRepository;
import com.umesh.crawler.rest.model.BaseResponse;
import com.umesh.crawler.rest.model.StatusResponse;
import com.umesh.crawler.utils.ProcessingStatus;

@Service
public class CrawlingServiceImpl implements CrawlingService{

	@Autowired
	HomePageRepository homePageRepository;
	
	@Autowired
	CrawlingService crawlingService;
	

	@Autowired
	ExecutorService executorService;
	
	@Override
	public ResponseEntity<StatusResponse> getStatus(String url) {
		HomePage homePage = homePageRepository.findByUrl(url);
		StatusResponse statusResponse = new StatusResponse();
		HttpStatus httpStatus;
		if(homePage != null) {
			statusResponse.setMessage(homePage.getStatus());
			statusResponse.setStatus(homePage.getStatus());
			httpStatus = HttpStatus.OK;
		}else {
			statusResponse.setMessage("URL not found in our database");
			httpStatus = HttpStatus.NOT_FOUND;
		}
		return new ResponseEntity<StatusResponse>(statusResponse, httpStatus);
	}

	@Override
	public ResponseEntity<BaseResponse> crawlPage(String url, int depth) {
		HomePage homePage = homePageRepository.findByUrl(url);
		BaseResponse baseResponse = new BaseResponse();
		if(homePage == null) {
			homePage = new HomePage();
			homePage.setDepth(depth);
			homePage.setUrl(url);
			homePage.setStatus(ProcessingStatus.SUBMITTED.toString());
			homePageRepository.save(homePage);
			executorService.submit(new CrawlPage(url, depth, homePageRepository));
		}
		baseResponse.setMessage("Request accepted");

		return new ResponseEntity<BaseResponse>(baseResponse, HttpStatus.ACCEPTED);
	}

	@Override
	public ResponseEntity<?> getDetailByUrl(String url) {
		HomePage homePage = homePageRepository.findByUrl(url);
		BaseResponse baseResponse = new BaseResponse();
		if(homePage == null) {
			
			baseResponse.setMessage("URL is not found in our database.");
			return new ResponseEntity<BaseResponse>(baseResponse, HttpStatus.NOT_FOUND);
		}else if( !homePage.getStatus().equals(ProcessingStatus.PROCESSED.toString())) {
			baseResponse.setMessage("Crawling is under processing");
			return new ResponseEntity<BaseResponse>(baseResponse, HttpStatus.OK);
		}else {
			return new ResponseEntity<HomePage>(homePage, HttpStatus.OK);
		}
		
	}

}
