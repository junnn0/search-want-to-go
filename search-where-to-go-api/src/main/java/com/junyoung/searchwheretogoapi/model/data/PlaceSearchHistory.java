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
@Table(indexes = @Index(name = "idx_place_search_history_user_id", columnList = "userId"))
@Entity
public class PlaceSearchHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int historyId;

    @Column(length = 300, nullable = false)
    private String query;

    @Column(length = 50, nullable = false)
    private String userId;

    @Column(columnDefinition = "DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createDatetime;

    public PlaceSearchHistory(String query, String userId) {
        this.query = query;
        this.userId = userId;
        this.createDatetime = LocalDateTime.now();
    }
}
