package br.com.pegasus.api.rest.commerce.infra.util;

import org.springframework.core.io.ClassPathResource;

import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public final class MethodUtil {

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

  public static final class Date {

    /**
     * Retorna a data e hora atual no fuso UTC.
     *
     * @return A data e hora atual no fuso UTC.
     */
    public static OffsetDateTime getOffsetDateTimeNow() {
      return OffsetDateTime.now(ZoneOffset.UTC);
    }

  }

}
