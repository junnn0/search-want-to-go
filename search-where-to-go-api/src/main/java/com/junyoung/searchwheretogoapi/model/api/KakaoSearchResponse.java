package com.junyoung.searchwheretogoapi.model.api;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class KakaoSearchResponse<T> implements Searchable {
    private List<T> documents;
    private Metadata meta;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    @Setter
    @NoArgsConstructor
    static class Metadata {
        private boolean end;
        private int pageableCount;
        private int totalCount;
    }
}
