package br.com.pegasus.api.rest.commerce.infra.security;

import br.com.pegasus.api.rest.commerce.infra.util.ConstUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtConfigSecurity {

  private final JwtTokenSecurity configSecurity;

  @Bean
  public SecurityFilterChain securityFilterChain(Environment env, HttpSecurity http) throws Exception {
    boolean securityController = env.getProperty("api.security.controller", Boolean.class, ConstUtil.BOOLEAN_TRUE);

    String[] withToken = {"/oauth/token"};
    String[] withOutToken = {"/**"};

    http.csrf(AbstractHttpConfigurer::disable);

    // PERMITIR IFRAMES (necessário para H2 Console)
    http.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));

    http.authorizeHttpRequests(auth -> auth.requestMatchers(securityController ? withToken : withOutToken)//
        .permitAll()//
        .anyRequest()//
        .authenticated()//
    );
    http.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.decoder(configSecurity.jwtDecoder())));
    return http.build();
  }


}