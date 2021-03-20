package com.junyoung.searchwheretogoapi.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class NamingUtilTest {

    @Test
    void eraseBoldTags() {
        assertEquals("NH농협은행 광화문금융센터", NamingUtil.eraseBoldTags("NH농협<b>은행</b> 광화문금융센터"));
        assertEquals("신한은행 서울광장출장소", NamingUtil.eraseBoldTags("신한<b>은행</b> 서울광장출장소"));
    }
}
