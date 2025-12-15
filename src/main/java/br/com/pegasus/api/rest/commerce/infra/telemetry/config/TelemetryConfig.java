package br.com.pegasus.api.rest.commerce.infra.telemetry.config;

import org.springframework.boot.actuate.web.exchanges.HttpExchangeRepository;
import org.springframework.boot.actuate.web.exchanges.InMemoryHttpExchangeRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TelemetryConfig {

  @Bean
  public HttpExchangeRepository createHttpExchangeRepository() {
    return new InMemoryHttpExchangeRepository();
  }

}