package br.com.pegasus.module.security;

import br.com.pegasus.module.security.config.BeanSecConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({BeanSecConfig.class})
public @interface ImportOAuthWebSecurity {
  // Porque o nome é import e não Enable no nome? Porque pode se obtar em habilitar/desabilitar atraves das propriedades
}
