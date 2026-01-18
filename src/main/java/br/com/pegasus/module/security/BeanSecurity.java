package br.com.pegasus.module.security;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

/** JWT (JSON Web Token) */
@Configuration
public class BeanSecurity {

  private final String audience;
  private final RSAPublicKey publicKey;
  private final RSAPrivateKey privateKey;

  public BeanSecurity(Environment env) throws Exception {
    String privateKey = MethodSecurity.getReqProp(env, ConstSecurity.PROP_RSA_PRIVATE_KEY);
    String publicKey = MethodSecurity.getReqProp(env, ConstSecurity.PROP_RSA_PUBLIC_KEY);

    this.audience = env.getProperty(ConstSecurity.PROP_AUDIENCE, ConstSecurity.MSG_NOT_FOUND);

    // Gera um par de chaves RSA de 2048 bits
    this.privateKey = (RSAPrivateKey) KeyFactory.getInstance(ConstSecurity.ALGORITHM)//
        .generatePrivate(new PKCS8EncodedKeySpec(MethodSecurity.getDecodedKey(privateKey)));
    this.publicKey = (RSAPublicKey) KeyFactory.getInstance(ConstSecurity.ALGORITHM)//
        .generatePublic(new X509EncodedKeySpec(MethodSecurity.getDecodedKey(publicKey)));
  }

  @Bean
  public JwtTokenSecurity createTokenGenerator(Environment env) {
    RSAKey rsaKey = new RSAKey.Builder(publicKey)//
        .privateKey(privateKey)//
        .keyID(UUID.randomUUID().toString())//
        .build();
    JwtEncoder encoderGenerator = new NimbusJwtEncoder(new ImmutableJWKSet<>(new JWKSet(rsaKey)));

    //Quem emitiu o token.
    String name = MethodSecurity.getReqProp(env, ConstSecurity.PROP_PROJECT_NAME);
    String audience = MethodSecurity.getReqProp(env, ConstSecurity.PROP_AUDIENCE);
    // mensagem adicional (nomeChave, ValorChave)
    String claimKey = MethodSecurity.getReqProp(env, ConstSecurity.PROP_CLAIM_NAME);
    String claimValue = MethodSecurity.getReqProp(env, ConstSecurity.PROP_CLAIM_VALUE);
    // Tempo de bloqueio de uso após a criação (data atual + propriedade)
    int validAfterSeconds = MethodSecurity.getReqIntProp(env, ConstSecurity.PROP_VALID_AFTER_SECONDS);
    // Quando vai expira (data atual + propriedade)
    int expiresAt = MethodSecurity.getReqIntProp(env, ConstSecurity.PROP_EXPIRES_AT);

    return subject -> {
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
      return encoderGenerator.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    };
  }

  @Bean
  public JwtDecoder createDecoderGenerator() { // valida
    OAuth2Error oA2Err = new OAuth2Error(ConstSecurity.MSG_INVALID_TOKEN, ConstSecurity.MSG_INVALID_AUDIENCE, null);
    NimbusJwtDecoder decoder = NimbusJwtDecoder.withPublicKey(publicKey).build();

    decoder.setJwtValidator(new DelegatingOAuth2TokenValidator<>(//
        JwtValidators.createDefault(), //
        jwt -> jwt.getAudience().contains(audience) //
            ? OAuth2TokenValidatorResult.success() //
            : OAuth2TokenValidatorResult.failure(oA2Err))//
    );
    return decoder;
  }

}