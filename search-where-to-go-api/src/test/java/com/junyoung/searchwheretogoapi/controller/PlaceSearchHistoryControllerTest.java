package com.junyoung.searchwheretogoapi.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.junyoung.searchwheretogoapi.model.common.ResponseType;
import com.junyoung.searchwheretogoapi.service.place.search.PlaceSearchHistoryQueryService;
import com.junyoung.searchwheretogoapi.util.TestDataUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
class PlaceSearchHistoryControllerTest {

    @Autowired private MockMvc mockMvc;

    @MockBean private PlaceSearchHistoryQueryService placeSearchHistoryQueryService;

    @BeforeEach
    void setUp() {
        given(placeSearchHistoryQueryService.getPlaceSearchHistories(any(), any()))
                .willReturn(TestDataUtil.createPlaceSearchHistories(10));
        SecurityContextHolder.getContext().setAuthentication(TestDataUtil.createAuthToken());
    }

    @Test
    void test_get_place_search_histories_success() throws Exception {
        mockMvc.perform(get("/v1.0/places/histories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.isSuccessful").value(true))
                .andExpect(jsonPath("$.header.resultMessage").value("SUCCESS"))
                .andExpect(jsonPath("$.body").isArray());
    }

    @Test
    void test_get_place_search_histories_empty_user() throws Exception {
        SecurityContextHolder.getContext().setAuthentication(null);

        mockMvc.perform(get("/v1.0/places/histories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.isSuccessful").value(false))
                .andExpect(
                        jsonPath("$.header.resultMessage")
                                .value(ResponseType.NOT_LOGIN_USER.getMessage()));
    }
}
