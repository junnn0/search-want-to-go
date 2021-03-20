package com.junyoung.searchwheretogoapi.service.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.junyoung.searchwheretogoapi.client.PlaceApiClient;
import com.junyoung.searchwheretogoapi.model.api.Place;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class SearchService {
    private final List<PlaceApiClient> placeApiClients;

    public CompletableFuture<List<? extends Place>> getPlaces(String query) {
        log.debug("> getPlaces(query={})", query);

        return placeApiClients.stream()
                .map(client -> client.getPlaces(query))
                .reduce((cf1, cf2) -> cf1.thenCombine(cf2, (lst1, lst2) -> {
                    List<Place> mergedList = new ArrayList<>(lst1);
                    mergedList.addAll(lst2);
                    return mergedList;
                }))
                .orElse(CompletableFuture.completedFuture(Collections.emptyList()));

    }

}
