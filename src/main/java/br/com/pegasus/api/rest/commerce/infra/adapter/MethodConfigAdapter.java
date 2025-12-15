package br.com.pegasus.api.rest.commerce.infra.adapter;

import br.com.pegasus.api.rest.commerce.domain.adapter.MethodAdapter;
import br.com.pegasus.api.rest.commerce.infra.exception.AppException;
import br.com.pegasus.api.rest.commerce.infra.util.ConstUtil;
import br.com.pegasus.api.rest.commerce.infra.util.DateTimeUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
public class MethodConfigAdapter implements MethodAdapter {

  @Override
  public void throwConflictNameException() {
    throw new AppException(HttpStatus.CONFLICT, ConstUtil.EXCEPTION_CONFLICT_NAME_MESSAGE);
  }

  @Override
  public AppException newNotFoundException() {
    return new AppException(HttpStatus.NOT_FOUND, ConstUtil.EXCEPTION_NOT_FOUND_MESSAGE);
  }

  @Override
  public OffsetDateTime getOffsetDateTimeNow() {
    return DateTimeUtil.getOffsetDateTimeNow();
  }
}