package br.com.pegasus.api.rest.commerce.infra.adapter;

import br.com.pegasus.api.rest.commerce.domain.adapter.KafkaAdapter;
import br.com.pegasus.api.rest.commerce.domain.adapter.LogAdapter;
import br.com.pegasus.api.rest.commerce.domain.adapter.MethodAdapter;
import br.com.pegasus.api.rest.commerce.domain.adapter.ToolAdapter;
import br.com.pegasus.api.rest.commerce.domain.adapter.jpa.ProductAdaterJPA;
import br.com.pegasus.api.rest.commerce.infra.telemetry.HandlerTelemetry;
import br.com.pegasus.api.rest.commerce.infra.util.ConstUtil;
import br.com.pegasus.api.rest.commerce.infra.util.TrackUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ToolConfigAdapter implements ToolAdapter {

  private final MethodAdapter validMethod;
  private final ProductAdaterJPA productJpa;
  private final HandlerTelemetry handlerTelemetry;
  private final KafkaAdapter kafka;

  @Override
  public ProductAdaterJPA getProductRepository() {
    return productJpa;
  }

  @Override
  public MethodAdapter getMethod() {
    return validMethod;
  }

  @Override
  public KafkaAdapter getKafka() {
    return kafka;
  }

  @Override
  public LogAdapter getLog(Class<?> clazz) {
    return new LogAdapter() {

      @Override
      public void startTrack(Class<?> clazz, String nameMethod) {
        handlerTelemetry.addTraceEvent(ConstUtil.REGEX_TRACE, TrackUtil.START,clazz.getSimpleName(), nameMethod);
      }

      @Override
      public void endTrack(Class<?> clazz, String nameMethod) {
        handlerTelemetry.addTraceEvent(ConstUtil.REGEX_TRACE, TrackUtil.FINISH,clazz.getSimpleName(), nameMethod);
      }

    };
  }

}