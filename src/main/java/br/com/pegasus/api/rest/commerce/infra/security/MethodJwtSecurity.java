package br.com.pegasus.api.rest.commerce.infra.security;

import br.com.pegasus.api.rest.commerce.infra.util.ConstUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class MethodJwtSecurity {

  private final JwtEncoder encoder;

  public String createToken(String username) {
    Instant now = Instant.now();
    JwtClaimsSet claims = JwtClaimsSet.builder()//
        .subject(username)//
        .issuedAt(now)//
        .expiresAt(now.plusSeconds(ConstUtil.INT_60))//
        .claim(ConstUtil.SECURITY_CLAIM_SCOPE, ConstUtil.SECURITY_CLAIM_READ_WRITE)//
        .build();
    return encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
  }
}