package acc.web.crawler.api;

public interface ICrawler {

    final String USER_AGENT_BROWSER = "Chrome/13.0.782.112"; // Identify as a normal web user.
    final String REFFERRER = "http://www.google.com"; // Identify as a normal web user.
    final int TIMEOUT = 20000; // Identify as a normal web user.

    /**
     * Enum for different crawler type
     */
    enum CrawlerTypeEnum{
        Email(1);

        private final int id;

        CrawlerTypeEnum (int cId){
            this.id = cId;
        }
        public static CrawlerTypeEnum getById(int id) {
            for(CrawlerTypeEnum c : values()) {
                if(c.id == id) return c;
            }
            return Email;//default
        }

        public static String printValues (){
            StringBuffer stringBuffer = new StringBuffer();
            for (CrawlerTypeEnum crawlerTypeEnum : CrawlerTypeEnum.values()){
                stringBuffer.append(crawlerTypeEnum.name() + "-" + crawlerTypeEnum.id);
            }
            return stringBuffer.toString();
        }

    }

    void crawl();

}
