package com.junyoung.searchwheretogoapi.model.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class KakaoSearchResponse<T> implements Searchable<T> {
    private List<T> documents;
    private Metadata meta;

    @Override
    public List<T> get() {
        return documents;
    }

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
