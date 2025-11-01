package br.com.pegasus.api.rest.commerce.infra.config.handler;

import br.com.pegasus.api.rest.commerce.infra.data.MetricData;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.web.exchanges.HttpExchangeRepository;
import org.springframework.boot.actuate.web.exchanges.InMemoryHttpExchangeRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Handler de métricas HTTP para produção:
 * - Counter: total de requisições
 * - Timer: tempo de execução
 * - Gauge: requisições ativas
 * - DistributionSummary: tamanho do request e response
 */
@Configuration
@RequiredArgsConstructor
public class MetricsHandler {

  private static final String TAG_METHOD = "method";
  private static final String TAG_URL = "url";
  private static final String TAG_STATUS = "status";

  private final MeterRegistry meterRegistry;
  private final RequestAttributeHandler requestAttributeHandler;
  private final AtomicInteger activeRequests = new AtomicInteger(0);

  @Bean
  public HttpExchangeRepository httpExchangeRepository() {
    return new InMemoryHttpExchangeRepository();
  }

  /** Inicia métricas e armazena MetricData */
  public void starts(HttpServletRequest request, HttpServletResponse response) {
    MetricData metric = requestAttributeHandler.createNewMetrics();
    metric.setStartData(System.currentTimeMillis());
    metric.setMethod(request.getMethod());
    metric.setRequestURI(request.getRequestURI());
    metric.setStatus(String.valueOf(response.getStatus()));
    metric.setRequestSize(request.getContentLengthLong());

    activeRequests.incrementAndGet();
  }

  /** Finaliza métricas e registra */
  public void send(HttpServletResponse response) {
    MetricData metric = requestAttributeHandler.getMetrics();
    metric.setRuntime(System.currentTimeMillis() - metric.getStartData());
    metric.setResponseSize(readResponseBytes(response));

    registerCounter(metric);
    registerTimer(metric);
    registerGauge();
    registerDistributionSummary(metric);

    activeRequests.decrementAndGet();
  }

  /** Contador cumulativo de requisições */
  private void registerCounter(MetricData metric) {
    Counter.builder("http_requests_total")//
        .tag(TAG_METHOD, metric.getMethod())//
        .tag(TAG_URL, metric.getRequestURI())//
        .tag(TAG_STATUS, metric.getStatus())//
        .register(meterRegistry)//
        .increment();
  }

  /** Timer para medir duração da request */
  private void registerTimer(MetricData metric) {
    Timer.builder("http_request_duration_ms")//
        .tag(TAG_METHOD, metric.getMethod())//
        .tag(TAG_URL, metric.getRequestURI())//
        .tag(TAG_STATUS, metric.getStatus())//
        .register(meterRegistry)//
        .record(metric.getRuntime(), TimeUnit.MILLISECONDS);
  }

  /** Gauge de requisições ativas */
  private void registerGauge() {
    Gauge.builder("http_active_requests", activeRequests, AtomicInteger::get)//
        .description("Número de requisições HTTP em andamento")//
        .register(meterRegistry);
  }

  /** Distribuição do tamanho do request e response */
  private void registerDistributionSummary(MetricData metric) {
    DistributionSummary.builder("http_response_size_bytes")//
        .tag(TAG_METHOD, metric.getMethod())//
        .tag(TAG_URL, metric.getRequestURI())//
        .tag(TAG_STATUS, metric.getStatus())//
        .register(meterRegistry)//
        .record(metric.getResponseSize() != null ? metric.getResponseSize() : 0);

    DistributionSummary.builder("http_request_size_bytes")//
        .tag(TAG_METHOD, metric.getMethod())//
        .tag(TAG_URL, metric.getRequestURI())//
        .tag(TAG_STATUS, metric.getStatus())//
        .register(meterRegistry)//
        .record(metric.getRequestSize() != null ? metric.getRequestSize() : 0);
  }

  /** Captura o tamanho real do response */
  private Long readResponseBytes(HttpServletResponse response) {
    if (response instanceof ResponseSizeWrapper wrapper) {
      try {
        wrapper.flushToResponse();
      } catch (IOException ignored) {
      }
      return wrapper.getContentSize();
    }
    return 0L;
  }

  /** Wrapper para medir resposta */
  public static class ResponseSizeWrapper extends HttpServletResponseWrapper {
    private final ByteArrayOutputStream buffer;
    private final ServletOutputStream outputStream;
    private PrintWriter writer;

    public ResponseSizeWrapper(HttpServletResponse response) {
      super(response);
      buffer = new ByteArrayOutputStream();
      this.outputStream = createServletOutputStream(buffer);
    }

    @Override
    public ServletOutputStream getOutputStream() {
      return outputStream;
    }

    @Override
    public PrintWriter getWriter() {
      if (writer == null) {
        writer = new PrintWriter(outputStream, true, StandardCharsets.UTF_8);
      }
      return writer;
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
