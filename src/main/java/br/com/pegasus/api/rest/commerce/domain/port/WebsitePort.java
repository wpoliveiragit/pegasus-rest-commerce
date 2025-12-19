package br.com.pegasus.api.rest.commerce.domain.port;

import java.util.Map;

public interface WebsitePort {
  Map<String, ?> website(int page);
  Map<String, ?> license(int page);
  Map<String, ?> terms(int page);
}