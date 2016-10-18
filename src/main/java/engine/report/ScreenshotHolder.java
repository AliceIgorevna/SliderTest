package engine.report;

import engine.constants.Constants;
import engine.configuratin.DriverConfiguration;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

/**
 * Created by Алиса on 15.10.2016.
 */
public class ScreenshotHolder extends TestListenerAdapter {
    protected WebDriver driver;

    @Override
    public void onTestFailure(ITestResult tr) {
        super.onTestFailure(tr);
        driver = DriverConfiguration.getDriverInstance();
        String currentDate = Calendar.getInstance().getTime().toString().replaceAll(":", "-");
        String fileName = Constants.SCREENSHOT_FIRST_NAME_PART + currentDate + Constants.IMAGE_EXTENSION;
        File screenShotSrc = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String workingDir = System.getProperty(Constants.SYSTEM_USER_PROPERTIES);
        String dirName = workingDir + Constants.SCREENSHOT_RELATIVE_PATH;
        File screenShotDest = new File(dirName + fileName);
        try {
            FileUtils.copyFile(screenShotSrc, screenShotDest);
        } catch (IOException e) {
            Reporter.log(e.getMessage());
        }
        String filePath = screenShotDest.getAbsolutePath();
        Reporter.setCurrentTestResult(tr);
        ReportHolder.addScreenshotImage(filePath);
    }
}
