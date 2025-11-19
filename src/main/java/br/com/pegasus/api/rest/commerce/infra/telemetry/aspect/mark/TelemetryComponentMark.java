package br.com.pegasus.api.rest.commerce.infra.telemetry.aspect.mark;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TelemetryComponentMark {
  String value() default "";
}
