package by.tut.pagesPOP;


/**
 * Created by Alexandr.Vershok on 3/15/2017.
 */
public class SearchPage extends TutByPages{
    private String searchField = "//input[@name='text']";


    public String findSearchField(){
        return searchField;
    }
}
