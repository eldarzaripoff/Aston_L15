package ru.netology.helpers;

import java.util.Arrays;
import java.util.List;

public class ExpectedData {
    public static final String MTS_LINK = "http://mts.by";
    public static final String EXPECTED_TITLE_OF_MTS_MAIN_PAGE = "Онлайн пополнение\n" + "без комиссии";

    public static final String VISA = "Visa";

    public static final String VERIFIED_BY_VISA = "Verified By Visa";

    public static final String MASTER_CARD = "MasterCard";

    public static final String MASTER_CARD_SECURE_CODE = "MasterCard Secure Code";

    public static final String BEL_CARD = "Белкарт";

    public static final String EXPECTED_CARD_NUMBER = "Номер карты";

    public static final String EXPECTED_VALIDITY_PERIOD = "Срок действия";

    public static final String CVC = "CVC";

    public static final String NAME_OF_CARD_HOLDER = "Имя держателя (как на карте)";

    public static final List<String> EXPECTED_ICONS = Arrays.asList("mastercard-system.svg",
            "visa-system.svg",
            "belkart-system.svg",
            "mir-system-ru.svg",
            "maestro-system.svg");

}
