package com.blueyonder.test.sumit.restkafkademo.service.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

class ConverterTest {

    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private final Converter converter = new Converter(OBJECT_MAPPER);

    @Test
    void shouldConvertObject() {
        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("city", "kolkata");
        String stringValue = converter.convert(dataMap);

        assertThat(stringValue, is("{\"city\":\"kolkata\"}"));
    }

    @Test
    void shouldReturnNullWhenInputIsNull() {
        assertThat(converter.convert(null), is(nullValue()));
    }

    @Test
    void shouldReturnStringWhenInputTypeIsString() {
        assertThat(converter.convert("foo"), is("foo"));
    }
}