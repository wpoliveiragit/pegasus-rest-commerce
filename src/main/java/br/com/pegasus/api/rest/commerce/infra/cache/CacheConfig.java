package br.com.pegasus.api.rest.commerce.infra.cache;

import br.com.pegasus.api.rest.commerce.infra.telemetry.logger.TrackLogger;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.concurrent.TimeUnit;

/** Configurador de comportamento do cache com Caffeine */
@Configuration
@EnableCaching
@RequiredArgsConstructor
public class CacheConfig {

  private final TrackLogger trackLog;

  @Bean
  public CacheManager cacheManager(Environment env) {
    return new CaffeineCacheManager() {

      @Override
      protected @NotNull Cache<Object, Object> createNativeCaffeineCache(@NotNull String name) {
        return Caffeine.newBuilder()//
            .maximumSize(1000)//
            .expireAfterWrite(10, TimeUnit.MINUTES)//
            .recordStats().build();
      }

      @Override
      protected @NotNull org.springframework.cache.Cache adaptCaffeineCache(@NotNull String name, @NotNull Cache<Object, Object> cache) {
        return new CaffeineCache(name, cache) {
          @Override
          public ValueWrapper get(@NotNull Object key) {
            ValueWrapper value = super.get(key);
            if (value == null) {
              return null;
            }
            trackLog.append("✪ cache(↻)");
            return value;
          }
        };
      }
    };
  }

}