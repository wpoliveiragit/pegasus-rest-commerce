package br.com.pegasus.module.security.core;

import br.com.pegasus.module.security.props.SecurityProps;
import br.com.pegasus.module.security.util.MethodSecurityUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

@Log4j2
public class OAuthWebSecCore {
  public static final int INT_0 = 0;

  public SecurityFilterChain createRequestFilterConfig(HttpSecurity http, JwtDecoder jwtDecoder, SecurityProps props) throws Exception {
    final boolean enabledLog = props.isEnableLog();
    final String[] openRoutes = props.getOpenRoutes().toArray(new String[INT_0]);

    this.desableCSRF(http, enabledLog);
    this.desableIframe(http, enabledLog, props.isEnableH2Console());
    this.allowRoutes(http, openRoutes);
    this.addDecoder(http, jwtDecoder);

    String chooseRoutes = openRoutes.length == 0 ? "none" : String.join(", ", openRoutes);
    MethodSecurityUtil.logInfo(log, enabledLog, "Public endpoints (no JWT required): {}", chooseRoutes);
    return http.build();
  }

  private void desableCSRF(HttpSecurity http, boolean enabledLog) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable);
    MethodSecurityUtil.logInfo(log, enabledLog, "CSRF protection disabled (stateless JWT authentication)");
  }

  private void desableIframe(HttpSecurity http, boolean enabledLog, boolean enableH2ConsoleProp) throws Exception {
    if (enableH2ConsoleProp) { // O H2 Console usa iframe internamente precisa desabilitar para funcionar
      http.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));
    }
    MethodSecurityUtil.logInfo(log, enabledLog, " H2 Console Web: {}", enableH2ConsoleProp ? "habilitado" : "desabilitado");
  }

  private void allowRoutes(HttpSecurity http, String[] openRoutes) throws Exception {
    http.authorizeHttpRequests(auth -> auth.requestMatchers(openRoutes)//
        .permitAll()//
        .anyRequest()//
        .authenticated()//
    );
  }

  private void addDecoder(HttpSecurity http, JwtDecoder jwtDecoder) throws Exception {
    http.oauth2ResourceServer(o -> o.jwt(jwt -> jwt.decoder(jwtDecoder)));
  }

}