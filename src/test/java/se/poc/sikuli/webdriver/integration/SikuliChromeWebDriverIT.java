package se.poc.sikuli.webdriver.integration;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import se.poc.sikuli.webdriver.ImageElement;
import se.poc.sikuli.webdriver.SikuliWebDriverFactory;
import se.poc.sikuli.webdriver.util.ResourceHandler;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assume.assumeTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class SikuliChromeWebDriverIT extends BaseIT {

    @Test
    public void findImageElement() throws IOException {
        URL coolSmiley = ResourceHandler.getResource("chrome-cool-smiley.png");
        driver.get(baseUrl + "/index.html");
        ImageElement element = driver.findImageElement(coolSmiley);
        assertThat(element, is(notNullValue()));
    }

    @Test
    public void surfUsingImageRecognition() throws IOException, InterruptedException {
        URL coolSmiley = ResourceHandler.getResource("chrome-cool-smiley.png");
        URL superHero = ResourceHandler.getResource("chrome-superhero.png");

        driver.get(baseUrl + "/index.html");
        ImageElement element = driver.findImageElement(coolSmiley);
        assertThat(element, is(notNullValue()));
        element.click();
        assertThat(new WebDriverWait(driver, 10).until(titleIs("Sikuli WebDriver Generator - Second page")), is(true));

        element = driver.findImageElement(superHero);
        assertThat(element, is(notNullValue()));
        element.click();
        assertThat(new WebDriverWait(driver, 10).until(titleIs("Sikuli WebDriver Generator - Second page")), is(true));
    }

    @BeforeClass
    public static void setup() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        assumeTrue(false);
        driver = SikuliWebDriverFactory.createFactory(ChromeDriver.class).create();
        driver.setScreenSize(new Dimension(1280, 1024));
    }
}
