package com.junyoung.searchwheretogoapi.client;

import com.junyoung.searchwheretogoapi.exception.ExternalApiException;
import com.junyoung.searchwheretogoapi.model.api.NaverPlace;
import com.junyoung.searchwheretogoapi.model.api.Place;
import com.junyoung.searchwheretogoapi.model.api.SearchListResponse;
import com.junyoung.searchwheretogoapi.properties.NaverApiProperties;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "openapi.naver", name = "base-url")
@Component
public class NaverPlaceApiClient extends RetryablePlaceApiClient {
    private static final String AUTH_HEADER_KEY_ID = "X-Naver-Client-Id";
    private static final String AUTH_HEADER_KEY_SECRET = "X-Naver-Client-Secret";

    private final NaverApiProperties properties;
    private final RestTemplate restTemplate;

    @Override
    @Retryable(
            value = ExternalApiException.class,
            recover = FALLBACK_METHOD_NAME,
            backoff = @Backoff(delay = 2000L))
    public List<? extends Place> getPlaces(String query) {
        log.debug("> getPlaces(query={})", query);

        String uri =
                properties.getBaseUrl()
                        + UriComponentsBuilder.fromUriString(properties.getApi().get("places"))
                                .buildAndExpand(query);

        SearchListResponse<NaverPlace> response;
        try {
            response =
                    restTemplate
                            .exchange(
                                    uri,
                                    HttpMethod.GET,
                                    new HttpEntity<>(makeAuthHeaders()),
                                    new ParameterizedTypeReference<
                                            SearchListResponse<NaverPlace>>() {})
                            .getBody();
        } catch (Exception ex) {
            throw new ExternalApiException(ex);
        }

        if (response != null) {
            return response.get();
        } else {
            log.warn("> external api(Naver) returns empty response.");
            return Collections.emptyList();
        }
    }

    private HttpHeaders makeAuthHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(AUTH_HEADER_KEY_ID, properties.getClientId());
        headers.set(AUTH_HEADER_KEY_SECRET, properties.getClientSecret());
        return headers;
    }
}
