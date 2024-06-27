package by.mts;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class Tests extends BaseTests {
    @Test
    public void checkTheTitleOfBlock() {
        chromeDriver.get("http://mts.by");
        clickCookieButton();
        WebElement title = chromeDriver.findElement(By.xpath("//h2[contains(text(), 'Онлайн пополнение')]"));
        String expected = "Онлайн пополнение\n" +
                "без комиссии";
        Assertions.assertEquals(title.getText(), expected, "Заголовок не соответствует требуемому");
    }

    @ParameterizedTest
    @ValueSource(strings = {"Visa", "Verified By Visa", "MasterCard", "MasterCard Secure Code", "Белкарт"})
    public void checkTheLogoOfBlock(String input) {
        chromeDriver.get("http://mts.by");
        clickCookieButton();
        List<WebElement> logoList = chromeDriver.findElements(By.xpath("//img[contains(@src, " +
                "'/local/templates/new_design/assets/html/images/pages/index/pay')]"));
        List<String> logoNames = logoList.stream()
                .map(webElement -> webElement.getAttribute("alt"))
                .collect(Collectors.toList());
        Assertions.assertTrue(logoNames.contains(input), "На странице отсутствуют требуемое изображение: " + input);
    }

    @Test
    public void checkTheLink() {
        chromeDriver.get("http://mts.by");
        clickCookieButton();
        String startPage = chromeDriver.getCurrentUrl();
        WebElement button = chromeDriver.findElement(By.xpath("//a[contains(text(), 'Подробнее о сервисе')]"));
        button.click();
        String newPage = chromeDriver.getCurrentUrl();
        Assertions.assertNotEquals(startPage, newPage, "Ссылка 'Подробнее о сервисе' нерабочая");
    }

    @Test
    public void checkInputData() {
        chromeDriver.get("http://mts.by");
        clickCookieButton();
        WebElement phoneNumber =
                chromeDriver.findElement(By.xpath("//div[@class='input-wrapper input-wrapper_label-left']" +
                        "/input[@class='phone'][../label[@for='connection-phone']]"));
        phoneNumber.sendKeys("297777777");
        WebElement sum =
                chromeDriver.findElement(By.xpath("//div[@class='input-wrapper input-wrapper_label-right']" +
                        "/input[@class='total_rub'][../label[@for='connection-sum']]"));
        sum.sendKeys("100");
        WebElement continueButton =
                chromeDriver.findElement(By.xpath("//form[@class='pay-form opened']/button[contains(text(), " +
                        "'Продолжить')]"));
        continueButton.click();
        WebDriverWait wait = new WebDriverWait(chromeDriver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//iframe[@class='bepaid-iframe']")));
        Assertions.assertTrue(chromeDriver.findElement(By.xpath("//iframe[@class='bepaid-iframe']")).isDisplayed(),
                "Кнопка 'Продолжить' не работает");

    }

    private void clickCookieButton() {
        WebElement cookieButton = chromeDriver.findElement(By.xpath("//button[@class='btn btn_black cookie__ok']"));
        cookieButton.click();
    }
}
