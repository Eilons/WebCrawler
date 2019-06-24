Web Crawler
This project is a Java implementation of a Web crawler that collects valid e-mail addresses accross the Web.

Getting Started
This is a maven project (3.5.2) that was built using java version 1.7.0
Use the Maven build tool to create a single executable jar as follows.
Inside the project directory type: "mvn clean install -DskipTests"

Run the crawler
To run the project you should provide the following arguments:
"-s" (cSeed) - Crawler seeds (URLs). To provide several seeds just type: -s URL1 -s URL2 -s URL3 ...
"-m" (max) - Number of maximum pages to crawl 
"-c" (crawlerType) - The id of the requested crawler. Currently, there is only an email crawler.
"-t" (threads) - Number of threads for parallel computing

java -jar "the executable jar" -s "crawler seeds" -m "Number of maximum pages to crawl (integer number)" -c "crawler type (integer id: 1)" -t 2
press Enter.

Adding more crawlers
Follow the next simple steps to add more crawlers:
1. Add a java class and extends the "AbstractCrawler" class.
2. Implement the crawel() method.
3. In the interface calss, "Icrawler", update the enum class "CrawlerTypeEnum" to include your new crawler if a unique ID.
4. In the main class, "ExperimentRunner", add a new "case" in the "getCrawler" method to include the type of your created crawler.
5. Done :)

Author
Eilon Sheetrit





