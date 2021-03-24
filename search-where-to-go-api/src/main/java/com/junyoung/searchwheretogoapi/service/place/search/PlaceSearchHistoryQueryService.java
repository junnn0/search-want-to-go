package com.junyoung.searchwheretogoapi.service.place.search;

import com.junyoung.searchwheretogoapi.model.api.ListResponse;
import com.junyoung.searchwheretogoapi.model.api.PageParam;
import com.junyoung.searchwheretogoapi.model.data.PlaceSearchHistory;
import com.junyoung.searchwheretogoapi.model.data.User;
import com.junyoung.searchwheretogoapi.repository.PlaceSearchHistoryQueryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class PlaceSearchHistoryQueryService {
  private final PlaceSearchHistoryQueryRepository historyQueryRepository;

  public ListResponse<PlaceSearchHistory> getPlaceSearchHistories(User user, PageParam pageParam) {
    log.debug("> getPlaceSearchHistories(user={}, pageParam={})", user, pageParam);
    List<PlaceSearchHistory> searchHistories = historyQueryRepository.findByUserIdOrderByHistoryIdDesc(
        user.getUserId(), PageRequest.of(pageParam.getPageNum() - 1, pageParam.getPageSize()));
    int totalCount = historyQueryRepository.countByUserId(user.getUserId());
    return new ListResponse<>(searchHistories, totalCount);
  }
}
