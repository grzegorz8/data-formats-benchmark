package com.getindata.benchmark;

import java.util.List;

import static java.util.stream.Collectors.toList;

public interface Deserializer<T> {
    T convert(byte[] value);

    default List<T> convert(List<byte[]> values) {
        return values.stream()
                .map(this::convert)
                .collect(toList());

    }
}
