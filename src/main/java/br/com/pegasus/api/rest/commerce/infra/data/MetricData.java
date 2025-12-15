package br.com.pegasus.api.rest.commerce.infra.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MetricData {
  private MetricRequestData request;
  private MetricResponseData response;
  private StringBuilder messageBuild = new StringBuilder();
}
