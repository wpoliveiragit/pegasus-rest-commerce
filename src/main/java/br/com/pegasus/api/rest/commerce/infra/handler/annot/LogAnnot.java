package br.com.pegasus.api.rest.commerce.infra.handler.annot;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogAnnot {

  String started() default "started";

  String finished() default "finished";
}
