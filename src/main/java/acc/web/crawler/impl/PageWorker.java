package acc.web.crawler.impl;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.concurrent.Callable;

import static acc.web.crawler.api.ICrawler.REFFERRER;
import static acc.web.crawler.api.ICrawler.TIMEOUT;
import static acc.web.crawler.api.ICrawler.USER_AGENT_BROWSER;

public class PageWorker implements Callable<WebPage>{

    private WebPage webPage;
    private String currentUrl;
    public PageWorker(String url){
         this.webPage = new WebPage(url);
         this.currentUrl = url;
    }

    /**
     * Open an http request, parse the Web page and extract all the out urls
     */
    public void parseUrl() throws Exception {
            Connection connection = Jsoup.connect(this.currentUrl).userAgent(USER_AGENT_BROWSER).
                    referrer(REFFERRER).timeout(TIMEOUT);
            Document htmlDocument = connection.get();
            this.webPage.setHtmlDoc(htmlDocument);
            System.out.println("Found a web page at " + this.currentUrl);
            Elements linksOnPage = this.webPage.getHtmlDoc().select("a[href]");
            System.out.println("Found (" + linksOnPage.size() + ") links");
            for(Element link : linksOnPage)
            {
                this.webPage.addUrl(link.absUrl("href"));
            }
    }

    @Override
    public WebPage call() throws Exception {
        parseUrl();
        return this.webPage;
    }
}
