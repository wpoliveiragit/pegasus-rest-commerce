package br.com.pegasus.api.rest.commerce.infra.telemetry;

import br.com.pegasus.api.rest.commerce.infra.data.TraceEventLogListData;
import br.com.pegasus.api.rest.commerce.infra.handler.RequestContextHandler;
import br.com.pegasus.api.rest.commerce.infra.util.ConstUtil;
import br.com.pegasus.api.rest.commerce.infra.util.TextUtil;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Handler de métricas HTTP:
 * - Counter: total de requisições
 * - Timer: tempo de execução
 * - Gauge: requisições em andamento
 * - DistributionSummary: tamanhos de request/response
 */
@Log4j2
@Configuration
public class HandlerTelemetry {// MetricsTelemetry

  private final RegisterMetricsTelemetry registerMetrics;
  private final RequestContextHandler requestContext;
  private final LongAdder activeRequests;

  public HandlerTelemetry(MeterRegistry meterRegistry, RequestContextHandler requestContext) {
    this.registerMetrics = new RegisterMetricsTelemetry(meterRegistry);
    this.activeRequests = new LongAdder();
    this.requestContext = requestContext;
    this.registerMetrics.gaugeRegister(activeRequests);
  }

  /** Inicia métricas e salva dados iniciais */
  public void starts() {
    try {
      activeRequests.increment();
      final HttpServletRequest request = requestContext.getCurrentRequest();

      String traceId = request.getHeader(ConstUtil.REST_HEADER_X_TRACE_ID);
      String method = request.getMethod();
      String url = request.getRequestURI();
      long requestSize = request.getContentLengthLong();

      TraceEventLogListData traceEventLogList = new TraceEventLogListData(traceId, method, url, requestSize);

      requestContext.setTraceEventLogListData(traceEventLogList);
    } catch (ServletException ex) {
      log.error(ex.getMessage());
    }
  }

  public void addTraceEvent(String message) {
    try {
      requestContext.getTraceEventLogListData().addEvent(message);
    } catch (Throwable ex) {
      log.error(ex.getMessage());
    }
  }

  public void addTraceEvent(String formatMessage, Object... objs) {
    try {
      requestContext.getTraceEventLogListData().addEvent(TextUtil.format(formatMessage, objs));
    } catch (Throwable ex) {
      String s = ex.getMessage() + ". " + formatMessage + ": " + Stream.of(objs).map(o -> "'" + o.toString() + "'").collect(Collectors.joining(", "));
      log.warn(s);
    }
  }

  public void ends(int status) {
    try {
      requestContext.getTraceEventLogListData().finish(status, readResponseBytes());
    } catch (Exception ex) {
      log.error(ex.getMessage());
    }
  }

  /** Finaliza métricas e registra */
  public void send() {
    try {
      TraceEventLogListData traceEventLogListData = requestContext.getTraceEventLogListData();
      List<Tag> tags = traceEventLogListData.getTags();

      registerMetrics.counterRegister(tags);
      registerMetrics.timerRegister(tags, traceEventLogListData.getRuntime());
      registerMetrics.distributionSummaryRegister(tags, traceEventLogListData.getKeyDistributionSummary(), traceEventLogListData.getRequestSize());
      registerMetrics.tracelogRegister(traceEventLogListData);
    } catch (Exception ex) {
      log.error(ex.getMessage());
    } finally {
      activeRequests.decrement();
    }
  }

  public void onlyTraceSend() {
    try {
      TraceEventLogListData traceEventLogListData = requestContext.getTraceEventLogListData();
      registerMetrics.tracelogRegister(traceEventLogListData);
    } catch (Exception ex) {
      log.error(ex.getMessage());
    } finally {
      activeRequests.decrement();
    }
  }

  /** Obtém o tamanho real da resposta */
  private Long readResponseBytes() {
    HttpServletResponse response = requestContext.getCurrentResponse();
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