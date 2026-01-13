package br.com.pegasus.api.rest.commerce.app.controller;

import br.com.pegasus.api.rest.commerce.domain.port.WebsitePort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@RequiredArgsConstructor
@Controller
public class WebSiteController {

  private final WebsitePort service;

  @GetMapping("/website")
  public String website(Model model) {
    createModel(model);
    return "website";
  }

  @GetMapping("/license")
  public String license(Model model) {
    createModel(model);
    return "license";
  }

  @GetMapping("/terms")
  public String terms(Model model) {
    createModel(model);
    return "terms";
  }

  @GetMapping(value = "/h2-console-info", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public Map<String, String> h2ConsoleInfo() {
    return service.geth2ConsoleInfo(1);
  }

  private void createModel(Model model) {
    model.addAttribute("prop", service.getProp(1));
  }

}