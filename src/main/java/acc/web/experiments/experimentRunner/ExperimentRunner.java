package acc.web.experiments.experimentRunner;

import acc.web.crawler.EmailCrawler;
import acc.web.crawler.api.ICrawler;
import acc.web.experiments.apiAccess.ParseCmd;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import java.util.Arrays;
import java.util.List;

public class ExperimentRunner {

    public static void main(String[] args) {

        Options options = new Options();
        Option cSeedI = new Option("s","cSeed",true,"Urls of crawler seeds");
        cSeedI.setRequired(true);
        options.addOption(cSeedI);

        Option maxI = new Option("m","max",true,"Number of maximum pages to crawl");
        maxI.setRequired(true);
        options.addOption(maxI);

        Option threadI = new Option("t","threads",true,"Number of threads");
        threadI.setRequired(true);
        options.addOption(threadI);

        Option cTypeI = new Option("c","crawlerType",true,"Crawler type - pass id (integer). " +
                ICrawler.CrawlerTypeEnum.printValues());
        cTypeI.setRequired(true);
        options.addOption(cTypeI);

        CommandLine cmd = ParseCmd.parse(options, args);
        List<String> seedUrls = Arrays.asList(cmd.getOptionValues("cSeed"));
        int maxSearch = Integer.parseInt(cmd.getOptionValue("max"));
        int threads = Integer.parseInt(cmd.getOptionValue("threads"));
        ICrawler.CrawlerTypeEnum crawlerTypeEnum = ICrawler.CrawlerTypeEnum.getById
                (Integer.parseInt(cmd.getOptionValue("crawlerType")));

        try {
            ICrawler crawler = getCrawler (crawlerTypeEnum,seedUrls,maxSearch,threads);
            crawler.crawl();

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    private static ICrawler getCrawler(ICrawler.CrawlerTypeEnum crawlerTypeEnum,
                                       List<String> seedUrls,
                                       int maxSearch, int threads) throws Exception {

        switch (crawlerTypeEnum){
            case Email:
                return new EmailCrawler(maxSearch,seedUrls,threads);
            default:
                throw new Exception("Cannot resolve: " + crawlerTypeEnum.name());
        }
    }
}
