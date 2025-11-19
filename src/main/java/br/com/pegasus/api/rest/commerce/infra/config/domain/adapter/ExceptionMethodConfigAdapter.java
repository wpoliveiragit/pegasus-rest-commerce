package br.com.pegasus.api.rest.commerce.infra.config.domain.adapter;

import br.com.pegasus.api.rest.commerce.domain.adapter.ExceptionMethodAdapter;
import br.com.pegasus.api.rest.commerce.infra.exception.AppException;
import br.com.pegasus.api.rest.commerce.infra.util.ConstUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ExceptionMethodConfigAdapter implements ExceptionMethodAdapter {

  @Override
  public void throwConflictName() {
    throw new AppException(HttpStatus.CONFLICT, ConstUtil.EXCEPTION_CONFLICT_NAME_MESSAGE);
  }

  @Override
  public AppException newNotFound() {
    return new AppException(HttpStatus.NOT_FOUND, ConstUtil.EXCEPTION_NOT_FOUND_MESSAGE);
  }

}