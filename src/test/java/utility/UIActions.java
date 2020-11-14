package utility;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


public abstract class UIActions {

    // ---- Fields ----- //
    protected static WebDriver driver;
    protected static WebDriverWait waits;
    private static final Integer WAIT_TIME = 80;
    private static ArrayList<String> tabs;
    private static WaitUntil waitUntil;


    // ---- Constructors ----- //
    protected UIActions() {
        driver = DriverUtil.getDriver();
        waits = new WebDriverWait(driver, WAIT_TIME);
        waitUntil = new WaitUntil(waits);
    }

    // ---- Methods ----- //
    //region Browser Actions
    protected void fullScreen() {
        driver.manage().window().fullscreen();
    }

    protected void maximize() {
        driver.manage().window().maximize();
    }

    protected void setResolution(int width, int height) {
        Dimension size = new Dimension(width, height);
        driver.manage().window().setSize(size);
    }

    protected void switchToIFrame() {
        WebElement iframe = waitUntilElementVisible(By.tagName("iframe"));
        driver = driver.switchTo().frame(iframe);
    }

    protected void switchToIFrame(By locator) {
        WebElement iframe = waitUntilElementVisible(locator);
        driver = driver.switchTo().frame(iframe);
    }

    protected void switchBackFromIframe() {
        driver = driver.switchTo().defaultContent();
    }

