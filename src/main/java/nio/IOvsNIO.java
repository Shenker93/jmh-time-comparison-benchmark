package nio;


import org.openjdk.jmh.annotations.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/**
 * Contains some methods that are using "old school" loops and new java 8 approach based on streams
 */
@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(1)                            //
@Warmup(iterations = 10)            //time can be defined here too
@Measurement(iterations = 10)       //time can be defined here too
public class IOvsNIO {

    private static final String FILE_PATH = "src/main/resources/test.txt";

    @Benchmark
    public int countWordsIO() throws IOException {

        return countWordsInFileIO(FILE_PATH);
    }

    private int countWordsInFileIO(String fileName) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            int words = 0;
            for (String line; (line = br.readLine()) != null; ) {
                words += countWordsEst(line);
            }
            return words;
        }
    }

    @Benchmark
    public int countLinesNIO() throws IOException {

        return countLinesFileNIO(FILE_PATH);
    }

    private int countLinesFileNIO(String fileName) throws IOException {
        final List<String> strings = Files.readAllLines(Paths.get(fileName));
        int words = 0;
        for (String s : strings) {
            words += countWordsEst(s);
        }
        return words;
    }

    @Benchmark
    public int countLinesNIOStream() throws IOException {

        return countLinesInFileStream(FILE_PATH);
    }

    private int countLinesInFileStream(String fileName) throws IOException {
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

            return stream.map(IOvsNIO::countWordsEst).mapToInt(i -> i).sum();

        }
    }

    private static int countWordsEst(String string) {
        return string.split(" *").length;
    }
}
