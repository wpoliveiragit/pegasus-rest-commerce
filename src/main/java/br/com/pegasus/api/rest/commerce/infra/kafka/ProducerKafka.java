package br.com.pegasus.api.rest.commerce.infra.kafka;

import br.com.pegasus.api.rest.commerce.infra.kafka.event.ProductRegisterEvent;
import br.com.pegasus.api.rest.commerce.infra.util.ConstUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class ProducerKafka {

  private final KafkaTemplate<String, ProductRegisterEvent> kafkaTemplate;

//  public ProducerKafka(KafkaTemplate<String, ProductRegisterEvent> kafkaTemplate) {
//    this.kafkaTemplate = kafkaTemplate;
//  }

  public void sendProductLifecycleEvents(String action, long productId) {
    ProductRegisterEvent event = new ProductRegisterEvent();
    event.setProductId(productId);
    event.setAction(action);
    event.setTimestamp(Instant.now().toString());
    kafkaTemplate.send(ConstUtil.KAFKA_PRODUCT_LIFECYCLE_TOPIC, event);
  }

}