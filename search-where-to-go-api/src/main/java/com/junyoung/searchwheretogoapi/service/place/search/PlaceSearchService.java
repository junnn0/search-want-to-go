package com.junyoung.searchwheretogoapi.service.place.search;

import com.junyoung.searchwheretogoapi.client.PlaceApiClient;
import com.junyoung.searchwheretogoapi.model.api.Place;
import com.junyoung.searchwheretogoapi.model.api.PlaceData;
import com.junyoung.searchwheretogoapi.util.NamingUtil;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class PlaceSearchService {
  private final List<PlaceApiClient> placeApiClients;

  @Cacheable("getPlaces")
  public List<PlaceData> getPlaces(String query) {
    log.debug("> getPlaces(query={})", query);

    List<PlaceData> placeData =
        placeApiClients.stream()
            .flatMap(placeApiClient -> placeApiClient.getPlaces(query).stream())
            .map(Place::toPlaceData)
            .collect(Collectors.toList());

    return sortBy.apply(placeData);
  }

  /** 중복허용 리스트에서 빈도수를 기준으로 정렬 */
  private final UnaryOperator<List<PlaceData>> sortBy =
      places -> {
        Map<String, PlaceData> duplicatedNameMap = new HashMap<>();
        Map<String, PlaceData> uniqueNameMap = new HashMap<>();
        Map<String, Integer> frequencyCounter = new HashMap<>();

        for (PlaceData place : places) {
          String placeName = NamingUtil.eraseBoldTags(place.getName());
          place.setName(placeName);
          if (frequencyCounter.containsKey(placeName)) {
            frequencyCounter.put(placeName, frequencyCounter.get(placeName) + 1);
            duplicatedNameMap.put(placeName, uniqueNameMap.remove(placeName));
          } else {
            frequencyCounter.put(placeName, 1);
            uniqueNameMap.put(placeName, place);
          }
        }

        Stream<PlaceData> duplicatedSortedByFrequency =
            frequencyCounter.entrySet().stream()
                .filter(entry -> duplicatedNameMap.containsKey(entry.getKey()))
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .map(entry -> duplicatedNameMap.get(entry.getKey()));

        Stream<PlaceData> uniqueSortedBySource =
            uniqueNameMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(PlaceData::compareTo))
                .map(Map.Entry::getValue);

        return Stream.concat(duplicatedSortedByFrequency, uniqueSortedBySource)
            .collect(Collectors.toList());
      };
}
