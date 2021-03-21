package com.junyoung.searchwheretogoapi.service.place;

import com.junyoung.searchwheretogoapi.model.data.SearchCount;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SearchCountService {
    private static final int FIRST_INPUT_COUNT = 1;
    private final Map<String, Integer> counterMap = new ConcurrentHashMap<>();

    public List<SearchCount> getSearchedCounts() {
        return counterMap.entrySet().stream()
                .map(entry -> new SearchCount(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    public void count(String query) {
        String trimmedQuery = query.trim();
        if (this.counterMap.containsKey(trimmedQuery)) {
            this.counterMap.put(trimmedQuery, this.counterMap.get(trimmedQuery) + 1);
        } else {
            this.counterMap.put(trimmedQuery, FIRST_INPUT_COUNT);
        }
    }

    public void clearCounter() {
        this.counterMap.clear();
    }
}
