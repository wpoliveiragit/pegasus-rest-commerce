package br.com.pegasus.api.rest.commerce.infra.handler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

public record InvocationHandlerImpl(Object bean, Class<?> clazz,
                                    Map<Method, LogAnnotHandler> logMap) implements InvocationHandler {

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    LogAnnotHandler mtd = logMap.get(clazz.getMethod(method.getName(), method.getParameterTypes()));
    if (mtd != null) mtd.started();
    Object result = method.invoke(bean, args);
    if (mtd != null) mtd.finished();
    return result;
  }
}
