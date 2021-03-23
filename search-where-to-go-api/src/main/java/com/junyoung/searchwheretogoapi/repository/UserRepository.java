package com.junyoung.searchwheretogoapi.repository;

import com.junyoung.searchwheretogoapi.model.data.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
  User findByUsername(String username);
}
