package br.com.pegasus.api.rest.commerce.infra.adapter;

import br.com.pegasus.api.rest.commerce.domain.adapter.KafkaAdapter;
import br.com.pegasus.api.rest.commerce.infra.kafka.ProducerKafka;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaConfigAdapter implements KafkaAdapter {

  private final ProducerKafka producerKafka;

  public void sendProductLifecycleEvents(String action, long productId) {
    producerKafka.sendProductLifecycleEvents(action, productId);
  }

}