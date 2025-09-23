package br.com.pegasus.api.rest.commerce.infra.handler.log;

import br.com.pegasus.api.rest.commerce.infra.handler.annot.LogAnnot;
import br.com.pegasus.api.rest.commerce.infra.util.StreamUtil;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.logging.Logger;

public class CorelLog {

  private final MethodLog startt;
  private final MethodLog endd;
  private final MethodLog exceptionn;
  private final MethodLog finallyy;

  public CorelLog() {
    startt =createMethodLog(null,null);
    endd = createMethodLog(null,null);
    exceptionn = createMethodLog(null,null);
    finallyy = createMethodLog(null,null);
  }

  public CorelLog(final Class<?> clazz, final Method method) {
    final LogAnnot annot = method.getAnnotation(LogAnnot.class);

    Map<String, String> MsgMap = StreamUtil.of(annot.value().split(" "))//
        .map(keyValue -> {
          String[] kv = keyValue.split(":");
          return Map.entry(kv[0], kv[1]);
        })//
        .toMap(Map.Entry::getKey, Map.Entry::getValue);

    final Logger log = Logger.getLogger(clazz.getSimpleName());
    final String baseMsg = clazz.getSimpleName() + " ⇉ " + method.getName() + " ⇉ ";
    final String startMsg = MsgMap.get("ST");
    final String endMsg = MsgMap.get("EN");
    final String exeptionMsg = MsgMap.get("EX");
    final String finallyMsg = MsgMap.get("FI");

    startt = createMethodLog(log, baseMsg + startMsg);
    endd = createMethodLog(log, baseMsg + endMsg);
    exceptionn = createMethodLog(log, baseMsg + exeptionMsg);
    finallyy = createMethodLog(log, baseMsg + finallyMsg);
  }

  public void startt() {
    startt.invoke();
  }

  public void endd() {
    endd.invoke();
  }

  public void exceptionn() {
    exceptionn.invoke();
  }

  public void finallyy() {
    finallyy.invoke();
  }

  private static MethodLog createMethodLog(final Logger log, final String msg) {
    return (msg == null) //
        ? () -> {}
        : () -> log.info(msg);
  }

}
