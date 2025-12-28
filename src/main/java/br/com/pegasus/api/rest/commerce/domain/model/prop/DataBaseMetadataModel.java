package br.com.pegasus.api.rest.commerce.domain.model.prop;

import br.com.pegasus.api.rest.commerce.infra.util.MethodUtil;
import br.com.pegasus.api.rest.commerce.infra.util.YamlLoader;
import lombok.Getter;
import org.springframework.core.env.Environment;

import java.util.List;
import java.util.Map;

@Getter
public class DataBaseMetadataModel {

  private final String type;
  private final String url;
  private final List<String> schemaLocations;
  private List<MetadataPropModel> metadata;

  public DataBaseMetadataModel(Environment env) {
    Map<String, Object> databasePropYmlMap = YamlLoader.load("properties/database-prop.yml");
    url = databasePropYmlMap.getOrDefault("spring.datasource.url", "").toString();
    type = databasePropYmlMap.get("api.database.info.type").toString();
    schemaLocations = MethodUtil.envGetPropertyList(env, "spring.sql.init.schema-locations");

    if (databasePropYmlMap.get("api.database.metadata") instanceof List<?> list) {
      metadata = list.stream().filter(Map.class::isInstance).map(Map.class::cast)//
          .map(map -> {
            String propName = map.get("key").toString();
            return MetadataPropModel.builder()//
                .key(propName)//
                .value(env.getProperty(propName, "-"))//
                .content(map.get("content").toString())//
                .build();
          }).toList();
    }


    System.out.println();
  }

  private void getInfo(Map<String, Object> databasePropYmlMap) {

  }

}
