package br.com.pegasus.api.rest.commerce.app.controller;

import org.springframework.boot.SpringBootVersion;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class WebSiteController {

  @GetMapping("/website")
  public String website(Model model) {
    int javaVersion = Runtime.version().feature();
    String springVersion = SpringBootVersion.getVersion();

    model.addAttribute("system", Map.of(
        "databaseUrl", "jdbc:h2:file:./src/main/resources/db/banco-dados-h2",
        "javaVersion", javaVersion,
        "springBootVersion", springVersion
    ));
    model.addAttribute("environmentHtml", environmentBlock(javaVersion, springVersion));

    return "website"; // templates/website.html
  }

  @GetMapping("/license")
  public String license(Model model) {
    return "license"; // templates/license.html
  }

  @GetMapping("/terms")
  public String terms(Model model) {
    return "terms"; // templates/terms.html
  }


  public static String environmentBlock(int javaVersion,
                                        String springBootVersion) {

    StringBuilder html = new StringBuilder();
    html.append("<ul>");//
    html.append("<li>Java: ").append(javaVersion).append("</li>");//
    html.append("<li>Spring Boot: ").append(springBootVersion).append("</li>");//
    html.append("</ul>");

    return html.toString();
  }


}