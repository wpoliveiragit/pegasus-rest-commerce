package br.com.pegasus.api.rest.commerce.domain.adapter;

public interface HtmlAdapter {

  String addNegrito(String text);

  String addCode(String message);

  String addParagraph(String messageFormat, Object... values);


  // other
  HtmlAdapter newCode();

  String getCode();

  //add
  HtmlAdapter addDdFormater(String text, Object... arrayObj);

  HtmlAdapter addH2(String text);

  HtmlAdapter addDtH2B(String text);

  HtmlAdapter addDtH4B(String text);

  HtmlAdapter addHtml(HtmlAdapter html);

  //open close
  HtmlAdapter opensDl();

  HtmlAdapter closeDL();

}