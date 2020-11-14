package tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import pages.CreatePage;
import pages.HomePage;
import utility.DriverUtil;

public abstract class BaseTest {

    private HomePage home;
    private CreatePage create;

    @BeforeMethod
    public void setUp() {
        DriverUtil.openBrowser();
    }

    @AfterMethod
    public void cleanUp() {
        DriverUtil.closeBrowser();
    }

    public HomePage homePage() {
        if(home == null) {
            home = new HomePage();
        }
        return home;
    }

    public CreatePage createPage() {
        if(create == null) {
            create = new CreatePage();
        }
        return create;
    }
}
