package br.com.pegasus.module.security.props;


import jakarta.validation.constraints.AssertTrue;
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
  @jakarta.validation.Valid
  private ClaimProps claim = new ClaimProps();
  @jakarta.validation.Valid
  private RsaProps rsa = new RsaProps();
  private List<String> openRoutes = List.of("/**");
  private boolean enableH2Console = false;
  private boolean enableLog = true;
  private int validAfterSeconds = 0;
  @NotBlank
  private String projName;
  @NotBlank
  private String audience;
  @Min(1)
  private Integer expiresAt;

  @AssertTrue(message = "validAfterSeconds cannot be negative.")
  public boolean isValidAfterSeconds() {
    return validAfterSeconds >= 0;
  }

}
