package br.com.pegasus.api.rest.commerce.app.controller;

import br.com.pegasus.api.rest.commerce.infra.handler.annot.LogAnnot;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    List<String[]> keys = List.of(
        new String[]{"app.name", "name"},
        new String[]{"app.url", "url"},
        new String[]{"app.consoleUrl", "consoleUrl"},
        new String[]{"app.user", "user"},
        new String[]{"app.password", "password"},
        new String[]{"app.ddlAuto", "ddlAuto"}
    );

    prop.put("db", keys.stream()
        .map(k -> Map.entry(k[1], env.getProperty(k[0], err)))
        .filter(entry -> !entry.getValue().equals(err))
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
    );
  }
}
