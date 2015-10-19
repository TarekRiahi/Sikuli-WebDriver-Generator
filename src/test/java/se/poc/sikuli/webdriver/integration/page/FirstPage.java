package se.poc.sikuli.webdriver.integration.page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import se.poc.sikuli.webdriver.ImageElement;
import se.poc.sikuli.webdriver.support.FindByImage;
import se.poc.sikuli.webdriver.support.StateMapping;
import se.poc.sikuli.webdriver.support.Score;

public class FirstPage {

    @FindByImage(@StateMapping(state = "default", url = "classpath:img/mac/phantomjs-cool-smiley.jpg", minScore = Score.GOOD))
    private ImageElement smiley;

    @FindByImage({
            @StateMapping(state = "active", url = "classpath:img/mac/phantomjs-activeButton.png"),
            @StateMapping(state = "inactive", url = "classpath:img/mac/phantomjs-inactiveButton.png")
    })
    private ImageElement multiStateButton;

    @FindBy(name = "theButton")
    private WebElement button;

    public void clickSmiley() {
        smiley.click();
    }

    public void clickButton() {
        button.click();
    }

    public void clickMultiStateButton() {
        multiStateButton.click();
    }
}
