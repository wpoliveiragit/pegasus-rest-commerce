package br.com.pegasus.api.rest.commerce.infra.kafka;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.DefaultErrorHandler;

@Configuration
public class ConfigKafka {

  @Bean
  public CommonErrorHandler errorHandler() {
    return new DefaultErrorHandler((record, ex) -> {
      System.out.println("Erro ao processar: " + record.value());
    });
  }

}