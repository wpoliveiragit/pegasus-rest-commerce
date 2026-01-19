package br.com.pegasus.module.security.config;

import br.com.pegasus.module.security.core.JwtProviderSecCore;
import br.com.pegasus.module.security.JwtTokenSecurity;
import br.com.pegasus.module.security.core.OAuthWebSecCore;
import br.com.pegasus.module.security.props.SecurityProps;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableConfigurationProperties(SecurityProps.class)
@ComponentScan(basePackages = {"br.com.pegasus.module.security.props"})// apagar
public class BeanSecConfig {

  @Bean
  public SecurityFilterChain createRequestFilterConfig(Environment env, HttpSecurity http, JwtDecoder jwtDecoder) {
    return new OAuthWebSecCore().createRequestFilterConfig(env, http, jwtDecoder);
  }

  @Bean
  public JwtTokenSecurity createTokenGenerator(Environment env, SecurityProps props) {


    return new JwtProviderSecCore().createTokenGenerator(env, props);
  }

  @Bean
  public JwtDecoder createDecoderGenerator(Environment env) {
    return new JwtProviderSecCore().createDecoderGenerator(env);
  }


}
