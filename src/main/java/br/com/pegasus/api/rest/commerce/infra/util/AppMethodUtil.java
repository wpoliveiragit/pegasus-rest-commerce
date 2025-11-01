package br.com.pegasus.api.rest.commerce.infra.util;

import br.com.pegasus.api.rest.commerce.infra.enums.AppEnumException;
import br.com.pegasus.gen.openapi.type.ProductCreateBodyType;
import br.com.pegasus.gen.openapi.type.ProductUpdateBodyType;
import org.springframework.core.io.ClassPathResource;

import java.nio.charset.StandardCharsets;

public final class AppMethodUtil {

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

  public static void page(Integer page, Integer size) {
    ValidatedUtil.positive(page, AppEnumException.BAD_REQUEST_PAGE);
    ValidatedUtil.positiveOrZero(size, AppEnumException.BAD_REQUEST_SIZE);
  }

  public static void commonId(Integer id) {
    validId(id, AppEnumException.BAD_REQUEST_ID);
  }

  public static void createBody(ProductCreateBodyType body) {
    notNullBody(body);
    validName(body.getName());
    validPrice(body.getPrice());
    validQuantity(body.getQuantity());
  }

  public static void updateBody(Integer id, ProductUpdateBodyType body) {
    commonId(id);
    notNullBody(body);
  }

  private static void validName(String value) {
    ValidatedUtil.notBlank(value, AppEnumException.BAD_REQUEST_NAME);
  }

  private static void validPrice(Number value) {
    ValidatedUtil.positiveOrZero(value, AppEnumException.BAD_REQUEST_PRICE);
  }

  private static void validQuantity(Number value) {
    ValidatedUtil.positiveOrZero(value, AppEnumException.BAD_REQUEST_QUANTITY);
  }

  public static void validId(Integer id, AppEnumException appEnumException) {
    ValidatedUtil.positiveOrZero(id, appEnumException);
  }

  public static void notNullBody(Object body) {
    ValidatedUtil.notNull(body, AppEnumException.BAD_REQUEST_BODY);
  }

}
