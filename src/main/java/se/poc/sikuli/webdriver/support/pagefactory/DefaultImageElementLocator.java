package se.poc.sikuli.webdriver.support.pagefactory;

import se.poc.sikuli.webdriver.ImageElement;
import se.poc.sikuli.webdriver.SikuliWebDriver;
import se.poc.sikuli.webdriver.support.FindByImage;
import se.poc.sikuli.webdriver.support.StateMapping;
import se.poc.sikuli.webdriver.support.pagefactory.internal.protocols.classpath.Handler;

import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultImageElementLocator implements ImageElementLocator {

    private final SikuliWebDriver searchContext;
    private final FindByImage findByImage;

    /**
     * Creates a new element locator.
     *
     * @param searchContext The context to use when finding the element
     * @param field         The field on the Page Object that will hold the located value
     */
    public DefaultImageElementLocator(SikuliWebDriver searchContext, Field field) {
        this.searchContext = searchContext;
        findByImage = field.getAnnotation(FindByImage.class);
    }

    @Override
    public ImageElement findImageElement() {
        ImageElement element;

        try {
            Map<String, Map<String, Object>> states = new HashMap<>();
            for(StateMapping stateMapping : findByImage.value()) {
                if(stateMapping.state() == null)
                    throw new RuntimeException("State cannot be null");

                Map<String, Object> state = new HashMap<>();
                URL url;

                if(stateMapping.url().startsWith("classpath")) {
                    url = new URL(null, stateMapping.url(), new Handler(ClassLoader.getSystemClassLoader()));
                } else {
                    url = new URL(stateMapping.url());
                }

                state.put("url", url);
                state.put("minscore", stateMapping.minScore());

                if(states.put(stateMapping.state(), state) != null)
                    throw new RuntimeException("Duplication of state keys");
            }
            element = searchContext.findImageElement(states, findByImage.timeout());
        } catch (MalformedURLException e) {
            return null;
        }

        return element;
    }

    @Override
    public List<ImageElement> findImageElements() {
        throw new UnsupportedOperationException();
    }
}
