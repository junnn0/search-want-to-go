package com.junyoung.searchwheretogoapi.util;

import java.util.Arrays;
import java.util.List;

import com.junyoung.searchwheretogoapi.model.api.KakaoPlace;
import com.junyoung.searchwheretogoapi.model.api.NaverPlace;
import com.junyoung.searchwheretogoapi.model.api.Place;

public class TestDataUtil {
    public static List<Place> createPlaces() {
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

}
