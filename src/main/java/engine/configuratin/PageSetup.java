package engine.configuratin;

import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

/**
 * Created by Алиса on 15.10.2016.
 */
public class PageSetup implements BaseOperations {
    private static WebDriver driver;

    protected PageSetup(WebDriver driver) {
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        PageSetup.driver = driver;
    }

    public static WebDriver getDriver() {
        return driver;
    }
}
