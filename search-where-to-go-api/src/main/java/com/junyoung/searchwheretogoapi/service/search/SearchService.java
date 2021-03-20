package com.junyoung.searchwheretogoapi.service.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import com.junyoung.searchwheretogoapi.client.PlaceApiClient;
import com.junyoung.searchwheretogoapi.model.api.Place;
import com.junyoung.searchwheretogoapi.model.api.PlaceData;
import com.junyoung.searchwheretogoapi.util.NamingUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class SearchService {
    private final List<PlaceApiClient> placeApiClients;

    public CompletableFuture<List<PlaceData>> getPlaces(String query) {
        log.debug("> getPlaces(query={})", query);

        return placeApiClients.stream()
                .map(client -> client.getPlaces(query))
                .reduce((cf1, cf2) -> cf1.thenCombine(cf2, (lst1, lst2) -> {
                    List<Place> mergedList = new ArrayList<>(lst1);
                    mergedList.addAll(lst2);
                    return mergedList;
                }))
                .map(places -> places.thenApply(mapToPlaceData).thenApply(sortByFrequency))
                .orElse(CompletableFuture.completedFuture(Collections.emptyList()));

    }

    private final Function<List<? extends Place>, List<PlaceData>> mapToPlaceData = places ->
            places.stream().map(Place::toPlaceData).collect(Collectors.toList());

    private final UnaryOperator<List<PlaceData>> sortByFrequency = places -> {
        Map<String, PlaceData> nameMap = new HashMap<>();
        Map<String, Integer> frequencyCounter = new HashMap<>();
        for (PlaceData place : places) {
            String placeName = NamingUtil.eraseBoldTags(place.getName());
            if (frequencyCounter.containsKey(placeName)) {
                frequencyCounter.put(placeName, frequencyCounter.get(placeName) + 1);
            } else {
                frequencyCounter.put(placeName, 1);
                nameMap.put(placeName, place);
            }
        }

        return frequencyCounter.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .map(entry -> nameMap.get(entry.getKey()))
                .collect(Collectors.toList());
    };
}
