package tests;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import pages.CreatePage;
import pages.HomePage;
import pages.RunPage;
import utility.DriverUtil;

public abstract class BaseTest {

    private HomePage home;
    private CreatePage create;
    private RunPage run;

    @BeforeMethod
    public void setUp() {
        DriverUtil.openBrowser();
    }

    @AfterMethod
    public void destroy() {
        DriverUtil.closeBrowser();
    }




    public HomePage homePage() {
//        if(home == null) {
//            home = new HomePage();
//        }
//        return home;
        return new HomePage();
    }

    public CreatePage createPage() {
//        if(create == null) {
//            create = new CreatePage();
//        }
//        return create;
        return new CreatePage();
    }

    public RunPage runPage() {
//        if(run == null) {
//            run = new RunPage();
//        }
//        return run;
        return new RunPage();
    }
}
