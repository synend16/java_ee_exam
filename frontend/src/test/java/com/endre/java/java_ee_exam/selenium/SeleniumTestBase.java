package com.endre.java.java_ee_exam.selenium;

import com.endre.java.java_ee_exam.selenium.po.IndexPO;
import com.endre.java.java_ee_exam.selenium.po.SignUpPO;
import com.endre.java.java_ee_exam.selenium.po.admin.AdminPO;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public abstract class SeleniumTestBase {

    protected abstract WebDriver getDriver();

    protected abstract String getServerHost();

    protected abstract int getServerPort();


    private static final AtomicInteger counter = new AtomicInteger(0);

    private String getUniqueId(){return "foo_seleniumTest_" + counter.getAndIncrement();}

    private IndexPO home;

    private IndexPO createNewUser(String username, String password, boolean isAdmin){
        home.toStartPage();

        SignUpPO signUpPO = home.toSignUp();

        IndexPO indexPO;
        if (isAdmin)
            indexPO = signUpPO.createAdmin(username, password);
        else
            indexPO = signUpPO.createUser(username, password);

        assertNotNull(indexPO);
        return indexPO;
    }

    @Before
    public void setUp() throws Exception {
        getDriver().manage().deleteAllCookies();

        home = new IndexPO(getDriver(), getServerHost(), getServerPort());

        home.toStartPage();

        assertTrue("Failed to start from Home Page", home.isOnPage());
    }

    @Test
    public void testCreateAndLogoutUser() {
        assertFalse(home.isLoggedIn());

        String username = getUniqueId();
        String password = "12345678";
        home = createNewUser(username, password, false);

        assertTrue(home.isLoggedIn());
        assertTrue(home.getDriver().getPageSource().contains(username));
    }

    @Test
    public void testCreateAdminAndGoToAdminPage() {
        assertFalse(home.isLoggedIn());

        String username = getUniqueId();
        String password = "123456789";
        home = createNewUser(username, password, true);

        AdminPO adminPO = home.goToAdmin();

        assertTrue(adminPO.isOnPage());
    }
}
