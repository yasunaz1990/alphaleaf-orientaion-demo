package pages;

import org.openqa.selenium.By;
import utility.Steps;
import utility.UIActions;

public class HomePage extends UIActions  {
    private final String URL = "https://ailab.acr.org/";
    private By enter_button = xpath("//a/button");
    private By create_tab = xpath("//a/span[text()='Create']");
    private By run_tab = xpath("//a/span[text()='Run']");

    public HomePage open(){
        Steps.log("Home page is opened");
        gotoSite(URL);
        click(enter_button);
        return this;
    }

    public void gotoCreatePage() {
        Steps.log("User is directed to the Create Page");
        click(create_tab);
    }

    public void gotoRunPage() {
        Steps.log("User is directed to the Run Inference Page");
        click(run_tab);
    }
}
