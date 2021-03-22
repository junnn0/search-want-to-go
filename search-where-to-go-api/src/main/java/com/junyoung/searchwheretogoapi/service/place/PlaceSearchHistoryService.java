package com.junyoung.searchwheretogoapi.service.place;

import com.junyoung.searchwheretogoapi.model.data.PlaceSearchHistory;
import com.junyoung.searchwheretogoapi.repository.PlaceSearchHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PlaceSearchHistoryService {
    private final PlaceSearchHistoryRepository placeSearchHistoryRepository;
    private final SearchCounter searchCounter;

    @Transactional
    public void save(PlaceSearchHistory placeSearchHistory) {
        searchCounter.count(placeSearchHistory.getQuery());
        placeSearchHistoryRepository.save(placeSearchHistory);
    }
}
