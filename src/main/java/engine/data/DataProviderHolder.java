package engine.data;

import engine.constants.Constants;
import org.testng.annotations.DataProvider;

/**
 * Created by Алиса on 15.10.2016.
 */
public class DataProviderHolder {
    @DataProvider(name = "balanceTestData")
    public static Object[][] positiveTestData(){
        return TestDataHolder.parse(Constants.EXCEL_RELATIVE_PATH, "BalanceData");
    }
    @DataProvider(name = "negativeTestData")
    public static Object[][] negativeTestData(){
        return TestDataHolder.parse(Constants.EXCEL_RELATIVE_PATH, "NegativeData");
    }
    @DataProvider(name = "mixedTestData")
    public static Object[][] mixedTestData(){
        return TestDataHolder.parse(Constants.EXCEL_RELATIVE_PATH, "MixedData");
    }
}
