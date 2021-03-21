package com.junyoung.searchwheretogoapi.repository;

import com.junyoung.searchwheretogoapi.model.data.PlaceSearchHistory;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceSearchHistoryQueryRepository
        extends PagingAndSortingRepository<PlaceSearchHistory, Integer> {
    List<PlaceSearchHistory> findByUsernameOrderByIdDesc(String username, Pageable pageable);
}
