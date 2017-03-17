package by.tut.pagesPOP;

/**
 * Created by Alexandr.Vershok on 3/15/2017.
 */
public class ResultsPage {

    private String searchResults = "//div[@class=\"search-result\"]";
    private String vacancies = "//div[@class=\"search-result-item__head\"]/a";

    public String findSearchResults(){
        return searchResults;
    }

    public String returnVacancies(){
        return vacancies;
    }


}
