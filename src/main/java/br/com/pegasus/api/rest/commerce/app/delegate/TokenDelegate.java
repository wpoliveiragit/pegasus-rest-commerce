package br.com.pegasus.api.rest.commerce.app.delegate;

import br.com.pegasus.api.rest.commerce.app.handler.HttpMethodHandler;
import br.com.pegasus.api.rest.commerce.domain.port.AuthPort;
import br.com.pegasus.api.rest.commerce.infra.telemetry.aspect.mark.TelemetryControllerMark;
import br.com.pegasus.gen.openapi.api.TokenApiDelegate;
import br.com.pegasus.gen.openapi.type.CreateToken200Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@TelemetryControllerMark("Delegate.Token")
@Component
@RequiredArgsConstructor
public class TokenDelegate implements TokenApiDelegate {

  private final AuthPort service;
  private final HttpMethodHandler httpMethod;

  @Override
  public CompletableFuture<ResponseEntity<CreateToken200Response>> createToken(String username) {
    String token = service.createToken(username);
    CreateToken200Response response = CreateToken200Response.builder().accessToken(token).build();
    return httpMethod.created(response);
  }
}
