package com.junyoung.searchwheretogoapi.model.api;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PageParam {
    private int pageNum;
    private int pageSize;
}
