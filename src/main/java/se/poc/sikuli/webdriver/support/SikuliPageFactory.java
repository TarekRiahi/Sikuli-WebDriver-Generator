package se.poc.sikuli.webdriver.support;

import org.openqa.selenium.support.PageFactory;
import se.poc.sikuli.webdriver.SikuliWebDriver;
import se.poc.sikuli.webdriver.support.pagefactory.DefaultImageElementLocatorFactory;
import se.poc.sikuli.webdriver.support.pagefactory.ImageElementFieldDecorator;
import se.poc.sikuli.webdriver.support.pagefactory.ImageElementLocatorFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class SikuliPageFactory {

    public static <T> T initElements(SikuliWebDriver driver, Class<T> pageClassToProxy) {
        T page = instantiatePage(driver, pageClassToProxy);
        initElements(driver, page);
        return page;
    }

    public static void initElements(SikuliWebDriver driver, Object page) {
        final SikuliWebDriver driverRef = driver;
        initElements(new DefaultImageElementLocatorFactory(driverRef), page);
    }

    public static void initElements(ImageElementLocatorFactory factory, Object page) {
        final ImageElementLocatorFactory factoryRef = factory;
        PageFactory.initElements(new ImageElementFieldDecorator(factoryRef), page);
    }

    private static <T> T instantiatePage(SikuliWebDriver driver, Class<T> pageClassToProxy) {
        try {
            try {
                Constructor<T> constructor = pageClassToProxy.getConstructor(SikuliWebDriver.class);
                return constructor.newInstance(driver);
            } catch (NoSuchMethodException e) {
                return pageClassToProxy.newInstance();
            }
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
