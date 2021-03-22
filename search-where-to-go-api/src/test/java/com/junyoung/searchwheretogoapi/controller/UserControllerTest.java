package com.junyoung.searchwheretogoapi.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.junyoung.searchwheretogoapi.model.api.UserParam;
import com.junyoung.searchwheretogoapi.model.common.ResponseType;
import com.junyoung.searchwheretogoapi.service.auth.JwtService;
import com.junyoung.searchwheretogoapi.service.user.UserService;
import com.junyoung.searchwheretogoapi.util.TestDataUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
class UserControllerTest {

    @Autowired private MockMvc mockMvc;

    @MockBean private UserService userService;

    @MockBean private JwtService jwtService;

    @BeforeEach
    void setUp() {
        SecurityContextHolder.getContext().setAuthentication(TestDataUtil.createAuthToken());
        given(userService.findByUsername(anyString())).willReturn(TestDataUtil.createUser());
        given(userService.createUser(any())).willReturn(TestDataUtil.createUser());
        given(userService.isCorrectPassword(anyString(), any())).willReturn(true);
        given(jwtService.toToken(any())).willReturn(TestDataUtil.createToken());
    }

    @Test
    void test_get_user_success() throws Exception {
        mockMvc.perform(get("/user").header(HttpHeaders.AUTHORIZATION, TestDataUtil.createToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.isSuccessful").value(true))
                .andExpect(jsonPath("$.body").isNotEmpty())
                .andExpect(jsonPath("$.body.userId").isNotEmpty())
                .andExpect(jsonPath("$.body.username").isNotEmpty())
                .andExpect(jsonPath("$.body.token").isNotEmpty());
    }

    @Test
    void test_get_user_with_empty_user() throws Exception {
        SecurityContextHolder.getContext().setAuthentication(null);
        mockMvc.perform(get("/user").header(HttpHeaders.AUTHORIZATION, TestDataUtil.createToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.isSuccessful").value(false))
                .andExpect(
                        jsonPath("$.header.resultCode")
                                .value(ResponseType.NOT_LOGIN_USER.getCode()))
                .andExpect(
                        jsonPath("$.header.resultMessage")
                                .value(ResponseType.NOT_LOGIN_USER.getMessage()))
                .andExpect(jsonPath("$.body").isEmpty());
    }

    @Test
    void test_create_user_success() throws Exception {
        UserParam userParam = new UserParam();
        userParam.setUsername(TestDataUtil.faker.name().name());
        userParam.setPassword(TestDataUtil.faker.random().hex(30));

        mockMvc.perform(
                        post("/users")
                                .header(HttpHeaders.AUTHORIZATION, TestDataUtil.createToken())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestDataUtil.convertAsString(userParam)))
                .andExpect(jsonPath("$.header.isSuccessful").value(true))
                .andExpect(jsonPath("$.body").isNotEmpty());
    }

    @Test
    void test_create_user_empty_user_param() throws Exception {
        mockMvc.perform(
                        post("/users")
                                .header(HttpHeaders.AUTHORIZATION, TestDataUtil.createToken())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.header.isSuccessful").value(false))
                .andExpect(
                        jsonPath("$.header.resultCode")
                                .value(ResponseType.INVALID_PARAMETER.getCode()));
    }

    @Test
    void test_login_user_success() throws Exception {
        UserParam userParam = new UserParam();
        userParam.setUsername(TestDataUtil.faker.name().name());
        userParam.setPassword(TestDataUtil.faker.random().hex(30));

        mockMvc.perform(
                        post("/users/login")
                                .header(HttpHeaders.AUTHORIZATION, TestDataUtil.createToken())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestDataUtil.convertAsString(userParam)))
                .andExpect(jsonPath("$.header.isSuccessful").value(true))
                .andExpect(jsonPath("$.body").isNotEmpty());
    }

    @Test
    void test_login_user_empty_password() throws Exception {
        UserParam emptyPasswordUserParam = new UserParam();
        emptyPasswordUserParam.setUsername(TestDataUtil.faker.name().name());

        mockMvc.perform(
                        post("/users/login")
                                .header(HttpHeaders.AUTHORIZATION, TestDataUtil.createToken())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestDataUtil.convertAsString(emptyPasswordUserParam)))
                .andExpect(jsonPath("$.header.isSuccessful").value(false))
                .andExpect(
                        jsonPath("$.header.resultCode")
                                .value(ResponseType.INVALID_PARAMETER.getCode()));
    }

    @Test
    void test_login_user_incorrect_password() throws Exception {
        UserParam incorrectPasswordUserParam = new UserParam();
        incorrectPasswordUserParam.setUsername(TestDataUtil.faker.name().name());
        incorrectPasswordUserParam.setPassword(TestDataUtil.faker.name().name());

        given(userService.isCorrectPassword(anyString(), any())).willReturn(false);

        mockMvc.perform(
                        post("/users/login")
                                .header(HttpHeaders.AUTHORIZATION, TestDataUtil.createToken())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestDataUtil.convertAsString(incorrectPasswordUserParam)))
                .andExpect(jsonPath("$.header.isSuccessful").value(false))
                .andExpect(
                        jsonPath("$.header.resultCode")
                                .value(ResponseType.NOT_EXISTS_USER.getCode()));
    }
}
