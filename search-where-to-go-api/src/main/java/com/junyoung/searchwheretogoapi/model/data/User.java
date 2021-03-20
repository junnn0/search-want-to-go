package com.junyoung.searchwheretogoapi.model.data;

import javax.persistence.Entity;
import javax.persistence.Id;

import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class User {
  @Id
  private String id;
  private String username;
  private String password;

  public User(String username, String password) {
    this.id = UUID.randomUUID().toString();
    this.username = username;
    this.password = password;
  }
}
