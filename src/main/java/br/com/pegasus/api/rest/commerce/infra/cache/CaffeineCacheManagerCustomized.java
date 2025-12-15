package br.com.pegasus.api.rest.commerce.infra.cache;

import br.com.pegasus.api.rest.commerce.infra.telemetry.logger.TrackLogger;
import br.com.pegasus.api.rest.commerce.infra.util.ConstUtil;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.cache.caffeine.CaffeineCacheManager;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class CaffeineCacheManagerCustomized extends CaffeineCacheManager {

  private final TrackLogger trackLog;

  @Override
  protected @NotNull Cache<Object, Object> createNativeCaffeineCache(@NotNull String name) {
    return Caffeine.newBuilder()//
        .maximumSize(ConstUtil.INT_1000)//
        .expireAfterWrite(ConstUtil.INT_10, TimeUnit.MINUTES)//
        .recordStats().build();
  }

  @Override
  protected @NotNull org.springframework.cache.Cache adaptCaffeineCache(@NotNull String name, @NotNull Cache<Object, Object> cache) {
    return new CaffeineCacheCustomized(name, cache, trackLog);
  }
}