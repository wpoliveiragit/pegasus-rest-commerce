package br.com.pegasus.api.rest.commerce.infra.kafka.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class ProductRegisterEvent {
  private long productId;
  private String action;
  private String timestamp;
}