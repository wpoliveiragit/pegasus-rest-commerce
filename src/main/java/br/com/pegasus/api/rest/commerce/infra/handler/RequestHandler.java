package br.com.pegasus.api.rest.commerce.infra.handler;

import br.com.pegasus.api.rest.commerce.infra.handler.telemetria.MetricsTelemetry;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class RequestHandler extends HttpFilter {// controle de request, antes e depois da request (após o advice)

  private final MetricsTelemetry metricsTelemetry;

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    try {
      metricsTelemetry.starts();
      filterChain.doFilter(servletRequest, servletResponse);
    } finally {
      metricsTelemetry.send();
    }
  }

}