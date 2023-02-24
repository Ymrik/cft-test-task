package com.barievumar.projects.cft.sort;

import com.barievumar.projects.cft.enums.DataType;
import com.barievumar.projects.cft.enums.SortDirection;

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

/**
 * Класс, содержащий алгоритм слияния файлов.
 *
 * @param <T> Тип данных, с которым работает класс.
 */
public class FilesMergeSort<T> {
    /**
     * Логгер.
     */
    public static Logger logger = Logger.getGlobal();

    /**
     * Тип данных, с которым работает класс.
     *
     * @see DataType
     */
    private final DataType dataType;

    /**
     * Конструктор
     *
     * @param dataType тип данным.
     * @see DataType
     */
    public FilesMergeSort(DataType dataType) {
        this.dataType = dataType;
    }

    /**
     * Метод, выполняющий сортировку слиянием нескольких файлов.
     *
     * @param sortDirection направление сортировки.
     * @param files         пути к файлам, которые будут объеденены и отсортированы отсортированы.
     * @return Список отсортированных значений.
     * @see SortDirection
     */
    public List<T> mergeSortFiles(SortDirection sortDirection, Path... files) throws IOException {
        // сначала получаем содержимое первого файла.
        var resultFiles = sortFile(files[0], sortDirection);

        for (int i = 1; i < files.length; i++) {
            var file = files[i];
            var values = sortFile(file, sortDirection);
            logger.info("Merging with file " + file);
            resultFiles = merge(resultFiles, values, sortDirection);
        }

        return resultFiles;
    }

    /**
     * Метод, выполняющий сортировку одного файла. В случае, если файл
     * уже отсортирован, возвращает значения из файла.
     *
     * @param filePath      путь к файлу.
     * @param sortDirection направление сортировки.
     * @return Список отсортированных значений из файла.
     * @see SortDirection
     */
    private List<T> sortFile(Path filePath, SortDirection sortDirection) throws IOException {
        var values = getAllValuesFromFile(filePath);
        if (checkIfValuesAreNotSorted(values, sortDirection)) {
            logger.info("File " + filePath + " is not sorted. Sorting this file...");
            values = mergeSortValues(values, sortDirection);
            logger.info("File " + filePath + " sorted successfully");
        }
        return values;
    }

    /**
     * Метод, проверяющий, отсортированы ли значения.
     *
     * @param values        список значений.
     * @param sortDirection направление сортировкк.
     * @return true если значения отсортированы, false, если значения не отсортированы.
     */
    private boolean checkIfValuesAreNotSorted(List<T> values, SortDirection sortDirection) {
        for (int i = 0; i < values.size() - 1; i++) {
            if (sortDirection.equals(SortDirection.ASCENDING)) {
                if (compare(values.get(i), values.get(i + 1)) < 0) return true;
            }
            if (sortDirection.equals(SortDirection.DESCENDING)) {
                if (compare(values.get(i), values.get(i + 1)) > 0) return true;
            }
        }
        return false;
    }

    /**
     * Метод возвращающий все значения из файла. Также выполняющий обработку ошибок.
     * Если в строке файла содержится несколько значений, они рассматриваются по отдельности.
     * Если строка файла содержит недопустимое значение, это значение пропускается.
     *
     * @param filePath путь к файлу.
     * @return список значений из файла.
     */
    private List<T> getAllValuesFromFile(Path filePath) throws IOException {
        var values = new ArrayList<T>();

        var fileData = Files.readAllLines(filePath);

        for (int i = 0; i < fileData.size(); i++) {
            var line = fileData.get(i).trim();
            if (line.contains(" ")) {
                logger.warning("Line " + (i + 1) + " in file " + filePath + " contains few values." +
                        " They will be separated: " + line);
                var valuesInLine = line.split(" ");
                for (String value : valuesInLine) {
                    try {
                        values.add(parseValue(value));
                    } catch (NumberFormatException e) {
                        logger.warning("Error in line number " + (i + 1) + " in file " + filePath
                                + ". Value '" + value + "' Cannot be parsed into " + dataType + ". This value will be skipped.");
                    }
                }
                continue;
            }
            try {
                values.add(parseValue(line));
            } catch (NumberFormatException e) {
                logger.warning("Error in line number " + (i + 1) + " in file " + filePath
                        + ". Value '" + line + "' Cannot be parsed into Integer. This value will be skipped.");
            }
        }
        return values;
    }

    /**
     * Метод, выполняющий сортировку слиянием значений из переданного списка.
     *
     * @param values        список значений, которые будут сортироваться.
     * @param sortDirection направление сортировки.
     * @return список отсортированных значений.
     */
    private List<T> mergeSortValues(List<T> values, SortDirection sortDirection) {
        var resultAsArray = mergeSortAsArray(createAndFillArr(values), sortDirection);

        return List.of(resultAsArray);
    }

