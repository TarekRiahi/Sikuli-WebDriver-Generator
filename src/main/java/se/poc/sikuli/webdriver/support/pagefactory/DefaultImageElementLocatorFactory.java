package se.poc.sikuli.webdriver.support.pagefactory;

import org.openqa.selenium.support.pagefactory.DefaultElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import se.poc.sikuli.webdriver.SikuliWebDriver;

import java.lang.reflect.Field;

public class DefaultImageElementLocatorFactory implements ImageElementLocatorFactory {

    private final SikuliWebDriver searchContext;

    public DefaultImageElementLocatorFactory(SikuliWebDriver searchContext) {
        this.searchContext = searchContext;
    }

    @Override
    public ImageElementLocator createImageElementLocator(Field field) {
        return new DefaultImageElementLocator(searchContext, field);
    }

    @Override
    public ElementLocator createLocator(Field field) {
        return new DefaultElementLocator(searchContext, field);
    }
}
