package br.com.pegasus.api.rest.commerce.infra.config.handler;

import br.com.pegasus.api.rest.commerce.infra.log.AppBaseLog;
import br.com.pegasus.api.rest.commerce.infra.log.AppFactoryLog;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class RequestContextHandler implements Filter {

  private static final AppBaseLog log = AppFactoryLog.getCommonLog(RequestContextHandler.class);

  private final RequestAttributeHandler requestContextHandler;
  private final MetricsHandler metricHandler;

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)//
      throws IOException, ServletException {

    HttpServletRequest request = (HttpServletRequest) servletRequest;
    HttpServletResponse response = (HttpServletResponse) servletResponse;

    try {
      preparesRequest(request, response);
      ClassTest classTest = new ClassTest(requestContextHandler.getRequestId(), request);
      classTest.methodStarts();

      filterChain.doFilter(servletRequest, servletResponse);

      classTest.methodEnds();
    } finally {
      endss(response);
      metricHandler.send(response);
    }
  }

  private void preparesRequest(HttpServletRequest request, HttpServletResponse response) {
    String traceId = requestContextHandler.createNewTraceId();
    log.info("CURRENT REQUEST SIZE: {}: {}B", traceId, request.getContentLengthLong());
    metricHandler.starts(request, response);
  }

  private void endss(HttpServletResponse response) {
    metricHandler.send(response);
  }

  static class ClassTest {
    final long start;
    final HttpServletRequest request;
    final String traceId;

    ClassTest(String traceId, HttpServletRequest request) {
      this.traceId = traceId;
      this.request = request;
      this.start = System.currentTimeMillis();
    }

    void methodStarts() {
      log.info("[traceId={}] Início {} {}", traceId, request.getMethod(), request.getRequestURI());
    }

    void methodEnds() {
      long duration = System.currentTimeMillis() - start;
      log.info("[traceId={}] Fim {} {} ({} ms)", traceId, request.getMethod(), request.getRequestURI(), duration);
    }

  }

}