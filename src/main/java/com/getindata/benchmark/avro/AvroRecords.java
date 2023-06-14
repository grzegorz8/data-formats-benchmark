package com.getindata.benchmark.avro;

import com.getindata.schemas.avro.TestRecord;
import lombok.experimental.UtilityClass;
import tech.allegro.schema.json2avro.converter.JsonAvroConverter;

import java.util.Arrays;
import java.util.List;

import static com.getindata.benchmark.json.JsonRecords.jsonRecords;
import static java.util.stream.Collectors.toList;

@UtilityClass
public class AvroRecords {
    private static final JsonAvroConverter JSON_AVRO_CONVERTER = new JsonAvroConverter();

    public static List<byte[]> avroRecordsAsBytes()  {
        return Arrays.stream(jsonRecords())
                .map(it-> JSON_AVRO_CONVERTER.convertToAvro(it.getBytes(), TestRecord.SCHEMA$))
                .collect(toList());
    }

}
