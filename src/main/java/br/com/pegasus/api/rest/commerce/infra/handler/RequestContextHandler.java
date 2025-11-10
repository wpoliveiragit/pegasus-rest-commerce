package br.com.pegasus.api.rest.commerce.infra.handler;

import br.com.pegasus.api.rest.commerce.infra.data.MetricData;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class RequestContextHandler {

  public HttpServletRequest getCurrentRequest() throws ServletException {
    ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    if (attrs == null) {
      throw new ServletException("Nenhuma requisição HTTP ativa no contexto atual. Request sem metrica de 'track'");
    }
    return attrs.getRequest();
  }

  public HttpServletResponse getCurrentResponse() {
    ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    return (attrs == null) ? new MockHttpServletResponse() : attrs.getResponse();
  }

  public void setMetricData(MetricData metricData) throws ServletException{
    this.getCurrentRequest().setAttribute(MetricData.class.getSimpleName(), metricData);
  }

  public MetricData getMetricData() throws ServletException{
    return (MetricData) this.getCurrentRequest().getAttribute(MetricData.class.getSimpleName());
  }

}
