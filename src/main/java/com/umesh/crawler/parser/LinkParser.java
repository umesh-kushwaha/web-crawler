package com.umesh.crawler.parser;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.assertj.core.annotations.NonNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class LinkParser {

	public LinkParser getInstance() {
		return SingletonHelper.INSTANCE;
	}
	
	private static class SingletonHelper{
		public static final LinkParser INSTANCE = new LinkParser();
	}
	
	synchronized public Document getDocument(@NonNull String url) throws IOException {
		return Jsoup.connect(url).get();
	}
	
	synchronized public int getImageCount(@NonNull Document document) {
		return document.select("img").size();
	}
	
	synchronized public Set<String> getLinks(Document document){
		 Elements linksOnPage = document.select("a[href]");
		 Set<String> urls = new HashSet<>();
		 for (Element page : linksOnPage) {
         	urls.add(page.attr("abs:href"));
         }
		 
		 return urls;
	}
}
