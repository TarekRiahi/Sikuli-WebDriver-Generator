package se.poc.sikuli.webdriver.support.pagefactory;

import se.poc.sikuli.webdriver.ImageElement;

import java.util.List;

public interface ImageElementLocator {

    ImageElement findImageElement();
    List<ImageElement> findImageElements();

}
