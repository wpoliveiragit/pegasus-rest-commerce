package br.com.pegasus.api.rest.commerce.domain.core;

import br.com.pegasus.api.rest.commerce.domain.adapter.EnvPropAdapter;
import br.com.pegasus.api.rest.commerce.domain.port.WebsitePort;

import java.util.Map;

public class WebsiteCore implements WebsitePort {

  private final EnvPropAdapter envProp;

  public WebsiteCore(EnvPropAdapter envProp) {
    this.envProp= envProp;
  }

  @Override
  public Map<String, ?> getProp(int page){
    return Map.of(//
        "metadata", Map.of(//
            "database", Map.of(//
                "info", envProp.getPropertyMap("metadata.database"), //
                "dataLocations", envProp.getPropertyMap("spring.sql.init.data-locations"), //
                "schemaLocations", envProp.getPropertyList("spring.sql.init.schema-locations") //
            ) //
        ) //
    );
  }

  @Override
  public Map<String, String> geth2ConsoleInfo(int page) {
    return Map.of(//
        "Saved Settings", "UP", //
        "Setting Name", "UP", //
        "Driver Class", "UP", //
        "JDBC URL", "UP", //
        "Username", "UP", //
        "Password", "api"//
    );
  }

}