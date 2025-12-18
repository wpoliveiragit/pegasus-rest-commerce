package br.com.pegasus.api.rest.commerce.infra.cache;

import br.com.pegasus.api.rest.commerce.infra.telemetry.HandlerTelemetry;
import br.com.pegasus.api.rest.commerce.infra.util.ConstUtil;
import com.github.benmanes.caffeine.cache.Cache;
import org.jetbrains.annotations.NotNull;
import org.springframework.cache.caffeine.CaffeineCache;

public class TraceCaffeineInventoryCache extends CaffeineCache {

  private final HandlerTelemetry handlerTelemetry;

  public TraceCaffeineInventoryCache(String name, Cache<Object, Object> cache, HandlerTelemetry handlerTelemetry) {
    super(name, cache);
    this.handlerTelemetry = handlerTelemetry;
  }

  @Override
  public ValueWrapper get(@NotNull Object key) {
    ValueWrapper value = super.get(key);
    if (value == null) return null;
    handlerTelemetry.addTraceEvent(ConstUtil.REGEX_TRACE_CACHE, key);
    return value;
  }

}
