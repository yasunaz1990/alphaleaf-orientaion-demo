package pages;

import org.openqa.selenium.By;
import utility.UIActions;

public class HomePage extends UIActions  {
    private final String URL = "https://ailab.acr.org/";
    private By enter_button = xpath("//a/button");
    private By create_tab = xpath("//a/span[text()='Create']");


    public HomePage open(){
        gotoSite(URL);
        click(enter_button);
        return this;
    }

    public void gotoCreatePage() {
        click(create_tab);
    }
}
