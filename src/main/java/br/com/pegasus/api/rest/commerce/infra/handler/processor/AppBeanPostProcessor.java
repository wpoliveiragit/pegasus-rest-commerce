package br.com.pegasus.api.rest.commerce.infra.handler.processor;

import br.com.pegasus.api.rest.commerce.infra.handler.AppInvocationHandler;
import br.com.pegasus.api.rest.commerce.infra.handler.LogAnnotHandler;
import br.com.pegasus.api.rest.commerce.infra.handler.annot.LogAnnot;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

@Component
public class AppBeanPostProcessor implements BeanPostProcessor {

  @Override
  public Object postProcessAfterInitialization(Object bean, @NotNull String beanName) throws BeansException {
    Map<Method, LogAnnotHandler> logAnnotMap = new HashMap<>(15);
    Class<?> clazz = bean.getClass();
    for (Method method : clazz.getMethods()) {
      if (method.isAnnotationPresent(LogAnnot.class)) {
        logAnnotMap.put(method, new LogAnnotHandler(clazz, method));
      }
    }
    return (logAnnotMap.isEmpty()) ? bean : this.createProxy(bean, logAnnotMap);
  }

  private Object createProxy(Object bean, Map<Method, LogAnnotHandler> logMap) {
    Class<?> clazz = bean.getClass();
    ClassLoader classLoader = clazz.getClassLoader();
    Class<?>[] interfaces = clazz.getInterfaces();
    AppInvocationHandler appInvocationHandler = new AppInvocationHandler(bean, clazz, logMap);
    return Proxy.newProxyInstance(classLoader, interfaces, appInvocationHandler);
  }

}
