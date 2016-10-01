package com.github._64q.codeandplay2016.util;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github._64q.codeandplay2016.exception.DeserialisationException;

public class JsonUtils {
  private static final ObjectMapper MAPPER = new ObjectMapper();
  
  public static <T> T fromJson(String content, Class<T> clazz) {
    try {
      return MAPPER.readValue(content, clazz);
    } catch (IOException e) {
      throw new DeserialisationException("Erreur de déserialisation", e);
    }
  }

}
