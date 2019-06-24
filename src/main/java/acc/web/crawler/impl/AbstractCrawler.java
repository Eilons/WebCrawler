package acc.web.crawler.impl;

import acc.web.crawler.api.ICrawler;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public abstract class AbstractCrawler implements ICrawler {

    protected final int numCrawelingPages; //The max searched pages
    protected LinkedList<String> queue = new LinkedList<String>(); // bfs queue
    protected Set<String> seenPagesSet; // keep the urls of the seen pages
    protected Set<String> visitedPagesSet; // keep the urls of the visited pages

    public AbstractCrawler (int numCrawelingPages, List<String> crawlerSeeds){
        this.numCrawelingPages = numCrawelingPages;
        this.queue.addAll(crawlerSeeds);
        this.seenPagesSet = new HashSet<String>();
        this.visitedPagesSet = new HashSet<String>();
    }

    public synchronized void updateNewOuthyperlinks(List<String> urlCandidates) {
        for (int i=0; i<urlCandidates.size(); i++){
            if (!this.seenPagesSet.contains(urlCandidates.get(i))){
                this.queue.add(urlCandidates.get(i));
            }
        }
    }
}
