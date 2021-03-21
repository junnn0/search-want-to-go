package com.junyoung.searchwheretogoapi.model.data;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class PlaceSearchHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String query;
    private String username;
    private LocalDateTime createDateTime;

    public PlaceSearchHistory(String query, String username) {
        this.query = query;
        this.username = username;
        this.createDateTime = LocalDateTime.now();
    }
}
