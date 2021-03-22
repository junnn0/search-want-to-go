package com.junyoung.searchwheretogoapi.service.place;

import com.junyoung.searchwheretogoapi.model.data.SearchCount;
import java.util.List;
import java.util.Map;

public interface SearchCounter {
    void count(String query);

    List<SearchCount> getSearchedCounts();

    Map<String, Long> getCounterMapSnapshot();

    void clearCounter();
}
