package com.junyoung.searchwheretogoapi.model.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
public class NaverPlace implements Place {
  private String title;
  private String link;
  private String category;
  private String description;
  private String telephone;
  private String address;
  private String roadAddress;

  @Override
  public PlaceData toPlaceData() {
    return new PlaceData(title, roadAddress, telephone, link);
  }
}
