package com.junyoung.searchwheretogoapi.service.place.search;

import com.junyoung.searchwheretogoapi.model.data.SearchCount;
import com.junyoung.searchwheretogoapi.repository.PlaceSearchCountRepository;
import com.junyoung.searchwheretogoapi.service.place.SearchCounter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PlaceSearchCountQueryService {
  private static final int FIRST_PAGE_NUM = 0;
  private static final int MAX_TOP_SEARCH_COUNT = 10;
  private static final String ORDER_COLUMN = "count";
  private final PlaceSearchCountRepository placeSearchCountRepository;
  private final SearchCounter searchCounter;

  public List<SearchCount> getTopSearchedCounts() {
    Map<String, Long> countsFromCache = searchCounter.getCounterMapSnapshot();
    List<SearchCount> searchCounts =
        placeSearchCountRepository
            .findAll(
                PageRequest.of(
                    FIRST_PAGE_NUM, MAX_TOP_SEARCH_COUNT, Sort.by(Sort.Order.desc(ORDER_COLUMN))))
            .getContent();
    return searchCounts.stream()
        .peek(
            searchCount -> {
              if (countsFromCache.containsKey(searchCount.getQuery())) {
                searchCount.addCount(countsFromCache.get(searchCount.getQuery()));
              }
            })
        .collect(Collectors.toList());
  }
}
