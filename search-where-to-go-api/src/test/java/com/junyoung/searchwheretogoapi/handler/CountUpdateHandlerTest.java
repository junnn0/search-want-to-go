package com.junyoung.searchwheretogoapi.handler;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

import com.junyoung.searchwheretogoapi.repository.PlaceSearchCountRepository;
import com.junyoung.searchwheretogoapi.service.place.SearchCounter;
import com.junyoung.searchwheretogoapi.util.TestDataUtil;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class CountUpdateHandlerTest {

  @MockBean private SearchCounter searchCounter;

  @MockBean private PlaceSearchCountRepository placeSearchCountRepository;

  @Autowired private CountUpdateHandler countUpdateHandler;

  @Test
  void test_polling_success() {
    given(searchCounter.getSearchedCounts()).willReturn(TestDataUtil.createSearchCounts(3));
    given(placeSearchCountRepository.findById(anyString()))
        .willReturn(Optional.of(TestDataUtil.createSearchCount()));

    // when
    countUpdateHandler.poll();

    // then
    then(placeSearchCountRepository).should(times(3)).findById(anyString());
    then(placeSearchCountRepository).should(times(3)).save(any());
  }

  @Test
  void test_polling_nothing() {
    // given
    given(searchCounter.getSearchedCounts()).willReturn(Collections.emptyList());

    // when
    countUpdateHandler.poll();

    // then
    then(placeSearchCountRepository).should(never()).findById(anyString());
    then(placeSearchCountRepository).should(never()).save(any());
  }
}
