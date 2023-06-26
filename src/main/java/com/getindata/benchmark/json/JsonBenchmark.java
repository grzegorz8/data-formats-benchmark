package com.getindata.benchmark.json;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.getindata.benchmark.json.JsonRecords.jsonRecordsAsBytes;

@Fork(value = 1, warmups = 1)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class JsonBenchmark {

    private static final JsonDeserializer JSON_CONVERTER = new JsonDeserializer();

    @State(Scope.Benchmark)
    public static class BenchmarkState {
        public List<byte[]> inputRecords;

        @Setup
        public void doSetup() {
            inputRecords = jsonRecordsAsBytes();
        }
    }

    @Benchmark
    public void readJsonObjects(JsonBenchmark.BenchmarkState state, Blackhole blackhole) {
        blackhole.consume(JSON_CONVERTER.convert(state.inputRecords));
    }

}
