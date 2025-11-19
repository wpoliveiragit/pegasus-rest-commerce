package br.com.pegasus.api.rest.commerce.infra.telemetry;

import br.com.pegasus.api.rest.commerce.infra.data.MetricData;
import br.com.pegasus.api.rest.commerce.infra.data.MetricRequestData;
import br.com.pegasus.api.rest.commerce.infra.data.MetricResponseData;
import br.com.pegasus.api.rest.commerce.infra.handler.RequestContextHandler;
import br.com.pegasus.api.rest.commerce.infra.telemetry.logger.TrackLogger;
import br.com.pegasus.api.rest.commerce.infra.util.ConstUtil;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.actuate.web.exchanges.HttpExchangeRepository;
import org.springframework.boot.actuate.web.exchanges.InMemoryHttpExchangeRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
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

  private final RegisterMetricsTelemetry registerMetrics;
  private final RequestContextHandler requestContext;
  private final LongAdder activeRequests;
  private final TrackLogger trackLog;

  public MetricsTelemetry(MeterRegistry meterRegistry, RequestContextHandler requestContext, TrackLogger trackLog) {
    this.registerMetrics = new RegisterMetricsTelemetry(meterRegistry);
    this.activeRequests = new LongAdder();
    this.requestContext = requestContext;
    this.trackLog = trackLog;
    this.registerMetrics.registerGauge(activeRequests);
  }

  @Bean
  public HttpExchangeRepository createHttpExchangeRepository() {
    return new InMemoryHttpExchangeRepository();
  }

  public void addTraceMessage(String message) {
    trackLog.append(message);
  }

  public void addTraceMessage(String message, Object... args) {
    trackLog.append(message, args);
  }

  /** Inicia métricas e salva dados iniciais */
  public void starts() {
    try {
      activeRequests.increment();
      HttpServletRequest request = requestContext.getCurrentRequest();

      MetricRequestData metricRequest = new MetricRequestData();
      metricRequest.setStart(System.currentTimeMillis());
      metricRequest.setRequestSize(request.getContentLengthLong());
      metricRequest.setXTraceId(request.getHeader(ConstUtil.HEADER_X_TRACE_ID));
      metricRequest.setMethod(request.getMethod());
      metricRequest.setUrl(request.getRequestURI());

      MetricData metric = new MetricData();
      metric.setRequest(metricRequest);
      requestContext.setMetricData(metric);
    } catch (ServletException ex) {
      log.error(ex.getMessage());
    }
  }

  /** Finaliza métricas e registra */
  public void send(int status) {
    try {
      ends(status);
      MetricData metric = requestContext.getMetricData();
      MetricRequestData request = metric.getRequest();

      List<Tag> tags = List.of(//
          Tag.of(ConstUtil.METRIC_TAG_METHOD, request.getMethod()),//
          Tag.of(ConstUtil.METRIC_TAG_URL, request.getUrl()),//
          Tag.of(ConstUtil.METRIC_TAG_STATUS, metric.getResponse().getStatus())//
      );

      registerMetrics.registerCounter(tags);
      registerMetrics.registerTimer(tags, metric);
      registerMetrics.registerDistributionSummary(tags, metric);
      trackLog.log(metric);
    } catch (Exception ex) {
      log.error(ex.getMessage());
    } finally {
      activeRequests.decrement();
    }
  }

  public void advice(Throwable ex) {
    trackLog.append(" ✕ ", ex.getCause().getMessage());
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

  private void ends(int status) throws Exception {
    HttpServletResponse httpResp = requestContext.getCurrentResponse();
    MetricData metric = requestContext.getMetricData();
    MetricRequestData request = metric.getRequest();

    String method = request.getMethod();

    MetricResponseData response = new MetricResponseData();
    response.setKeyDistributionSummary(method + request.getUrl() + httpResp.getStatus());
    response.setStatus(status + "");
    response.setRuntime(System.currentTimeMillis() - request.getStart());
    response.setResponseSize(readResponseBytes());

    metric.setResponse(response);
  }

  private static class ResponseSizeWrapperTelemetry extends HttpServletResponseWrapper {

    private final ServletOutputStream outputStream;
    private final ByteArrayOutputStream buffer;
    private PrintWriter writer;

    public ResponseSizeWrapperTelemetry(HttpServletResponse response) {
      super(response);
      this.buffer = new ByteArrayOutputStream();
      this.outputStream = createServletOutputStream(buffer);
    }

    @Override
    public ServletOutputStream getOutputStream() {
      return outputStream;
    }

    @Override
    public PrintWriter getWriter() {
      return (writer == null) //
          ? (writer = new PrintWriter(outputStream, true, StandardCharsets.UTF_8)) : writer;
    }

    public long getContentSize() {
      return buffer.size();
    }

    public void flushToResponse() throws IOException {
      super.getOutputStream().write(buffer.toByteArray());
      super.getOutputStream().flush();
    }

    private static ServletOutputStream createServletOutputStream(ByteArrayOutputStream buffer) {
      return new ServletOutputStream() {
        @Override
        public void write(int b) {
          buffer.write(b);
        }

        @Override
        public boolean isReady() {
          return true;
        }

        @Override
        public void setWriteListener(jakarta.servlet.WriteListener listener) {
        }
      };
    }
  }

}