    protected void openNewTab() {
        ((JavascriptExecutor) driver).executeScript("window.open()");
        tabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(0));
    }

    protected void switchToTab(int tabNumber) {
        tabs = new ArrayList<String>(driver.getWindowHandles());
        for (String s : tabs) {
            System.out.println("Tab: " + s + "     title:" + driver.getTitle());
        }
        System.out.println("\n\n");
        driver.switchTo().window(tabs.get(tabNumber));
    }

    protected void switchToTab(String tabTitle) {
        tabs = new ArrayList<String>(driver.getWindowHandles());
        for (int i = 0; i < tabs.size(); i++) {
            driver.switchTo().window(tabs.get(i));
            String windowTitle = driver.getTitle();
            if (windowTitle.equalsIgnoreCase(tabTitle)) {
                break;
            }
        }
    }

    protected void closeCurrentTab() {
        driver.close();
        tabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(0));
    }

    protected void loadCookie(String name, String token) {
        Cookie cookie = new Cookie(name, token);
        driver.manage().addCookie(cookie);
    }

    protected void deleteCookie(String name) {
        driver.manage().deleteCookieNamed(name);
    }

    protected void deleteAllCookies() {
        driver.manage().deleteAllCookies();
    }

    protected void gotoSite(String url) {
        driver.get(url);
    }

    protected void reload() {
        driver.navigate().refresh();
    }

    protected String getCurrentURL() {
        return driver.getCurrentUrl();
    }

    protected void goBack() {
        driver.navigate().back();
    }

    protected void goFoward() {
        driver.navigate().forward();
    }

    protected String pageTitle() {
        return driver.getTitle();
    }

    protected void scrollToBottom() {
        String jscode = "window.scrollTo(0, document.body.scrollHeight)";
        ((JavascriptExecutor) driver).executeScript(jscode);
    }

    protected void scrollToTop() {
        String jscode = "window.scrollTo(0, 0)";
        ((JavascriptExecutor) driver).executeScript(jscode);
    }

    protected void scrollDownByPixel(int pixel) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0," + pixel + ")");
    }

    protected void scrollUpByPixel(int pixel) {
        pixel = pixel - (pixel * 2);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0," + pixel + ")");
    }

    protected void dropFile(File filePath, By locator) {
        WebElement target = findElement(locator);

        if (!filePath.exists())
            throw new WebDriverException("File not found: " + filePath.toString());

        JavascriptExecutor jse = (JavascriptExecutor) driver;
        WebDriverWait wait = new WebDriverWait(driver, 30);

        String js_drop_file =
                "var target = arguments[0]," +
                        "    offsetX = arguments[1]," +
                        "    offsetY = arguments[2]," +
                        "    document = target.ownerDocument || document," +
                        "    window = document.defaultView || window;" +
                        "" +
                        "var input = document.createElement('INPUT');" +
                        "input.type = 'file';" +
                        "input.style.display = 'none';" +
                        "input.onchange = function () {" +
                        "  var rect = target.getBoundingClientRect()," +
                        "      x = rect.left + (offsetX || (rect.width >> 1))," +
                        "      y = rect.top + (offsetY || (rect.height >> 1))," +
                        "      dataTransfer = { files: this.files };" +
                        "" +
                        "  ['dragenter', 'dragover', 'drop'].forEach(function (name) {" +
                        "    var evt = document.createEvent('MouseEvent');" +
                        "    evt.initMouseEvent(name, !0, !0, window, 0, 0, 0, x, y, !1, !1, !1, !1, 0, null);" +
                        "    evt.dataTransfer = dataTransfer;" +
                        "    target.dispatchEvent(evt);" +
                        "  });" +
                        "" +
                        "  setTimeout(function () { document.body.removeChild(input); }, 25);" +
                        "};" +
                        "document.body.appendChild(input);" +
                        "return input;";

        WebElement input = (WebElement) jse.executeScript(js_drop_file, target, 0, 0);
        input.sendKeys(filePath.getAbsoluteFile().toString());
        wait.until(ExpectedConditions.stalenessOf(input));
    }

    protected void uploadFile(By locator, String filePath) {
        WebElement fileInput = waitUntilElementVisible(locator);
        highlight(fileInput);
        fileInput.sendKeys(filePath);
    }
    //endregion


    //region Element Actions
    protected void click(By locator) {
        try {
            WebElement element = waits.until(ExpectedConditions.elementToBeClickable(locator));
            highlight(element);
            element.click();
        } catch (Exception ex) {
            System.out.println("============CLICK ERROR==================");
            System.out.println("Location>> " + driver.getCurrentUrl());
            System.out.println("Element info: " + locator.toString());
            System.out.println("=========================================");
        }
    }

    protected void click(String text) {
        String expression = "//*[text()='" + text + "']";
        By selector = By.xpath(expression);
        waitUntilElementVisible(selector);
        List<WebElement> foundElems = driver.findElements(selector);
        if (foundElems.size() == 0) {
            StringBuilder strb = new StringBuilder();
            strb.append("\n\nException Message : \n");
            strb.append("\tUnable to find the element with text{ +" + text + " } from UI");
            strb.append("\tAny typo? Also text matching is case sensitive, if you cannot \n");
            strb.append("\tresolve this, please use another locators to locate to element. \n");
            strb.append("Exception Location:  Class  -> UIActions\n");
            strb.append("Exception Occured :  Method -> click(String text)\n");
            throw new NotFoundException(strb.toString());
        } else {
            for (WebElement elem : foundElems) {
                elem.click();
                break;
            }
        }
    }

    protected void doubleClick(By locator) {
        new Actions(driver)
                .doubleClick(waitUntil.elementIsClickable(locator))
                .build()
                .perform();
    }

    protected void rightClick(By locator) {
        new Actions(driver)
                .contextClick(waitUntil.elementIsClickable(locator))
                .build()
                .perform();
    }

    protected void clickRightSide(By locator) {
        WebElement element = findElement(locator);
        highlight(element);
        Actions actions = new Actions(driver);
        actions.moveToElement(element, 100, 10)
                .click()
                .perform();
    }

    private void clickNthElement(By locator, int atIndex) {
        waitUntilElementVisible(locator);
        List<WebElement> elementAllElements = driver.findElements(locator);
        WebElement elementElementByIdx = elementAllElements.get(atIndex);
        elementElementByIdx.click();
    }

    protected void tapOnce(By locator) {
        try {
            WebElement element = waitUntil.elementIsThereAndVisibleToUser(locator);
            element.click();
        } catch (Exception ex) {
            System.out.println("Element was not clickalbe. Check its locators logic ( Ex: css, xpath .etc");
        }
    }

    protected void hover(By locator) {
        WebElement elem = waitUntilElementVisible(locator);
        new Actions(driver).moveToElement(elem).build().perform();
    }

    protected void focus(By locator) {
        WebElement element = waitUntilElementVisible(locator);
        if ("input".equals(element.getTagName())) {
            element.sendKeys("");
        } else {
            new Actions(driver).moveToElement(element).perform();
        }
    }

    protected void mouseDownOn(By element) {
        new Actions(driver)
                .moveToElement(waitUntilElementVisible(element))
                .clickAndHold().perform();
    }

    protected void moveTo(By element) {
        new Actions(driver).moveToElement(waitUntilElementVisible(element))
                .build()
                .perform();
    }

    protected void mouseUpOn(By element) {
        new Actions(driver).moveToElement(waitUntilElementVisible(element))
                .release()
                .perform();
    }

    protected void dragAndDrop(By base, By target) {
        mouseDownOn(base);
        moveTo(target);
        mouseUpOn(target);
    }

    protected boolean selectOptionWithText(By locator, String text) {
        WebElement dropdown = findElement(locator);
        Select datasetOptions = new Select(dropdown);

        for (WebElement opt : datasetOptions.getOptions()) {
            String textVal = opt.getText();
            textVal = textVal.trim();
            if (textVal.equalsIgnoreCase(text)) {
                opt.click();
                return true;
            }
        }//end:for
        return false;
    }

    protected void selectOption(By dropdown, String optionTxt) {
        List<WebElement> options = new Select(findElement(dropdown)).getOptions();
        for (WebElement element : options) {
            boolean ret = element.getText().equalsIgnoreCase(optionTxt);
            if (ret) {
                pauseforMillisecond(200);
                highlight(element);
                element.click();
                break;
            }
        }//:for
    }

    protected void selectOptionWithValue(By locator, String value) {
        WebElement dropdown = findElement(locator);
        Select datasetOptions = new Select(dropdown);
        datasetOptions.selectByValue(value);
    }

    protected void moveViewToElement(By selector) {
        WebElement where;
        Actions builder = new Actions(driver);
        where = waitUntilElementVisible(selector);
        builder.moveToElement(where).perform();
    }

    public void moveToVewBotton() {
        WebElement where;
        Actions actions = new Actions(driver);
        where = waits.until(ExpectedConditions.visibilityOfElementLocated(xpath("//footer")));
        actions.moveToElement(where).perform();
    }

    protected void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    // ============= HIGHLIGHT EFFECT ========================= //

    protected void highlight(By locator) {
        WebElement element = waitUntilElementVisible(locator);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        // Red double color
        js.executeScript("arguments[0].setAttribute('style', 'border: 4px double #D46F6F;');", element);
    }

    protected void checkHighlight(By locator) {
        WebElement element = waitUntilElementVisible(locator);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].setAttribute('style', 'border: 3px solid #f1c727;');", element);
        pauseforMillisecond(100);
