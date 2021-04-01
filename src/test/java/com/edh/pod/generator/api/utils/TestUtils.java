package com.edh.pod.generator.api.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TestUtils {
    public String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
