package br.com.pegasus.api.rest.commerce.domain.core;

import br.com.pegasus.api.rest.commerce.domain.adapter.AuthAdapter;
import br.com.pegasus.api.rest.commerce.domain.port.AuthPort;

public class AuthRestCore implements AuthPort {

  private final AuthAdapter auth;

  public AuthRestCore(AuthAdapter auth) {
    this.auth = auth;
  }

  @Override
  public String createToken(String username) {
    return auth.createToken(username);
  }

}