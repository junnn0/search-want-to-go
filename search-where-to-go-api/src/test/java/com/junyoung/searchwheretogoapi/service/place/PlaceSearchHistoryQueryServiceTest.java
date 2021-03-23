package com.junyoung.searchwheretogoapi.service.place;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import com.junyoung.searchwheretogoapi.client.PlaceApiClient;
import com.junyoung.searchwheretogoapi.model.api.PlaceData;
import com.junyoung.searchwheretogoapi.service.place.search.PlaceSearchService;
import com.junyoung.searchwheretogoapi.util.TestDataUtil;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.CollectionUtils;

class PlaceSearchHistoryQueryServiceTest {

  private PlaceSearchService placeSearchService;

  @BeforeEach
  void setUp() {
    // given
    PlaceApiClient placeApiClient = mock(PlaceApiClient.class);

    given(placeApiClient.getPlaces(anyString()))
        .willAnswer(invocation -> TestDataUtil.createPlaces());

    placeSearchService = new PlaceSearchService(Collections.singletonList(placeApiClient));
  }

  @Test
  void testGetSortedPlaces() {
    // when
    List<PlaceData> places = placeSearchService.getPlaces("query");

    // then
    assertFalse(CollectionUtils.isEmpty(places));
    for (PlaceData placeData : places.subList(0, 3)) {
      assertTrue(placeData.getName().contains("dup"));
    }
    for (PlaceData placeData : places.subList(3, places.size())) {
      assertTrue(placeData.getName().contains("unique"));
    }
  }
}
