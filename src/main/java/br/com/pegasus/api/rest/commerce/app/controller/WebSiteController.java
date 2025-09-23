package br.com.pegasus.api.rest.commerce.app.controller;

import br.com.pegasus.api.rest.commerce.infra.handler.annot.LogAnnot;
import br.com.pegasus.api.rest.commerce.infra.util.StreamUtil;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class WebSiteController {

  private final Environment env;
  private final Map<String, Object> prop;

  public WebSiteController(Environment env) {
    this.env = env;
    prop = new HashMap<>();
    createPropDataBase(prop);
  }

  @LogAnnot
  @GetMapping("/")
  public String home(Model model) {
    model.addAttribute(prop);
    return "index";
  }

  private void createPropDataBase(Map<String, Object> prop) {
    final String err = "@ERR";

    List<String> keys = List.of(//
        "app.name", "name",//
        "app.url", "url",//
        "app.user", "user"//
    );

    prop.put("db", StreamUtil.of(keys)//
        .map(key -> Map.entry(key, env.getProperty(key, err)))//
        .filter(entry -> !entry.getValue().equals(err))//
        .toMap(Map.Entry::getKey, Map.Entry::getValue)//
    );
  }
}
