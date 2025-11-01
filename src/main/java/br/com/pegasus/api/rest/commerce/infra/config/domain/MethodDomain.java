package br.com.pegasus.api.rest.commerce.infra.config.domain;

import br.com.pegasus.api.rest.commerce.domain.adapter.MethodDomainAdapter;
import br.com.pegasus.api.rest.commerce.infra.enums.AppEnumException;
import br.com.pegasus.api.rest.commerce.infra.exception.AppException;
import br.com.pegasus.api.rest.commerce.infra.util.MethodUtil;
import org.springframework.stereotype.Component;

@Component
public class MethodDomain implements MethodDomainAdapter {

  @Override
  public void throwConflictName() {
    throw AppEnumException.CONFLICT_NAME.getAppException();
  }

  @Override
  public AppException newNotFound() {
    return AppEnumException.NOT_FOUND_ELEMENT.getAppException();
  }

  @Override
  public String validNameUpdate(String value) {
    if (MethodUtil.isBlank(value)) {
      throw AppEnumException.BAD_REQUEST_NAME.getAppException();
    }
    return value;
  }

  @Override
  public Float validPriceUpdate(Float value) {
    if (MethodUtil.isNegative(value)) {
      throw AppEnumException.BAD_REQUEST_PRICE.getAppException();
    }

    return value;
  }

  @Override
  public Integer validQualityUpdate(Integer value) {
    if (MethodUtil.isNegative(value)) {
      throw AppEnumException.BAD_REQUEST_QUANTITY.getAppException();
    }
    return value;
  }

}