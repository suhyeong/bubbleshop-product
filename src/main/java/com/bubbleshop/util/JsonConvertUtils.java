package com.bubbleshop.util;

import com.bubbleshop.constants.ResponseCode;
import com.bubbleshop.exception.ApiException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Set;

public class JsonConvertUtils {

    public static String convertSetToJson(Set<String> stringSet) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(stringSet);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiException(ResponseCode.SERVER_ERROR, e.getCause().getLocalizedMessage());
        }
    }

    public static Set<String> convertJsonToSet(String str) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(str, new TypeReference<>(){});
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiException(ResponseCode.SERVER_ERROR, e.getCause().getLocalizedMessage());
        }
    }
}
