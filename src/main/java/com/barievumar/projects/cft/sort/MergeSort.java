package com.barievumar.projects.cft.sort;

import com.barievumar.projects.cft.enums.DataType;
import com.barievumar.projects.cft.enums.SortDirection;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

/**
 * Основной класс, являющийся входной точкой в сортировку файлов.
 */
public class MergeSort {
    /**
     * Логгер
     */
    public static Logger logger = Logger.getGlobal();

    /**
     * Таблица, содержащая различные алгоритмы слияния файлов.
     */
    private static final HashMap<DataType, FilesMergeSort<?>> filesMergeSorts = new HashMap<>();

    static {
        filesMergeSorts.put(DataType.INTEGER, new FilesMergeSort<Integer>(DataType.INTEGER));
        filesMergeSorts.put(DataType.STRING, new FilesMergeSort<String>(DataType.STRING));
    }

    /**
     * Метод, выполняющий сортировку входных файлов и запись значений в выходной файл.
     *
     * @param dataType      тип входных данных.
     * @param sortDirection направление сортировки
     * @param outputFile    путь к выходному файлу
     * @param inputFiles    пути к входным файлам.
     * @see DataType
     * @see SortDirection
     */
    public static void sortAndWrite(DataType dataType, SortDirection sortDirection, Path outputFile, Path... inputFiles) throws IOException {
        var sortResult = MergeSort.sort(dataType, sortDirection, inputFiles);

        try (PrintWriter outputStream = new PrintWriter(new FileWriter(outputFile.toString()))) {
            for (Object i : sortResult) {
                outputStream.println(i);
            }
        }

        logger.info("Successfully written in the file. Check merging result in file " + outputFile);
    }

    /**
     * Метод, выполняющий сортировку входных файлов.
     *
     * @param dataType      тип входных данных.
     * @param sortDirection направление сортировки
     * @param inputFiles    пути к входным файлам.
     * @return список отсортированных значений из входных файлов.
     * @see DataType
     * @see SortDirection
     */
    public static List<?> sort(DataType dataType, SortDirection sortDirection, Path... inputFiles) throws IOException {
        logger.info("Started merging files");
        List<?> result;

        result = filesMergeSorts.get(dataType).mergeSortFiles(sortDirection, inputFiles);

        logger.info("Files merged successfully.");
        return result;
    }

    /**
     * Метод, добавляющий в список алгоритмов новый экземпляр.
     *
     * @param dataType       тип данных.
     * @param filesMergeSort алгоритм для данного типа данных.
     */
    public static void addFileMergeSort(DataType dataType, FilesMergeSort<?> filesMergeSort) {
        filesMergeSorts.put(dataType, filesMergeSort);
    }
}
