package com.junyoung.searchwheretogoapi.controller;

import com.junyoung.searchwheretogoapi.model.api.PlaceData;
import com.junyoung.searchwheretogoapi.model.api.QueryParam;
import com.junyoung.searchwheretogoapi.model.common.ApiResponse;
import com.junyoung.searchwheretogoapi.model.common.ResponseType;
import com.junyoung.searchwheretogoapi.model.data.PlaceSearchHistory;
import com.junyoung.searchwheretogoapi.model.data.User;
import com.junyoung.searchwheretogoapi.service.place.PlaceSearchHistoryService;
import com.junyoung.searchwheretogoapi.service.place.SearchCountService;
import com.junyoung.searchwheretogoapi.service.place.search.PlaceSearchService;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1.0")
public class PlaceSearchController {

    private final SearchCountService searchCountService;
    private final PlaceSearchHistoryService placeSearchHistoryService;
    private final PlaceSearchService placeSearchService;

    @GetMapping("/places")
    public CompletableFuture<ApiResponse<List<PlaceData>>> searchPlaces(
            @AuthenticationPrincipal User user, @Valid QueryParam queryParam) {
        searchCountService.count(queryParam.getQuery());
        return placeSearchService
                .getPlaces(queryParam.getQuery())
                .thenApply(
                        responseData -> {
                            placeSearchHistoryService.save(
                                    new PlaceSearchHistory(
                                            queryParam.getQuery(), user.getUserId()));
                            return ApiResponse.success(responseData);
                        })
                .exceptionally(
                        throwable -> {
                            log.info("> error: ", throwable);
                            return ApiResponse.fail(ResponseType.EXTERNAL_API_ERROR);
                        });
    }
}
