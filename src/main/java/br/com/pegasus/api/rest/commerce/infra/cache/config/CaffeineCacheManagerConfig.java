package br.com.pegasus.api.rest.commerce.infra.cache.config;

import br.com.pegasus.api.rest.commerce.infra.cache.TraceCaffeineInventoryCache;
import br.com.pegasus.api.rest.commerce.infra.telemetry.HandlerTelemetry;
import br.com.pegasus.api.rest.commerce.infra.util.ConstUtil;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.cache.caffeine.CaffeineCacheManager;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class CaffeineCacheManagerConfig extends CaffeineCacheManager {

  private final HandlerTelemetry handlerTelemetry;

  @Override
  protected @NotNull Cache<Object, Object> createNativeCaffeineCache(@NotNull String name) {
    return Caffeine.newBuilder()//
        .maximumSize(ConstUtil.INT_1000)//
        .expireAfterWrite(ConstUtil.INT_10, TimeUnit.MINUTES)//
        .recordStats()//
        .build();
  }

  @Override
  protected @NotNull org.springframework.cache.Cache adaptCaffeineCache(@NotNull String name,//
                                                                        @NotNull Cache<Object, Object> cache) {
    return new TraceCaffeineInventoryCache(name, cache, handlerTelemetry);
  }

}