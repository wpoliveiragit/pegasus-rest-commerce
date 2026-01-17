package br.com.pegasus.module.security;

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
@ComponentScan(basePackages = {"br.com.pegasus.module.security"})
@Import({BeanSecurity.class,})
public @interface ImportOAuthSecurity {
}
