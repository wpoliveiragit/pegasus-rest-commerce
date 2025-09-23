package br.com.pegasus.api.rest.commerce.infra.handler.annot;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogAnnot {

  /**
   * Use o padrão String de map abaixo para definir os textos do log de cada ponto, caso não queira algum tipo algum
   * log, basta apenas exluir a etapa;
   *
   * ST:started EN:ending EX:exception FI:finally
   */
  String value() default "start:started end:ending exception:exception finally:finally";

}
