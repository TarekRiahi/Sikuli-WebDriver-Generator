package se.poc.sikuli.webdriver.support.pagefactory;

import org.openqa.selenium.support.pagefactory.DefaultFieldDecorator;
import org.openqa.selenium.support.pagefactory.FieldDecorator;
import se.poc.sikuli.webdriver.ImageElement;
import se.poc.sikuli.webdriver.support.FindByImage;
import se.poc.sikuli.webdriver.support.pagefactory.internal.LocatingImageElementHandler;
import se.poc.sikuli.webdriver.support.pagefactory.internal.LocatingImageElementListHandler;

import java.lang.reflect.*;
import java.util.List;

public class ImageElementFieldDecorator extends DefaultFieldDecorator implements FieldDecorator {

    private final ImageElementLocatorFactory factory;

    public ImageElementFieldDecorator(ImageElementLocatorFactory factory) {
        super(factory);
        this.factory = factory;
    }

    @Override
    public Object decorate(ClassLoader loader, Field field) {
        if (!(ImageElement.class.isAssignableFrom(field.getType())
                || isDecoratableList(field)))
            return super.decorate(loader, field);

        ImageElementLocator locator = factory.createImageElementLocator(field);
        if(locator == null)
            return null;

        if(ImageElement.class.isAssignableFrom(field.getType())) {
            return ImageElementFieldDecorator.this.proxyForLocator(loader, locator);
        } else if (List.class.isAssignableFrom(field.getType())) {
            return ImageElementFieldDecorator.this.proxyForListLocator(loader, locator);
        } else {
            return null;
        }
    }

    private boolean isDecoratableList(Field field) {
        if (!List.class.isAssignableFrom(field.getType()))
            return false;

        // Type erasure in Java isn't complete. Attempt to discover the generic
        // type of the list.
        Type genericType = field.getGenericType();
        if (!(genericType instanceof ParameterizedType))
            return false;

        Type listType = ((ParameterizedType) genericType).getActualTypeArguments()[0];

        if (!ImageElement.class.equals(listType))
            return false;

        if (field.getAnnotation(FindByImage.class) == null)
            return false;

        return true;
    }

    protected ImageElement proxyForLocator(ClassLoader loader, ImageElementLocator locator) {
        InvocationHandler handler = new LocatingImageElementHandler(locator);

        ImageElement proxy;
        proxy = (ImageElement) Proxy.newProxyInstance(
                loader, new Class[]{ImageElement.class}, handler);
        return proxy;
    }

    protected List<ImageElement> proxyForListLocator(ClassLoader loader, ImageElementLocator locator) {
        InvocationHandler handler = new LocatingImageElementListHandler(locator);

        List<ImageElement> proxy;
        proxy = (List<ImageElement>) Proxy.newProxyInstance(
                loader, new Class[] {List.class}, handler);
        return proxy;
    }
}
