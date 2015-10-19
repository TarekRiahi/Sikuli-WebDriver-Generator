package se.poc.sikuli.webdriver.support.pagefactory;

import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

import java.lang.reflect.Field;

public interface ImageElementLocatorFactory extends ElementLocatorFactory {

    ImageElementLocator createImageElementLocator(Field field);
}
