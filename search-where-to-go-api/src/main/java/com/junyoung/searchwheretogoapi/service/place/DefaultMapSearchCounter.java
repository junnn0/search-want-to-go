package com.junyoung.searchwheretogoapi.service.place;

import com.fasterxml.jackson.core.type.TypeReference;
import com.junyoung.searchwheretogoapi.model.data.SearchCount;
import com.junyoung.searchwheretogoapi.util.JsonUtil;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class DefaultMapSearchCounter implements SearchCounter {
    private static final Long FIRST_INPUT_COUNT = 1L;
    private final Map<String, Long> counterMap = new ConcurrentHashMap<>();

    @Override
    public void count(String query) {
        String trimmedQuery = query.trim();
        if (counterMap.containsKey(trimmedQuery)) {
            counterMap.put(trimmedQuery, this.counterMap.get(trimmedQuery) + 1);
        } else {
            counterMap.put(trimmedQuery, FIRST_INPUT_COUNT);
        }
    }

    @Override
    public List<SearchCount> getSearchedCounts() {
        return counterMap.entrySet().stream()
                .map(entry -> new SearchCount(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, Long> getCounterMapSnapshot() {
        return JsonUtil.convertAs(counterMap, new TypeReference<Map<String, Long>>() {});
    }

    @Override
    public void clearCounter() {
        counterMap.clear();
    }
}
