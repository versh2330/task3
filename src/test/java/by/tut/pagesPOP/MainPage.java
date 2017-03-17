package by.tut.pagesPOP;

/**
 * Created by Alexandr.Vershok on 3/15/2017.
 */
public class MainPage extends TutByPages{

    private String mainPageUrl;
    private String jobLocator = ".//a[@title=\"Работа\"]";


    public void setMainPageUrl(String mainPageUrl) throws Exception {
        this.mainPageUrl = mainPageUrl;
    }

    public String getMainPageUrl(){
        return mainPageUrl;
    }

    public String getJobLocator(){
        return jobLocator;
    }


}
