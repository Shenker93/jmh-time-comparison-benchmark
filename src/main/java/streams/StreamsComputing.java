package streams;

import org.openjdk.jmh.annotations.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * Contains some methods that are using "old school" loops and new java 8 approach based on streams
 */
@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(1)                            //
@Warmup(iterations = 10)            //time can be defined here too
@Measurement(iterations = 10)       //time can be defined here too
public class StreamsComputing {

    private List<Integer> smallSimilarDataSet;
    private List<Integer> midSimilarDataSet;
    private List<Integer> largeSimilarDataSet;
    private List<Integer> xxlSimilarDataSet;

    private List<Integer> smallRandomDataSet;
    private List<Integer> midRandomDataSet;
    private List<Integer> largeRandomDataSet;
    private List<Integer> xxlRandomDataSet;

    @Benchmark
    public int countSumInLoopSmallSimilarDataSet() {
        return countSumInLoop(smallSimilarDataSet);
    }

    @Benchmark
    public int countSumInStreamSmallSimilarDataSet() {
        return countSumInStream(smallSimilarDataSet);
    }

    @Benchmark
    public int countSumInLoopMidSimilarDataSet() {
        return countSumInLoop(midSimilarDataSet);
    }

    @Benchmark
    public int countSumInStreamMidSimilarDataSet() {
        return countSumInStream(midSimilarDataSet);
    }

    @Benchmark
    public int countSumInLoopLargeSimilarDataSet() {
        return countSumInLoop(largeSimilarDataSet);
    }

    @Benchmark
    public int countSumInStreamLargeSimilarDataSet() {
        return countSumInStream(largeSimilarDataSet);
    }

    @Benchmark
    public int countSumInLoopXXLSimilarDataSet() {
        return countSumInLoop(xxlSimilarDataSet);
    }

    @Benchmark
    public int countSumInStreamXXLSimilarDataSet() {
        return countSumInStream(xxlSimilarDataSet);
    }

    @Benchmark
    public int countSumInLoopSmallRandomDataSet() {
        return countSumInLoop(smallRandomDataSet);
    }

    @Benchmark
    public int countSumInStreamSmallRandomDataSet() {
        return countSumInStream(smallRandomDataSet);
    }

    @Benchmark
    public int countSumInLoopMidRandomDataSet() {
        return countSumInLoop(midRandomDataSet);
    }

    @Benchmark
    public int countSumInStreamMidRandomDataSet() {
        return countSumInStream(midRandomDataSet);
    }

    @Benchmark
    public int countSumInLoopLargeRandomDataSet() {
        return countSumInLoop(largeRandomDataSet);
    }

    @Benchmark
    public int countSumInStreamLargeRandomDataSet() {
        return countSumInStream(largeRandomDataSet);
    }

    @Benchmark
    public int countSumInLoopXXLRandomDataSet() {
        return countSumInLoop(xxlRandomDataSet);
    }

    @Benchmark
    public int countSumInStreamXXLRandomDataSet() {
        return countSumInStream(xxlRandomDataSet);
    }

    @Benchmark
    public List<Integer> filterInLoopSmallDataSet() {
        return filterListInLoop(smallRandomDataSet, x -> x % 2 == 0);
    }

    @Benchmark
    public List<Integer> filterInStreamSmallDataSet() {
        return filterListInStream(smallRandomDataSet, x -> x % 2 == 0);
    }

    @Benchmark
    public List<Integer> filterInLoopLargeDataSet() {
        return filterListInLoop(largeRandomDataSet, x -> x % 2 == 0);
    }

    @Benchmark
    public List<Integer> filterInStreamLargeDataSet() {
        return filterListInStream(largeRandomDataSet, x -> x % 2 == 0);
    }

    @Benchmark
    public List<Integer> filterInLoopXXLDataSet() {
        return filterListInLoop(xxlRandomDataSet, x -> x % 2 == 0);
    }

    @Benchmark
    public List<Integer> filterInStreamXXLDataSet() {
        return filterListInStream(xxlRandomDataSet, x -> x % 2 == 0);
    }

    /**
     * Preparing data to run JMH benchmarking on
     */
    @Setup
    public void init() {
        smallSimilarDataSet = buildDataSet(() -> 1, 10);
        smallRandomDataSet = buildDataSet(new Random()::nextInt, 10);

        midSimilarDataSet = buildDataSet(() -> 1, 100);
        midRandomDataSet = buildDataSet(new Random()::nextInt, 100);

        largeSimilarDataSet = buildDataSet(() -> 1, 1000);
        largeRandomDataSet = buildDataSet(new Random()::nextInt, 1000);

        xxlSimilarDataSet = buildDataSet(() -> 1, 1_000_000);
        xxlRandomDataSet = buildDataSet(new Random()::nextInt, 1_000_000);
    }

    private int countSumInLoop(List<Integer> data) {
        int sum = 0;
        for (Integer i : data) {
            sum += i;
        }
        return sum;
    }

    private int countSumInStream(List<Integer> data) {
        return data.stream().mapToInt(i -> i).sum();
    }

    private List<Integer> filterListInLoop(List<Integer> data, Predicate<Integer> filter) {
        List<Integer> res = new ArrayList<>(data.size());
        for (Integer integer : data) {
            if (filter.test(integer)) {
                res.add(integer);
            }
        }
        return res;
    }

    private List<Integer> filterListInStream(List<Integer> data, Predicate<Integer> filter) {
        return data.stream().filter(filter).collect(toList());
    }

    private List<Integer> buildDataSet(Supplier<Integer> dataElemSupplier, long size) {
        return Stream.generate(dataElemSupplier).limit(size).collect(toList());
    }


}
