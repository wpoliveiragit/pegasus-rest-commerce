package br.com.pegasus.api.rest.commerce.app.controller;

import br.com.pegasus.api.rest.commerce.domain.port.WebsitePort;
import br.com.pegasus.api.rest.commerce.infra.handler.marker.ControllerLayerMarker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@RequiredArgsConstructor
@ControllerLayerMarker("Controller.Website")
@Controller
public class WebSiteController {

  private final WebsitePort service;

  @GetMapping("/website")
  public String website(Model model) {
    model.addAttribute("prop", service.website(1));
    return "website";
  }

  @GetMapping("/license")
  public String license(Model model) {
    model.addAttribute("prop", service.license(1));
    return "license";
  }

  @GetMapping("/terms")
  public String terms(Model model) {
    model.addAttribute("prop", service.terms(1));
    return "terms";
  }

}