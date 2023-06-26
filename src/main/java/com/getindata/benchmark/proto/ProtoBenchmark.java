package com.getindata.benchmark.proto;

import com.getindata.benchmark.compression.Compressor;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.getindata.benchmark.proto.ProtoRecords.protoRecordsAsBytes;
import static java.util.stream.Collectors.toList;

@Fork(value = 1, warmups = 1)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Warmup(iterations = 1)
@Measurement(iterations = 1)
public class ProtoBenchmark {

    private static final ProtoDeserializer PROTO_CONVERTER = new ProtoDeserializer();
    @Param({"none", "gzip", "lz4", "snappy", "zstd"})
    private String compression;
    private List<byte[]> inputRecords;
    private Compressor compressor;

    @Setup
    public void setup() {
        compressor = Compressor.byName(compression);
        inputRecords = protoRecordsAsBytes().stream().map(compressor::compress).collect(toList());
    }

    @Benchmark
    public void readProtoObjects(Blackhole blackhole) {
        blackhole.consume(
                inputRecords.stream()
                        .map(compressor::decompress)
                        .map(PROTO_CONVERTER::convert)
                        .collect(toList())
        );
    }

}
