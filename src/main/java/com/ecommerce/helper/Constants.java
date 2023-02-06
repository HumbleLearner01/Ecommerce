package com.ecommerce.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Constants {
    private static final List<String> initialCardList = Arrays.asList(
            "0000000000000000",
            "9999999999999999",
            "8888888888888888",
            "7777777777777777",
            "6666666666666666",
            "5555555555555555",
            "4444444444444444",
            "3333333333333333",
            "2222222222222222",
            "1111111111111111");
    private static final List<String> initialCvv2List = Arrays.asList(
            "0000", "000",
            "9999", "999",
            "8888", "888",
            "7777", "777",
            "6666", "666",
            "5555", "555",
            "4444", "444",
            "3333", "333",
            "2222", "222",
            "1111", "111");

    public static List<String> CVV2 = new ArrayList<>(initialCvv2List);
    public static List<String> CARD_NUMBER = new ArrayList<>(initialCardList);
}