package com.junyoung.searchwheretogoapi.service.search;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import com.junyoung.searchwheretogoapi.client.PlaceApiClient;
import com.junyoung.searchwheretogoapi.model.api.PlaceData;
import com.junyoung.searchwheretogoapi.util.TestDataUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.CollectionUtils;

class SearchServiceTest {

    private SearchService searchService;

    @BeforeEach
    void setUp() {
        PlaceApiClient placeApiClient = mock(PlaceApiClient.class);
        searchService = new SearchService(Collections.singletonList(placeApiClient));

        //given
        given(placeApiClient.getPlaces(anyString())).willReturn(CompletableFuture.completedFuture(TestDataUtil.createPlaces()));
    }

    @Test
    void testGetSortedPlaces() throws ExecutionException, InterruptedException {
        //when
        List<PlaceData> places = searchService.getPlaces("query").get();

        //then
        assertFalse(CollectionUtils.isEmpty(places));
        for (PlaceData placeData : places.subList(0, 3)) {
            assertTrue(placeData.getName().contains("dup"));
        }
        for (PlaceData placeData : places.subList(3, places.size())) {
            assertTrue(placeData.getName().contains("unique"));
        }
    }
}