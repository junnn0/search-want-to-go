package com.junyoung.searchwheretogoapi.client;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.junyoung.searchwheretogoapi.model.api.Place;

public interface PlaceApiClient {
    CompletableFuture<List<? extends Place>> getPlaces(String query);
}
