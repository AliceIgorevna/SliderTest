package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import engine.configuratin.PageSetup;

/**
 * Created by Алиса on 15.10.2016.
 */
public class SliderMainPage extends PageSetup {
    private final By RECHARGE_AMOUNT_FIELD = By.xpath(".//input[@id='amount']");
    private final By DO_PAYMENT_BUTTON = By.xpath(".//*[contains(@data-bind, 'doPayment')]");
    private final By DO_RESET_BUTTON = By.xpath(".//*[contains(@data-bind, 'doReset')]");
    private final By BALANCE_AMOUNT_INDICATOR = By.xpath(".//*[contains(@data-bind, 'balance')]");
    private final By MINUS_BUTTON = By.xpath(".//*[contains(@data-bind, 'moveLeft')]");
    private final By PLUS_BUTTON = By.xpath(".//*[@class='increase']/a");
    private final String CURRENT_CONDITION_SECTION = ".//*[@class='tariff unavailable']";
    private final By CURRENT_CONDITION_DAYS_MSG = By.xpath(CURRENT_CONDITION_SECTION + "//*[@class='time']");
    private final By CURRENT_CONDITION_SPEED_MSG = By.xpath(CURRENT_CONDITION_SECTION + "//*[@class='speed']");
    private final By CURRENT_CONDITION_COST_MSG = By.xpath(CURRENT_CONDITION_SECTION + "//*[contains(@class, 'cost')]");
    private final String NEW_TARIFF_CONDITION_SECTION = ".//*[contains(@class, 'main-offer-container')]";
    private final By NEW_TARIFF_CONDITION_COST = By
            .xpath(NEW_TARIFF_CONDITION_SECTION + "//*[contains(@data-bind, 'currentCost')]");
    private final By DO_PURCHASE_BUTTON = By.xpath(".//*[contains(@data-bind, 'doPurchase')]");

    public SliderMainPage(WebDriver driver) {
        super(driver);
    }

    public void verifyCurrentTariffConditionSection(String daysLeftMessage, String speedMessage, String costMessage) {
        Assert.assertEquals(getElementText(getDriver(), CURRENT_CONDITION_DAYS_MSG), daysLeftMessage);
        Assert.assertEquals(getElementText(getDriver(), CURRENT_CONDITION_SPEED_MSG), speedMessage);
        Assert.assertEquals(getElementText(getDriver(), CURRENT_CONDITION_COST_MSG), costMessage);
    }

    /**
     * This method accepts boolean type parameter, which is an indicator of Purchase button availability
     *
     * @param state
     *            true when button is enable, otherwise false
     */
    public void verifyPurchaseButtonState(boolean state) {
        String actualPurchaseBtnState = findElement(getDriver(), DO_PURCHASE_BUTTON).getAttribute("class");
        if (!state) {
            Assert.assertTrue(actualPurchaseBtnState.contains("disabled"));
        } else
            Assert.assertEquals(actualPurchaseBtnState, "btn");
    }

    public void verifyCurrentBalance(int expectedBalanceAmount) {
        int actualBalance = Integer.parseInt(getElementText(getDriver(), BALANCE_AMOUNT_INDICATOR));
        Assert.assertEquals(actualBalance, expectedBalanceAmount, "Verify balance is set to " + expectedBalanceAmount);
    }

    public void addFundsToBalance(String replenishmentAmount) {
        sendKeysToElement(getDriver(), RECHARGE_AMOUNT_FIELD, replenishmentAmount);
        clickButton(getDriver(), DO_PAYMENT_BUTTON);
    }

    public void addFundsToBalancePreviouslyClearRechargeField(String replenishmentAmount) {
        clearTextField(getDriver(), RECHARGE_AMOUNT_FIELD);
        sendKeysToElement(getDriver(), RECHARGE_AMOUNT_FIELD, replenishmentAmount);
        clickButton(getDriver(), DO_PAYMENT_BUTTON);
    }

    /**
     * This method may produce infinity loop, if tariff that need to be setted by {@link #setSliderToTariffTariff(int)}
     * unable to click on any of "+" or "-" buttons
     *
     * @param tariffCost
     *            accepts cost of preferred tariff to set
     */
    public void setTariff(int tariffCost) {
        setSliderToTariffTariff(tariffCost);
        clickButton(getDriver(), DO_PURCHASE_BUTTON);
    }

    public void setSliderToTariffTariff(int tariffCost) {
        if (Integer.parseInt(getElementText(getDriver(), NEW_TARIFF_CONDITION_COST)) > tariffCost) {
            do {
                clickButton(getDriver(), MINUS_BUTTON);
            } while (tariffCost != Integer.parseInt(getElementText(getDriver(), NEW_TARIFF_CONDITION_COST)));
        } else {
            while (tariffCost != Integer.parseInt(getElementText(getDriver(), NEW_TARIFF_CONDITION_COST))) {
                clickButton(getDriver(), PLUS_BUTTON);
            }
        }
    }

    public void clickPurchaseButton() {
        clickButton(getDriver(), DO_PURCHASE_BUTTON);
    }

    public void clickResetButton() {
        clickButton(getDriver(), DO_RESET_BUTTON);
    }

    public void refreshApplication() {
        getDriver().navigate().refresh();
    }
}
