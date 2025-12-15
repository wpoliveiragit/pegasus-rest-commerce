package br.com.pegasus.api.rest.commerce.infra.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;

import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public final class MethodUtil {

  private static final ObjectMapper objectMapper = new ObjectMapper();

  /**
   * Converte  o objeto para json.
   *
   * @param obj o objeto a ser convertido
   * @return o json do objeto
   */
  public static String toJson(Object obj) {
    try {
      return objectMapper.writeValueAsString(obj);
    } catch (Exception e) {
      return "{}";
    }
  }

  /**
   * Lê um arquivo do classpath e retorna seu conteúdo como {@link String} em UTF-8.
   *
   * @param filePath caminho relativo em {@code resources}, ex: {@code "site/index.html"}.
   * @return conteúdo do arquivo em texto.
   * @throws RuntimeException se ocorrer erro na leitura.
   */
  public static String readResourceFileToStringUTF8(String filePath) {
    try {
      return new String(new ClassPathResource(filePath).getInputStream().readAllBytes(), StandardCharsets.UTF_8);
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }

  public static String addColorGreenText(String text) {
    return ConstUtil.COLOR_GREEN + text + ConstUtil.COLOR_RESET;
  }

}