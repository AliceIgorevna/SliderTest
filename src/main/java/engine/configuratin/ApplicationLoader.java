package engine.configuratin;

import engine.constants.Constants;
import org.testng.Reporter;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Алиса on 16.10.2016.
 */
public class ApplicationLoader {
    public static void loadSliderApplication() {
        Path currentRelativePath = Paths.get("");
        String command = "cmd /c start \"\" \"" + currentRelativePath.toAbsolutePath().toString() + Constants.BAT_RELATIVE_PATH;
        try {
            Runtime.getRuntime().exec(command).waitFor();
        } catch (Exception e) {
            Reporter.log("<p> Fail to load Slider Application </p>");
            e.printStackTrace();
        }
    }
}
