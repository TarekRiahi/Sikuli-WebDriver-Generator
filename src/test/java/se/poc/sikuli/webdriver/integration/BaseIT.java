package se.poc.sikuli.webdriver.integration;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import se.poc.sikuli.webdriver.SikuliWebDriver;
import se.poc.sikuli.webdriver.util.ResourceHandler;

import java.io.IOException;
import java.util.Properties;

public abstract class BaseIT {

    static SikuliWebDriver driver;
    static String baseUrl;

    @BeforeClass
    public static void setupClass() throws IOException {
        Properties prop = new Properties();
        prop.load(ResourceHandler.getIntegrationTestPropertiesFile());
        baseUrl = prop.getProperty("baseUrl");
    }

    @AfterClass
    public static void after() {
        if(driver != null)
            driver.quit();
    }
}
