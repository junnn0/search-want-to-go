package com.junyoung.searchwheretogoapi.service.user;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import com.junyoung.searchwheretogoapi.model.UserParam;
import com.junyoung.searchwheretogoapi.model.data.User;
import com.junyoung.searchwheretogoapi.repository.UserRepository;
import com.junyoung.searchwheretogoapi.service.auth.EncryptService;
import com.junyoung.searchwheretogoapi.util.TestDataUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class UserServiceTest {

    @MockBean private EncryptService encryptService;

    @MockBean private UserRepository userRepository;

    @Autowired private UserService userService;

    @BeforeEach
    void setUp() {
        given(userRepository.findByUsername(anyString())).willReturn(null);
        given(encryptService.encryptPassword(anyString())).willReturn("encrypted_password");
        given(userRepository.save(any()))
                .willAnswer(invocationOnMock -> invocationOnMock.getArguments()[0]);
    }

    @Test
    void test_create_user_success() {
        UserParam userParam = new UserParam();
        String username = TestDataUtil.faker.name().fullName();
        userParam.setUsername(username);
        userParam.setPassword(TestDataUtil.createToken());

        User createdUser = userService.createUser(userParam);
        assertEquals(username, createdUser.getUsername());
    }
}
