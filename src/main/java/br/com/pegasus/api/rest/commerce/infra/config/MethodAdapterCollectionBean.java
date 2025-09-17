package br.com.pegasus.api.rest.commerce.infra.config;

import br.com.pegasus.api.rest.commerce.domain.adapter.ExceptionMethodAdapter;
import br.com.pegasus.api.rest.commerce.domain.adapter.ValidMethodAdapter;
import br.com.pegasus.api.rest.commerce.infra.exception.BadRequestCoreException;
import br.com.pegasus.api.rest.commerce.infra.exception.ConflictCoreException;
import br.com.pegasus.api.rest.commerce.infra.exception.NotFoundCoreException;
import br.com.pegasus.api.rest.commerce.infra.util.CommomMethod;
import br.com.pegasus.api.rest.commerce.infra.util.CpfUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MethodAdapterCollectionBean {

  @Bean
  public ExceptionMethodAdapter createExceptionMethodAdapter() {
    return new ExceptionMethodAdapter() {

      @Override
      public void throwConflictDocumentNumber() {
        throw ConflictCoreException.newExistingDocumentNumber();
      }

      @Override
      public void throwConflictName() {
        throw ConflictCoreException.newExistingName();
      }

      @Override
      public NotFoundCoreException newNotFound() {
        return NotFoundCoreException.newElement();
      }

      @Override
      public NotFoundCoreException newCooperatorNotFound() {
        return NotFoundCoreException.newCooperator();
      }

    };
  }

  @Bean
  public ValidMethodAdapter createValidMethodAdapter() {
    return new ValidMethodAdapter() {

      @Override
      public boolean isNotBlank(String value) {
        return !CommomMethod.isBlank(value);
      }

      @Override
      public String getValueNotBlank(String value, String defaultValue) {
        return CommomMethod.getValueNotBlank(value, defaultValue);
      }

      @Override
      public <T extends Number> T getValueNotNegative(T value, T defaultValue) {
        return CommomMethod.getValueNotNegative(value, defaultValue);
      }

      @Override
      public void validDocumentNumber(String documentNumber) {
        if (CpfUtil.valid(documentNumber)) {
          return;
        }
        throw BadRequestCoreException.newDocumentNumber();
      }

      @Override
      public void validPrice(Number value) {
        if(CommomMethod.isNegative(value)){
          throw BadRequestCoreException.newPrice();
        }
      }

      @Override
      public void validQuantity(Number value) {
        if(CommomMethod.isNegative(value)){
          throw BadRequestCoreException.newQuantity();
        }
      }
    };
  }

}
