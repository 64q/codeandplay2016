package com.github._64q.codeandplay2016.util;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github._64q.codeandplay2016.exception.DeserialisationException;

/**
 * Utilitaires JSON
 * 
 * @author qlebourgeois &lt;contact@qlebourgeois.me&gt;
 */
public class JsonUtils {
  /**
   * Object Mapper Jackson
   */
  private static final ObjectMapper MAPPER = new ObjectMapper();

  /**
   * Conversion d'une string en un objet Java via Jackson
   * 
   * @param content flux à convertir
   * @param clazz type de retour
   * @return une instance de clazz
   * @throws DeserialisationException en cas d'erreur à la déserialisation
   */
  public static <T> T fromJson(String content, Class<T> clazz) throws DeserialisationException {
    try {
      return MAPPER.readValue(content, clazz);
    } catch (IOException e) {
      throw new DeserialisationException("Erreur de déserialisation", e);
    }
  }

}
