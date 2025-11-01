package br.com.pegasus.api.rest.commerce.infra.config.handler;

import br.com.pegasus.api.rest.commerce.infra.data.MetricData;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.UUID;

@Component
public class RequestAttributeHandler {

  private static final String KEY_TRACE_ID = "001-TRACE-ID";
  private static final String KEY_METRICS = "002-METRICS";

  public String createNewTraceId() {
    String traceId = UUID.randomUUID().toString();
    this.put(KEY_TRACE_ID, traceId);
    return traceId;
  }

  public MetricData createNewMetrics() {
    MetricData metricData = new MetricData();
    this.put(KEY_METRICS, metricData);
    return metricData;
  }

  public String getRequestId() {
    return this.get(KEY_TRACE_ID, String.class);
  }

  public MetricData getMetrics() {
    return this.get(KEY_METRICS, MetricData.class);
  }

  private void put(String key, Object value) {
    getRequestAttributes().setAttribute(key, value, RequestAttributes.SCOPE_REQUEST);
  }

  private <T> T get(String key, Class<T> type) {
    Object value = getRequestAttributes().getAttribute(key, RequestAttributes.SCOPE_REQUEST);
    return type.cast(value);
  }

  private RequestAttributes getRequestAttributes() {
    return RequestContextHolder.currentRequestAttributes();
  }

}



