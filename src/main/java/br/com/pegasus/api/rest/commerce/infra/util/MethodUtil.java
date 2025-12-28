package br.com.pegasus.api.rest.commerce.infra.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Log4j2
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
      log.error(e.getMessage());
      return "{" + e.getMessage() + "}";
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

  public static List<String> envGetPropertyList(Environment env, String key){
    return Binder.get(env).bind(key, Bindable.listOf(String.class)).orElse(List.of());
  }

}