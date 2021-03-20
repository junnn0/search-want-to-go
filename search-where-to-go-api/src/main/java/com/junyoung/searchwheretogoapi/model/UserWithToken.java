package com.junyoung.searchwheretogoapi.model;

import com.junyoung.searchwheretogoapi.model.data.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserWithToken {
    private String id;
    private String username;
    private String token;

    public UserWithToken(User user, String token) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.token = token;
    }
}
