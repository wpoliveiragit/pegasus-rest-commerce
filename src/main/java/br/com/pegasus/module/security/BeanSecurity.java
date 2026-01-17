package br.com.pegasus.module.security;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
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
import org.springframework.security.web.SecurityFilterChain;

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

  private final int expiresAt;
  private final int validAfterSeconds;
  private final String claimKey;
  private final String claimValue;
  private final String projName;
  private final String audience;
  private final RSAPublicKey publicKey;
  private final RSAPrivateKey privateKey;

  public BeanSecurity(Environment env) throws Exception {
    String privateKey = MethodSecurity.getRequiredStringProperty(env, ConstSecurity.PROP_RSA_PRIVATE_KEY);
    String publicKey = MethodSecurity.getRequiredStringProperty(env, ConstSecurity.PROP_RSA_PUBLIC_KEY);

    this.projName = env.getProperty(ConstSecurity.PROP_PROJECT_NAME, ConstSecurity.MSG_NOT_FOUND);
    this.expiresAt = env.getProperty(ConstSecurity.PROP_EXPIRES_AT, Integer.class, ConstSecurity.INT_60);
    this.validAfterSeconds = env.getProperty(ConstSecurity.PROP_VALID_AFTER_SECONDS, Integer.class, ConstSecurity.INT_60);
    this.claimKey = env.getProperty(ConstSecurity.PROP_CLAIM_NAME, ConstSecurity.PROP_CLAIM_DEFAULT_NAME);
    this.claimValue = env.getProperty(ConstSecurity.PROP_CLAIM_VALUE, ConstSecurity.PROP_CLAIM_DEFAULT_VALUE);
    this.audience = env.getProperty(ConstSecurity.PROP_AUDIENCE, ConstSecurity.MSG_NOT_FOUND);

    // Gera um par de chaves RSA de 2048 bits
    this.privateKey = (RSAPrivateKey) KeyFactory.getInstance(ConstSecurity.ALGORITHM)//
        .generatePrivate(new PKCS8EncodedKeySpec(MethodSecurity.getDecodedKey(privateKey)));
    this.publicKey = (RSAPublicKey) KeyFactory.getInstance(ConstSecurity.ALGORITHM)//
        .generatePublic(new X509EncodedKeySpec(MethodSecurity.getDecodedKey(publicKey)));
  }

  @Bean
  public SecurityFilterChain createRequestFilterConfig(Environment env, HttpSecurity http, JwtDecoder jwtDecoder) throws Exception {
    boolean activated = env.getProperty(ConstSecurity.PROP_ENABLED, Boolean.class, ConstSecurity.BOOLEAN_TRUE);
    String[] withToken = Binder.get(env)//
        .bind(ConstSecurity.PROP_OPEN_ROUTES, Bindable.listOf(String.class))//
        .orElse(List.of())//
        .toArray(new String[ConstSecurity.INT_0]);

    http.csrf(AbstractHttpConfigurer::disable);

    // PERMITIR IFRAMES (necessário para H2 Console)
    http.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));

    http.authorizeHttpRequests(//
        auth -> auth.requestMatchers(activated ? withToken : new String[]{ConstSecurity.ALL_PATHS})//
            .permitAll().anyRequest().authenticated());
    http.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.decoder(jwtDecoder)));
    return http.build();
  }

  @Bean
  public JwtTokenSecurity createTokenGenerator(JwtEncoder encoder) {
    return subject -> {
      Instant now = Instant.now();
      JwtClaimsSet claims = JwtClaimsSet.builder()//
          .subject(subject)// Quem solicitou o token
          .id(UUID.randomUUID().toString()) // rastreio e blacklist de token
          .issuedAt(now)// Quando foi criado
          .issuer(projName)//Quem emitiu o token.
          .audience(List.of(audience)).expiresAt(now.plusSeconds(expiresAt))// Quando vai expira (data atual + propriedade)
          .notBefore(now.plusSeconds(validAfterSeconds))// Tempo de bloqueio de uso após a criação (data atual + propriedade)
          .claim(claimKey, claimValue)// mensagem adicional (nomeChave, ValorChave)
          .build();
      return encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    };
  }

  @Bean
  public JwtEncoder createEncoderGenerator() { // gera
    RSAKey rsaKey = new RSAKey.Builder(publicKey)//
        .privateKey(privateKey)//
        .keyID(UUID.randomUUID().toString())//
        .build();
    return new NimbusJwtEncoder(new ImmutableJWKSet<>(new JWKSet(rsaKey)));
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