package com.getindata.benchmark.avro;

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

import static com.getindata.benchmark.avro.AvroRecords.avroRecordsAsBytes;

@Fork(value = 1, warmups = 1)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class AvroBenchmark {

    private static final AvroDeserializer AVRO_CONVERTER = new AvroDeserializer();

    @State(Scope.Benchmark)
    public static class BenchmarkState {
        public List<byte[]> inputRecords;

        @Setup
        public void doSetup() {
            inputRecords = avroRecordsAsBytes();
        }
    }

    @Benchmark
    public void readAvroObjects(AvroBenchmark.BenchmarkState state, Blackhole blackhole) {
        blackhole.consume(AVRO_CONVERTER.convert(state.inputRecords));
    }

}
