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

  public SecurityFilterChain createRequestFilterConfig(HttpSecurity http, JwtDecoder jwtDecoder, SecurityProps props) throws Exception {
    boolean enabledLog = props.isEnableLog();
    boolean enableH2ConsoleProp = props.isEnableH2Console();

    http.csrf(AbstractHttpConfigurer::disable);
    MethodSecurityUtil.logInfo(log, enabledLog, "CSRF protection disabled (stateless JWT authentication)");

    if (enableH2ConsoleProp) { // O H2 Console usa iframe internamente precisa desabilitar para funcionar
      http.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));
    }
    MethodSecurityUtil.logInfo(log, enabledLog, " H2 Console Web: {}", enableH2ConsoleProp ? "habilitado" : "desabilitado"); // nota: melhore a mensagem

    String[] openRoutes = props.getOpenRoutes().toArray(new String[ConstSecUtil.INT_0]);

    http.authorizeHttpRequests(auth -> auth.requestMatchers(openRoutes).permitAll().anyRequest().authenticated());
    http.oauth2ResourceServer(o -> o.jwt(jwt -> jwt.decoder(jwtDecoder)));

    MethodSecurityUtil.logInfo(log, enabledLog, "Public endpoints (no JWT required): {}", //
        openRoutes.length == 0 ? "none" : String.join(", ", openRoutes));
    return http.build();
  }

}