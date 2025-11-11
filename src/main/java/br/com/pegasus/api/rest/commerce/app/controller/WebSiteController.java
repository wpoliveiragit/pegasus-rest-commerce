package br.com.pegasus.api.rest.commerce.app.controller;

import br.com.pegasus.api.rest.commerce.infra.util.MethodUtil;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebSiteController {

  private final String siteHome;
  private final String siteLicense;
  private final String siteTerms;

  public WebSiteController() {
    this.siteHome = MethodUtil.readResourceFileToStringUTF8("site/index.html");
    this.siteLicense = MethodUtil.readResourceFileToStringUTF8("site/license.html");
    this.siteTerms = MethodUtil.readResourceFileToStringUTF8("site/terms.html");
  }

  //!: SITE :: HOME
  @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
  public String getWebsiteHome(Model model) {
    return siteHome;
  }

  //!: Site :: licensa
  @GetMapping(value = "/license", produces = MediaType.TEXT_HTML_VALUE)
  public String getWebsiteLicense(Model model) {
    return siteLicense;
  }

  //!: Site :: Termos
  @GetMapping(value = "/terms", produces = MediaType.TEXT_HTML_VALUE)
  public String getWebsiteTerms(Model model) {
    return siteTerms;
  }

}
