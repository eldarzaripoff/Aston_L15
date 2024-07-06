package by.mts;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.WebElement;
import ru.netology.helpers.ExpectedData;
import ru.netology.helpers.XPaths;
import ru.netology.pages.MTSMainPage;

import java.util.Map;
import java.util.stream.Stream;

public class Tests extends BaseTests {
    @Test
    @DisplayName("Проверка названия заголовка")
    public void checkTheTitleOfBlock() {
        chromeDriver.get(ExpectedData.MTS_LINK);
        MTSMainPage mtsMainPage = new MTSMainPage(chromeDriver);
        mtsMainPage.getCookieButton().click();
        Assertions.assertEquals(
                ExpectedData.EXPECTED_TITLE_OF_MTS_MAIN_PAGE,
                mtsMainPage.getTitle().getText(),
                "Название заголовка не соответствует требуемому");
    }

    @ParameterizedTest
    @DisplayName("Проверка наличия логотипов платёжных систем")
    @ValueSource(strings = {ExpectedData.VISA, ExpectedData.VERIFIED_BY_VISA, ExpectedData.MASTER_CARD,
            ExpectedData.MASTER_CARD_SECURE_CODE, ExpectedData.BEL_CARD})
    public void checkTheLogoOfBlock(String input) {
        chromeDriver.get(ExpectedData.MTS_LINK);
        MTSMainPage mtsMainPage = new MTSMainPage(chromeDriver);
        mtsMainPage.getCookieButton().click();
        Assertions.assertTrue(
                mtsMainPage.getLogoListAsListOfStrings().contains(input),
                "На странице отсутствуют требуемое изображение: " + input);
        Assertions.assertTrue(mtsMainPage.getLogoList().stream().allMatch(WebElement::isDisplayed));
    }

    @Test
    @DisplayName("Проверка работы ссылки «Подробнее о сервисе»")
    public void checkTheLink() {
        chromeDriver.get(ExpectedData.MTS_LINK);
        MTSMainPage mtsMainPage = new MTSMainPage(chromeDriver);
        mtsMainPage.getCookieButton().click();
        mtsMainPage.getButtonInDetails().click();
        Assertions.assertNotEquals(ExpectedData.MTS_LINK,
                mtsMainPage.getCurrentUrl(),
                "Ссылка 'Подробнее о сервисе' нерабочая");
    }

    @ParameterizedTest
    @DisplayName("Проверка работы кнопки «Продолжить» (проверяем только вариант «Услуги связи», номер для теста 297777777)")
    @MethodSource("provideMapData")
    public void checkInputData(Map<String, String> inputData) {
        chromeDriver.get(ExpectedData.MTS_LINK);
        MTSMainPage mtsMainPage = new MTSMainPage(chromeDriver);
        mtsMainPage.getCookieButton().click();
        mtsMainPage.fillCommunicationServices(inputData);
        mtsMainPage.waitForVisibilityOfFrame();
        Assertions.assertTrue(mtsMainPage.getIframe().isDisplayed(),
                "Кнопка 'Продолжить' не работает");
    }

    @ParameterizedTest
    @DisplayName("""
            Проверка надписи в незаполненных полях каждого варианта оплаты услуг:\s
            услуги связи,\s
            домашний интернет,\s
            рассрочка,\s
            задолженность;""")
    @MethodSource("providePlaceHolders")
    public void checkPlaceHolders(String option, String phoneXpath, String phoneNumberExpected, String sumXpath,
                                  String sumExpected, String emailXpath, String emailExpected) {
        chromeDriver.get(ExpectedData.MTS_LINK);
        MTSMainPage mtsMainPage = new MTSMainPage(chromeDriver);
        mtsMainPage.getCookieButton().click();
        mtsMainPage.getListUnrollButton().click();
        mtsMainPage.getButtonForSelectOptionInList(option).click();

        /*
        Проверка плэйсхолдера в поле номера телефона
         */
        Assertions.assertTrue(mtsMainPage.getPlaceHolderAsString(phoneXpath).contains(phoneNumberExpected));
         /*
        Проверка плэйсхолдера в поле введённой суммы
         */
        Assertions.assertTrue(mtsMainPage.getPlaceHolderAsString(sumXpath).contains(sumExpected));
         /*
        Проверка плэйсхолдера в поле электронной почты
         */
        Assertions.assertTrue(mtsMainPage.getPlaceHolderAsString(emailXpath).contains(emailExpected));
    }

