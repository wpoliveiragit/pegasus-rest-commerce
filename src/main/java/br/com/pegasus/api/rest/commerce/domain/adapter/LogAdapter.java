package br.com.pegasus.api.rest.commerce.domain.adapter;

public interface LogAdapter {
  void startedTrack(Class<?> clazz, String nameMethod);
  void endedTrack(Class<?> clazz, String nameMethod);
}