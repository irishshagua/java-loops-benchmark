package com.mooneyserver;

import org.openjdk.jmh.annotations.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.IntStream;

@State(Scope.Benchmark)
@Warmup(iterations = 5, time = 500, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(2)
@Threads(4)
public class ForLoopBenchmarks {

  private List<Integer> ints;

  @Param({"100", "10000", "1000000"})
  private int size;

  @Setup
  public void createTestData() {
    ints = new ArrayList<>(size);
    IntStream.range(1, size + 1).forEach(ints::add);
  }

  @Benchmark
  public long sumWithTraditionalForLoop() {
    long sum = 0;
    for (int i = 0; i < ints.size(); i++) {
      sum += ints.get(i);
    }

    return sum;
  }

  @Benchmark
  public long sumWithNoIndexForLoop() {
    long sum = 0;
    for (int i : ints) {
      sum += i;
    }

    return sum;
  }

  @Benchmark
  public long sumWithForEachLoop() {
    final long[] sum = {0};
    ints.forEach(l -> sum[0] += l);

    return sum[0];
  }

  @Benchmark
  public long sumWithSequentialStreamSum() {
    return ints.stream().mapToInt(Integer::intValue).sum();
  }

  @Benchmark
  public long sumWithParallelStreamSum() {
    return ints.parallelStream().mapToInt(Integer::intValue).sum();
  }

  @Benchmark
  public long sumWithLongAdder() {
    LongAdder la = new LongAdder();
    ints.parallelStream().forEach(la::add);

    return la.sum();
  }
}
