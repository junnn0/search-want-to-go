package com.junyoung.searchwheretogoapi.repository;

import com.junyoung.searchwheretogoapi.model.data.PlaceSearchHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceSearchHistoryRepository extends JpaRepository<PlaceSearchHistory, Integer> {}
