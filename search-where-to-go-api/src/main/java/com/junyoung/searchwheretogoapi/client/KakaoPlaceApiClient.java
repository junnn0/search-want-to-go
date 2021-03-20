package com.junyoung.searchwheretogoapi.client;

import com.junyoung.searchwheretogoapi.model.api.KakaoPlace;
import com.junyoung.searchwheretogoapi.model.api.KakaoSearchResponse;
import com.junyoung.searchwheretogoapi.properties.KakaoApiProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@RequiredArgsConstructor
@Component
public class KakaoPlaceApiClient implements PlaceApiClient {
    private static final String AUTH_HEADER_PREFIX = "KakaoAK ";

    private final KakaoApiProperties properties;
    private final RestTemplate restTemplate;

    @Override
    public KakaoSearchResponse<KakaoPlace> getPlaces(String query) {
        log.info("> getPlaces(query={})", query);
        String uri = properties.getBaseUrl()
                + UriComponentsBuilder.fromUriString(properties.getApi().get("getPlaces"))
                .buildAndExpand(query);

        return restTemplate.exchange(
                uri,
                HttpMethod.GET,
                new HttpEntity<>(makeAuthHeaders()),
                new ParameterizedTypeReference<KakaoSearchResponse<KakaoPlace>>() {})
                .getBody();
    }

    private HttpHeaders makeAuthHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, AUTH_HEADER_PREFIX + properties.getApiKey());
        return headers;
    }

}
