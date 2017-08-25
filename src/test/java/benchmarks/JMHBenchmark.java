package benchmarks;

import org.junit.Test;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * Actually starts JMH benchmark
 */
public class JMHBenchmark {

    @Test
    public void startBenchmark() throws RunnerException{

        // Specify benchmark classes to run
        final Options opt = new OptionsBuilder()
                .include("nio.IOvsNIO.*")
                .include("streams.StreamsComputing.*")
                .build();
        new Runner(opt).run();
    }
}