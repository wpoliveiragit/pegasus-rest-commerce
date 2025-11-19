package br.com.pegasus.api.rest.commerce.infra.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MetricRequestData {
  private long start;
  private Long requestSize;
  private String xTraceId;
  private String method;
  private String url;
}
