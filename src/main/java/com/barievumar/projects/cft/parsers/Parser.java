package com.barievumar.projects.cft.parsers;

/**
 * Интерфейс, объединяющий парсеры.
 *
 * @param <T>
 */
public interface Parser<T> {
    /**
     * Метод, выполняющий парсинг строки.
     *
     * @param strValue выходная строка.
     * @return значение - результат парсинга.
     */
    T parse(String strValue);
}
