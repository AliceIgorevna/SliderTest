package engine.report;

import org.testng.Reporter;

/**
 * Created by Алиса on 15.10.2016.
 */
public class ReportHolder {
    private static final String IMAGE = "<br /><a href='%s'><img src='%s' height='100' width='100' /></a>";
    public static final String STEP = "<br><b><font size = 2 color = #000000>%s</font></b></br>";


    public static void addScreenshotImage(String filePath){
        String logMessage = String.format(IMAGE, filePath,
                filePath);
        Reporter.log(logMessage);
    }

    public static void stepReporter(int step, String description, String ... expectedResult) {
        String logMessage = String.format(STEP, "STEP-" + step + ": " + description);
        Reporter.log(logMessage);
        if (!expectedResult.equals("")) {
            Reporter.log(String.format(STEP, "Expected results:"));
            Reporter.log(expectedResult.toString());
        }
    }
}
