package com.junyoung.searchwheretogoapi.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.junyoung.searchwheretogoapi.model.api.KakaoPlace;
import com.junyoung.searchwheretogoapi.model.api.NaverPlace;
import com.junyoung.searchwheretogoapi.model.api.Place;
import com.junyoung.searchwheretogoapi.model.api.PlaceData;
import com.junyoung.searchwheretogoapi.model.api.SearchListResponse;
import com.junyoung.searchwheretogoapi.model.data.PlaceSearchHistory;
import com.junyoung.searchwheretogoapi.model.data.SearchCount;
import com.junyoung.searchwheretogoapi.model.data.User;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class TestDataUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    public static final Faker faker = new Faker();

    public static String convertAsString(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            return StringUtils.EMPTY;
        }
    }

    public static List<? extends Place> createPlaces() {
        return Arrays.asList(
                createKakaoPlace("dup1"),
                createKakaoPlace("unique1"),
                createKakaoPlace("dup3"),
                createKakaoPlace("dup5"),
                createKakaoPlace("unique2"),
                createKakaoPlace("unique3"),
                createNaverPlace("dup1"),
                createNaverPlace("dup3"),
                createNaverPlace("unique4"),
                createNaverPlace("unique5"),
                createNaverPlace("dup5"));
    }

    public static Place createKakaoPlace(String name) {
        KakaoPlace kakaoPlace = new KakaoPlace();
        kakaoPlace.setPlaceName(name);
        return kakaoPlace;
    }

    public static Place createNaverPlace(String name) {
        NaverPlace naverPlace = new NaverPlace();
        naverPlace.setTitle(name);
        return naverPlace;
    }

    public static SearchListResponse<KakaoPlace> createKakaoPlaceResponse() {
        SearchListResponse<KakaoPlace> response = new SearchListResponse<>();
        response.setData(createKakaoPlaces(10));
        return response;
    }

    public static List<KakaoPlace> createKakaoPlaces(int size) {
        return createList(size, TestDataUtil::createKakaoPlace);
    }

    public static KakaoPlace createKakaoPlace() {
        return new KakaoPlace(
                faker.address().fullAddress(),
                faker.random().hex(10),
                faker.random().hex(4),
                faker.phoneNumber().cellPhone(),
                faker.commerce().productName(),
                faker.internet().url(),
                faker.address().secondaryAddress());
    }

    public static List<SearchCount> createSearchCounts(int size) {
        return createList(size, TestDataUtil::createSearchCount);
    }

    public static SearchCount createSearchCount() {
        return new SearchCount(faker.name().fullName(), faker.number().numberBetween(0, 500));
    }

    public static List<PlaceData> createPlaceData(int size) {
        return createList(size, TestDataUtil::createPlaceData);
    }

    private static PlaceData createPlaceData() {
        return new PlaceData(
                faker.name().name(),
                faker.address().fullAddress(),
                faker.phoneNumber().cellPhone(),
                faker.internet().url());
    }

    private static <T> List<T> createList(int size, Supplier<T> elementSupplier) {
        List<T> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(elementSupplier.get());
        }
        return list;
    }

    public static UsernamePasswordAuthenticationToken createAuthToken() {
        return new UsernamePasswordAuthenticationToken(
                new User(faker.name().name(), faker.random().hex(16)),
                null,
                Collections.emptyList());
    }

    public static User createUser() {
        return new User(faker.name().name(), faker.random().hex(30));
    }

    public static String createToken() {
        return String.format("%s %s", faker.random().hex(10), faker.random().hex(50));
    }

    public static List<PlaceSearchHistory> createPlaceSearchHistories(int size) {
        return createList(size, TestDataUtil::createPlaceSearchHistory);
    }

    private static PlaceSearchHistory createPlaceSearchHistory() {
        return new PlaceSearchHistory(faker.name().fullName(), createToken());
    }
}
