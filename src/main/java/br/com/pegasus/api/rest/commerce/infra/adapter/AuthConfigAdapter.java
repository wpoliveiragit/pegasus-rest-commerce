package br.com.pegasus.api.rest.commerce.infra.adapter;

import br.com.pegasus.api.rest.commerce.domain.adapter.AuthAdapter;
import br.com.pegasus.api.rest.commerce.infra.handler.marker.ComponentLayerMarker;
import br.com.pegasus.api.rest.commerce.infra.security.MethodJwtSecurity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@ComponentLayerMarker("Infra.Auth")
@Component
@RequiredArgsConstructor
public class AuthConfigAdapter implements AuthAdapter {

  private final MethodJwtSecurity jwtSecurity;

  @Override
  public String createToken(String username) {
    return jwtSecurity.createToken(username);
  }

}