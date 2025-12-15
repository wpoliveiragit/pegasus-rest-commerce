package br.com.pegasus.api.rest.commerce.infra.data;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MetricRequestData {

  private final HttpServletRequest request;
  private final long requestStartTime;
  private Long requestSize;
  private String xTraceId;
  private String method;
  private String url;

  public MetricRequestData(HttpServletRequest request, long requestStartTime) {
    this.request = request;
    this.requestStartTime = requestStartTime;
  }

}