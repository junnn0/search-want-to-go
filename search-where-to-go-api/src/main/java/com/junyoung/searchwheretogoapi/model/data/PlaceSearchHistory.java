package com.junyoung.searchwheretogoapi.model.data;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Table(indexes = @Index(name = "idx_place_search_history_username", columnList = "username"))
@Entity
public class PlaceSearchHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(length = 300, nullable = false)
    private String query;
    @Column(length = 50, nullable = false)
    private String username;
    @Column(columnDefinition = "DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createDatetime;

    public PlaceSearchHistory(String query, String username) {
        this.query = query;
        this.username = username;
        this.createDatetime = LocalDateTime.now();
    }
}
