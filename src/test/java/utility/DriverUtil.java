package utility;

import com.github.javafaker.Faker;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class DriverUtil {

    // ===== FIELDS ======= //
    private static WebDriver driver;
    private static String CHOSEN_BROWSER = "";


    // ===== METHODS ======= //
    public static void openBrowser() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
    }


    public static void closeBrowser() {
        if (driver != null) {
            driver.close();
            driver.quit();
        }

        // WebDriverPool.DEFAULT.dismissAll();
    }

    public static WebDriver getDriver() {
        if (driver == null) {
            throw new NullPointerException("Driver is null");
        }
        return driver;
    }

    public static String screenshot() {
        WebDriver driver = DriverUtil.getDriver();
        TakesScreenshot newScreen = (TakesScreenshot) driver;
        String scnShot = newScreen.getScreenshotAs(OutputType.BASE64);
        return "data:image/jpg;base64, " + scnShot;
    }

    public static String screenshotAsPng() {
        //create a string variable which will be unique always
        Locale locale;
        Faker faker = new Faker();
        Random rand = new Random();
        String screenShotName = faker.name().firstName() + rand.nextInt(10000);
        String df = new SimpleDateFormat("yyyyMMddhhss").format(new Date());

        //create object variable of TakeScreenshot class
        TakesScreenshot ts = (TakesScreenshot) DriverUtil.getDriver();

        //create File object variable which holds the screen shot reference
        File source = ts.getScreenshotAs(OutputType.FILE);

        //store the screen shot path in path variable. Here we are storing the screenshots under screenshots folder
        String path = System.getProperty("user.dir") + "/screenshots/" + screenShotName + df + ".png";

        //create another File object variable which points(refer) to the above stored path variable
        File destination = new File(path);

        //use FileUtils class method to save the screen shot at desired path
        try {
            FileUtils.copyFile(source, destination);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //return the path where the screen shot is saved
        return path;

    }

}