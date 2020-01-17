package com.umesh.crawler.concurrency;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.umesh.crawler.model.HomePage;
import com.umesh.crawler.model.PageDetail;
import com.umesh.crawler.repository.HomePageRepository;
import com.umesh.crawler.utils.ProcessingStatus;

public class CrawlPage implements Runnable{

	String homePageUrl;
	int maxDepth;
	Map<String, PageDetail> pageDetailMap;
	private Logger logger = LoggerFactory.getLogger(CrawlPage.class);
	
	HomePageRepository homePageRepository;
	
	
	
	public CrawlPage(String homePageUrl, int maxDepth, 
			HomePageRepository homePageRepository) {
		super();
		this.homePageUrl = homePageUrl;
		this.maxDepth = maxDepth;
		this.pageDetailMap = new HashMap<String, PageDetail>();
		this.homePageRepository = homePageRepository;
	}



	@Override
	public void run() {
		try {
		HomePage homePage = homePageRepository.findByUrl(this.homePageUrl);
		homePage.setStatus(ProcessingStatus.IN_PROCESS.toString());
		homePageRepository.save(homePage);
		
		processPage(this.homePageUrl, 0, homePage.getId());
		
		Set<PageDetail> pageDetail = pageDetailMap.values().stream()
				.collect(Collectors.toSet());
		homePage.getPageDetail().addAll(pageDetail);
		homePage.setStatus(ProcessingStatus.PROCESSED.toString());
		homePage.setTotalLinks(pageDetail.size());
		int totalImgLinks = pageDetail.stream().collect(Collectors.summingInt(PageDetail::getImageCount));
		homePage.setTotalImages(totalImgLinks);
		homePageRepository.save(homePage);
        logger.info(">> Depth: finished>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void processPage(String url, int depth, long pageId) {
		 if ((!pageDetailMap.containsKey(url) && (depth < maxDepth))) {
	            logger.info(">> Depth: " + depth + " [" + url + "]");
	            depth++;
	            try {
	            	PageDetail pageDetail = new PageDetail();
	            	pageDetail.setPageUrl(url);
	            	pageDetail.setPageId(pageId);
	            	pageDetailMap.put(url, pageDetail);

	                Document document = Jsoup.connect(url).get();
	                Elements linksOnPage = document.select("a[href]");

	                Elements imageElements = document.select("img");
	                pageDetail.setImageCount(imageElements.size());
	                pageDetail.setPageTitle(document.title());
	                for (Element page : linksOnPage) {
	                	processPage(page.attr("abs:href"), depth,pageId);
	                }
	            } catch (IOException e) {
	            	logger.error("For '" + url + "': " + e.getMessage());
	            }catch(Exception e) {
	            	logger.error("For '" + url + "': " + e.getMessage());
	            }
	        }
	}

}
