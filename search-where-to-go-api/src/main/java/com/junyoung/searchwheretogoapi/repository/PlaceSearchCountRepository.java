package com.junyoung.searchwheretogoapi.repository;

import com.junyoung.searchwheretogoapi.model.data.SearchCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceSearchCountRepository extends JpaRepository<SearchCount, String> {}
