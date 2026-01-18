package br.com.pegasus.module.security.config;

import br.com.pegasus.module.security.ConstSecurity;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
public class WebSecurity {

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

}
