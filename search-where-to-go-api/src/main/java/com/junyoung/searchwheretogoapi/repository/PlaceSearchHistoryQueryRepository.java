package com.junyoung.searchwheretogoapi.repository;

import com.junyoung.searchwheretogoapi.model.data.PlaceSearchHistory;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceSearchHistoryQueryRepository
    extends JpaRepository<PlaceSearchHistory, String> {
  List<PlaceSearchHistory> findByUserIdOrderByHistoryIdDesc(String userId, Pageable pageable);
}
