package pages;

import org.openqa.selenium.By;
import utility.Steps;
import utility.UIActions;

public class CreatePage extends UIActions {
    private By use_case = id("useCases");
    private By training_data = id("datapercentage");
    private By augmentation = id("augmentation");
    private By start_processing = id("btnStartProcessing");
    private By processing_complete = css("div > span:nth-of-type(2)");
    private By architecture = id("architecture");
    private By sampling = id("equalsampling");
    private By loss_function = id("lossfunction");
    private By pre_training = id("pretraining");
    private By early_stopping = id("earlystopping");
    private By train_test_button = id("btnTrainAndTest");
    private By accuracy_overview = xpath("//div[text()='Accuracy Overview']");
    private By loss_overview = xpath("//div[text()='Loss Overview']");
    private By performance_testing = xpath("//div[text()='Performance Testing']");
    private By model_footer_text = xpath("//div[@id='app']/div/section/section/div/div[3]");



    public CreatePage selectUseCase() {
        Steps.log("User has selected the [Breast Density] use case");
        selectOption(use_case, "Breast Density");
        return this;
    }

    public CreatePage prepareData() {
        Steps.log("Preparing the Data");
        click(training_data);
        selectOption(training_data, "1000 Mammo images");
        click(augmentation);
        selectOption(augmentation, "None");
        click(start_processing);
        waitUntilElementVisible(processing_complete);
        highlight(processing_complete);
        return this;
    }

    public CreatePage configureModel() {
        Steps.log("Configuring the model");
        click(architecture);
        selectOption(architecture, "ResNet");
        click(sampling);
        selectOption(sampling, "Random sampling");
        click(loss_function);
        selectOption(loss_function, "Categorical-crossentropy");
        click(pre_training);
        selectOption(pre_training, "Pre-trained weights");
        click(early_stopping);
        selectOption(early_stopping, "FALSE");
        click(early_stopping);
        return this;
    }

    public CreatePage startTraining() {
        Steps.log("Model training has started");
        click(train_test_button);
        textHighlight(xpath("//div[@class='flex-header']"));
        return this;
    }

    public boolean veirfyModel() {
        Steps.log("Model training has concluded, verifying the Model");
        waitUntilElementVisible(accuracy_overview);
        // Verify accuracy overview
        checkHighlight(accuracy_overview);
        highlight(css("div:nth-of-type(1) > .example > .chartjs-render-monitor"));

        // Verify Loss Overview
        checkHighlight(loss_overview);
        highlight(css("div:nth-of-type(2) > .example > .chartjs-render-monitor"));

        // Verify performance testing
        checkHighlight(performance_testing);
        highlight(css("svg"));

        textHighlight(xpath("//div[text()='Predictions']"));
        pauseforMillisecond(200);
        textHighlight(model_footer_text);
        pausefor(1);
        return true;
    }


}
