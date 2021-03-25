package com.junyoung.searchwheretogoapi.controller;

import com.junyoung.searchwheretogoapi.exception.UserAuthenticationException;
import com.junyoung.searchwheretogoapi.model.api.ListResponse;
import com.junyoung.searchwheretogoapi.model.api.PageParam;
import com.junyoung.searchwheretogoapi.model.common.ApiResponse;
import com.junyoung.searchwheretogoapi.model.common.ResponseType;
import com.junyoung.searchwheretogoapi.model.data.PlaceSearchHistory;
import com.junyoung.searchwheretogoapi.model.data.User;
import com.junyoung.searchwheretogoapi.service.place.search.PlaceSearchHistoryQueryService;
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
public class PlaceSearchHistoryController {
  private final PlaceSearchHistoryQueryService placeSearchHistoryQueryService;

  @GetMapping("/places/histories")
  public ApiResponse<ListResponse<PlaceSearchHistory>> getPlaceSearchHistories(
      @AuthenticationPrincipal User user, @Valid PageParam pageParam) {
    log.debug("> getPlaceSearchHistories(user={}, pageParam={})", user, pageParam);
    if (user == null) {
      throw new UserAuthenticationException(ResponseType.NOT_LOGIN_USER);
    }
    return ApiResponse.success(
        placeSearchHistoryQueryService.getPlaceSearchHistories(user, pageParam));
  }
}
