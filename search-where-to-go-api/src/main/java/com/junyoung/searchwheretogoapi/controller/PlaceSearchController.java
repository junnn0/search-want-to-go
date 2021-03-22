package com.junyoung.searchwheretogoapi.controller;

import com.junyoung.searchwheretogoapi.model.api.PlaceData;
import com.junyoung.searchwheretogoapi.model.api.QueryParam;
import com.junyoung.searchwheretogoapi.model.common.ApiResponse;
import com.junyoung.searchwheretogoapi.model.data.PlaceSearchHistory;
import com.junyoung.searchwheretogoapi.model.data.User;
import com.junyoung.searchwheretogoapi.service.place.PlaceSearchHistoryService;
import com.junyoung.searchwheretogoapi.service.place.search.PlaceSearchService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1.0")
public class PlaceSearchController {

    private final PlaceSearchService placeSearchService;
    private final PlaceSearchHistoryService placeSearchHistoryService;

    @GetMapping("/places")
    public ApiResponse<List<PlaceData>> searchPlaces(
            @AuthenticationPrincipal User user, @Valid QueryParam queryParam) {
        List<PlaceData> placeData = placeSearchService.getPlaces(queryParam.getQuery());
        placeSearchHistoryService.save(
                new PlaceSearchHistory(queryParam.getQuery(), user.getUserId()));
        return ApiResponse.success(placeData);
    }
}
