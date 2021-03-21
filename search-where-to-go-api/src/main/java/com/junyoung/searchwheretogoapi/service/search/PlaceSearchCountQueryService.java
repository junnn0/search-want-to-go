package com.junyoung.searchwheretogoapi.service.search;

import com.junyoung.searchwheretogoapi.model.data.SearchCount;
import com.junyoung.searchwheretogoapi.repository.PlaceSearchCountRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class PlaceSearchCountQueryService {
    private static final String ORDER_COLUMN = "count";
    private final PlaceSearchCountRepository placeSearchCountRepository;

    public List<SearchCount> getTopSearchedCounts() {
        return placeSearchCountRepository
                .findAll(PageRequest.of(0, 10, Sort.by(Sort.Order.desc(ORDER_COLUMN))))
                .getContent();
    }
}
