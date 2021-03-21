package com.junyoung.searchwheretogoapi.service.auth;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EncryptServiceTest {

    @Autowired private EncryptService encryptService;

    @Test
    void test_check_password_work() {
        String encryptedPassword = encryptService.encryptPassword("password");
        assertTrue(encryptService.checkPassword(encryptedPassword, "password"));
        assertFalse(encryptService.checkPassword(encryptedPassword, "wrong password"));
    }
}
