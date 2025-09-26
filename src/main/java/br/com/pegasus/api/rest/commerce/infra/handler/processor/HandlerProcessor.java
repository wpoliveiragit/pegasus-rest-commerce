package br.com.pegasus.api.rest.commerce.infra.handler.processor;

import br.com.pegasus.api.rest.commerce.infra.handler.annot.LogAnnot;
import br.com.pegasus.api.rest.commerce.infra.handler.log.CorelLog;
import br.com.pegasus.api.rest.commerce.infra.handler.log.HandlerLog;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

//@Component
public class HandlerProcessor implements BeanPostProcessor {

  @Override
  public Object postProcessAfterInitialization(Object bean, @NotNull String beanName) throws BeansException {
    ToolProcessor tool = log(bean);
    return (tool == null) ? bean : createProxy(bean, tool);
  }

  private static Object createProxy(Object bean, ToolProcessor tool) {
    return Proxy.newProxyInstance(//
        bean.getClass().getClassLoader(), //
        bean.getClass().getInterfaces(), //
        new InvocationHandlerProcessor(bean, tool));
  }

  private static ToolProcessor log(Object bean) {
    Map<Method, CorelLog> logMap = new HashMap<>(16);
    Class<?> clazz = bean.getClass();
    for (Method method : clazz.getMethods()) {
      if (method.isAnnotationPresent(LogAnnot.class)) {
        logMap.put(method, new CorelLog(clazz, method));
      }
    }
    if (logMap.isEmpty()) {
      return null;
    }
    HandlerLog log = HandlerLog.builder().clazz(clazz).logMap(logMap).build();
    return ToolProcessor.builder().log(log).build();
  }

}
