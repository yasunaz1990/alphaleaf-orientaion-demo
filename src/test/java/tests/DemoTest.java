package tests;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;


// Test Class:
// Contains automated test cases
@Listeners(utility.TestListener.class)
public class DemoTest extends BaseTest {


    @Test
    public void verify_model_creation() {
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


    @Test
    public void verify_run_inference() {
        homePage()
                .open()
                .gotoRunPage();

        runPage()
                .selectUseCase()
                .runPrediction();

        Assert.assertTrue(runPage().verifyPredictionResult());
    }

}//end::DemoTest