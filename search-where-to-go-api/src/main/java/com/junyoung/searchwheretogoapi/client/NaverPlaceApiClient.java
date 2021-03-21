package com.junyoung.searchwheretogoapi.client;

import com.junyoung.searchwheretogoapi.exception.ExternalApiException;
import com.junyoung.searchwheretogoapi.model.api.NaverPlace;
import com.junyoung.searchwheretogoapi.model.api.NaverSearchResponse;
import com.junyoung.searchwheretogoapi.model.api.Place;
import com.junyoung.searchwheretogoapi.model.common.ResponseType;
import com.junyoung.searchwheretogoapi.properties.NaverApiProperties;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "openapi.naver", name = "base-url")
@Component
public class NaverPlaceApiClient implements PlaceApiClient {
    private static final String AUTH_HEADER_KEY_ID = "X-Naver-Client-Id";
    private static final String AUTH_HEADER_KEY_SECRET = "X-Naver-Client-Secret";

    private final NaverApiProperties properties;
    private final RestTemplate restTemplate;

    @Async
    @Override
    public CompletableFuture<List<? extends Place>> getPlaces(String query) {
        log.debug("> getPlaces(query={})", query);

        String uri =
                properties.getBaseUrl()
                        + UriComponentsBuilder.fromUriString(properties.getApi().get("getPlaces"))
                                .buildAndExpand(query);

        try {
            NaverSearchResponse<NaverPlace> response =
                    restTemplate
                            .exchange(
                                    uri,
                                    HttpMethod.GET,
                                    new HttpEntity<>(makeAuthHeaders()),
                                    new ParameterizedTypeReference<
                                            NaverSearchResponse<NaverPlace>>() {})
                            .getBody();
            if (response != null) {
                return CompletableFuture.completedFuture(response.get());
            } else {
                return CompletableFuture.completedFuture(Collections.emptyList());
            }
        } catch (Exception ex) {
            throw new ExternalApiException(ResponseType.EXTERNAL_API_ERROR);
        }
    }

    private HttpHeaders makeAuthHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(AUTH_HEADER_KEY_ID, properties.getClientId());
        headers.set(AUTH_HEADER_KEY_SECRET, properties.getClientSecret());
        return headers;
    }
}
