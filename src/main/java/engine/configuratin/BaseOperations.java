package engine.configuratin;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

/**
 * Created by Алиса on 15.10.2016.
 */
public interface BaseOperations {

    default void open(WebDriver driver, String URL) {
        driver.get(URL);
        driver.manage().window().maximize();
    }

    default void clickButton(WebDriver driver, By element) {
        Actions builder = new Actions(driver);
        builder.click(driver.findElement(element)).build().perform();
    }

    default void sendKeysToElement(WebDriver driver, By element, String value) {
        driver.findElement(element).sendKeys(value);
    }

    default WebElement findElement(WebDriver driver, By element) {
        return driver.findElement(element);
    }

    default String getElementText(WebDriver driver, By element) {
        return driver.findElement(element).getText().replaceAll("\\n", "");
    }

    default void clearTextField(WebDriver driver, By element) {
        driver.findElement(element).clear();
    }
}
