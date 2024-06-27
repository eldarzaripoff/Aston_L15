package by.mts;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class BaseTests {
    WebDriver chromeDriver;
    @BeforeEach
    public void setChromeDriver() {
        System.setProperty("webdriver.chrome.driver", System.getenv("CHROME_DRIVER"));
        //System.setProperty("webdriver.chrome.driver", "C:\\Temp\\chromedriver-win64\\chromedriver.exe");
        chromeDriver = new ChromeDriver();
        chromeDriver.manage().window().maximize();
        chromeDriver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);

    }
    @AfterEach
    public void after() {
        chromeDriver.quit();
    }
}
