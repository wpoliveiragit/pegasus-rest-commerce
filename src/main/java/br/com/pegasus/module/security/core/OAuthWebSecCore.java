package br.com.pegasus.module.security.core;

import br.com.pegasus.module.security.util.ConstSecUtil;
import br.com.pegasus.module.security.util.PropertySecurityUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

@Log4j2
public class OAuthWebSecCore {

  public SecurityFilterChain createRequestFilterConfig(Environment env, HttpSecurity http, JwtDecoder jwtDecoder) {
    PropertySecurityUtil propS = new PropertySecurityUtil(env);
    try {
      http.csrf(AbstractHttpConfigurer::disable);
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
    try {
      boolean enableH2ConsoleProp = propS.getEnableH2Console();
      if (enableH2ConsoleProp) {// PERMITIR IFRAMES (necessário para H2 Console)
        http.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));
      }
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }

    try {
      String[] withToken = propS.getOpenRoutes().toArray(new String[ConstSecUtil.INT_0]);
      String[] withoutToken = {ConstSecUtil.PATTERN_ALL_PATHS};
      boolean activated = propS.getEnable();

      http.authorizeHttpRequests(//
          auth -> auth.requestMatchers(activated ? withToken : withoutToken)//
              .permitAll().anyRequest().authenticated());
      http.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.decoder(jwtDecoder)));

      return http.build();
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }

}