package br.com.pegasus.module.security.core;

import br.com.pegasus.module.security.props.SecurityProps;
import br.com.pegasus.module.security.util.ConstSecUtil;
import br.com.pegasus.module.security.util.MethodSecurityUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

@Log4j2
public class OAuthWebSecCore {

  public SecurityFilterChain createRequestFilterConfig(HttpSecurity http, JwtDecoder jwtDecoder, SecurityProps props) {
    boolean enabledLog = props.isEnabledLog();
    boolean enableH2ConsoleProp = props.isEnableH2Console();

    try {
      http.csrf(AbstractHttpConfigurer::disable);
      MethodSecurityUtil.logInfo(log, enabledLog, "não sei o q isso desabilita"); // nota: ajuste a mensagem
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }

    try {
      if (enableH2ConsoleProp) {
        http.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));
      }
      MethodSecurityUtil.logInfo(log, enabledLog, " H2 Console Web: {}", enableH2ConsoleProp ? "habilitado" : "desabilitado"); // nota: ajuste a mensagem
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }

    try {
      String[] publicAccessEndpoints = props.getOpenRoutes().toArray(new String[ConstSecUtil.INT_0]);

      http.authorizeHttpRequests(//
          auth -> auth.requestMatchers(publicAccessEndpoints)//
              .permitAll().anyRequest().authenticated());
      http.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.decoder(jwtDecoder)));

      MethodSecurityUtil.logInfo(log, enabledLog, "Endpoints without token requirement: ({})", String.join(", ", publicAccessEndpoints)); // nota: ajuste mostrando os endpoints permitidos
      return http.build();
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }

}