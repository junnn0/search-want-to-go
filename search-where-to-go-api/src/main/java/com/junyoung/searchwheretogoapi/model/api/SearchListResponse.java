package com.junyoung.searchwheretogoapi.model.api;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Setter
@Getter
@NoArgsConstructor
public class SearchListResponse<T> implements Searchable<T> {
    @JsonAlias({"documents", "items"})
    private List<T> data;

    @Override
    public List<T> get() {
        return data;
    }
}
