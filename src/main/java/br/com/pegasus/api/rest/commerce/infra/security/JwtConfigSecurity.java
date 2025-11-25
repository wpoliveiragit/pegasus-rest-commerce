package br.com.pegasus.api.rest.commerce.infra.security;

import br.com.pegasus.api.rest.commerce.infra.util.ConstUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtConfigSecurity {

  private final JwtTokenSecurity configSecurity;

  @Bean
  public SecurityFilterChain securityFilterChain(Environment env, HttpSecurity http) throws Exception {
    boolean securityController = env.getProperty("api.security.controller", Boolean.class, ConstUtil.BOOLEAN_TRUE);

    String[] withToken = {"/oauth/token"}; // hambiente funcional
    String[] withOutToken = {"/**"}; //ambiente para testes rapidos e locais

    return http.csrf(AbstractHttpConfigurer::disable) //
        .authorizeHttpRequests(auth -> {
          auth.requestMatchers((securityController) ? withToken : withOutToken)//
              .permitAll()//
              .anyRequest()//
              .authenticated();
        }).oauth2ResourceServer(oauth2 -> {
          oauth2.jwt(jwt -> jwt.decoder(configSecurity.jwtDecoder()));
        }).build();
  }

}
