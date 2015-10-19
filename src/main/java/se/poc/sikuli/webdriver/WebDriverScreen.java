package se.poc.sikuli.webdriver;

import org.openqa.selenium.OutputType;
import org.sikuli.api.Screen;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class WebDriverScreen implements Screen {

    final private SikuliWebDriver driver;
    final private Dimension size;

    public WebDriverScreen(SikuliWebDriver driver) throws IOException {
        this.driver = driver;
        File screenshotFile = driver.getScreenshotAs(OutputType.FILE);
        BufferedImage b = ImageIO.read(screenshotFile);

        if(b != null) {
            size = new Dimension(b.getWidth(),b.getHeight());
        } else {
            size = new Dimension(driver.manage().window().getSize().getWidth(), driver.manage().window().getSize().getHeight());
        }
    }

    BufferedImage crop(BufferedImage src, int x, int y, int width, int height) {
        BufferedImage dest = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        Graphics g = dest.getGraphics();
        g.drawImage(src, 0, 0, width, height, x, y, x + width, y + height, null);
        g.dispose();
        return dest;
    }


    @Override
    public BufferedImage getScreenshot(int x, int y, int width,
                                       int height) {
        File screenshotFile = driver.getScreenshotAs(OutputType.FILE);
        try {
            BufferedImage full = ImageIO.read(screenshotFile);
            BufferedImage cropped = crop(full, x, y, width, height);
            return cropped;
        } catch (IOException e) {
        }
        return null;
    }

    @Override
    public Dimension getSize() {
        return size;
    }
}
