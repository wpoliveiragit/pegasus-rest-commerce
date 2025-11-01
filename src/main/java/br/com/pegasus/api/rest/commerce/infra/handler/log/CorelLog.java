package br.com.pegasus.api.rest.commerce.infra.handler.log;

import java.lang.reflect.Method;
import java.util.logging.Logger;

public class CorelLog {

  private final MethodLog starts;
  private final MethodLog ends;
  private final MethodLog exceptionn;

  public CorelLog() { // CONSTRUTOR SEM LOG
    MethodLog methodLogNull = () -> {
    };
    starts = methodLogNull;
    ends = methodLogNull;
    exceptionn = methodLogNull;
  }

  public CorelLog(final Class<?> clazz, final Method method) { // CONSTRUTOR COM LOG
    String baseMessage = clazz.getSimpleName() + "." + method.getName();

    Logger log = Logger.getLogger(clazz.getSimpleName());
    starts = createMethodLog(log, " ⇉ " +baseMessage);
    ends = createMethodLog(log," ⇇ " + baseMessage);
    exceptionn = createMethodLog(log," ⮒ " + baseMessage);
  }

  public void startt() {
    starts.invoke();
  }

  public void endd() {
    ends.invoke();
  }

  public void exceptionn() {
    exceptionn.invoke();
  }

  private static MethodLog createMethodLog(final Logger log, final String msg) {
    return () -> log.info(msg);
  }

}
