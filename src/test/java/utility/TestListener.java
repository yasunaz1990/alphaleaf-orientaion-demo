package utility;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    private ExtentReports extent;
    private ExtentTest testcase;
    private ExtentSparkReporter spark;


    // ===== TEST  ====== //
    public void onStart(ITestContext test) {
        String reportPath = System.getProperty("user.dir") + "/reports/report.html";
        extent = new ExtentReports();
        spark = new ExtentSparkReporter(reportPath);
        spark.config().setTheme(Theme.STANDARD);
        extent.attachReporter(spark);
    }

    public void onFinish(ITestContext test) {
        extent.flush();
    }

    // ===== TEST CASE  ====== //
    public void onTestStart(ITestResult result) {
        testcase = extent.createTest(result.getName());
        Steps.init(testcase);
    }

    public void onTestSuccess(ITestResult result) {
        testcase.pass("Test Case Passed");
        String sc = DriverUtil.screenshot();
        testcase.pass("CLICK BELOW", MediaEntityBuilder.createScreenCaptureFromBase64String(sc).build());
    }

    public void onTestFailure(ITestResult result) {
        testcase.fail("This test case resulted in failure");
        String sc = DriverUtil.screenshot();
        testcase.fail("CLICK BELOW", MediaEntityBuilder.createScreenCaptureFromBase64String(sc).build());
    }

    public void onTestSkipped(ITestResult result) {
        testcase.skip("Test execution was skipped");
    }

    public void onTestFailedWithTimeout(ITestResult result) {
        testcase.fail(result.getThrowable().getStackTrace().toString());
        this.onTestFailure(result);
    }
}