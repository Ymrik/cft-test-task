package com.barievumar.projects.cft.parsers;

/**
 * Парсер для Integer.
 */
public class IntegerParser implements Parser<Integer> {
    @Override
    public Integer parse(String strValue) {
        return Integer.valueOf(strValue);
    }
}
