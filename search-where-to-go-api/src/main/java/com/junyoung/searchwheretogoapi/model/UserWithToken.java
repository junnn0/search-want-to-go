package com.junyoung.searchwheretogoapi.model;

import com.junyoung.searchwheretogoapi.model.data.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserWithToken {
    private String userId;
    private String username;
    private String token;

    public UserWithToken(User user, String token) {
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.token = token;
    }
}
