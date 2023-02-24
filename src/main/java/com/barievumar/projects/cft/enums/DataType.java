package com.barievumar.projects.cft.enums;

import com.barievumar.projects.cft.parsers.IntegerParser;
import com.barievumar.projects.cft.parsers.Parser;
import com.barievumar.projects.cft.parsers.StringParser;

import java.util.List;

/**
 * Enum, содержащий типы данных, с которыми работают алгоритмы сортировки.
 */
public enum DataType {
    INTEGER("-i", Integer.class, new IntegerParser()),
    STRING("-s", String.class, new StringParser());

    /**
     * Значение типа данных как аргумента.
     */
    private final String value;
    /**
     * Класс соответствующего типа данных.
     */
    private final Class<?> aClass;
    /**
     * Парсер соответствующего типа данных.
     */
    private final Parser<?> parser;

    DataType(String value, Class<?> aClass, Parser<?> parser) {
        this.value = value;
        this.aClass = aClass;
        this.parser = parser;
    }

    public String getValue() {
        return value;
    }

    public Class<?> getaClass() {
        return aClass;
    }

    public Parser<?> getParser() {
        return parser;
    }

    /**
     * Метод, возвращающий все возможные значения типов данных.
     *
     * @return все возможные значения типов данных.
     */
    public static List<String> getValues() {
        return List.of(INTEGER.value, STRING.value);
    }
}
