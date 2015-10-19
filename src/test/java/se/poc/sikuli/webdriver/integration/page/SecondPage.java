package se.poc.sikuli.webdriver.integration.page;

import se.poc.sikuli.webdriver.ImageElement;
import se.poc.sikuli.webdriver.support.FindByImage;
import se.poc.sikuli.webdriver.support.StateMapping;

import static se.poc.sikuli.webdriver.support.Score.GOOD;

public class SecondPage {

    @FindByImage(value = {
            @StateMapping(state = "default", url = "classpath:img/mac/phantomjs-superhero.png", minScore = GOOD)
    }, timeout = 5000)
    private ImageElement superHero;

    public void clickSuperHero() {
        superHero.click();
    }
}
