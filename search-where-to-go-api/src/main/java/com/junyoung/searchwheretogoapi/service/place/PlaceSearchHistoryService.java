package com.junyoung.searchwheretogoapi.service.place;

import com.junyoung.searchwheretogoapi.model.data.PlaceSearchHistory;
import com.junyoung.searchwheretogoapi.repository.PlaceSearchHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PlaceSearchHistoryService {
    private final PlaceSearchHistoryRepository placeSearchHistoryRepository;

    public void save(PlaceSearchHistory placeSearchHistory) {
        placeSearchHistoryRepository.save(placeSearchHistory);
    }
}
