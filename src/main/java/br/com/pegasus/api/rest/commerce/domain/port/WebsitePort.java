package br.com.pegasus.api.rest.commerce.domain.port;

import java.util.Map;

public interface WebsitePort {
  Map<String, ?> getProp(int page);
  Map<String, String> geth2ConsoleInfo(int page);
}