package com.getindata.benchmark.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.getindata.benchmark.Deserializer;
import com.getindata.schemas.pojo.TestRecord;
import lombok.SneakyThrows;

public class JsonDeserializer implements Deserializer<TestRecord> {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @SneakyThrows
    @Override
    public TestRecord convert(byte[] value) {
        return OBJECT_MAPPER.readValue(new String(value), TestRecord.class);
    }


}
