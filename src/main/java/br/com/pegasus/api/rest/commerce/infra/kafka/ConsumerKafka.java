package br.com.pegasus.api.rest.commerce.infra.kafka;

import br.com.pegasus.api.rest.commerce.infra.kafka.event.ProductRegisterEvent;
import br.com.pegasus.api.rest.commerce.infra.util.MethodUtil;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ConsumerKafka {

  @KafkaListener(topics = "PRODUCT-LIFECYCLE-TOPIC", groupId = "PEGASUS-GROUP")
  public void receivesProductLifeCycleTopic(ProductRegisterEvent event) {
    System.out.println("Recebido: " + MethodUtil.toJson(event));
  }

}