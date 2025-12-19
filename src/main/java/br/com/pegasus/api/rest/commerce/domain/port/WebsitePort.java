package br.com.pegasus.api.rest.commerce.domain.port;

import java.util.Map;

public interface WebsitePort {
  Map<String, ?> info(int page);
}