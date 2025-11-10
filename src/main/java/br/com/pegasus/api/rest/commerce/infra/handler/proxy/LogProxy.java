package br.com.pegasus.api.rest.commerce.infra.handler.proxy;

import br.com.pegasus.api.rest.commerce.infra.handler.annotation.LogProxyAnnotation;
import br.com.pegasus.api.rest.commerce.infra.handler.log.TrackLogHandler;

public class LogProxy {

  private final TrackLogHandler trackLogHandler;

  private final String text;

  public LogProxy(Object bean, TrackLogHandler trackLogHandler) {
    this.trackLogHandler = trackLogHandler;
    LogProxyAnnotation annot = bean.getClass().getAnnotation(LogProxyAnnotation.class);

    this.text = annot.value();
  }

  public void logIn(String methodName) {
    trackLogHandler.appendMessageLog(" ● " + text + "." + methodName);
  }

  public void logOut(String methodName) {
    trackLogHandler.appendMessageLog(" ◎ " + text + "." + methodName);
  }

  public void logFail(String methodName, Throwable exception) {
    trackLogHandler.appendMessageLog(" ✕ " + text + "." + methodName + ": " + exception.getMessage());
  }

}
