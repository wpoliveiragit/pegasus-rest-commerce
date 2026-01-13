package br.com.pegasus.api.rest.commerce.domain.adapter;

import java.util.List;
import java.util.Map;

public interface EnvPropAdapter {
  Object getPropertyMap(String key);
  List<String> getPropertyList(String key);
  Map<String, String> getH2ConsoleInfo(int page);
}