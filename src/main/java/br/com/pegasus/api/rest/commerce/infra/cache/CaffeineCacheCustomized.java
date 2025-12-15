package br.com.pegasus.api.rest.commerce.infra.cache;

import br.com.pegasus.api.rest.commerce.infra.telemetry.logger.TrackLogger;
import br.com.pegasus.api.rest.commerce.infra.util.ConstUtil;
import com.github.benmanes.caffeine.cache.Cache;
import org.jetbrains.annotations.NotNull;
import org.springframework.cache.caffeine.CaffeineCache;

public class CaffeineCacheCustomized extends CaffeineCache {

  private final TrackLogger trackLog;

  public CaffeineCacheCustomized(String name, Cache<Object, Object> cache, TrackLogger trackLog) {
    super(name, cache);
    this.trackLog = trackLog;
  }

  @Override
  public ValueWrapper get(@NotNull Object key) {
    ValueWrapper value = super.get(key);
    if (value == null) return null;
    trackLog.append(ConstUtil.REGEX_TRACE_CACHE, key);
    return value;
  }
}
