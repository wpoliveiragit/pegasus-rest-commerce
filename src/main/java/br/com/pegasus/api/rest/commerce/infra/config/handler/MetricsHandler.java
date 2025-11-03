package br.com.pegasus.api.rest.commerce.infra.config.handler;

import br.com.pegasus.api.rest.commerce.infra.data.MetricData;
import io.micrometer.core.instrument.*;
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
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Handler de métricas HTTP:
 * - Counter: total de requisições
 * - Timer: tempo de execução
 * - Gauge: requisições em andamento
 * - DistributionSummary: tamanhos de request/response
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
  private final Map<String, DistributionSummary> responseSizeCache = new ConcurrentHashMap<>();
  private final Map<String, DistributionSummary> requestSizeCache = new ConcurrentHashMap<>();

  @Bean
  public HttpExchangeRepository httpExchangeRepository() {
    return new InMemoryHttpExchangeRepository();
  }

  /** Inicia métricas e salva dados iniciais */
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

  /** Contador de requisições */
  private void registerCounter(MetricData metric) {
    Counter.builder("http_requests_total")
        .tag(TAG_METHOD, metric.getMethod())
        .tag(TAG_URL, metric.getRequestURI())
        .tag(TAG_STATUS, metric.getStatus())
        .register(meterRegistry)
        .increment();
  }

  /** Tempo da requisição */
  private void registerTimer(MetricData metric) {
    Timer.builder("http_request_duration_ms")
        .tag(TAG_METHOD, metric.getMethod())
        .tag(TAG_URL, metric.getRequestURI())
        .tag(TAG_STATUS, metric.getStatus())
        .register(meterRegistry)
        .record(metric.getRuntime(), TimeUnit.MILLISECONDS);
  }

  /** Quantidade de requisições em andamento */
  private void registerGauge() {
    Gauge.builder("http_active_requests", activeRequests, AtomicInteger::get)
        .description("Número de requisições HTTP em andamento")
        .register(meterRegistry);
  }

  /** Request/Response size — com cache */
  private void registerDistributionSummary(MetricData metric) {
    String key = metric.getMethod() + metric.getRequestURI() + metric.getStatus();

    responseSizeCache
        .computeIfAbsent(key, k -> createSummary("http_response_size_bytes", metric))
        .record(metric.getResponseSize() != null ? metric.getResponseSize() : 0);

    requestSizeCache
        .computeIfAbsent(key, k -> createSummary("http_request_size_bytes", metric))
        .record(metric.getRequestSize() != null ? metric.getRequestSize() : 0);
  }

  /** Cria um DistributionSummary com tags padrão */
  private DistributionSummary createSummary(String name, MetricData metric) {
    return DistributionSummary.builder(name)
        .tag(TAG_METHOD, metric.getMethod())
        .tag(TAG_URL, metric.getRequestURI())
        .tag(TAG_STATUS, metric.getStatus())
        .register(meterRegistry);
  }

  /** Obtém o tamanho real da resposta */
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

  /** Wrapper de resposta para medir tamanho */
  private static class ResponseSizeWrapper extends HttpServletResponseWrapper {
    private final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    private final ServletOutputStream outputStream = createServletOutputStream(buffer);
    private PrintWriter writer;

    public ResponseSizeWrapper(HttpServletResponse response) {
      super(response);
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
