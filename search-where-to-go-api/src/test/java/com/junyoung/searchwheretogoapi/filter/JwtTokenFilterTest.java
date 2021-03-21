package com.junyoung.searchwheretogoapi.filter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import com.junyoung.searchwheretogoapi.model.data.User;
import com.junyoung.searchwheretogoapi.repository.UserRepository;
import com.junyoung.searchwheretogoapi.service.auth.JwtService;
import com.junyoung.searchwheretogoapi.util.TestDataUtil;
import java.io.IOException;
import java.util.Optional;
import javax.servlet.ServletException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

@SpringBootTest
class JwtTokenFilterTest {

    @MockBean private UserRepository userRepository;

    @MockBean private JwtService jwtService;

    @Autowired private JwtTokenFilter jwtTokenFilter;

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockFilterChain filterChain;

    @BeforeEach
    void setUp() {
        SecurityContextHolder.getContext().setAuthentication(null);

        given(jwtService.getUserIdFromToken(anyString()))
                .willReturn(Optional.of(TestDataUtil.createToken()));
        given(userRepository.findById(anyString()))
                .willReturn(Optional.of(TestDataUtil.createUser()));

        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        filterChain = new MockFilterChain();
    }

    @Test
    void test_authenticated_user_filtering() throws ServletException, IOException {
        request.addHeader(HttpHeaders.AUTHORIZATION, TestDataUtil.createToken());

        jwtTokenFilter.doFilterInternal(request, response, filterChain);

        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken)
                        SecurityContextHolder.getContext().getAuthentication();

        assertTrue(authenticationToken.getPrincipal() instanceof User);
    }

    @Test
    void test_unauthorized_user_empty_auth_header() throws ServletException, IOException {
        jwtTokenFilter.doFilterInternal(request, response, filterChain);

        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken)
                        SecurityContextHolder.getContext().getAuthentication();

        assertNull(authenticationToken);
    }

    @Test
    void test_unauthorized_user_wrong_token() throws ServletException, IOException {
        request.addHeader(HttpHeaders.AUTHORIZATION, "wrong_token");

        jwtTokenFilter.doFilterInternal(request, response, filterChain);

        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken)
                        SecurityContextHolder.getContext().getAuthentication();

        assertNull(authenticationToken);
    }
}
