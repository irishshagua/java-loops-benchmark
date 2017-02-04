package com.mooneyserver;

import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class ExecuteBenchmarks {

  public static void main(String... args) throws RunnerException {
    Options options = new OptionsBuilder()
        .include(ForLoopBenchmarks.class.getSimpleName())
        .resultFormat(ResultFormatType.CSV)
        .build();
    new Runner(options).run();
  }
}
