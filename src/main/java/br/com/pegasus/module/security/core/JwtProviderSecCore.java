package br.com.pegasus.module.security.core;

import br.com.pegasus.module.security.JwtTokenSecurity;
import br.com.pegasus.module.security.props.SecurityProps;
import br.com.pegasus.module.security.util.MethodSecurityUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
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
    int expiresAt = props.getExpiresAt();
    boolean enabledLog = props.isEnabledLog();

    String publicKey = props.getRsa().getPublicKey();
    String privateKey = props.getRsa().getPrivateKey();
    JwtEncoder encoderGenerator = MethodSecurityUtil.createJwtEncoder(publicKey, privateKey);

    JwtTokenSecurity jwtTokenSecurity = subject -> {
      Instant now = Instant.now();
      JwtClaimsSet claims = JwtClaimsSet.builder()//
          .subject(subject)// Quem solicitou o token
          .id(UUID.randomUUID().toString()) // rastreio e blacklist de token
          .issuedAt(now)// Quando foi criado
          .issuer(name)//
          .audience(List.of(audience))//
          .expiresAt(now.plusSeconds(expiresAt))//
          .notBefore(now.plusSeconds(validAfterSeconds))//
          .claim(claimKey, claimValue)//
          .build();
      String tokenValue = encoderGenerator.encode(JwtEncoderParameters.from(claims)).getTokenValue();

      MethodSecurityUtil.logInfo(log, enabledLog, "New token created with ID: {}", claims.getId());
      return tokenValue;
    };

    MethodSecurityUtil.logInfo(log, enabledLog, "Create token Generator ({})", JwtTokenSecurity.class.getSimpleName());
    return jwtTokenSecurity;
  }

  public JwtDecoder createDecoder(SecurityProps props) { // valida
    MethodSecurityUtil methodS = new MethodSecurityUtil();

    OAuth2Error oA2Err = methodS.createOAuth2Error();
    NimbusJwtDecoder decoder = methodS.createNimbusJwtDecoder(props.getRsa().getPublicKey());
    String audience = props.getAudience();
    boolean enabledLog = props.isEnabledLog();

    decoder.setJwtValidator(new DelegatingOAuth2TokenValidator<>(//
            JwtValidators.createDefault(),//
            jwt -> jwt.getAudience().contains(audience) //
                ? OAuth2TokenValidatorResult.success() //
                : OAuth2TokenValidatorResult.failure(oA2Err)//
        )//
    );
    MethodSecurityUtil.logInfo(log, enabledLog, "Create Decoder ({})", JwtDecoder.class.getSimpleName());
    return decoder;
  }

}