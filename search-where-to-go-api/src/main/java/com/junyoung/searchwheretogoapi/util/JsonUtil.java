package com.junyoung.searchwheretogoapi.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  public static <T> T convertAs(Object obj, TypeReference<T> typeReference) {
    return OBJECT_MAPPER.convertValue(obj, typeReference);
  }
}
