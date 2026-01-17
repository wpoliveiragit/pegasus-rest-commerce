package br.com.pegasus.api.rest.commerce.app;

import br.com.pegasus.module.security.ImportOAuthSecurity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@ImportOAuthSecurity
public class StartApplication {

  public static void main(String[] args) {
    SpringApplication.run(StartApplication.class, args);
  }

}