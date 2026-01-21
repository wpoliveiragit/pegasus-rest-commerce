package br.com.pegasus.module.security.core;

import br.com.pegasus.module.security.JwtTokenSecurity;
import br.com.pegasus.module.security.props.SecurityProps;
import br.com.pegasus.module.security.util.MethodSecurityUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Log4j2
public class JwtProviderSecCore {

  public JwtTokenSecurity createTokenGenerator(SecurityProps props) {
    String name = props.getProjName();
    String audience = props.getAudience();
    String claimKey = props.getClaim().getName();
    String claimValue = props.getClaim().getValue();
    int validAfterSeconds = props.getValidAfterSeconds();
    Integer expiresAt = props.getExpiresAt();
    boolean enabledLog = props.isEnableLog();

    String publicKey = props.getRsa().getPublicKey();
    String privateKey = props.getRsa().getPrivateKey();
    JwtEncoder encoderGenerator = MethodSecurityUtil.createJwtEncoder(publicKey, privateKey);

    JwtClaimsSet.Builder builder = JwtClaimsSet.builder()//
        .issuer(name)//
        .audience(List.of(audience))//
        .claim(claimKey, claimValue);

    JwtTokenSecurity jwtTokenSecurity = subject -> {
      Instant now = Instant.now();

      if (expiresAt != null) {
        builder.expiresAt(now.plusSeconds(expiresAt));
      }
      JwtClaimsSet claims = builder//
          .subject(subject)//
          .id(UUID.randomUUID().toString()) //
          .issuedAt(now)//
          .notBefore(now.plusSeconds(validAfterSeconds))//
          .build();

      String tokenValue = encoderGenerator.encode(JwtEncoderParameters.from(claims)).getTokenValue();
      MethodSecurityUtil.logInfo(log, enabledLog, "New token created with ID: {}", claims.getId());
      return tokenValue;
    };

    MethodSecurityUtil.logInfo(log, enabledLog, "JWT Token Generator initialized using RSA keys and audience '{}'", audience);
    return jwtTokenSecurity;
  }

  public JwtDecoder createDecoder(SecurityProps props) { // valida
    OAuth2Error oA2Err = MethodSecurityUtil.createOAuth2Error();
    NimbusJwtDecoder decoder = MethodSecurityUtil.createNimbusJwtDecoder(props.getRsa().getPublicKey());
    String audience = props.getAudience();
    boolean enabledLog = props.isEnableLog();

    decoder.setJwtValidator(new DelegatingOAuth2TokenValidator<>(//
            JwtValidators.createDefault(),//
            jwt -> validToken(jwt, audience) //
                ? OAuth2TokenValidatorResult.success() //
                : OAuth2TokenValidatorResult.failure(oA2Err)//
        )//
    );
    MethodSecurityUtil.logInfo(log, enabledLog, "JWT Decoder initialized with audience validation: '{}'", audience);
    return decoder;
  }

  private boolean validToken(Jwt jwt, String audience) {
    // REGRAS JÁ APLICADAS POR DEFAULT
    // - Expiração (exp): Se now > exp → token inválido
    // - Not Before (nbf): Se now < nbf → token ainda não é válido
    // - Issued At (iat): Valida se não está muito no futuro (proteção contra relógio errado)
    // - Formato básico do JWT: Estrutura de assinatura RSA válida com sua public key
    // - Algoritmo compatível: Garante que o token foi assinado com algoritmo esperado

    // VALIDACOES EXTRAS
    return jwt.getAudience().contains(audience);
  }

}