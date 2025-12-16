package br.com.pegasus.api.rest.commerce.infra.data;

import br.com.pegasus.api.rest.commerce.infra.util.MethodUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@RequiredArgsConstructor
@Getter
public class TraceEventLogData {

  private final int order;
  private final String timestamp;
  private final String message;

  @Override
  public String toString(){
    return MethodUtil.toJson(this);
  }
}
