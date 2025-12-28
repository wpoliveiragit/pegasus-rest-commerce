package br.com.pegasus.api.rest.commerce.domain.model.prop;

import lombok.Getter;
import org.springframework.boot.SpringBootVersion;
import org.springframework.core.env.Environment;

@Getter
public class MetadataModelProp {

  private final String javaVersion;
  private final String springVersion;
  private final String name;
  private final String email;
  private final String sac;
  private final DataBaseMetadataModel dataBase;

  public MetadataModelProp(Environment env) {
    this.javaVersion = Runtime.version().feature() + "";
    this.springVersion = SpringBootVersion.getVersion();
    this.name = env.getProperty("api.website.info.name", "-");
    this.email = env.getProperty("api.website.info.email", "-");
    this.sac = env.getProperty("api.website.info.sac", "-");
    this.dataBase = new DataBaseMetadataModel(env);
  }

}
