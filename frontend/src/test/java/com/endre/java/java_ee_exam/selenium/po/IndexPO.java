package com.endre.java.java_ee_exam.selenium.po;

import com.endre.java.java_ee_exam.selenium.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class IndexPO extends LayoutPO{

    public IndexPO(WebDriver driver, String host, int port){
        super(driver, host, port);
    }

    public IndexPO(PageObject other) {
        super(other);
    }

    public void toStartPage(){
        getDriver().get(host + ":" + port);
    }

    //TODO: Check this if i change title in index page
    public boolean isOnPage() {
        return getDriver().getTitle().contains("Home");
    }


    public BookDetailPO goToDetailsPage(int row){
        getDriver().findElement(By.xpath("//A[@id='allBooksTable:"+row+":titleForm:goToDetailsId']")).click();
        waitForPageToLoad();

        BookDetailPO po = new BookDetailPO(this);
        assertTrue(po.isOnPage());

        return po;
    }

    public int getNumberOfDisplayedBooks(){
        List<WebElement> elements = new ArrayList<>();
        try{
            elements = driver.findElements(
                    By.xpath("//table[@id='allBooksTable']//tbody//tr[string-length(text()) > 0]"));
        }catch (NoSuchElementException e){}
        return elements.size();
    }


    public int getNumberOfColumnsToSeeIfSellColumnIsMissing(){
        List<WebElement> elements = new ArrayList<>();
        try{
            elements = driver.findElements(
                    By.xpath("//table[@id='allBooksTable']//tbody//tr[1]//td"));
        }catch (NoSuchElementException e){}
        return elements.size();
    }


    public void markSellBook(int row){
        getDriver().findElement(By.xpath("//INPUT[@id='allBooksTable:"+row+":idAddUserToBookId']")).click();
        waitForPageToLoad();
    }

    public void unmarkSellBook(int row){
        getDriver().findElement(By.xpath("//INPUT[@id='allBooksTable:"+row+":idRemoveUserFromBookId']")).click();
        waitForPageToLoad();
    }

    public String getNumberOfSellers(int row){
        return getDriver().findElement(By.xpath("//LABEL[@id='allBooksTable:"+row+":numberOfSellers']")).getText();
    }

}
