package com.getindata.benchmark.avro;

import com.getindata.schemas.avro.TestRecord;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AvroDeserializer {

    public TestRecord convert(byte[] value) throws IOException {
        var reader = new SpecificDatumReader<>(TestRecord.class);
        var decoder = DecoderFactory.get().binaryDecoder(value, null);
        return reader.read(null, decoder);
    }


    public List<TestRecord> convert(List<byte[]> values) throws IOException {
        List<TestRecord> result = new ArrayList<>(values.size());
        for (byte[] value : values) {
            result.add(convert(value));
        }
        return result;
    }

}
