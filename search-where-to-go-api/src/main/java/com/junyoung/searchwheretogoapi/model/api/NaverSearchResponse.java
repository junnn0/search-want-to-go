package com.junyoung.searchwheretogoapi.model.api;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class NaverSearchResponse<T> implements Searchable<T> {
    private String lastBuildDate;
    private int total;
    private int start;
    private int display;
    private List<T> items;

    @Override
    public List<T> get() {
        return items;
    }
}
