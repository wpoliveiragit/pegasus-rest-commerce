package br.com.pegasus.api.rest.commerce.app;

import br.com.pegasus.module.security.EnabledOAuthWebSecurity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnabledOAuthWebSecurity
public class StartApplication {

  public static void main(String[] args) {
    SpringApplication.run(StartApplication.class, args);
  }

}