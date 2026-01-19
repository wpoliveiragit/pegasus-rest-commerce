package br.com.pegasus.module.security.props;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@ConfigurationProperties(prefix = "api.security")
@Validated
@Getter
@Setter
public class SecurityProps {

  private ClaimProps claim = new ClaimProps();
  private RsaProps rsa = new RsaProps();
  private List<String> openRoutes = List.of();
  private boolean enableH2Console = false;
  private boolean enabled = true;
  private int validAfterSeconds;

  @NotBlank
  private String projName;

  @NotBlank
  private String audience;

  @Min(1)
  private int expiresAt;

}
