package br.com.pegasus.rest.commerce;

import br.com.pegasus.api.rest.commerce.infra.util.CpfUtil;

public class Main {

  public static void main(String[] args) {
    System.out.println(CpfUtil.valid("34772872825"));
    System.out.println(CpfUtil.valid("00000000000"));
  }
}
