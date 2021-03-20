package com.junyoung.searchwheretogoapi.client;

import com.junyoung.searchwheretogoapi.model.api.NaverPlace;
import com.junyoung.searchwheretogoapi.model.api.NaverSearchResponse;
import com.junyoung.searchwheretogoapi.properties.NaverApiProperties;
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
public class NaverPlaceApiClient implements PlaceApiClient {
    private static final String AUTH_HEADER_KEY_ID = "X-Naver-Client-Id";
    private static final String AUTH_HEADER_KEY_SECRET = "X-Naver-Client-Secret";

    private final NaverApiProperties properties;
    private final RestTemplate restTemplate;

    @Override
    public NaverSearchResponse<NaverPlace> getPlaces(String query) {
        log.info("> getPlaces(query={})", query);
        String uri = properties.getBaseUrl()
                + UriComponentsBuilder.fromUriString(properties.getApi().get("getPlaces"))
                .buildAndExpand(query);

        return restTemplate.exchange(
                uri,
                HttpMethod.GET,
                new HttpEntity<>(makeAuthHeaders()),
                new ParameterizedTypeReference<NaverSearchResponse<NaverPlace>>() {})
                .getBody();
    }

    private HttpHeaders makeAuthHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(AUTH_HEADER_KEY_ID, properties.getClientId());
        headers.set(AUTH_HEADER_KEY_SECRET, properties.getClientSecret());
        return headers;
    }
}
