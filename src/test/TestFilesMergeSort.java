import com.barievumar.projects.cft.Main;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;


public class TestFilesMergeSort {

    private final Path intFile1 = Path.of("src/test/resources/int_file_1.txt");
    private final Path intFile2 = Path.of("src/test/resources/int_file_2.txt");
    private final Path intFile3 = Path.of("src/test/resources/int_file_3.txt");
    private final Path wrongIntFile = Path.of("src/test/resources/int_file_with_mistakes.txt");

    private final Path strFile1 = Path.of("src/test/resources/str_file_1.txt");
    private final Path strFile2 = Path.of("src/test/resources/str_file_2.txt");
    private final Path strFile3 = Path.of("src/test/resources/str_file_3.txt");
    private final Path wrongStrFile = Path.of("src/test/resources/str_file_with_mistakes.txt");

    private final Path outputFile = Path.of("src/test/resources/output_file.txt");

    @Test
    public void testWithIntegersAsc() throws IOException {
        Main.main(new String[]{"-a", "-i", outputFile.toString(), intFile1.toString()
                , intFile2.toString(), intFile3.toString()});

        var expected = getIntContentAsc();

        var actual = Files.readAllLines(Path.of("src/test/resources/output_file.txt"))
                .stream()
                .map(Integer::parseInt)
                .toList();

        assertNotNull(actual);
        assertEquals(actual, expected);
    }

    @Test
    public void testWithIntegersDesc() throws IOException {
        Main.main(new String[]{"-d", "-i", outputFile.toString(), intFile1.toString()
                , intFile2.toString(), intFile3.toString()});

        var expected = getIntContentAsc();
        Collections.reverse(expected);

        var actual = Files.readAllLines(Path.of("src/test/resources/output_file.txt"))
                .stream()
                .map(Integer::parseInt)
                .toList();

        assertNotNull(actual);
        assertEquals(actual, expected);
    }

    @Test
    public void testWithIntegerWithWrongInputFile() throws IOException {
        Main.main(new String[]{"-a", "-i", outputFile.toString(), intFile1.toString()
                , intFile2.toString(), intFile3.toString(), wrongIntFile.toString()});

        var expected = getIntContentAsc();
        expected.addAll(List.of(8, 61, 27, 56, 23, -23));
        Collections.sort(expected);

        var actual = Files.readAllLines(Path.of("src/test/resources/output_file.txt"))
                .stream()
                .map(Integer::parseInt)
                .toList();

        assertNotNull(actual);
        assertEquals(actual, expected);
    }

    @Test
    public void testWithNoDirectionArg() throws IOException {
        Main.main(new String[]{"-i", outputFile.toString(), intFile1.toString()
                , intFile2.toString(), intFile3.toString()});

        var expected = getIntContentAsc();

        var actual = Files.readAllLines(Path.of("src/test/resources/output_file.txt"))
                .stream()
                .map(Integer::parseInt)
                .toList();

        assertNotNull(actual);
        assertEquals(actual, expected);
    }

    @Test
    public void testWithNoDataTypeArg() throws IOException {
        try {
            Main.main(new String[]{"-a", outputFile.toString(), intFile1.toString()
                    , intFile2.toString(), intFile3.toString()});
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }
        fail();
    }

    @Test
    public void testWithNoDataTypeAndDirectionArgs() throws IOException {
        try {
            Main.main(new String[]{outputFile.toString(), intFile1.toString()
                    , intFile2.toString(), intFile3.toString()});
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }
        fail();
    }

    @Test
    public void testWithNoInputFiles() {
        try {
            Main.main(new String[]{"-i", outputFile.toString()});
        } catch (IllegalArgumentException | IOException e) {
            System.out.println(e.getMessage());
            return;
        }
        fail();
    }

    @Test
    public void testWithInputFileDoesntExist() throws IOException {
        Main.main(new String[]{"-a", "-i", outputFile.toString(), intFile1.toString()
                , intFile2.toString(), intFile3.toString(), "imaginaryFile.txt"});

        var expected = getIntContentAsc();

        var actual = Files.readAllLines(Path.of("src/test/resources/output_file.txt"))
                .stream()
                .map(Integer::parseInt)
                .toList();

        assertNotNull(actual);
        assertEquals(actual, expected);
    }

    @Test
    public void testWithStringAsc() throws IOException {
        Main.main(new String[]{"-a", "-s", outputFile.toString(), strFile1.toString()
                , strFile2.toString(), strFile3.toString()});

        var expected = getStringContentAsc();

        var actual = Files.readAllLines(Path.of("src/test/resources/output_file.txt"))
                .stream()
                .map(String::trim)
                .toList();

        assertNotNull(actual);
        assertEquals(actual, expected);
    }

    @Test
    public void testWithStringDesc() throws IOException {
        Main.main(new String[]{"-d", "-s", outputFile.toString(), strFile1.toString()
                , strFile2.toString(), strFile3.toString()});

        var expected = getStringContentAsc();
        Collections.reverse(expected);

        var actual = Files.readAllLines(Path.of("src/test/resources/output_file.txt"))
                .stream()
                .map(String::trim)
                .toList();

        assertNotNull(actual);
        assertEquals(actual, expected);
    }

    @Test
    public void testStringWithWrongInputFile() throws IOException {
        Main.main(new String[]{"-a", "-s", outputFile.toString(), strFile1.toString()
                , strFile2.toString(), strFile3.toString(), wrongStrFile.toString()});

        var expected = getStringContentAsc();
        expected.addAll(List.of("tree", "giraffe", "money", "string", "string2", "-23"));
        Collections.sort(expected);

        var actual = Files.readAllLines(Path.of("src/test/resources/output_file.txt"))
                .stream()
                .toList();

        assertNotNull(actual);
        assertEquals(actual, expected);
    }


    public List<Integer> getIntContentAsc() throws IOException {
        var file1Content = Files.readAllLines(intFile1).stream().map(Integer::parseInt).toList();
        var file2Content = Files.readAllLines(intFile2).stream().map(Integer::parseInt).toList();
        var file3Content = Files.readAllLines(intFile3).stream().map(Integer::parseInt).toList();

        var result = new ArrayList<Integer>();
        result.addAll(file1Content);
        result.addAll(file2Content);
        result.addAll(file3Content);

        result.sort(Integer::compareTo);
        return result;
    }

    public List<String> getStringContentAsc() throws IOException {
        var file1Content = Files.readAllLines(strFile1).stream().map(String::trim).toList();
        var file2Content = Files.readAllLines(strFile2).stream().map(String::trim).toList();
        var file3Content = Files.readAllLines(strFile3).stream().map(String::trim).toList();

        var result = new ArrayList<String>();
        result.addAll(file1Content);
        result.addAll(file2Content);
        result.addAll(file3Content);

        result.sort(String::compareTo);
        return result;
    }
}
