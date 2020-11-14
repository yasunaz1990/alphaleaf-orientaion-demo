package tests;

import org.testng.Assert;
import org.testng.annotations.Test;


// Test Class:
// Contains automated test cases
public class DemoTest extends BaseTest {

    @Test
    public void typical_automation() {
        homePage()
                .open()
                .gotoCreatePage();

        createPage()
                .selectUseCase()
                .prepareData()
                .configureModel()
                .startTraining();

        Assert.assertTrue(createPage().veirfyModel());
    }

}//end::DemoTest