package acc.web.crawler.impl;

import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;

public class WebPage {

    private List<String> urlLinks;
    private Document htmlDoc; // Keep the Web page (a document)
    private String url;

    public WebPage(String url){this.urlLinks = new ArrayList<String>(); this.url=url;}

    public void setHtmlDoc(Document doc){this.htmlDoc = doc;}
    public void addUrl (String url) {this.urlLinks.add(url);}
    public List<String> getUrlLinks(){return this.urlLinks;}
    public Document getHtmlDoc() {return this.htmlDoc;}
    public String getUrl() {return this.url;}
}
