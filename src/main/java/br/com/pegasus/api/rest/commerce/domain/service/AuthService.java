package br.com.pegasus.api.rest.commerce.domain.service;

import br.com.pegasus.api.rest.commerce.domain.adapter.AuthAdapter;
import br.com.pegasus.api.rest.commerce.domain.core.AuthRestCore;
import br.com.pegasus.api.rest.commerce.domain.port.AuthPort;
import br.com.pegasus.api.rest.commerce.infra.handler.marker.ComponentLayerMarker;
import org.springframework.stereotype.Service;

@Service
@ComponentLayerMarker("Service.Auth")
public class AuthService implements AuthPort {

  private final AuthRestCore authCore;

  public AuthService(AuthAdapter auth) {
    this.authCore = new AuthRestCore(auth);
  }

  @Override
  public String createToken(String username) {
    return authCore.createToken(username);
  }

}