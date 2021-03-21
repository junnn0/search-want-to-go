package com.junyoung.searchwheretogoapi.client;

import com.junyoung.searchwheretogoapi.model.api.Place;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface PlaceApiClient {
    CompletableFuture<List<? extends Place>> getPlaces(String query);
}
