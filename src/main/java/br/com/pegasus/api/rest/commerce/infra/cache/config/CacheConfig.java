package br.com.pegasus.api.rest.commerce.infra.cache.config;

import br.com.pegasus.api.rest.commerce.infra.telemetry.HandlerTelemetry;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Configurador de comportamento do cache com Caffeine */
@Configuration
@EnableCaching
public class CacheConfig {

  @Bean
  public CacheManager cacheManager(HandlerTelemetry handlerTelemetry) {
    return new CaffeineCacheManagerConfig(handlerTelemetry);
  }

}