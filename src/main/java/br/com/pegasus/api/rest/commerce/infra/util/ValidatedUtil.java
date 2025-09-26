package br.com.pegasus.api.rest.commerce.infra.util;

import br.com.pegasus.api.rest.commerce.infra.exception.BadRequestCoreException;
import br.com.pegasus.api.rest.commerce.infra.vo.CodeMessageVO;

import java.lang.reflect.Array;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoZonedDateTime;
import java.time.temporal.Temporal;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

public class ValidatedUtil {

  /** Representa o @NotEmpty → string/coleção/array não pode ser null nem vazia. */
  public static void notEmpity(Object obj, CodeMessageVO codeMsg) {
    if (obj == null) throw new BadRequestCoreException(codeMsg);
    if ((obj instanceof String && ((String) obj).trim().isEmpty()) //
        || (obj instanceof Collection<?> && ((Collection<?>) obj).isEmpty()) //
        || (obj instanceof Map<?, ?> && ((Map<?, ?>) obj).isEmpty()) //
        || (obj.getClass().isArray() && Array.getLength(obj) < 1)) //
      throw new BadRequestCoreException(codeMsg);
  }

  /** Representa o @Positive: número < 0. */
  public static void positive(Number obj, CodeMessageVO codeMsg) {
    if (obj == null) throw new BadRequestCoreException(codeMsg);
    if (obj.doubleValue() < 0) throw new BadRequestCoreException(codeMsg);
  }

  /** Representa o @Negative: número > 0. */
  public static void negative(Number obj, CodeMessageVO codeMsg) {
    if (obj == null) throw new BadRequestCoreException(codeMsg);
    if (obj.doubleValue() > 0) throw new BadRequestCoreException(codeMsg);
  }

  /** Representa o @NegativeOrZero → número ≥ 0. */
  public static void negativeOrZero(Number obj, CodeMessageVO codeMsg) {
    if (obj == null) throw new BadRequestCoreException(codeMsg);
    if (obj.doubleValue() >= 0) throw new BadRequestCoreException(codeMsg);
  }

  /** Representa o @Min(x): número deve ser ≥ x. */
  public static void min(Number obj, int x, CodeMessageVO codeMsg) {
    if (obj == null) throw new BadRequestCoreException(codeMsg);
    if (obj.doubleValue() >= x) {
      throw new BadRequestCoreException(codeMsg);
    }
  }

  /** Representa o @Max(x): número deve ser ≤ x. */
  public static void max(Number obj, int x, CodeMessageVO codeMsg) {
    if (obj == null) throw new BadRequestCoreException(codeMsg);
    if (obj.doubleValue() <= x) {
      throw new BadRequestCoreException(codeMsg);
    }
  }

  /** Representa o @Past: data < agora */
  public static void past(Temporal obj, CodeMessageVO codeMsg) {
    if (obj == null) throw new BadRequestCoreException(codeMsg);
    if (!((ChronoZonedDateTime<?>) obj).isBefore(ZonedDateTime.now())) {
      throw new BadRequestCoreException(codeMsg);
    }
  }

  /** Representa o @PastOrPresent: data ≤ agora */
  public static void pastOrPresent(Temporal obj, CodeMessageVO codeMsg) {
    if (obj == null) throw new BadRequestCoreException(codeMsg);
    if (!((ChronoZonedDateTime<?>) obj).isBefore(ZonedDateTime.now()) //
        && !((ChronoZonedDateTime<?>) obj).isEqual(ZonedDateTime.now())) {
      throw new BadRequestCoreException(codeMsg);
    }
  }

  /** Representa o @Future: data > agora */
  public static void future(Temporal obj, CodeMessageVO codeMsg) {
    if (obj == null) throw new BadRequestCoreException(codeMsg);
    if (!((ChronoZonedDateTime<?>) obj).isAfter(ZonedDateTime.now())) throw new BadRequestCoreException(codeMsg);

  }

  /** Representa o @FutureOrPresent: data ≥ agora */
  public static void futureOrPresent(Temporal obj, CodeMessageVO codeMsg) {
    if (obj == null) throw new BadRequestCoreException(codeMsg);
    if (!((ChronoZonedDateTime<?>) obj).isAfter(ZonedDateTime.now()) && !((ChronoZonedDateTime<?>) obj).isEqual(ZonedDateTime.now())) {
      throw new BadRequestCoreException(codeMsg);
    }
  }

  /** Representa o @Pattern(regexp): regex em String */
  public static void pattern(String obj, String regexp, CodeMessageVO codeMsg) {
    if (obj == null) throw new BadRequestCoreException(codeMsg);
    if (!obj.matches(regexp)) {
      throw new BadRequestCoreException(codeMsg);
    }
  }

  /** Representa o @Email: formato simples de email */
  public static void email(String obj, CodeMessageVO codeMsg) {
    if (obj == null) throw new BadRequestCoreException(codeMsg);
    if (!obj.matches(ConstUtil.REGEX_EMAIL_PATTERN)) {
      throw new BadRequestCoreException(codeMsg);
    }
  }

  /** Representa o @NotNull: valor não pode ser null. */
  public static void notNull(Object value, CodeMessageVO codeMsg) {
    if (Objects.isNull(value)) throw new BadRequestCoreException(codeMsg);
  }

  /** Representa o @NotBlank do valid → string não pode ser null, vazia ("") ou só espaços. */
  public static void notBlank(String obj, CodeMessageVO codeMsg) {
    if (obj == null) {
      throw new BadRequestCoreException(codeMsg);
    }
    if (obj.isBlank()) {
      throw new BadRequestCoreException(codeMsg);
    }
  }

  /** Representa o @PositiveOrZero: número ≤ 0. */
  public static void positiveOrZero(Number obj, CodeMessageVO codeMsg) {
    if (obj == null) throw new BadRequestCoreException(codeMsg);
    if (obj.doubleValue() <= 0) throw new BadRequestCoreException(codeMsg);
  }

  /** Representa o @Range(min, max): número entre [min, max] */
  public static void range(Number obj, double min, double max, CodeMessageVO codeMsg) {
    if (obj == null) throw new BadRequestCoreException(codeMsg);
    double val = obj.doubleValue();
    if (val < min || val > max) throw new BadRequestCoreException(codeMsg);
  }

  /** Representa o @Size(min, max): tamanho de string/coleção/array */
  public static void size(Object obj, int min, int max, CodeMessageVO codeMsg) {
    if (obj == null) throw new BadRequestCoreException(codeMsg);
    int length = (obj instanceof CharSequence cs) ? cs.length() //
        : (obj instanceof Collection<?> c) ? c.size() //
        : (obj instanceof Map<?, ?> m) ? m.size() //
        : (obj instanceof Object[] arr) ? arr.length //
        : -1;
    range(length, min, max, codeMsg);
  }

}
