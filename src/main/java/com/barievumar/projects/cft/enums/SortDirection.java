package com.barievumar.projects.cft.enums;

import java.util.List;

/**
 * Enum, содержащий направления сортировки, с которыми работают алгоритмы сортировки.
 */
public enum SortDirection {
    ASCENDING("-a"),
    DESCENDING("-d");

    /**
     * Значение направления сортировки как аргумента.
     */
    private final String value;

    SortDirection(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    /**
     * Метод, возвращающий все возможные направления сортировки.
     *
     * @return все возможные значения направления сортировки.
     */
    public static List<String> getAllValues() {
        return List.of(ASCENDING.getValue(), DESCENDING.getValue());
    }
}
