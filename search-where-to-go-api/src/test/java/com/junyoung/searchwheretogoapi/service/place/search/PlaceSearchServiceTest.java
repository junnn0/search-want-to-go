package com.junyoung.searchwheretogoapi.service.place.search;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import com.junyoung.searchwheretogoapi.client.PlaceApiClient;
import com.junyoung.searchwheretogoapi.constants.SourceType;
import com.junyoung.searchwheretogoapi.model.api.PlaceData;
import com.junyoung.searchwheretogoapi.service.place.search.PlaceSearchService;
import com.junyoung.searchwheretogoapi.util.TestDataUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.CollectionUtils;

class PlaceSearchServiceTest {

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
    for (PlaceData kakaoPlaceData : places.subList(3, 6)) {
      assertEquals(SourceType.KAKAO, kakaoPlaceData.getSourceType());
    }
    for (PlaceData naverPlaceData : places.subList(6, places.size())) {
      assertEquals(SourceType.NAVER, naverPlaceData.getSourceType());
    }
  }
}
