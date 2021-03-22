package com.junyoung.searchwheretogoapi.client;

import com.junyoung.searchwheretogoapi.exception.ExternalApiException;
import com.junyoung.searchwheretogoapi.model.api.KakaoPlace;
import com.junyoung.searchwheretogoapi.model.api.Place;
import com.junyoung.searchwheretogoapi.model.api.SearchListResponse;
import com.junyoung.searchwheretogoapi.model.common.ResponseType;
import com.junyoung.searchwheretogoapi.properties.KakaoApiProperties;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "openapi.kakao", name = "base-url")
@Component
public class KakaoPlaceApiClient implements PlaceApiClient {
    private static final String AUTH_HEADER_PREFIX = "KakaoAK ";

    private final KakaoApiProperties properties;
    private final RestTemplate restTemplate;

    @Cacheable("kakaoPlaces")
    @Override
    public List<? extends Place> getPlaces(String query) {
        log.debug("> getPlaces(query={})", query);

        String uri =
                properties.getBaseUrl()
                        + UriComponentsBuilder.fromUriString(properties.getApi().get("getPlaces"))
                                .buildAndExpand(query);

        try {
            SearchListResponse<KakaoPlace> response =
                    restTemplate
                            .exchange(
                                    uri,
                                    HttpMethod.GET,
                                    new HttpEntity<>(makeAuthHeaders()),
                                    new ParameterizedTypeReference<
                                            SearchListResponse<KakaoPlace>>() {})
                            .getBody();
            if (response != null) {
                return response.get();
            } else {
                return Collections.emptyList();
            }
        } catch (Exception ex) {
            throw new ExternalApiException(ResponseType.EXTERNAL_API_ERROR);
        }
    }

    private HttpHeaders makeAuthHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, AUTH_HEADER_PREFIX + properties.getApiKey());
        return headers;
    }
}
