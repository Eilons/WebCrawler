package acc.web.crawler;

import acc.web.crawler.impl.AbstractCrawler;
import acc.web.crawler.impl.PageWorker;
import acc.web.crawler.impl.WebPage;
import org.jsoup.nodes.Document;

import java.util.*;
import java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class EmailCrawler extends AbstractCrawler {

    Set<String> crawledEmailSet;
    List<Future<WebPage>> futures;
    private ExecutorService execService;

    public EmailCrawler (int numCrawelingPages, List<String> crawlerSeeds, int threads){
        super(numCrawelingPages,crawlerSeeds);
        this.crawledEmailSet = new HashSet<String>();
        this.execService = Executors.newFixedThreadPool(threads);
        this.futures = Collections.synchronizedList(new LinkedList<Future<WebPage>>());
    }

    @Override
    public void crawl() {
        // submit seeds
        addNewURLs();
        while (checkCrawling() && this.visitedPagesSet.size() <= this.numCrawelingPages);
        this.execService.shutdownNow();
        printEmails();
    }

    private void printEmails() {
        System.out.println("Emails:");
        for (String email : this.crawledEmailSet) {
            System.out.println(email);
        }
    }

    private boolean checkCrawling() {
        try {
            Iterator<Future<WebPage>> iterator = futures.iterator();
            while (iterator.hasNext()) {
                Future<WebPage> future = iterator.next();
                if (future.isDone()) {
                    iterator.remove();
                    WebPage webPage = future.get();
                    this.visitedPagesSet.add(webPage.getUrl());
                    // update queue
                    if (webPage.getUrlLinks().size() > 0) {
                        updateNewOuthyperlinks(webPage.getUrlLinks());
                    }
                    //Extract emails
                    extractEmails(webPage.getHtmlDoc());
                }
            }
        } catch (InterruptedException e) {
        } catch (ExecutionException e) {
        }
        addNewURLs();
        return (futures.size()>0);
    }

    private void addNewURLs() {
        while (!queue.isEmpty()){
            String url = this.queue.remove();
            submitNewUrl (url);
        }
    }

    private void submitNewUrl(String url) {
        if (this.seenPagesSet.contains(url)) return;
        this.seenPagesSet.add(url);
        PageWorker pageWorker = new PageWorker(url);
        this.futures.add(this.execService.submit(pageWorker));
    }


    private void extractEmails(Document htmlDoc) {
        if (htmlDoc != null) { // valid pages
            Pattern p = Pattern.compile("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+");
            Matcher matcher = p.matcher(htmlDoc.text());
            while (matcher.find()) {
                this.crawledEmailSet.add(matcher.group());
            }
        }
    }
}
