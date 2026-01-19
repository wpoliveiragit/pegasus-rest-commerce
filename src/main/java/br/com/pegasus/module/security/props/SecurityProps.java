package br.com.pegasus.module.security.props;


import br.com.pegasus.module.security.util.ConstSecUtil;
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

  private List<String> openRoutes = List.of(ConstSecUtil.PATTERN_ALL_PATHS);

  private boolean enableH2Console = false;

  private boolean enableLog = true;

  /** Tempo de bloqueio de uso após a criação (data atual + propriedade). */
  private int validAfterSeconds = 0;

  /** Quem emitiu o token. */
  @NotBlank
  private String projName;

  @NotBlank
  private String audience;

  /** Quando vai expira (data atual + propriedade) */
  @Min(1)
  private int expiresAt;

  @AssertTrue(message = "validAfterSeconds cannot be negative.")
  public boolean isValidAfterSeconds() {
    return validAfterSeconds >= 0;
  }

}
