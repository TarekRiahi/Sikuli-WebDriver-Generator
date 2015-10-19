Sikuli Web Driver Generator
=========================================
A library that generalizes sikuli and selenium web drivers. Developers can inject whatever webdriver that supports 
taking full page screenshots, and gets rewarded with image recognition attached ontop of the web driver.

This small library can be seen as the glue between the following two:

* [Sikuli](https://code.google.com/p/sikuli-api/)
* [Selenium WebDriver](http://www.seleniumhq.org/projects/webdriver/)

Sikuli Web Driver Generator adds functionality to existing Web Drivers to support Sikuli's ability for image recognition.
This provides you the ability to surf the web using only images as a source. No DOM parsing required. It also works in headless mode.

The library has been tested using the following:

* Selenium version 2.35.0
* [FirefoxDriver](https://code.google.com/p/selenium/wiki/FirefoxDriver) version 2.35.0
* [PhantomJSDriver](https://github.com/detro/ghostdriver) version 1.0.4
* Sony Vaio VPCZ1 - Windows 8
* Sony Vaio VPCSE - Windows 7
* MacBook Pro OS X 10.8.5

## How does it work?
### Architectural overview
Sikuli Web Driver Generator is a bidirectional plugin which can be seen as the glue between Sikuli and Selenium.
The end result is an API that provides Selenium Web Driver, including page object pattern, and also Sikuli's image recognition functionality.
![Alt text](BILD"Architectural overview")

### Component diagram
The component diagram below clarifies how Sikuli and Selenium are combined and delivered as one unit to potential developers.
![Alt text](BILD "Component diagram")

## Example of usage

### PhantomJS
<pre class="brush: java">
SikuliWebDriver driver = SikuliWebDriverFactory.createFactory(PhantomJSDriver.class).create();
driver.setScreenSize(new Dimension(1280, 1024));
driver.get("http://www.google.com/ncr");
ImageElement element = driver.findImageElement(someImageURL);
element.click();
</pre>

### Firefox
<pre class="brush: java">
SikuliWebDriver driver = SikuliWebDriverFactory.createFactory(FirefoxDriver.class).create();
driver.setScreenSize(new Dimension(1280, 1024));
driver.get("http://www.google.com/ncr");
ImageElement element = driver.findImageElement(someImageURL);
element.click();
</pre>

### Page object pattern
Sikuli Web Driver Generator supports Selenium's page object pattern.

#### A new cool annotation
A new @FindByImage annotation is introduced. It supports ImageElement to change states. This is very useful
for highly dynamic applications, where the very same component changes its look and feel depending on user interaction.

<table border="1" cellspacing="0" cellpadding="4">
 <caption style="text-align:left;">@FindByImage</caption>
 <tr>
  <th>Property</th>
  <th>Explanation</th>
  <th>Default value</th>
 </tr>
 <tr>
  <td>value</td>
  <td>Array of <code>@StateMapping</code></td>
  <td><code>null</code></td>
 </tr>
 <tr>
  <td>timeout</td>
  <td><code>milliseconds</code> to wait for image</td>
  <td><code>10000</code></td>
 </tr>
</table>
<br />
<table border="1" cellspacing="0" cellpadding="4">
 <caption style="text-align:left;">@StateMapping</caption>
 <tr>
  <th>Property</th>
  <th>Explanation</th>
  <th>Default value</th>
 </tr>
 <tr>
  <td>state</td>
  <td>Must be unique within <code>@FindByImage</code></td>
  <td><code>default</code></td>
 </tr>
 <tr>
  <td>url</td>
  <td>Image URL, can be prefixed by <code>classpath:</code> to load from classpath</td>
  <td><code>null</code></td>
 </tr>
 <tr>
  <td>minScore</td>
  <td>Minimum accepted level of match; Score.FULL, Score.GOOD, Score.MEDIOCRE and Score.LOW</td>
  <td><code>Score.GOOD</code></td>
 </tr>
</table>
<br />
#### Page
<pre>
public class FirstPage {

    @FindByImage(value = {
        @StateMapping(state = "active", url = "classpath:img/activeButton.png", minScore = GOOD),
        @StateMapping(state = "inactive", url = "classpath:img/inactiveButton.png", minScore = GOOD)
    }, timeout = 10000)
    private ImageElement multiStateButton;

    @FindBy(name = "theButton")
    private WebElement button;

    public void clickMultiStateButton() {
        multiStateButton.click();
    }

    public void clickButton() {
        button.click();
    }
}
</pre>

#### Init page elements using Sikuli Page Factory
<pre>
FirstPage firstPage = SikuliPageFactory.initElements(driver, FirstPage.class);
firstPage.clickButton();
firstPage.clickMultiStateButton();
firstPage.clickMultiStateButton();
</pre>



