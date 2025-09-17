package br.com.pegasus.api.rest.commerce.infra.handler;

import br.com.pegasus.api.rest.commerce.infra.handler.annot.LogAnnot;

import java.lang.reflect.Method;
import java.util.logging.Logger;

public class LogAnnotHandler {

  private final String logStarted;
  private final String logFinished;
  private final Logger log;

  public LogAnnotHandler(final Class<?> clazz, final Method method) {
    LogAnnot annot = method.getAnnotation(LogAnnot.class);
    this.logStarted = clazz.getSimpleName() + " ⇉ " + method.getName() + " ⇉ " + annot.started();
    this.logFinished = clazz.getSimpleName() + " ⇉ " + method.getName() + " ⇉ " + annot.finished();
    this.log = Logger.getLogger(clazz.getSimpleName());
  }

  public void started() {
    log.info(logStarted);
  }

  public void finished() {
    log.info(logFinished);
  }
}
