package com.junyoung.searchwheretogoapi.model.data;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Table(indexes = @Index(name = "idx_user_username", columnList = "username"))
@Entity
public class User {
    @Id
    @Column(length = 50, nullable = false, unique = true)
    private String userId;

    @Column(length = 50, nullable = false)
    private String username;

    @Column(length = 100, nullable = false)
    private String password;

    public User(String username, String password) {
        this.userId = UUID.randomUUID().toString();
        this.username = username;
        this.password = password;
    }
}