//        js.executeScript("arguments[0].setAttribute('style', '');", element);
//        pauseforMillisecond(400);
//        js.executeScript("arguments[0].setAttribute('style', 'border: 2px solid #f1c727;');", element);
//        js.executeScript("arguments[0].setAttribute('style', '');", element);
//        pauseforMillisecond(400);
        js.executeScript("arguments[0].setAttribute('style', 'border: 3px solid #54aa77;');", element);
        pauseforMillisecond(100);
    }


    public void highlight(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        js.executeScript("arguments[0].setAttribute('style', 'border: 3px dashed #E6D0FF;');", element);
    }

    protected void textHighlight(By locator) {
        WebElement element = waitUntilElementVisible(locator);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px dashed green;');", element);
    }


    // ======================================================== //

    protected void clear(By locator) {
        waitUntilElementVisible(locator).clear();
    }

    protected void write(By locator, String text) {
        highlight(locator);
        waitUntil.elementIsThereAndVisibleToUser(locator).sendKeys(text);
    }

    protected void clearThenWrite(By locator, String text) {
        WebElement inputElem = waitUntilElementVisible(locator);
        inputElem.clear();
        inputElem.sendKeys(text);
    }

    protected void pausefor(int second) {
        try {
            Thread.sleep(second * 1000);
        } catch (Exception e) {
            // do nothing
        }
    }

    protected void pauseforMillisecond(int milisecond) {
        try {
            Thread.sleep(milisecond);
        } catch (Exception e) {
            // do nothing
        }
    }

    protected void submit(By element) {
        waitUntilElementVisible(element).submit();
    }

    protected WebElement findElement(By locator) {
        // WebElement elem = waitUntilElementVisible(locator);
        //highlight(locator);
        WebElement element = waitUntil.elementIsThereAndVisibleToUser(locator);
        highlight(element);
        return element;
    }

    protected WebElement findPresentElement(By locator) {
        WebElement element = waits.until(ExpectedConditions.presenceOfElementLocated(locator));
        highlight(element);
        return element;
    }

    protected List<WebElement> findElements(By locator) {
        waitUntilElementVisible(locator);
        return driver.findElements(locator);
    }
    //endregion


    //region Selectors
    protected By css(String expression) {
        return By.cssSelector(expression);
    }

    protected By id(String value) {
        return By.id(value);
    }

    protected By xpath(String expression) {
        return By.xpath(expression);
    }

    protected By link(String text) {
        return By.linkText(text);
    }

    protected By linktextContains(String text) {
        return By.partialLinkText(text);
    }

    protected By name(String value) {
        return By.name(value);
    }

    protected By withTag(String tagname) {
        return By.tagName(tagname);
    }
    //endregion


    //region Waiters
    protected WebElement waitUntilElementVisible(By locator) {
        WebElement elem = waits.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return elem;
    }

    protected boolean waitUntilElementInvisible(By locator) {
        highlight(locator);
        WebDriverWait waits = new WebDriverWait(driver, 100);
        boolean result = waits.until(ExpectedConditions.invisibilityOfElementLocated(locator));
        return result;
    }

    protected boolean waitUntilTextPresent(By locator, String target) {
        boolean result = waits.until(ExpectedConditions.textToBePresentInElementLocated(locator, target));
        WebElement element = driver.findElement(locator);
        highlight(element);
        return result;
    }
    //endregion
}
