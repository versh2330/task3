package by.tut.jobsearch;

import by.tut.pagesPOP.MainPage;
import by.tut.pagesPOP.ResultsPage;
import by.tut.pagesPOP.SearchPage;
import by.tut.pagesPOP.TutByPages;
import com.google.common.base.Function;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.annotations.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import static com.google.common.base.Ascii.toLowerCase;
import static org.testng.Assert.*;



/**
 * Created by Alexandr.Vershok on 3/15/2017.
 */
public class JobSearchTest {
    private String mainUrl;
    private WebDriver driver;
    private MainPage mainPage;
    private SearchPage searchPage;
    private ResultsPage resultsPage;
    private List<WebElement> searchResults;
    private Iterator iterator;
    private String searchQuery;
    private int resultsNumber = 0;
    private int fuzzyResultsNumber = 0;
    private List<String> partialHits;
    private List<String> exactHits;

    public void hitCount(List<WebElement> results, Iterator iterator) {
        partialHits = new ArrayList();
        exactHits = new ArrayList();
        searchResults = results;
        this.iterator = iterator;
        List<String> stringify = new ArrayList<>();
        for (WebElement result : searchResults) {
            stringify.add(result.getText());
        }

        for (String result : stringify) {

            if (result.matches(("(.*)" + searchQuery + "(.*)"))) { // Строгое
                exactHits.add(result);
                resultsNumber++;
            } else { // Нестрогое - проверяет наличие в результате одного из значимых (больше 3х символов) слов запроса
                 for (String fuzzyHit : searchQuery.split(" ")) {
                    if (fuzzyHit.length() > 3 && result.contains(fuzzyHit)) {
                        partialHits.add(result);
                        fuzzyResultsNumber++;
                    }

                }
            }


        }
    }

//    public void navigateTo(TutByPages tutByPages){
//        String goTo = tutByPages.getClass().toString();
//
//        switch (goTo){
//            case "class by.tut.pagesPOP.MainPage" :
//                driver.get(mainUrl);
//                break;
//            case "class by.tut.pagesPOP.SearchPage" :
//                driver.get("https://jobs.tut.by");
//        }
//    }

        @BeforeTest @Parameters({"mainPageUrl", "searchQuery"})
        public void setUp (String mainUrl, String searchQuery) throws Exception {
            this.mainUrl = mainUrl;
            this.searchQuery = searchQuery;
            Capabilities cap = DesiredCapabilities.chrome();
            driver = new ChromeDriver(cap);
            driver.manage().window().maximize();

        }

        @Test(priority = 1)
        public void testTutBy ()throws Exception {
            mainPage = new MainPage();
            mainPage.setMainPageUrl(mainUrl);
            driver.get(mainPage.getMainPageUrl());
            assertNotNull(driver.findElement(By.xpath("//div[@id=\"title_news_block\"]")));
            Wait<WebDriver> wait = new FluentWait(driver).withTimeout(25, TimeUnit.SECONDS).pollingEvery(5, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);
            WebElement workLink = wait.until(new Function<WebDriver, WebElement>() {
                @Override
                public WebElement apply(WebDriver input) {
                    return driver.findElement(By.xpath(mainPage.getJobLocator()));
                }
            });
            workLink.click();
            searchPage = new SearchPage();
            assertNotNull(driver.findElement(By.xpath(searchPage.findSearchField())));
            driver.findElement(By.xpath(searchPage.findSearchField())).sendKeys(searchQuery + Keys.ENTER);
            resultsPage = new ResultsPage();
            assertNotNull(driver.findElements(By.xpath(resultsPage.findSearchResults())));
            searchResults = driver.findElements(By.xpath(resultsPage.returnVacancies()));
            iterator = searchResults.iterator();
            hitCount(searchResults, iterator);
            System.out.println("\n" + "Строгое совпадение: " + resultsNumber);
            for(String s : exactHits){ System.out.println(s);   }
            System.out.println("\n" + "Нестрогое совпадение: " + fuzzyResultsNumber);
            for(String s : partialHits){ System.out.println(s); }


        }

//        @Parameters()
//        @Test(priority = 2)
//        public void goToTest() throws Exception{
//        navigateTo(new SearchPage());
//        assert....
//        Thread.sleep(10000);
//    }


    @AfterTest
    public void tearDown(){
        driver.quit();
    }
}