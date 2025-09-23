package br.com.pegasus.rest.commerce;

import br.com.pegasus.api.rest.commerce.infra.util.StreamUtil;

import java.util.List;
import java.util.Map;

public class Main {

  public static void main(String[] args) {

    String[] arrayString = "aaa:111 bbb:222 ccc:333".split(" ");

    List<String> list = List.of("AAA:111","BBB:222", "CCC:333");

    String[][] pares = {
        {"chave1", "valor1"},
        {"chave2", "valor2"},
        {"chave3", "valor3"}
    };





    Map<String, String> mapResp1 = StreamUtil.of(arrayString)
        .map(e -> {
          String[] kv = e.split(":");
          return Map.entry(kv[0], kv[1]);
        }).toMap(Map.Entry::getKey, Map.Entry::getValue);

    Map<String, String> mapResp2 = StreamUtil.of(list)
        .map(e -> {
          String[] kv = e.split(":");
          return Map.entry(kv[0], kv[1]);
        }).toMap(Map.Entry::getKey, Map.Entry::getValue);


    Map<String, String> mapResp3 = StreamUtil.of(pares)
        .map(kv -> Map.entry(kv[0], kv[1]))
        .toLinkedHashMap(Map.Entry::getKey, Map.Entry::getValue);


    mapResp1.forEach((k,v) -> System.out.println(k + " : " + v ));
    mapResp2.forEach((k,v) -> System.out.println(k + " : " + v ));
    mapResp3.forEach((k,v) -> System.out.println(k + " : " + v ));

  }

}
