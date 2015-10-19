package se.poc.sikuli.webdriver.support.pagefactory.internal;

import se.poc.sikuli.webdriver.ImageElement;
import se.poc.sikuli.webdriver.support.pagefactory.ImageElementLocator;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class LocatingImageElementHandler implements InvocationHandler {

    private final ImageElementLocator locator;

    public LocatingImageElementHandler(ImageElementLocator locator) {
        this.locator = locator;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        ImageElement element = locator.findImageElement();

        try {
            return method.invoke(element, args);
        } catch (InvocationTargetException e) {
            // Unwrap the underlying exception
            throw e.getCause();
        }
    }
}
