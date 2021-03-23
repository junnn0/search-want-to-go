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

    return sortByFrequency.apply(placeData);
  }

  /** 중복허용 리스트에서 빈도수를 기준으로 정렬 */
  private final UnaryOperator<List<PlaceData>> sortByFrequency =
      places -> {
        Map<String, PlaceData> nameMap = new HashMap<>();
        Map<String, Integer> frequencyCounter = new HashMap<>();
        for (PlaceData place : places) {
          String placeName = NamingUtil.eraseBoldTags(place.getName());
          place.setName(placeName);
          if (frequencyCounter.containsKey(placeName)) {
            frequencyCounter.put(placeName, frequencyCounter.get(placeName) + 1);
          } else {
            frequencyCounter.put(placeName, 1);
            nameMap.put(placeName, place);
          }
        }

        return frequencyCounter.entrySet().stream()
            .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
            .map(entry -> nameMap.get(entry.getKey()))
            .collect(Collectors.toList());
      };
}
