package com.junyoung.searchwheretogoapi.controller;

import com.junyoung.searchwheretogoapi.model.api.PlaceData;
import com.junyoung.searchwheretogoapi.model.common.ApiResponse;
import com.junyoung.searchwheretogoapi.model.common.ResponseType;
import com.junyoung.searchwheretogoapi.model.data.User;
import com.junyoung.searchwheretogoapi.service.search.PlaceSearchService;
import com.junyoung.searchwheretogoapi.service.search.SearchCountService;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1.0")
public class PlaceSearchController {

    private final SearchCountService searchCountService;
    private final PlaceSearchService placeSearchService;

    @GetMapping("/places")
    public CompletableFuture<ApiResponse<List<PlaceData>>> searchPlaces(
            @AuthenticationPrincipal User user, @RequestParam String query) {

        searchCountService.count(query);
        return placeSearchService
                .getPlaces(user, query)
                .thenApply(ApiResponse::success)
                .exceptionally(throwable -> ApiResponse.fail(ResponseType.EXTERNAL_API_ERROR));
    }
}
