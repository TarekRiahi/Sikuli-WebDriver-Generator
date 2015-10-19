package se.poc.sikuli.webdriver.integration;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import se.poc.sikuli.webdriver.ImageElement;
import se.poc.sikuli.webdriver.SikuliWebDriverFactory;
import se.poc.sikuli.webdriver.util.ResourceHandler;
import se.poc.sikuli.webdriver.support.Score;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class SikuliPhantomJSWebDriverIT extends BaseIT {

    @Test
    public void findImageElement() throws IOException {
        URL coolSmiley = ResourceHandler.getResource("phantomjs-cool-smiley.jpg");
        driver.get(baseUrl + "/index.html");
        ImageElement element = driver.findImageElement(coolSmiley);
        assertThat(element, is(notNullValue()));
    }

    @Test
    public void surfUsingImageRecognition() throws IOException, InterruptedException {
        URL coolSmiley = ResourceHandler.getResource("phantomjs-cool-smiley.jpg");
        URL superHero = ResourceHandler.getResource("phantomjs-superhero.png");

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

    @Test
    public void howSikuliWebDriverReactWhenImageIsNotFound() {
        URL imageThatDoesntExistOnWebSite = ResourceHandler.getResource("sad-smiley.png");

        driver.get(baseUrl + "/index.html");
        assertThat(new WebDriverWait(driver, 10).until(titleIs("Sikuli WebDriver Generator - Home")), is(true));
        ImageElement elementOnIndexPage = driver.findImageElement(imageThatDoesntExistOnWebSite, Score.FULL, 5000);
        assertThat(elementOnIndexPage, is(equalTo(null)));

        driver.get(baseUrl + "/second-page.html");
        assertThat(new WebDriverWait(driver, 10).until(titleIs("Sikuli WebDriver Generator - Second page")), is(true));
        ImageElement elementOnSecondPage = driver.findImageElement(imageThatDoesntExistOnWebSite, Score.FULL, 5000);
        assertThat(elementOnSecondPage, is(equalTo(null)));
    }

    @BeforeClass
    public static void setup() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        driver = SikuliWebDriverFactory.createFactory(PhantomJSDriver.class).create();
        driver.setScreenSize(new Dimension(1920, 1080));
    }
}
