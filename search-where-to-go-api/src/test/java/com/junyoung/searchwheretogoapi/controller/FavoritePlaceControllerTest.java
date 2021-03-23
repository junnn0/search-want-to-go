package com.junyoung.searchwheretogoapi.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.junyoung.searchwheretogoapi.service.place.search.PlaceSearchCountQueryService;
import com.junyoung.searchwheretogoapi.util.TestDataUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
class FavoritePlaceControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private PlaceSearchCountQueryService placeSearchCountQueryService;

  @BeforeEach
  void setUp() {
    given(placeSearchCountQueryService.getTopSearchedCounts())
        .willReturn(TestDataUtil.createSearchCounts(10));
  }

  @Test
  void test_search_favorite_place_success() throws Exception {
    mockMvc
        .perform(
            get("/v1.0/places/favorites")
                .header(HttpHeaders.AUTHORIZATION, TestDataUtil.createToken()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.header.isSuccessful").value(true))
        .andExpect(jsonPath("$.header.resultMessage").value("SUCCESS"))
        .andExpect(jsonPath("$.body").isArray());
  }
}
