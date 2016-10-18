import engine.data.DataProviderHolder;
import engine.configuratin.DriverConfiguration;
import engine.report.ReportHolder;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.SliderMainPage;

/**
 * Created by Алиса on 15.10.2016.
 */
public class RegressionTest {
    protected SliderMainPage sliderPage;
    private static WebDriver driver;

    @BeforeTest
    public void setUp() {
        driver = DriverConfiguration.createWebDriver();
        sliderPage = new SliderMainPage(driver);
        ReportHolder.stepReporter(0, "Open Slider application", "Slider application is running");
        sliderPage.open(driver, "http://localhost:4567/index.html");
    }

    @Test(testName = "SB_TS_02", dataProvider = "balanceTestData", dataProviderClass = DataProviderHolder.class)
    public void addAmountToDefaultBalanceValueTest(String balance) {
        ReportHolder.stepReporter(1, "Enter to recharging balance field a positive number");
        sliderPage.addFundsToBalance(balance);
        ReportHolder.stepReporter(2, "Click 'Пополнить счет' button", "Balance is still set to default");
        sliderPage.verifyCurrentBalance(0);
    }

    @Test(testName = "SB_TS_03", dataProvider = "negativeTestData", dataProviderClass = DataProviderHolder.class)
    public void addNegativeBalanceTest(String negativeBalanceData) {
        ReportHolder.stepReporter(1, "Enter to recharging balance field a negative value");
        sliderPage.addFundsToBalancePreviouslyClearRechargeField(negativeBalanceData);
        ReportHolder.stepReporter(2, "Click 'Пополнить счет' button", "Balance is still set to default");
        sliderPage.verifyCurrentBalance(0);
    }

    @Test(testName = "SCB_TS_02", dataProvider = "balanceTestData", dataProviderClass = DataProviderHolder.class)
    public void purchaseButtonStateCase01Test(String balance) {
        ReportHolder.stepReporter(1, "Recharge balance to M руб.");
        sliderPage.addFundsToBalancePreviouslyClearRechargeField(balance);
        ReportHolder.stepReporter(2, "Set spinner to tariff to M+N руб.");
        sliderPage.setSliderToTariffTariff(600);
        ReportHolder.stepReporter(3, "Verify purchase button", "Purchase button is NOT active");
        sliderPage.verifyPurchaseButtonState(false);
    }

    @Test(testName = "SCB_TS_03", dataProvider = "balanceTestData", dataProviderClass = DataProviderHolder.class)
    public void purchaseButtonStateCase02Test(String balance) {
        ReportHolder.stepReporter(1, "Recharge balance to M руб.");
        sliderPage.addFundsToBalancePreviouslyClearRechargeField(balance);
        ReportHolder.stepReporter(2, "Set spinner to tariff to M руб.");
        sliderPage.setSliderToTariffTariff(300);
        ReportHolder.stepReporter(3, "Verify purchase button", "Purchase button is active");
        sliderPage.verifyPurchaseButtonState(true);
    }

    @Test(testName = "STC_TS_03")
    public void severalTimesTariffConnectionTest() {
        ReportHolder.stepReporter(1, "Recharge balance to Y=M+M руб.");
        sliderPage.addFundsToBalance("600");
        ReportHolder.stepReporter(2, "Connect to tariff that costs M руб.");
        sliderPage.setSliderToTariffTariff(300);
        sliderPage.clickPurchaseButton();
        ReportHolder.stepReporter(3, "Verify that tariff has conditions according to that costs", "Duration: 0 дней осталось Speed: 320 Кбит/сек (макс.) Cost: 300 руб. в месяц");
        sliderPage.verifyCurrentTariffConditionSection("0дней осталось", "320Кбит/сек (макс.)", "300руб. в месяц");
        sliderPage.verifyCurrentBalance(300);
        ReportHolder.stepReporter(4, "Connect to tariff that costs 0 руб.");
        sliderPage.setSliderToTariffTariff(0);
        sliderPage.clickPurchaseButton();
        ReportHolder.stepReporter(5, "Verify that tariff has conditions according to that costs", "Duration: 0 дней осталось Speed: 64 Кбит/сек (макс.) Cost: 0 руб. в месяц");
        sliderPage.verifyCurrentTariffConditionSection("0дней осталось", "64Кбит/сек (макс.)", "0руб. в месяц");
        ReportHolder.stepReporter(6, "Connect to tariff that costs M руб.");
        sliderPage.setSliderToTariffTariff(300);
        sliderPage.clickPurchaseButton();
        ReportHolder.stepReporter(7, "Verify that tariff has conditions according to that costs", "Duration: 0 дней осталось Speed: 320 Кбит/сек (макс.) Cost: 300 руб. в месяц");
        sliderPage.verifyCurrentTariffConditionSection("0дней осталось", "320Кбит/сек (макс.)", "300руб. в месяц");
        sliderPage.verifyCurrentBalance(0);
    }

    @Test(testName = "SS_TC-07", dataProvider = "mixedTestData", dataProviderClass = DataProviderHolder.class)
    public void resetBalanceAndRateTest(String balance, String daysLeft, String currentSpeed, String currentCost) {
        ReportHolder.stepReporter(1, "Recharge balance to M руб.");
        sliderPage.addFundsToBalancePreviouslyClearRechargeField(balance);
        ReportHolder.stepReporter(2, "Set NOT default tariff");
        sliderPage.setTariff(600);
        ReportHolder.stepReporter(3, "Click 'Сбросить' button", "Current tariff and balance is set to default");
        sliderPage.clickResetButton();
        sliderPage.verifyCurrentTariffConditionSection(daysLeft, currentSpeed, currentCost);
        sliderPage.verifyCurrentBalance(0);
    }

    @Test(testName = "SR_TC-01", dataProvider = "mixedTestData", dataProviderClass = DataProviderHolder.class)
    public void refreshApplicationTest(String balance, String daysLeft, String currentSpeed, String currentCost) {
        ReportHolder.stepReporter(1, "Recharge balance to M руб.");
        sliderPage.addFundsToBalancePreviouslyClearRechargeField(balance);
        ReportHolder.stepReporter(2, "Set NOT default tariff");
        sliderPage.setTariff(600);
        ReportHolder.stepReporter(3, "Refresh application", "Current tariff and balance is set to default");
        sliderPage.refreshApplication();
        sliderPage.verifyCurrentTariffConditionSection(daysLeft, currentSpeed, currentCost);
        sliderPage.verifyCurrentBalance(0);
    }

    @AfterTest
    public void quit() {
        sliderPage.clickResetButton();
        driver.quit();
    }
}
