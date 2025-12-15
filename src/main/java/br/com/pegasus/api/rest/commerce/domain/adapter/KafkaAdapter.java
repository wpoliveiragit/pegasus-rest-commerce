package br.com.pegasus.api.rest.commerce.domain.adapter;

public interface KafkaAdapter {
  void sendProductLifecycleEvents(String action, long productId);
}