    @ParameterizedTest
    @DisplayName("проверить корректность отображения суммы (в том числе на кнопке)," +
            " номера телефона, " +
            "а также надписей в незаполненных полях для ввода реквизитов карты, наличие иконок платёжных систем.")
    @MethodSource("provideMapData")
    public void checkPopUp(Map<String, String> inputData) {
        chromeDriver.get(ExpectedData.MTS_LINK);
        MTSMainPage mtsMainPage = new MTSMainPage(chromeDriver);
        mtsMainPage.getCookieButton().click();
        mtsMainPage.fillCommunicationServices(inputData);
        mtsMainPage.waitForVisibilityOfFrameThroughAttribute();
        mtsMainPage.switchTheFrame();
        mtsMainPage.waitForSpanInFrame();

        /*
        Проверка корректности отображения суммы в pop-up
         */
        Assertions.assertEquals(inputData.get("sum"), mtsMainPage.stringModifier(
                        XPaths.SPAN_CONTAINS_TEXT_BYN_XPATH, "\\.00 BYN", ""),
                "В pop-up сумма отображается некорректно");

        /*
        Проверка корректности отображения суммы на кнопке в pop-up
         */
        Assertions.assertEquals(inputData.get("sum"), mtsMainPage.stringModifier(
                        XPaths.BUTTON_CONTAINS_TEXT_BYN_XPATH, "Оплатить |.00 BYN", ""),
                "На кнопке в pop-up сумма отображается некорректно");

        /*
        Проверка корректности отображения номера телефона в pop-up
         */
        Assertions.assertEquals(inputData.get("phone"),
                mtsMainPage.stringModifier(XPaths.PHONE_NUMBER_XPATH, ".*?(297777777).*", "$1"),
                "Placeholder " + inputData.get("phone") + " отсутствует или содержит неверные данные");

        /*
        Проверка корректности отображения плэйсхоледра в поле 'Номер карты' в pop-up
         */
        Assertions.assertEquals(ExpectedData.EXPECTED_CARD_NUMBER,
                mtsMainPage.getPlaceHolder(XPaths.CARD_NUMBER_XPATH).getText(),
                "Placeholder 'Номер карты' неверный либо отсутствует");

        /*
        Проверка корректности отображения плэйсхоледра в поле 'Срок действия' в pop-up
         */
        Assertions.assertEquals(ExpectedData.EXPECTED_VALIDITY_PERIOD,
                mtsMainPage.getPlaceHolder(XPaths.VALIDITY_PERIOD_XPATH).getText(),
                "Placeholder 'Срок действия' неверный либо отсутствует");

        /*
        Проверка корректности отображения плэйсхоледра в поле 'CVC' в pop-up
         */
        Assertions.assertEquals(ExpectedData.CVC, mtsMainPage.getPlaceHolder(XPaths.CVC_XPATH).getText(),
                "Placeholder 'CVC' неверный либо отсутствует");

        /*
        Проверка корректности отображения плэйсхоледра в поле 'Имя держателя' в pop-up
         */
        Assertions.assertEquals(ExpectedData.NAME_OF_CARD_HOLDER, mtsMainPage.getPlaceHolder(XPaths.NAME_OF_CARD_XPATH).getText(),
                "Placeholder 'Имя держателя (как на карте)' неверный либо отсутствует");

        /*
        Проверка наличия иконок платёжных систем в pop-up
         */
        Assertions.assertTrue(mtsMainPage.getIconSourcesAsStrings().stream()
                .allMatch(source -> ExpectedData.EXPECTED_ICONS.stream()
                        .anyMatch(source::contains)));
    }

    /*
    Наборы аргументов для проверки каждого варианта оплаты услуг:
    1. Вариант услуги
    2. Фрагмент xPath для поля номера телефона
    3. Ожидаемое содержимое Placeholder для поля номера телефона
    4. Фрагмент xPath для поля вводимой суммы
    5. Ожидаемое содержимое Placeholder для поля суммы
    6. Фрагмент xPath для поля электронной почты
    7. Ожидаемое содержимое Placeholder для поля электронной почты
     */
    private static Stream<Arguments> providePlaceHolders() {
        return Stream.of(
                Arguments.arguments("Услуги связи",
                        "connection-phone", "Номер телефона",
                        "connection-sum", "Сумма",
                        "connection-email", "E-mail для отправки чека"),
                Arguments.arguments("Домашний интернет",
                        "internet-phone", "Номер абонента",
                        "internet-sum", "Сумма",
                        "internet-email", "E-mail для отправки чека"),
                Arguments.arguments("Рассрочка",
                        "score-instalment", "Номер счета на 44",
                        "instalment-sum", "Сумма",
                        "instalment-email", "E-mail для отправки чека"),
                Arguments.arguments("Задолженность",
                        "score-arrears", "Номер счета на 2073",
                        "arrears-sum", "Сумма",
                        "arrears-email", "E-mail для отправки чека")
        );
    }

    /*
    Вводимые данные в блоке "Услуги связи"
    Метод создан с целью повторного использования данных в различных тестах
     */
    private static Stream<Map<String, String>> provideMapData() {
        Map<String, String> inputData = Map.of("phone", "297777777", "sum", "100");
        return Stream.of(inputData);
    }
}
