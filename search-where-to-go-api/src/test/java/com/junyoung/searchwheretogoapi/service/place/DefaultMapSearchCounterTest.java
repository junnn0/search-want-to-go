package com.junyoung.searchwheretogoapi.service.place;

import static org.junit.jupiter.api.Assertions.*;

import com.junyoung.searchwheretogoapi.model.data.SearchCount;
import com.junyoung.searchwheretogoapi.util.TestDataUtil;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;

@SpringBootTest
class DefaultMapSearchCounterTest {

  @Autowired private DefaultMapSearchCounter defaultMapSearchCounter;

  @BeforeEach
  void setUp() {
    defaultMapSearchCounter.clearCounter();
  }

  @Test
  void test_count_correct_unique_query() {
    defaultMapSearchCounter.count(TestDataUtil.faker.name().fullName());
    defaultMapSearchCounter.count(TestDataUtil.faker.name().fullName());
    defaultMapSearchCounter.count(TestDataUtil.faker.name().fullName());

    List<SearchCount> searchCounts = defaultMapSearchCounter.getSearchedCounts();
    assertFalse(CollectionUtils.isEmpty(searchCounts));
    assertEquals(3, searchCounts.size());
  }

  @Test
  void test_count_correct_duplicated_query() {
    defaultMapSearchCounter.count("a");
    defaultMapSearchCounter.count("a");
    defaultMapSearchCounter.count("a");

    List<SearchCount> searchCounts = defaultMapSearchCounter.getSearchedCounts();
    assertFalse(CollectionUtils.isEmpty(searchCounts));
    assertEquals(1, searchCounts.size());
  }

  @Test
  void test_count_clear_success() {
    defaultMapSearchCounter.count(TestDataUtil.faker.name().fullName());
    defaultMapSearchCounter.count(TestDataUtil.faker.name().fullName());
    defaultMapSearchCounter.count(TestDataUtil.faker.name().fullName());
    defaultMapSearchCounter.clearCounter();

    List<SearchCount> searchCounts = defaultMapSearchCounter.getSearchedCounts();
    assertTrue(CollectionUtils.isEmpty(searchCounts));
  }

  @Test
  void test_deep_copy() {
    Map<String, Long> counterMapSnapshot = defaultMapSearchCounter.getCounterMapSnapshot();
    defaultMapSearchCounter.count(TestDataUtil.faker.name().fullName());
    defaultMapSearchCounter.count(TestDataUtil.faker.name().fullName());
    defaultMapSearchCounter.count(TestDataUtil.faker.name().fullName());

    assertTrue(counterMapSnapshot.isEmpty());
  }
}
