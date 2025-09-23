package br.com.pegasus.api.rest.commerce.infra.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamUtil<T> {

  private final Stream<T> stream;

  private StreamUtil(Stream<T> stream) {
    this.stream = stream;
  }

  public static <T> StreamUtil<T> of(T... values) {
    return of(Arrays.asList(values));
  }

  public static <T> StreamUtil<T> of(Collection<T> collection) {
    return new StreamUtil<>(collection.stream());
  }

  public <R> StreamUtil<R> map(Function<? super T, ? extends R> mapper) {
    return new StreamUtil<>(stream.map(mapper));
  }

  public StreamUtil<T> filter(Predicate<? super T> predicate) {
    return new StreamUtil<>(stream.filter(predicate));
  }

  // Metodos customizados
  public String joing(String delimiter) {
    return stream.map(Object::toString).collect(Collectors.joining(delimiter));
  }
  public <K, V> Map<K, V> toMap(
      Function<? super T, ? extends K> keyMapper,
      Function<? super T, ? extends V> valueMapper) {
    return stream.collect(Collectors.toMap(keyMapper, valueMapper, (v1, v2) -> v1));
  }

  public <K, V> Map<K, V> toLinkedHashMap(
      Function<? super T, ? extends K> keyMapper,
      Function<? super T, ? extends V> valueMapper) {

    return stream.collect(Collectors.toMap(keyMapper, valueMapper, (v1, v2) -> v1, LinkedHashMap::new));
  }

  public List<T> toList() {
    return stream.toList();
  }

}

