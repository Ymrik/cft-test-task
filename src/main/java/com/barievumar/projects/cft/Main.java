package com.barievumar.projects.cft;

import com.barievumar.projects.cft.enums.DataType;
import com.barievumar.projects.cft.enums.SortDirection;
import com.barievumar.projects.cft.sort.MergeSort;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    /**
     * Направление сортировки.
     */
    private static SortDirection direction;
    /**
     * Тип данных с которым работает программа.
     */
    private static DataType dataType;
    /**
     * Путь к выходному файлу.
     */
    private static Path outputFilePath;
    /**
     * Список путей к входным файлам.
     */
    private static List<Path> inputFilePaths;

    /**
     * Логгер.
     */
    public static Logger logger = Logger.getGlobal();

    public static void main(String[] args) throws IOException {
        verifyArguments(args);
        MergeSort.sortAndWrite(dataType, direction, outputFilePath, inputFilePaths.toArray(new Path[]{}));
    }

    /**
     * Метод, выполняющий проверку и запись входных аргументов программы.
     *
     * @param args входные аргументы программы.
     */
    public static void verifyArguments(String[] args) {
        var argsIndex = 0;
        try {
            // получаем направление сортировки
            if (SortDirection.getAllValues().contains(args[0])) {
                if (args[0].equals(SortDirection.DESCENDING.getValue())) direction = SortDirection.DESCENDING;
                else direction = SortDirection.ASCENDING;
                logger.info("Sort direction: " + direction);
                argsIndex++;
            } else {
                logger.info("No parameter for sort direction, or wrong syntax. Direction set by default: ASCENDING.");
                direction = SortDirection.ASCENDING;
            }

            // получаем тип данных
            if (!DataType.getValues().contains(args[argsIndex]))
                throw new IllegalArgumentException("No argument for data type, or wrong syntax! Please specify data type.");
            if (args[argsIndex].equals(DataType.STRING.getValue())) dataType = DataType.STRING;
            else dataType = DataType.INTEGER;
            argsIndex++;
            logger.info("Data type: " + dataType);

            // получаем выходной файл
            outputFilePath = Paths.get(args[argsIndex]);

            // получаем первый входной файл
            inputFilePaths = new ArrayList<>();
            var firstInputFilePath = Paths.get(args[++argsIndex]);
            if (!Files.exists(firstInputFilePath)) {
                throw new IllegalArgumentException("The only one input file with name " + firstInputFilePath + " doesn't exist!");
            }
            inputFilePaths.add(firstInputFilePath);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Not enough input elements!\ndataType: " + dataType
                    + "\ndirection: " + direction + "\noutputFile: " + outputFilePath + "\ninputFiles: " + inputFilePaths);
        }

        // получаем остальные входные файлы
        while (++argsIndex < args.length) {
            var inputFilePath = Paths.get(args[argsIndex]);
            if (!Files.exists(inputFilePath)) {
                logger.log(Level.WARNING, "No file with name " + inputFilePath + "! This file will be skipped.");
                continue;
            }
            inputFilePaths.add(inputFilePath);
        }
    }
}
