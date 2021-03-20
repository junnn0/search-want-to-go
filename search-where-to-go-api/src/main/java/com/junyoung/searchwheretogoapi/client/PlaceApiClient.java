package com.junyoung.searchwheretogoapi.client;

import com.junyoung.searchwheretogoapi.model.api.Searchable;

public interface PlaceApiClient {
    Searchable getPlaces(String query);
}
