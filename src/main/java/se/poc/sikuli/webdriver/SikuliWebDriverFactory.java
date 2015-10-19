package se.poc.sikuli.webdriver;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SikuliWebDriverFactory<T extends TakesScreenshot & WebDriver & JavascriptExecutor> {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    final Class<T> driverClass;

    public SikuliWebDriverFactory(final Class<T> driverClass) {
        this.driverClass = driverClass;
    }

    public SikuliWebDriver create(FirefoxBinary firefoxBinary) {
        final SikuliWebDriver driver;
        try {
            driver = new DefaultSikuliWebDriver(driverClass.getDeclaredConstructor(Capabilities.class).newInstance(firefoxBinary, null));
        } catch (Exception e) {
            logger.debug("Unable to create driver. Error message: {}", e.getMessage());
            return null;
        }
        return driver;
    }

    public SikuliWebDriver create(DesiredCapabilities desiredCapabilities) {
        final SikuliWebDriver driver;
        try {
            driver = new DefaultSikuliWebDriver(driverClass.getDeclaredConstructor(Capabilities.class).newInstance(desiredCapabilities));
        } catch (Exception e) {
            logger.debug("Unable to create driver. Error message: {}", e.getMessage());
            return null;
        }
        return driver;
    }

    public SikuliWebDriver create() {
        final SikuliWebDriver driver;
        try {
            driver = new DefaultSikuliWebDriver(driverClass.getDeclaredConstructor(Capabilities.class).newInstance(new DesiredCapabilities()));
        } catch (Exception e) {
            logger.debug("Unable to create driver. Error message: {}", e.getMessage());
            return null;
        }
        return driver;
    }

    public static <T extends TakesScreenshot & WebDriver & JavascriptExecutor> SikuliWebDriverFactory<T> createFactory(
            final Class<T> driverClass) {
        return new SikuliWebDriverFactory<>(driverClass);
    }
}