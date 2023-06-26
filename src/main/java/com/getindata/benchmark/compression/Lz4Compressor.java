package com.getindata.benchmark.compression;

import lombok.SneakyThrows;
import net.jpountz.lz4.LZ4FrameInputStream;
import net.jpountz.lz4.LZ4FrameOutputStream;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class Lz4Compressor implements Compressor {

    @SneakyThrows
    public byte[] decompress(byte[] input) {
        try (LZ4FrameInputStream stream = new LZ4FrameInputStream(new ByteArrayInputStream(input))) {
            return IOUtils.toByteArray(stream);
        }
    }

    @SneakyThrows
    public byte[] compress(byte[] input) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream(input.length);
             LZ4FrameOutputStream os = new LZ4FrameOutputStream(baos)) {
            os.write(input);
            os.close();
            return baos.toByteArray();
        }
    }
}
