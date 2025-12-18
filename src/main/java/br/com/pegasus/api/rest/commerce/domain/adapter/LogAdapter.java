package br.com.pegasus.api.rest.commerce.domain.adapter;

public interface LogAdapter {
  void startTrack(Class<?> clazz, String nameMethod);
  void endTrack(Class<?> clazz, String nameMethod);
}