Этот проект содержит в себе тестовое задание для стажировки в Центре Финансовых Технологий.

Текст задания прикреплен в конце данного файла.

Технологии и библиотеки, необходимые для запуска приложения:
- Java 18 (Oracle OpenJDK version 18.0.1)
- Hamcrest-core 1.3: https://mvnrepository.com/artifact/org.hamcrest/hamcrest-core/1.3
- JUnit 4.12:  https://mvnrepository.com/artifact/junit/junit/4.12

Запуск приложения.

Для запуска приложения необходимо указать аргументы командной строки и запустить метод Main.main():
1. режим сортировки (-a или -d), необязательный, по умолчанию сортируем по возрастанию;
2. тип данных (-s или -i), обязательный;
3. имя выходного файла, обязательное;
4. остальные параметры – имена входных файлов, не менее одного.
Пример: -d -i ouput.txt input1.txt input2.txt (по убыванию, целочисленный тип данных, входной файл - output.txt, два выходных файла - input1.txt и input2.txt)

В программе предусмотрена следующая обработка ошибок:
1. Проверка входных аргументов
  1a. При неверном указании или отсутствии параметра режима сортировки, сортировка устанавливается по умолчанию - по возрастанию.
  1б. При неверном указании типа данных, программа завершает работу.
  1в. При отсутствии выходного файла программа завершает работу.
  1г. Если программа получила на вход один входной файл, которого не существует, программа завершает работу
  1д. Если программа получила на вход несуществующие входные файлы, они пропускаются (при этом хотя бы один файл должен существовать. см. 1г).
2. Если строка файла содержит пробелы в конце или начале строки, они уничтожаются
3. Если строка файла содержит несколько значений, эти значения считаются как отдельные записи.
4. Если входной файл предварительно не отсортирован, выполняется его сортировка
5. Если типы данных в файле не соответствует установленному, конфликтующие значения пропускаются. Например если тип данных - Integer, а файл содержит значение 'string', это значение пропускается. Однако если установлен тип данных String и в строке файла есть числа, они обрабатываются как строки.

В программе присутствует логирование. А также Unit-тесты, проверяющие работоспособность программы.

В программе присутствует папка ресурсов, где присутсвтуют файлы, которые можно использовать как входные и выходные.
Если не хотите вручную писать пути к собственноручно созданным файлом, можете использовать указанные ниже параметры командной строки.

-d
-i
src/main/resources/outputFile.txt
src/main/resources/int_file_1.txt
src/main/resources/int_file_2.txt
src/main/resources/int_file_3.txt
