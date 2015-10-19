package se.poc.sikuli.webdriver;

import org.junit.Test;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.ArrayList;

/**
 * Created by andgra on 2014-05-09.
 */
public class ExampleTest {

    @Test
    public void getFullScreenshot() {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();

        ArrayList<String> cliArgsCap = new ArrayList<String>();
        cliArgsCap.add("--web-security=false");
        cliArgsCap.add("--ssl-protocol=any");
        cliArgsCap.add("--ignore-ssl-errors=true");
        desiredCapabilities.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, cliArgsCap);

        desiredCapabilities.setCapability("phantomjs.binary.path",
                "C:\\Program Files (x86)\\Webdriver\\phantomjs192.exe");
/*    desiredCapabilities.setCapability("--ignore-ssl-errors", true);*/
        SikuliWebDriverFactory.createFactory(PhantomJSDriver.class).create(desiredCapabilities);

    }
}
