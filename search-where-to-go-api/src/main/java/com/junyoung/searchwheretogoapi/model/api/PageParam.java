package com.junyoung.searchwheretogoapi.model.api;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PageParam {
    @PositiveOrZero private int pageNum = 0;
    @Positive private int pageSize = 10;
}
