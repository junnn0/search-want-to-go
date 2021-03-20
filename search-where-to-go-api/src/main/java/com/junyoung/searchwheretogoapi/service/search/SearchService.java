package com.junyoung.searchwheretogoapi.service.search;

import java.util.List;
import java.util.stream.Collectors;

import com.junyoung.searchwheretogoapi.client.PlaceApiClient;
import com.junyoung.searchwheretogoapi.model.api.Searchable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class SearchService {

    private final List<PlaceApiClient> placeApiClients;

    public List<Searchable> getPlaces(String query) {
        log.debug("> getPlaces(query={})", query);
        log.debug("> clients={}", placeApiClients);
        return placeApiClients.stream()
                .map(client -> client.getPlaces(query))
                .collect(Collectors.toList());
    }

}
