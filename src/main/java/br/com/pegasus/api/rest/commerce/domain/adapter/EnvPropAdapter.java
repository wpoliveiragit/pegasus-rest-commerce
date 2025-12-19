package br.com.pegasus.api.rest.commerce.domain.adapter;

public interface EnvPropAdapter {

  String getEnvProp(String key, String valueDefault);

  String getSpringBootVersion();
  int getJavaVersion();
}
