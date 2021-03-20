package com.junyoung.searchwheretogoapi.controller;

import java.util.concurrent.CompletableFuture;

import com.junyoung.searchwheretogoapi.model.common.ApiResponse;
import com.junyoung.searchwheretogoapi.model.common.ResponseType;
import com.junyoung.searchwheretogoapi.service.search.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1.0")
public class SearchApiController {

    private final SearchService searchService;

    @GetMapping("/places")
    public CompletableFuture<?> searchPlaces(@RequestParam String query) {
        return searchService.getPlaces(query)
                .thenApply(ApiResponse::success)
                .exceptionally(throwable -> ApiResponse.fail(ResponseType.EXTERNAL_API_ERROR));
    }
}
