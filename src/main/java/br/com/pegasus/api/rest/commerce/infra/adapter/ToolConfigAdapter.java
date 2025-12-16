package br.com.pegasus.api.rest.commerce.infra.adapter;

import br.com.pegasus.api.rest.commerce.domain.adapter.KafkaAdapter;
import br.com.pegasus.api.rest.commerce.domain.adapter.LogAdapter;
import br.com.pegasus.api.rest.commerce.domain.adapter.MethodAdapter;
import br.com.pegasus.api.rest.commerce.domain.adapter.ToolAdapter;
import br.com.pegasus.api.rest.commerce.domain.adapter.jpa.ProductAdaterJPA;
import br.com.pegasus.api.rest.commerce.infra.telemetry.logger.TrackLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ToolConfigAdapter implements ToolAdapter {

  private final MethodAdapter validMethod;
  private final ProductAdaterJPA productJpa;
  private final TrackLogger trackLogger;
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
      public void startedTrack(Class<?> clazz, String nameMethod) {
        trackLogger.append(" [★ INICIOU]" + clazz.getSimpleName() + "#" + nameMethod);
      }

      @Override
      public void endedTrack(Class<?> clazz, String nameMethod) {
        trackLogger.append(" [☆  FINALIZOU]" + clazz.getSimpleName() + "#" + nameMethod + " [VOLTOU]");
      }
    };
  }

}