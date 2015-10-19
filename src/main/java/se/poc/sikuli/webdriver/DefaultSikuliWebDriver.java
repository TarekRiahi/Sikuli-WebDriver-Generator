package se.poc.sikuli.webdriver;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.sikuli.api.*;
import se.poc.sikuli.webdriver.support.Score;

import java.awt.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Map;
import java.util.Set;

public class DefaultSikuliWebDriver extends SikuliWebDriver {

    ScreenRegion webdriverRegion;

    DefaultSikuliWebDriver(final WebDriver delegate) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        super(delegate);
        webdriverRegion = createWebDriverRegion();
    }

    @Override
    public ImageElement findImageElement(final URL imageUrl) {
        return findImageElement(imageUrl, Score.GOOD, 10000);
    }

    @Override
    public ImageElement findImageElement(final URL imageUrl, Score minScore, int timeout) {
        final ImageTarget target = new ImageTarget(imageUrl);
        target.setMinScore(minScore.value());

        final ScreenRegion imageRegion = findImage(target, timeout);

        return createImageElement(imageRegion);
    }

    @Override
    public ImageElement findImageElement(final Map<String, Map<String, Object>> stateToImageMapping, int timeout) {
        Set<String> states = stateToImageMapping.keySet();
        if(states.size() == 1) {
            Map<String, Object> singleState = stateToImageMapping.get(states.iterator().next());
            return findImageElement((URL)singleState.get("url"), (Score)singleState.get("minscore"), timeout);
        }

        final MultiStateTarget target = new MultiStateTarget();

        for(String state : states) {
            URL imageUrl = (URL)stateToImageMapping.get(state).get("url");
            double minScore = ((Score)stateToImageMapping.get(state).get("minscore")).value();

            ImageTarget t = new ImageTarget(imageUrl);
            t.setMinScore(minScore);
            target.addState(t, state);
        }

        final ScreenRegion imageRegion = findImage(target, timeout);

        return createImageElement(imageRegion);
    }

    private ImageElement createImageElement(ScreenRegion imageRegion) {
        if(imageRegion == null)
            return null;

        ScreenLocation center = imageRegion.getCenter();
        WebElement element = findElementByLocation(center.getX(), center.getY());

        Rectangle r = imageRegion.getBounds();
        return new DefaultImageElement(this, element,
                r.x,
                r.y,
                r.width,
                r.height);
    }

    @Override
    public WebElement findElementByLocation(int x, int y) {
        logger.debug("find element by location at {} {}", x, y);
        return (WebElement) executeScript("return document.elementFromPoint(" + x + " - window.pageXOffset," + (y - getPageYOffset()) + ");");
    }

    @Override
    public void setScreenSize(Dimension targetSize) {
        manage().window().setSize(targetSize);
        webdriverRegion = createWebDriverRegion();
    }

    @Override
    public Dimension getScreenSize() {
        return manage().window().getSize();
    }

    private ScreenRegion findImage(final Target target, int timeout) {
        ScreenRegion imageRegion = null;

        if(timeout > 0) {
            for(int timeslice = timeout / 10; imageRegion == null && timeout > 0; timeout -= timeslice) {
                webdriverRegion = createWebDriverRegion();
                imageRegion = webdriverRegion.wait(target, timeslice);
            }
        } else {
            webdriverRegion = createWebDriverRegion();
            imageRegion = webdriverRegion.wait(target, timeout);
        }

        if (imageRegion != null){
            Rectangle r = imageRegion.getBounds();
            logger.debug("image is found at {} {} {} {}", r.x, r.y, r.width, r.height);
            scrollTo(r.x, r.y);
        } else{
            logger.debug("image is not found");
            return null;
        }

        return imageRegion;
    }

    private ScreenRegion createWebDriverRegion() {
        WebDriverScreen webDriverScreen;
        try {
            webDriverScreen = new WebDriverScreen(this);
        } catch (IOException e) {
            throw new RuntimeException("unable to initialize Sikuli Web Driver Screen");
        }
        return new DefaultScreenRegion(webDriverScreen);
    }

    void scrollTo(int x, int y) {
        executeScript("window.scrollTo(" + x + "," + y + ");");
    }

    long getPageYOffset() {
        return (long) executeScript("" +
                "var pageYOffset;" +
                "if(typeof(window.pageYOffset) == 'number') {" +
                "   pageYOffset = window.pageYOffset;" +
                "} else {" +
                "   pageYOffset = document.documentElement.scrollTop;" +
                "}" +
                "return Math.round(pageYOffset);");
    }
}
