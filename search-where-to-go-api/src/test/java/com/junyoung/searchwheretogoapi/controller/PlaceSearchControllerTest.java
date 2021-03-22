package com.junyoung.searchwheretogoapi.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.junyoung.searchwheretogoapi.service.place.search.PlaceSearchService;
import com.junyoung.searchwheretogoapi.util.TestDataUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
class PlaceSearchControllerTest {

    @Autowired private MockMvc mockMvc;

    @MockBean private PlaceSearchService placeSearchService;

    @BeforeEach
    void setUp() {
        given(placeSearchService.getPlaces(anyString()))
                .willReturn(TestDataUtil.createPlaceData(10));

        SecurityContextHolder.getContext().setAuthentication(TestDataUtil.createAuthToken());
    }

    @Test
    void test_search_places_success() throws Exception {
        mockMvc.perform(
                        get("/v1.0/places?query=%EA%B3%B1%EC%B0%BD")
                                .header(HttpHeaders.AUTHORIZATION, TestDataUtil.createToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.isSuccessful").value(true))
                .andExpect(jsonPath("$.header.resultMessage").value("SUCCESS"));
    }

    @Test
    void test_search_places_empty_query() throws Exception {
        mockMvc.perform(
                        get("/v1.0/places")
                                .header(HttpHeaders.AUTHORIZATION, TestDataUtil.createToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.isSuccessful").value(false))
                .andExpect(
                        jsonPath("$.header.resultMessage")
                                .value("Request 'query' is invalid in value with 'null'"));
    }
}
