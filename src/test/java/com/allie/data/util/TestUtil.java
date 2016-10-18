package com.allie.data.util;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by andrew.larsen on 10/17/2016.
 */
public class TestUtil {
    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
