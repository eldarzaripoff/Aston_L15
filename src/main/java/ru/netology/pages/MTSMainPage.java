package ru.netology.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.netology.helpers.XPaths;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MTSMainPage {
    WebDriver chromeDriver;
    WebDriverWait wait;
    WebElement cookieButton;
    WebElement title;
    List<WebElement> logoList;
    WebElement buttonInDetails;
    WebElement phoneNumber;
    WebElement sum;
    WebElement continueButton;
    WebElement iframe;
    WebElement listUnrollButton;
    WebElement buttonForSelectOptionInList;
    List<WebElement> icons;

    public MTSMainPage(WebDriver chromeDriver) {
        this.chromeDriver = chromeDriver;
        wait = new WebDriverWait(chromeDriver, Duration.ofSeconds(10));
        this.cookieButton = chromeDriver.findElement(By.xpath(XPaths.COOKIE_BUTTON_XPATH));;
        this.title = chromeDriver.findElement(By.xpath(XPaths.TITLE_OF_FORM_XPATH));
        this.logoList = chromeDriver.findElements(By.xpath(XPaths.LOGO_LIST_XPATH));
        this.buttonInDetails = chromeDriver.findElement(By.xpath(XPaths.BUTTON_IN_DETAILS_XPATH));
        this.listUnrollButton = chromeDriver.findElement(By.xpath(XPaths.BUTTON_FOR_LIST_UNROLL_XPATH));
    }

    public List<String> getIconSourcesAsStrings() {
        icons = chromeDriver.findElements(By.xpath(XPaths.ICONS_XPATH));
        return icons.stream()
                .map(element -> element.getAttribute("src"))
                .collect(Collectors.toList());
    }

    public WebElement getButtonForSelectOptionInList(String option) {
        buttonForSelectOptionInList = chromeDriver.findElement(By.xpath("//p[contains(text(), '" + option + "')]"));
        wait.until(ExpectedConditions.elementToBeClickable(buttonForSelectOptionInList));
        return  buttonForSelectOptionInList;
    }

    public WebElement getListUnrollButton() {
        return listUnrollButton;
    }

    public WebElement getCookieButton() {
        wait.until(ExpectedConditions.elementToBeClickable(cookieButton));
        return cookieButton;
    }

    public List<WebElement> getLogoList() {
        return logoList;
    }

    public WebElement getButtonInDetails() {
        return buttonInDetails;
    }

    public WebElement getTitle() {
        return title;
    }

    public WebElement getPlaceHolder(String xPath) {
        return chromeDriver.findElement(By.xpath(xPath));
    }

    public List<String> getLogoListAsListOfStrings() {
        return logoList.stream()
                .map(webElement -> webElement.getAttribute("alt"))
                .collect(Collectors.toList());
    }
    public String getCurrentUrl() {
        return chromeDriver.getCurrentUrl();
    }

    /*
    Метод для заполнения анкеты в блоке "Услуги связи"
     */
    public void fillCommunicationServices(Map<String, String> inputData) {
        phoneNumber = chromeDriver.findElement(By.xpath("//div[@class='input-wrapper input-wrapper_label-left']" +
                        "/input[@class='phone'][../label[@for='connection-phone']]"));
        phoneNumber.sendKeys(inputData.get("phone"));

        sum = chromeDriver.findElement(By.xpath("//div[@class='input-wrapper input-wrapper_label-right']" +
                        "/input[@class='total_rub'][../label[@for='connection-sum']]"));
        sum.sendKeys(inputData.get("sum"));

        continueButton = chromeDriver.findElement(By.xpath("//form[@class='pay-form opened']/button[contains(text(), " +
                        "'Продолжить')]"));
        continueButton.click();
    }

    public void waitForVisibilityOfFrame() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XPaths.IFRAME_XPATH)));
    }

    public void waitForVisibilityOfFrameThroughAttribute() {
        wait.until(ExpectedConditions.attributeToBe(By.xpath(XPaths.IFRAME_XPATH), "style", "visibility: visible;"));
    }

    public WebElement getIframe() {
        iframe = chromeDriver.findElement(By.xpath(XPaths.IFRAME_XPATH));
        return iframe;
    }

    public String getPlaceHolderAsString(String partOfXpath) {
        WebElement webElement = chromeDriver.findElement(By.xpath("//input[@id='" + partOfXpath + "']"));
        return webElement.getAttribute("placeholder");
    }

    public void switchTheFrame() {
        chromeDriver.switchTo().frame(getIframe());
    }

    public void waitForSpanInFrame() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(), 'BYN')]")));
    }

    /*
    Метод извлекает и обрабатывает текстовые данные со страницы
     */
    public String stringModifier(String xPath, String regex, String replacement) {
        WebElement webElement = chromeDriver.findElement(By.xpath(xPath));
        return webElement.getText().replaceAll(regex, replacement);
    }
}
