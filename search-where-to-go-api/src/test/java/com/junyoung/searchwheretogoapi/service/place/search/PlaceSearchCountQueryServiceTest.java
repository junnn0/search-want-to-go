package com.junyoung.searchwheretogoapi.service.place.search;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import com.fasterxml.jackson.core.type.TypeReference;
import com.junyoung.searchwheretogoapi.model.data.SearchCount;
import com.junyoung.searchwheretogoapi.repository.PlaceSearchCountRepository;
import com.junyoung.searchwheretogoapi.service.place.SearchCounter;
import com.junyoung.searchwheretogoapi.util.JsonUtil;
import com.junyoung.searchwheretogoapi.util.TestDataUtil;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

class PlaceSearchCountQueryServiceTest {

    private SearchCounter searchCounter;
    private PlaceSearchCountRepository placeSearchCountRepository;
    private PlaceSearchCountQueryService placeSearchCountQueryService;

    @BeforeEach
    void setUp() {
        placeSearchCountRepository = mock(PlaceSearchCountRepository.class);
        searchCounter = mock(SearchCounter.class);

        placeSearchCountQueryService =
                new PlaceSearchCountQueryService(placeSearchCountRepository, searchCounter);
    }

    @Test
    void test_count_accuracy() {
        List<SearchCount> searchCounts = TestDataUtil.createSearchCounts(5);
        SearchCount firstSearchAdder = new SearchCount(searchCounts.get(0).getQuery(), 10L);

        given(placeSearchCountRepository.findAll(any(Pageable.class)))
                .willReturn(
                        new PageImpl<>(
                                JsonUtil.convertAs(
                                        searchCounts, new TypeReference<List<SearchCount>>() {})));
        given(searchCounter.getCounterMapSnapshot())
                .willReturn(
                        Collections.singletonMap(
                                firstSearchAdder.getQuery(), firstSearchAdder.getCount()));

        List<SearchCount> finalSearchCounts = placeSearchCountQueryService.getTopSearchedCounts();

        assertEquals(searchCounts.get(0).getQuery(), finalSearchCounts.get(0).getQuery());
        assertNotEquals(searchCounts.get(0).getCount(), finalSearchCounts.get(0).getCount());
    }
}
