package br.com.pegasus.api.rest.commerce.infra.handler.telemetria;

import br.com.pegasus.api.rest.commerce.infra.data.MetricData;
import br.com.pegasus.api.rest.commerce.infra.handler.RequestContextHandler;
import br.com.pegasus.api.rest.commerce.infra.handler.log.TrackLogHandler;
import br.com.pegasus.api.rest.commerce.infra.util.ConstUtil;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.actuate.web.exchanges.HttpExchangeRepository;
import org.springframework.boot.actuate.web.exchanges.InMemoryHttpExchangeRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

/**
 * Handler de métricas HTTP:
 * - Counter: total de requisições
 * - Timer: tempo de execução
 * - Gauge: requisições em andamento
 * - DistributionSummary: tamanhos de request/response
 */
@Log4j2
@Configuration
public class MetricsTelemetry {

  private final RegisterMetricsTelemetry registerMetricsTelemetry;
  private final LongAdder activeRequests;
  private final RequestContextHandler requestContextHandler;
  private final TrackLogHandler trackLogHandler;

  public MetricsTelemetry(MeterRegistry meterRegistry, RequestContextHandler requestContextHandler, TrackLogHandler trackLogHandler) {
    this.requestContextHandler = requestContextHandler;
    this.trackLogHandler = trackLogHandler;
    this.registerMetricsTelemetry = new RegisterMetricsTelemetry(meterRegistry);
    this.activeRequests = new LongAdder();
  }

  @Bean
  public HttpExchangeRepository createHttpExchangeRepository() {
    return new InMemoryHttpExchangeRepository();
  }

  @PostConstruct
  public void init(){
    registerMetricsTelemetry.registerGauge(activeRequests);
  }

  /** Inicia métricas e salva dados iniciais */
  public void starts() {
    try {
      activeRequests.increment();
      HttpServletRequest request = requestContextHandler.getCurrentRequest();
      MetricData metric = new MetricData();
      metric.setXTraceId(request.getHeader(ConstUtil.HEADER_X_TRACE_ID));
      metric.setStartRequest(System.currentTimeMillis());
      metric.setMethod(request.getMethod());
      metric.setRequestSize(request.getContentLengthLong());
      metric.setRequestURI(request.getRequestURI());
      requestContextHandler.setMetricData(metric);
    } catch (ServletException ex) {
      log.error(ex.getMessage());
    }
  }

  /** Finaliza métricas e registra */
  public void send() {
    try {
      MetricData metric = requestContextHandler.getMetricData();
      String method = metric.getMethod();

      metric.setStatus(String.valueOf(requestContextHandler.getCurrentResponse().getStatus()));
      metric.setRuntime(System.currentTimeMillis() - metric.getStartRequest());
      metric.setResponseSize(readResponseBytes());
      metric.setKeyDistributionSummary(method + metric.getRequestURI() + metric.getStatus());

      List<Tag> tags = List.of(//
          Tag.of(ConstUtil.METRIC_TAG_METHOD, method),//
          Tag.of(ConstUtil.METRIC_TAG_URL, metric.getRequestURI()),//
          Tag.of(ConstUtil.METRIC_TAG_STATUS, metric.getStatus())//
      );

      registerMetricsTelemetry.registerCounter(tags);
      registerMetricsTelemetry.registerTimer(tags, metric);
      registerMetricsTelemetry.registerDistributionSummary(metric, tags);
      trackLogHandler.log();

    } catch (ServletException ex) {
      log.error(ex.getMessage());
    }finally {
      activeRequests.decrement();
    }
  }

  /** Obtém o tamanho real da resposta */
  private Long readResponseBytes() {
    HttpServletResponse response = requestContextHandler.getCurrentResponse();
    if (response instanceof ResponseSizeWrapperTelemetry wrapper) {
      try {
        wrapper.flushToResponse();
      } catch (Exception ex) {
        return wrapper.getContentSize();
      }
      return wrapper.getContentSize();
    }
    return ConstUtil.LONG_0;
  }

}
