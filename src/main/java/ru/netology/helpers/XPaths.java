package ru.netology.helpers;

public class XPaths {
    public static final String COOKIE_BUTTON_XPATH = "//button[@class='btn btn_black cookie__ok']";

    public static final String TITLE_OF_FORM_XPATH = "//h2[contains(text(), 'Онлайн пополнение')]";

    public static final String LOGO_LIST_XPATH = "//img[contains(@src, " +
            "'/local/templates/new_design/assets/html/images/pages/index/pay')]";

    public static final String BUTTON_IN_DETAILS_XPATH = "//a[contains(text(), 'Подробнее о сервисе')]";

    public static final String IFRAME_XPATH = "//iframe[@class='bepaid-iframe']";

    public static final String BUTTON_FOR_LIST_UNROLL_XPATH = "//button[@class='select__header']";

    public static final String SPAN_CONTAINS_TEXT_BYN_XPATH = "//span[contains(text(), 'BYN')]";

    public static final String BUTTON_CONTAINS_TEXT_BYN_XPATH = "//button[contains(text(), 'BYN')]";

    public static final String PHONE_NUMBER_XPATH = "//span[contains(text(), 'Номер')]";

    public static final String CARD_NUMBER_XPATH = "//label[contains(text(), 'Номер карты')]";

    public static final String VALIDITY_PERIOD_XPATH = "//label[contains(text(), 'Срок действия')]";

    public static final String CVC_XPATH = "//label[contains(text(), 'CVC')]";

    public static final String NAME_OF_CARD_XPATH = "//label[contains(text(), 'Имя держателя (как на карте)')]";

    public static final String ICONS_XPATH = "//div[@class='cards-brands cards-brands__container ng-tns-c61-0 ng-trigger ng-trigger-brandsState ng-star-inserted']//img";
}
