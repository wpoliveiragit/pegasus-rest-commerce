package br.com.pegasus.module.security.config;

import br.com.pegasus.module.security.JwtTokenSecurity;
import br.com.pegasus.module.security.core.JwtProviderSecCore;
import br.com.pegasus.module.security.core.OAuthWebSecCore;
import br.com.pegasus.module.security.props.SecurityProps;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableConfigurationProperties(SecurityProps.class)
public class BeanSecConfig {

  @Bean
  public SecurityFilterChain createRequestFilterConfig(HttpSecurity http, JwtDecoder jwtDecoder, SecurityProps props) throws Exception {
    return new OAuthWebSecCore().createRequestFilterConfig(http, jwtDecoder, props);
  }

  @Bean
  public JwtTokenSecurity createTokenGenerator(SecurityProps props) {
    return new JwtProviderSecCore().createTokenGenerator(props);
  }

  @Bean
  public JwtDecoder createDecoderGenerator(SecurityProps props) {
    return new JwtProviderSecCore().createDecoder(props);
  }

}