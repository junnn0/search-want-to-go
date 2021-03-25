package com.junyoung.searchwheretogoapi.model.api;

import java.util.List;

public interface Searchable<T> {
  List<T> get();
}
