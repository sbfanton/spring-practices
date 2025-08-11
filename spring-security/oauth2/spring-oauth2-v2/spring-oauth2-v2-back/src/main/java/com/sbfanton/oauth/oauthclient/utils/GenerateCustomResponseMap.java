package com.sbfanton.oauth.oauthclient.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenerateCustomResponseMap {

    public static Map<String, Object> generateMap(List<String> fields, Map<String, Object> originalMap) {
        Map<String, Object> map = new HashMap<>();
        for(String field: fields) {
            map.put(field, originalMap.get(field));
        }
        return map;
    }
}
