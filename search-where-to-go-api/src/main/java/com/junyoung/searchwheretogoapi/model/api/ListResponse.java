package com.junyoung.searchwheretogoapi.model.api;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ListResponse<T> {
    private List<T> data;
    private Integer totalCount;
}
