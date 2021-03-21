package com.junyoung.searchwheretogoapi.model.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(indexes = @Index(name = "idx_column_count", columnList = "count DESC"))
public class SearchCount {
    @Id private String query;

    @Column(nullable = false)
    private long count;

    public void addCount(long count) {
        this.count += count;
    }
}
