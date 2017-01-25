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

import scala.Function1;
import scala.Some;
import scala.collection.JavaConverters;
import scala.math.Numeric;
import scala.math.Ordering;

import static scala.math.Numeric.IntIsIntegral$;

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
    private scala.collection.immutable.List<Integer> scalaSmallSimilarList;
    private List<Integer> midSimilarDataSet;
    private scala.collection.immutable.List<Integer> scalaMidSimilarList;
    private List<Integer> largeSimilarDataSet;
    private scala.collection.immutable.List<Integer> scalaLargeSimilarList;
    private List<Integer> xxlSimilarDataSet;
    private scala.collection.immutable.List<Integer> scalaXxlSimilarList;

    private List<Integer> smallRandomDataSet;
    private scala.collection.immutable.List<Integer> scalaSmallRandomList;
    private List<Integer> midRandomDataSet;
    private scala.collection.immutable.List<Integer> scalaMidRandomList;
    private List<Integer> largeRandomDataSet;
    private scala.collection.immutable.List<Integer> scalaLargeRandomList;
    private List<Integer> xxlRandomDataSet;
    private scala.collection.immutable.List<Integer> scalaXxlRandomList;

    @Benchmark
    public int countSumInLoopSmallSimilarDataSet() {
        return countSumInLoop(smallSimilarDataSet);
    }

    @Benchmark
    public int countSumInStreamSmallSimilarDataSet() {
        return countSumInStream(smallSimilarDataSet);
    }

    @Benchmark
    public int countSumInScalaSmallSimilarList() {
        return scalaSmallSimilarList.sum(integerNumeric);
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
    public int countSumInScalaMidSimilarList() {
        return scalaMidSimilarList.sum(integerNumeric);
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
    public int countSumInScalaLargeSimilarList() {
        return scalaLargeSimilarList.sum(integerNumeric);
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
    public int countSumInScalaXXLSimilarList() {
        return scalaSmallSimilarList.sum(integerNumeric);
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
    public int countSumInScalaSmallRandomList() {
        return scalaSmallRandomList.sum(integerNumeric);
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
    public int countSumInScalaMidRandomList() {
        return scalaMidRandomList.sum(integerNumeric);
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
    public int countSumInScalaLargeRandomList() {
        return scalaLargeRandomList.sum(integerNumeric);
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
    public int countSumInScalaXXLRandomList() {
        return scalaXxlRandomList.sum(integerNumeric);
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
        scalaSmallSimilarList = JavaConverters.asScalaBuffer(smallSimilarDataSet).toList();
        smallRandomDataSet = buildDataSet(new Random()::nextInt, 10);
        scalaSmallRandomList = JavaConverters.asScalaBuffer(smallRandomDataSet).toList();

        midSimilarDataSet = buildDataSet(() -> 1, 100);
        scalaMidSimilarList = JavaConverters.asScalaBuffer(midSimilarDataSet).toList();
        midRandomDataSet = buildDataSet(new Random()::nextInt, 100);
        scalaMidRandomList = JavaConverters.asScalaBuffer(midRandomDataSet).toList();

        largeSimilarDataSet = buildDataSet(() -> 1, 1000);
        scalaLargeSimilarList = JavaConverters.asScalaBuffer(largeSimilarDataSet).toList();
        largeRandomDataSet = buildDataSet(new Random()::nextInt, 1000);
        scalaLargeRandomList = JavaConverters.asScalaBuffer(largeRandomDataSet).toList();

        xxlSimilarDataSet = buildDataSet(() -> 1, 1_000_000);
        scalaXxlSimilarList = JavaConverters.asScalaBuffer(xxlSimilarDataSet).toList();
        xxlRandomDataSet = buildDataSet(new Random()::nextInt, 1_000_000);
        scalaXxlRandomList = JavaConverters.asScalaBuffer(xxlRandomDataSet).toList();
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

    private Numeric<Integer> integerNumeric = new Numeric<Integer>() {
        @Override
        public Some<Object> tryCompare(Integer x, Integer y) {
            return IntIsIntegral$.MODULE$.tryCompare(x, y);
        }

        @Override
        public double toDouble(Integer x) {
            return 0;
        }

        @Override
        public Integer plus(Integer x, Integer y) {
            return x + y;
        }

        @Override
        public Integer one() {
            return 1;
        }

        @Override
        public Integer max(Integer x, Integer y) {
            return Integer.max(x, y);
        }

        @Override
        public boolean gt(Integer x, Integer y) {
            return x > y;
        }

        @Override
        public float toFloat(Integer x) {
            return 0;
        }

        @Override
        public int toInt(Integer x) {
            return 0;
        }

        @Override
        public boolean gteq(Integer x, Integer y) {
            return x >= y;
        }

        @Override
        public Integer negate(Integer x) {
            return -x;
        }

        @Override
        public scala.math.Numeric.Ops mkNumericOps(Integer lhs) {
            return IntIsIntegral$.MODULE$.mkNumericOps(lhs);
        }

        @Override
        public <U> Ordering<U> on(Function1<U, Integer> f) {
            throw new RuntimeException("WTF");
        }

        @Override
        public boolean lt(Integer x, Integer y) {
            return x < y;
        }

        @Override
        public Integer abs(Integer x) {
            return Math.abs(x);
        }

        @Override
        public Integer min(Integer x, Integer y) {
            return Math.min(x, y);
        }

        @Override
        public Integer fromInt(int x) {
            return x;
        }

        @Override
        public long toLong(Integer x) {
            return x;
        }

        @Override
        public boolean equiv(Integer x, Integer y) {
            return IntIsIntegral$.MODULE$.equiv(x, y);
        }

        @Override
        public Integer times(Integer x, Integer y) {
            return x * y;
        }

        @Override
        public Ordering.Ops mkOrderingOps(Integer lhs) {
            return IntIsIntegral$.MODULE$.mkOrderingOps(lhs);
        }

        @Override
        public int signum(Integer x) {
            return IntIsIntegral$.MODULE$.signum(x);
        }

        @Override
        public Integer minus(Integer x, Integer y) {
            return x - y;
        }

        @Override
        public Ordering<Integer> reverse() {
            throw new RuntimeException("WTF2");
        }

        @Override
        public boolean lteq(Integer x, Integer y) {
            return x <= y;
        }

        @Override
        public Integer zero() {
            return 0;
        }

        @Override
        public int compare(Integer x, Integer y) {
            return Integer.compare(x, y);
        }
    };
}
