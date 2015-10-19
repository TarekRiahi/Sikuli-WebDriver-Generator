package se.poc.sikuli.webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

public class DefaultImageElement implements ImageElement {

    private enum MouseEvent { LEFT_CLICK, RIGHT_CLICK, MIDDLE_CLICK, DOUBLE_CLICK }

    final private DefaultSikuliWebDriver driver;
    final private WebElement webElement;
    final private int x;
    final private int y;
    final private int width;
    final private int height;

    DefaultImageElement(DefaultSikuliWebDriver driver, WebElement webElement, int x, int y, int width, int height){
        this.driver = driver;
        this.webElement = webElement;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void click() {
        executeJavaScriptMouseAction(MouseEvent.LEFT_CLICK);
    }

    @Override
    public void submit() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void sendKeys(CharSequence... keysToSend) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getTagName() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getAttribute(String name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isSelected() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isEnabled() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getText() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<WebElement> findElements(By by) {
        throw new UnsupportedOperationException();
    }

    @Override
    public WebElement findElement(By by) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isDisplayed() {
        return webElement != null && webElement.isDisplayed();
    }

    @Override
    public Point getLocation() {
        return new Point(x, y);
    }

    @Override
    public Dimension getSize() {
        return new Dimension(width, height);
    }

    @Override
    public String getCssValue(String propertyName) {
        throw new UnsupportedOperationException();
    }

    public void doubleClick() {
        executeJavaScriptMouseAction(MouseEvent.DOUBLE_CLICK);
    }

    public void rightClick() {
        executeJavaScriptMouseAction(MouseEvent.RIGHT_CLICK);
    }

    public void middleClick() {
        executeJavaScriptMouseAction(MouseEvent.MIDDLE_CLICK);
    }

    private void executeJavaScriptMouseAction(MouseEvent type){
        int x = this.x + width/2;
        int y = this.y + height/2;

        new Actions(driver).moveToElement(webElement).perform();

        switch (type) {
            case LEFT_CLICK:
                driver.executeScript("" +
                        "var event = document.createEvent('MouseEvents'); \n" +
                        "event.initMouseEvent('click',true, true, window, 0, 0, 0, "
                        + x + ", " + y + ", " +
                        "false, false, false, false, 0, null); \n" +
                        "arguments[0].dispatchEvent(event);", webElement);
                break;
            case RIGHT_CLICK:
                driver.executeScript("" +
                        "var event = document.createEvent('MouseEvents'); \n" +
                        "event.initMouseEvent('click',true, true, window, 0, 0, 0, "
                        + x + ", " + y + ", " +
                        "false, false, false, false, 2, null); \n" +
                        "arguments[0].dispatchEvent(event);", webElement);
                break;
            case MIDDLE_CLICK:
                driver.executeScript("" +
                        "var event = document.createEvent('MouseEvents'); \n" +
                        "event.initMouseEvent('click',true, true, window, 0, 0, 0, "
                        + x + ", " + y + ", " +
                        "false, false, false, false, 1, null); \n" +
                        "arguments[0].dispatchEvent(event);", webElement);
                break;
            case DOUBLE_CLICK:
                driver.executeScript("" +
                        "var event = document.createEvent('MouseEvents'); \n" +
                        "event.initMouseEvent('dblclick',true, true, window, 0, 0, 0, "
                        + x + ", " + y + ", " +
                        "false, false, false, false, 0, null); \n" +
                        "arguments[0].dispatchEvent(event);", webElement);
                break;
        }
    }
}
