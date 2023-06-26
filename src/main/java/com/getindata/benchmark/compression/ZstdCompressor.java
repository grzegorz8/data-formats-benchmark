package com.getindata.benchmark.compression;

import com.github.luben.zstd.ZstdInputStream;
import com.github.luben.zstd.ZstdOutputStream;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class ZstdCompressor implements Compressor {

    @SneakyThrows
    public byte[] decompress(byte[] input) {
        try (ZstdInputStream stream = new ZstdInputStream(new ByteArrayInputStream(input))) {
            return IOUtils.toByteArray(stream);
        }
    }

    @SneakyThrows
    public byte[] compress(byte[] input) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream(input.length);
             ZstdOutputStream cos = new ZstdOutputStream(baos)) {
            cos.write(input);
            cos.close();
            return baos.toByteArray();
        }
    }
}
