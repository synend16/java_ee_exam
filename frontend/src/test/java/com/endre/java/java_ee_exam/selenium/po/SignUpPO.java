package com.endre.java.java_ee_exam.selenium.po;

import com.endre.java.java_ee_exam.selenium.PageObject;
import org.openqa.selenium.By;

public class SignUpPO extends LayoutPO {


    public SignUpPO(PageObject other) {
        super(other);
    }

    //TODO: Check title value
    public boolean isOnPage() {
        return getDriver().getTitle().contains("Sign Up");
    }

    public IndexPO createUser(String userName, String password){

        setText("username", userName);
        setText("password", password);
        clickAndWait("submit");

        IndexPO po = new IndexPO(this);
        if (po.isOnPage()){
            return po;
        }
        return null;
    }

    public IndexPO createAdmin(String userName, String password){
        setText("username", userName);
        setText("password", password);
        driver.findElement(By.id("ceckIsAdmin")).click();
        clickAndWait("submit");

        IndexPO po = new IndexPO(this);
        if (po.isOnPage()){
            return po;
        }

        return null;
    }
}
