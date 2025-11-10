package br.com.pegasus.api.rest.commerce.infra.handler.proxy;

import br.com.pegasus.api.rest.commerce.infra.exception.AppException;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@RequiredArgsConstructor
public class BeanProxy implements InvocationHandler {

  private final Object bean; // o component
  private final LogProxy log;

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    String nomeMetodo = method.getName();
    try {
      if(nomeMetodo.equalsIgnoreCase("toString")){
        return method.invoke(bean, args);
      }
      log.logIn(nomeMetodo);
      Object ret = method.invoke(bean, args);
      log.logOut(nomeMetodo);
      return ret;
    }catch (Exception ex) {
      log.logFail(nomeMetodo, ex.getCause());
      throw ex.getCause();
    }
  }
}