    /**
     * Метод, выполняющий сортировку слиянием значений из переданного массива.
     *
     * @param values        массив значений.
     * @param sortDirection направление сортировки.
     * @return остортированный массив.
     */
    private T[] mergeSortAsArray(T[] values, SortDirection sortDirection) {
        if (values.length < 2) return createAndFillArr(List.of(values[0]));

        var firstHalf = mergeSortAsArray(Arrays.copyOfRange(values, 0, values.length / 2), sortDirection);
        var secondHalf = mergeSortAsArray(Arrays.copyOfRange(values, values.length / 2, values.length), sortDirection);

        return mergeAsArray(firstHalf, secondHalf, sortDirection);
    }

    /**
     * Метод выполняющий слияние двух отсортированных списков значений.
     *
     * @param firstHalf     первый список значений.
     * @param secondHalf    второй список значений.
     * @param sortDirection направление сортировки.
     * @return список, получившийся в результате слияния.
     */
    private List<T> merge(List<T> firstHalf, List<T> secondHalf, SortDirection sortDirection) {
        var firstHalfAsArr = createAndFillArr(firstHalf);
        var secondHalfAsArr = createAndFillArr(secondHalf);

        return List.of(mergeAsArray(firstHalfAsArr, secondHalfAsArr, sortDirection));
    }

    /**
     * Метод выполняющий слияние двух отсортированных массивов значений.
     *
     * @param firstHalf     первый массив значений.
     * @param secondHalf    второй массив значений.
     * @param sortDirection направление сортировки.
     * @return массив, получившийся в результате слияния.
     */
    private T[] mergeAsArray(T[] firstHalf, T[] secondHalf, SortDirection sortDirection) {
        var firstIndex = 0;
        var secondIndex = 0;

        var resultIndex = 0;
        var resultArr = createEmptyArr(firstHalf.length + secondHalf.length);

        while (firstIndex < firstHalf.length && secondIndex < secondHalf.length) {
            var ascAndSecondBigger = sortDirection.equals(SortDirection.ASCENDING)
                    && compare(firstHalf[firstIndex], secondHalf[secondIndex]) < 0;
            var descAndSecondLower = sortDirection.equals(SortDirection.DESCENDING)
                    && compare(firstHalf[firstIndex], secondHalf[secondIndex]) > 0;

            if (ascAndSecondBigger || descAndSecondLower) {
                resultArr[resultIndex] = firstHalf[firstIndex];
                firstIndex++;
            } else {
                resultArr[resultIndex] = secondHalf[secondIndex];
                secondIndex++;
            }

            resultIndex++;
        }

        if (firstIndex < firstHalf.length) return moveLastElements(firstHalf, firstIndex, resultArr, resultIndex);
        else return moveLastElements(secondHalf, secondIndex, resultArr, resultIndex);
    }

    /**
     * Метод, выполняющий перенос всех элементов из одного массива (начиная с какого-то индекса) в другой массив.
     * Размеры массивов и переносимых элементов должны правильно соотноситься.
     *
     * @param srcArr    массив, из которого будут переноситься значения.
     * @param srcIndex  индекс, начиная с которого будут переноситься значения из исходного массива.
     * @param destArr   массив, в который будут переноситься значения.
     * @param destIndex индекс, начиная с которого будет заполняться второй массив.
     * @return массив полученный в резултате переноса.
     */
    private T[] moveLastElements(T[] srcArr, int srcIndex, T[] destArr, int destIndex) {
        while (srcIndex < srcArr.length) {
            destArr[destIndex] = srcArr[srcIndex];
            srcIndex++;
            destIndex++;
        }
        return destArr;
    }

    /**
     * Метод создающий пустой массив заданного размера.
     *
     * @param size размер
     * @return пустой массив заданного размера
     */
    private T[] createEmptyArr(int size) {
        return (T[]) Array.newInstance(dataType.getaClass(), size);
    }

    /**
     * Создает и заполняет массив значениями из списка.
     *
     * @param values список значений.
     * @return массив, заполненный значениями из списка.
     */
    private T[] createAndFillArr(List<T> values) {
        return values.toArray(createEmptyArr(0));
    }

    /**
     * Метод, выполняющий сравнение двух эелементов. Элементы должны реализовывать интерфейс Comparable.
     *
     * @param val1 первый элемент.
     * @param val2 второй элемент.
     * @return результат сравнения.
     * -1, если второй элемент больше,
     * 0, если элементы равны,
     * 1, если первый элемент больше.
     */
    private int compare(T val1, T val2) {
        int result = 0;
        try {
            result = ((Comparable) val1).compareTo(val2);
        } catch (Exception e) {
            System.out.println("Тип данных должен быть сравниваемым, и реализовывать Comparable!\n" + e);
        }
        return result;
    }

    /**
     * Метод выполняющий парсинг строки к типу данных T.
     *
     * @param str исходная строка.
     * @return значние - результат парсинга.
     */
    private T parseValue(String str) {
        T result = null;
        try {
            result = (T) dataType.getParser().parse(str);
        } catch (NullPointerException e) {
            System.out.println("No parser for dataType " + dataType + "! Please create parser for this dataType\n" + e);
        }
        return result;
    }
}
