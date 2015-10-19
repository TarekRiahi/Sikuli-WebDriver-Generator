package se.poc.sikuli.webdriver.support.pagefactory.internal;

import se.poc.sikuli.webdriver.ImageElement;
import se.poc.sikuli.webdriver.support.pagefactory.ImageElementLocator;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class LocatingImageElementListHandler implements InvocationHandler {

    private final ImageElementLocator locator;

    public LocatingImageElementListHandler(ImageElementLocator locator) {
        this.locator = locator;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        List<ImageElement> elements = locator.findImageElements();

        try {
            return method.invoke(elements, args);
        } catch (InvocationTargetException e) {
            // Unwrap the underlying exception
            throw e.getCause();
        }
    }
}
