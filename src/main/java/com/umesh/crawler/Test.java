package com.umesh.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;

public class Test {
    private static final int MAX_DEPTH = 2;
    private HashSet<String> links;

    public Test() {
        links = new HashSet<>();
    }

    public void getPageLinks(String URL, int depth) {
        if ((!links.contains(URL) && (depth < MAX_DEPTH))) {
            System.out.println(">> Depth: " + depth + " [" + URL + "]");
            try {
                links.add(URL);

                Document document = Jsoup.connect(URL).get();
                Elements linksOnPage = document.select("a[href]");

                depth++;
                for (Element page : linksOnPage) {
                	
                    getPageLinks(page.attr("abs:href"), depth);
                }
            } catch (IOException e) {
                System.err.println("For '" + URL + "': " + e.getMessage());
            }catch(Exception e) {
            	 System.err.println("For '" + URL + "': " + e.getMessage());
            }
        }
        
        //System.out.println("Finished================");
    }

    public static void main(String[] args) {
        new Test().getPageLinks("https://www.baeldung.com/", 0);
        System.out.println("Finished================");
    }
}
