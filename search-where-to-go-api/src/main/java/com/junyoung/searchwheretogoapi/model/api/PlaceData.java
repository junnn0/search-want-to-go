package com.junyoung.searchwheretogoapi.model.api;

import com.junyoung.searchwheretogoapi.constants.SourceType;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PlaceData implements Serializable, Comparable<PlaceData> {
  private String name;
  private String address;
  private String phone;
  private String link;
  private SourceType sourceType;

  @Override
  public int compareTo(PlaceData o) {
    return this.sourceType.getOrder().compareTo(o.getSourceType().getOrder());
  }
}
