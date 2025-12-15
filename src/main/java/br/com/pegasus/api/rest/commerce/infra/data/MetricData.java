package br.com.pegasus.api.rest.commerce.infra.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class MetricData {

  private final MetricRequestData request;
  private MetricResponseData response;
  private StringBuilder messageBuild = new StringBuilder();

}