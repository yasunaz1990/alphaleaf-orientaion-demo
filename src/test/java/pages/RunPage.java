package pages;

import org.openqa.selenium.By;
import utility.Steps;
import utility.UIActions;

public class RunPage extends UIActions {
    private By use_case = id("useCases");
    private By model_selection = id("modelSelected");
    private By img_prediction_text = xpath("//div[@id='grid']//h5[.='Image Prediction']");
    private By prediction_button = id("btnPredictionModelDesktop");
    private By prediction_result_text = css("#btnImagePredictionDisplay > span");


    public RunPage selectUseCase() {
        Steps.log("User has selected use case: [Pneumothorax]");
        selectOption(use_case, "Pneumothorax");
        return this;
    }

    public RunPage runPrediction() {
        Steps.log("User is running image prediction");
        textHighlight(img_prediction_text);
        click(prediction_button);
        return this;
    }

    public boolean verifyPredictionResult() {
        Steps.log("Verifying the prediction result");
        textHighlight(prediction_result_text);
        String result = findElement(prediction_result_text).getText();
        boolean verification = result.equalsIgnoreCase("Success");
        if(!verification) {
            Steps.failed("Run verification failed, please re-test manually.");
        }
        return result.equalsIgnoreCase("Success");
    }
}
