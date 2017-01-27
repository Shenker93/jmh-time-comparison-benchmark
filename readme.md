**INSTALLATION**

Just use _mvn clean install_ command.
You will get _benchmarks.jar_ file in _target_ directory as a result.
Just use _java -jar target/benchmarks.jar_ command to start benchmarking.


**DATA**

To compare some loop- and stream-based approaches lists (actually ArrayList) of different length and data (random and all-the-same elements) are used.

To compare IO and NIO approaches to file reading _resources/test.txt_ file is used. Benchmark is running in single thread mode now.

**BENCHMARKING**

By default, 10 warm-up and measurement iterations are used in benchmarks. 
However, feel free to change this value to our own. 
_@Warmup_ and _@Measurement_ annotations are used in StreamComputing class definition to define number of iterations.
Also you can change BenchmarkMode 

You can also use command-line args to change this settings or specify another (use _-h_ to view full list).
Comparing to annotations, command-line args have higher priority.