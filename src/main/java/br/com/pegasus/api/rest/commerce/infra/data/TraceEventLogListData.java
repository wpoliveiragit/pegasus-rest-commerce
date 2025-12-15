package br.com.pegasus.api.rest.commerce.infra.data;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TracelogListData {

  private final List<TracelogData> trace;

  public TracelogListData(){
    trace = new ArrayList<>();
  }

  public void addTrace(){

  }

}
