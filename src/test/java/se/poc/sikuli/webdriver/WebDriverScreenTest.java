package se.poc.sikuli.webdriver;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.OutputType;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WebDriverScreenTest {

    static WebDriverScreen webDriverScreen;

    @Test
    public void getSize() {
        Dimension size = webDriverScreen.getSize();
        assertThat(size.getWidth(), is(1280D));
        assertThat(size.getHeight(), is(1024D));
    }

    @Test
    public void getFullScreenshot() {
        BufferedImage screenshot = webDriverScreen.getScreenshot(0, 0, 1280, 1024);
        assertThat(screenshot.getWidth(), is(1280));
        assertThat(screenshot.getHeight(), is(1024));
    }

    @Test
    public void getCroppedScreenshot() {
        BufferedImage screenshot = webDriverScreen.getScreenshot(0, 200, 225, 577);
        assertThat(screenshot.getWidth(), is(225));
        assertThat(screenshot.getHeight(), is(577));
    }

    @Test
    public void getScreenshotLargerThanScreenSize() {
        BufferedImage screenshot = webDriverScreen.getScreenshot(0, 0, 3000, 2000);
        assertThat(screenshot.getWidth(), is(3000));
        assertThat(screenshot.getHeight(), is(2000));
    }

    @BeforeClass
    public static void setup() throws IOException {
        SikuliWebDriver driver = mock(SikuliWebDriver.class);
        when(driver.getScreenshotAs(OutputType.FILE)).thenReturn(new File("./src/test/resources/img/empty_1280x1024.jpg"));
        webDriverScreen = new WebDriverScreen(driver);
    }
}
