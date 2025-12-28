package br.com.pegasus.api.rest.commerce.infra.adapter;

import br.com.pegasus.api.rest.commerce.domain.adapter.HtmlAdapter;
import br.com.pegasus.api.rest.commerce.infra.util.TextUtil;
import org.springframework.stereotype.Component;

@Component
public class HtmlConfigAdapter implements HtmlAdapter {
  @Override
  public String addNegrito(String text) {
    return "<b>" + text + "</b>";
  }

  private String code = "";

  @Override
  public String getCode() {
    return code;
  }

  @Override
  public HtmlAdapter newCode() {
    return new HtmlConfigAdapter();
  }

  @Override
  public HtmlAdapter addDdFormater(String text, Object... arrayObj) {
    code += "<dd>" + TextUtil.format(text, arrayObj) + "</dd>";
    return this;
  }

  @Override
  public HtmlAdapter addH2(String text) {
    code += "<h2>" + text + "</h2>";
    return this;
  }

  @Override
  public HtmlAdapter addDtH2B(String text) {
    code += "<dt>" + "<h3>" + "<b3>" + text + "</b3>" + "</h3>" + "</dt>";
    return this;
  }

  @Override
  public HtmlAdapter addDtH4B(String text) {
    code += "<dt>" + "<h4>" + "<b3>" + text + "</b3>" + "</h4>" + "</dt>";
    return this;
  }

  @Override
  public HtmlAdapter addHtml(HtmlAdapter html) {
    code += html.getCode();
    return this;
  }

  @Override
  public HtmlAdapter opensDl() {
    code += "<dl>";
    return this;
  }

  @Override
  public HtmlAdapter closeDL() {
    code += "</dl>";
    return this;
  }

  @Override
  public String addCode(String message) {
    return "<code>" + message + "</code>";
  }

  @Override
  public String addParagraph(String messageFormat, Object... values) {
    return "<p>" + TextUtil.format(messageFormat, values) + "</p>";
  }

}