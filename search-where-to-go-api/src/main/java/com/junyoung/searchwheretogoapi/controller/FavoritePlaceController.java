package com.junyoung.searchwheretogoapi.controller;

import com.junyoung.searchwheretogoapi.model.common.ApiResponse;
import com.junyoung.searchwheretogoapi.model.data.SearchCount;
import com.junyoung.searchwheretogoapi.service.place.search.PlaceSearchCountQueryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1.0")
public class FavoritePlaceController {

    private final PlaceSearchCountQueryService placeSearchCountQueryService;

    @GetMapping("/places/favorites")
    public ApiResponse<List<SearchCount>> searchFavoritePlaces() {
        log.debug("> searchFavoritePlaces");
        return ApiResponse.success(placeSearchCountQueryService.getTopSearchedCounts());
    }
}
