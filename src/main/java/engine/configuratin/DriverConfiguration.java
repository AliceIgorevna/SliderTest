package engine.configuratin;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Reporter;

/**
 * Created by Алиса on 15.10.2016.
 */
public class DriverConfiguration {
    public static WebDriver driver;
    private static final String chromeDriverKey = "webdriver.chrome.driver";
    private static final String chromeDriverPath = DriverConfiguration.class.getClassLoader().getResource("chromedriver.exe").getPath();

    private DriverConfiguration() {
    }

    public static WebDriver createWebDriver() {
        Reporter.log("<p> Browser is Chrome </p>");
        driver = createDriver();
        return driver;
    }

    public static WebDriver getDriverInstance() {
        return driver;
    }

    private static WebDriver createDriver() {
        System.setProperty(chromeDriverKey, chromeDriverPath);
        WebDriver driver = new ChromeDriver();
        return driver;
    }
}
