package com.sbfanton.oauth.oauthclient.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class GenericMapper {

    public static Map<String, Object>  convertToMap(Object obj) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(obj, Map.class);
    }
}
