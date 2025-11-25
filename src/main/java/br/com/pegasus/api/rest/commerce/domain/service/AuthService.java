package br.com.pegasus.api.rest.commerce.domain.service;

import br.com.pegasus.api.rest.commerce.domain.adapter.AuthAdapter;
import br.com.pegasus.api.rest.commerce.domain.core.AuthCore;
import br.com.pegasus.api.rest.commerce.domain.port.AuthPort;
import br.com.pegasus.api.rest.commerce.infra.telemetry.aspect.mark.TelemetryComponentMark;
import org.springframework.stereotype.Service;

@Service
@TelemetryComponentMark("Service.Auth")
public class AuthService implements AuthPort {

  private final AuthCore authCore;

  public AuthService(AuthAdapter auth) {
    this.authCore = new AuthCore(auth);
  }

  @Override
  public String createToken(String username) {
    return authCore.createToken(username);
  }

}