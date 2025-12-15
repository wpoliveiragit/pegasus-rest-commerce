package br.com.pegasus.api.rest.commerce.infra.cache;

import br.com.pegasus.api.rest.commerce.infra.telemetry.logger.TrackLogger;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Configurador de comportamento do cache com Caffeine */
@Configuration
@EnableCaching
public class CacheConfig {

  @Bean
  public CacheManager cacheManager(TrackLogger trackLog) {
    return new CaffeineCacheManagerCustomized(trackLog);
  }

}