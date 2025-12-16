package br.com.pegasus.api.rest.commerce.infra.handler;

import br.com.pegasus.api.rest.commerce.infra.data.TraceEventLogListData;
import br.com.pegasus.api.rest.commerce.infra.util.ConstUtil;
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
    if (attrs == null) throw new ServletException(ConstUtil.METRIC_NO_ACTIVE_REQUEST_MESSAGE);
    return attrs.getRequest();
  }

  public HttpServletResponse getCurrentResponse() {
    ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    return (attrs == null) ? new MockHttpServletResponse() : attrs.getResponse();
  }

  public void setTraceEventLogListData(TraceEventLogListData traceEventLogList) throws ServletException {
    this.getCurrentRequest().setAttribute(TraceEventLogListData.class.getSimpleName(), traceEventLogList);
  }

  public TraceEventLogListData getTraceEventLogListData() throws ServletException {
    return (TraceEventLogListData) this.getCurrentRequest().getAttribute(TraceEventLogListData.class.getSimpleName());
  }

}