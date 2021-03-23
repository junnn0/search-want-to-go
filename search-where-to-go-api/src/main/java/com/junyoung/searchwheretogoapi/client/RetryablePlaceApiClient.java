package com.junyoung.searchwheretogoapi.client;

import com.junyoung.searchwheretogoapi.model.api.Place;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Recover;

@Slf4j
public abstract class RetryablePlaceApiClient implements PlaceApiClient {
  protected static final String FALLBACK_METHOD_NAME = "getPlacesFallback";

  @Recover
  protected List<? extends Place> getPlacesFallback(Exception ex, String query) {
    log.warn(">> Request fallback executed. query={}", query, ex);
    return Collections.emptyList();
  }
}
