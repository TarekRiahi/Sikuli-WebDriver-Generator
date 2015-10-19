package se.poc.sikuli.webdriver.integration;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import se.poc.sikuli.webdriver.SikuliWebDriverFactory;
import se.poc.sikuli.webdriver.integration.page.FirstPage;
import se.poc.sikuli.webdriver.integration.page.SecondPage;
import se.poc.sikuli.webdriver.support.SikuliPageFactory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class SikuliPageObjectPatternIT extends BaseIT {

    @Test
    public void usePageObjectPattern() throws InterruptedException, IOException {
        driver.get(baseUrl + "/index.html");
        FirstPage firstPage = SikuliPageFactory.initElements(driver, FirstPage.class);

        firstPage.clickButton();
        firstPage.clickMultiStateButton();

        Thread.sleep(2000);

        firstPage.clickMultiStateButton();
        firstPage.clickSmiley();
        assertThat(new WebDriverWait(driver, 10).until(titleIs("Sikuli WebDriver Generator - Second page")), is(true));

        SecondPage secondPage = SikuliPageFactory.initElements(driver, SecondPage.class);
        secondPage.clickSuperHero();
        assertThat(new WebDriverWait(driver, 10).until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(@javax.annotation.Nullable WebDriver driver) {
                return driver.getCurrentUrl().contains("MiddleOfPage");
            }
        }), is(true));
    }

    @BeforeClass
    public static void setup() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        driver = SikuliWebDriverFactory.createFactory(PhantomJSDriver.class).create();
        driver.setScreenSize(new Dimension(1280, 1024));
    }
}
