package com.junyoung.searchwheretogoapi.client;

import com.junyoung.searchwheretogoapi.model.api.Place;
import java.util.List;

public interface PlaceApiClient {
  List<? extends Place> getPlaces(String query);
}
