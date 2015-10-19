package se.poc.sikuli.webdriver;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.Keyboard;
import org.openqa.selenium.interactions.Mouse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.poc.sikuli.webdriver.support.Score;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class SikuliWebDriver implements WebDriver, TakesScreenshot, JavascriptExecutor, HasInputDevices {

    final Logger logger = LoggerFactory.getLogger(getClass());
    final WebDriver delegate;

    SikuliWebDriver(final WebDriver delegate) {
        this.delegate = delegate;
    }

    public abstract ImageElement findImageElement(final URL imageUrl);
    public abstract ImageElement findImageElement(final URL imageUrl, Score minScore, int timeout);
    public abstract ImageElement findImageElement(final Map<String, Map<String, Object>> stateToImageMapping, int timeout);
    public abstract WebElement findElementByLocation(int x, int y);
    public abstract void setScreenSize(final Dimension targetSize);
    public abstract Dimension getScreenSize();

    public WebDriver getDelegate() {
        return delegate;
    }

    @Override
    public Object executeScript(String script, Object... args) {
        return JavascriptExecutor.class.cast(delegate).executeScript(script, args);
    }

    @Override
    public Object executeAsyncScript(String script, Object... args) {
        return JavascriptExecutor.class.cast(delegate).executeScript(script, args);
    }

    @Override
    public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
        return TakesScreenshot.class.cast(delegate).getScreenshotAs(target);
    }

    @Override
    public void get(String url) {
        delegate.get(url);
    }

    @Override
    public String getCurrentUrl() {
        return delegate.getCurrentUrl();
    }

    @Override
    public String getTitle() {
        return delegate.getTitle();
    }

    @Override
    public List<WebElement> findElements(By by) {
        return delegate.findElements(by);
    }

    @Override
    public WebElement findElement(By by) {
        return delegate.findElement(by);
    }

    @Override
    public String getPageSource() {
        return delegate.getPageSource();
    }

    @Override
    public void close() {
        delegate.close();
    }

    @Override
    public void quit() {
        delegate.quit();
    }

    @Override
    public Set<String> getWindowHandles() {
        return delegate.getWindowHandles();
    }

    @Override
    public String getWindowHandle() {
        return delegate.getWindowHandle();
    }

    @Override
    public TargetLocator switchTo() {
        return delegate.switchTo();
    }

    @Override
    public Navigation navigate() {
        return delegate.navigate();
    }

    @Override
    public Options manage() {
        return delegate.manage();
    }

    @Override
    public Keyboard getKeyboard() {
        return HasInputDevices.class.cast(delegate).getKeyboard();
    }

    @Override
    public Mouse getMouse() {
        return HasInputDevices.class.cast(delegate).getMouse();
    }
}
