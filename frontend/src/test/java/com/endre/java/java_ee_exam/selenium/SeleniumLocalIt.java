package com.endre.java.java_ee_exam.selenium;

import com.endre.java.java_ee_exam.Application;
import org.junit.AfterClass;
import org.junit.AssumptionViolatedException;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = RANDOM_PORT)
public class SeleniumLocalIt extends SeleniumTestBase{


    private static WebDriver driver;

    @LocalServerPort
    private int port;


    @BeforeClass
    public static void initClass(){
        driver = SeleniumDriverHandler.getChromeDriver();

        if (driver == null){
            throw new AssumptionViolatedException("Cannot find or initialize Chrome driver");
        }
    }


    @AfterClass
    public static void tearDown(){
        if (driver != null){
            driver.close();
        }
    }



    @Override
    protected WebDriver getDriver(){
        return driver;
    }

    @Override
    protected String getServerHost() {
        return "localhost";
    }

    @Override
    protected int getServerPort() {
        return port;
    }
}
