package com.mustafa.dishdash.utils;

import java.util.HashMap;
import java.util.Map;

public class CountryNameConverter {
    private static final Map<String, String> countryCodeMap = new HashMap<>();

    // Static block to initialize the country-code map
    static {
        countryCodeMap.put("American", "US");
        countryCodeMap.put("British", "GB");
        countryCodeMap.put("Canadian", "CA");
        countryCodeMap.put("Chinese", "CN");
        countryCodeMap.put("Croatian", "HR");
        countryCodeMap.put("Dutch", "NL");
        countryCodeMap.put("Egyptian", "EG");
        countryCodeMap.put("Filipino", "PH");
        countryCodeMap.put("French", "FR");
        countryCodeMap.put("Greek", "GR");
        countryCodeMap.put("Indian", "IN");
        countryCodeMap.put("Irish", "IE");
        countryCodeMap.put("Italian", "IT");
        countryCodeMap.put("Jamaican", "JM");
        countryCodeMap.put("Japanese", "JP");
        countryCodeMap.put("Kenyan", "KE");
        countryCodeMap.put("Malaysian", "MY");
        countryCodeMap.put("Mexican", "MX");
        countryCodeMap.put("Moroccan", "MA");
        countryCodeMap.put("Norwegian", "NO");
        countryCodeMap.put("Polish", "PL");
        countryCodeMap.put("Portuguese", "PT");
        countryCodeMap.put("Russian", "RU");
        countryCodeMap.put("Spanish", "ES");
        countryCodeMap.put("Thai", "TH");
        countryCodeMap.put("Tunisian", "TN");
        countryCodeMap.put("Turkish", "TR");
        countryCodeMap.put("Ukrainian", "UA");
        countryCodeMap.put("Uruguayan", "UY");
        countryCodeMap.put("Vietnamese", "VN");
    }

    // Function to get country code
    private static String getCountryCode(String countryName) {
        return countryCodeMap.getOrDefault(countryName, "Unknown");
    }

    public static String getCountryThumbnail(String countryName) {
        return Constants.COUNTRY_FLAGS_URL + getCountryCode(countryName) + Constants.PNG_EXTENSION;
    }
}
