package com.junyoung.searchwheretogoapi.model.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.junyoung.searchwheretogoapi.constants.SourceType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KakaoPlace implements Place {
  @JsonProperty("address_name")
  private String addressName;

  @JsonProperty("category_name")
  private String categoryName;

  private String id;

  private String phone;

  @JsonProperty("place_name")
  private String placeName;

  @JsonProperty("place_url")
  private String placeUrl;

  @JsonProperty("road_address_name")
  private String roadAddressName;

  @Override
  public PlaceData toPlaceData() {
    return new PlaceData(placeName, roadAddressName, phone, placeUrl, SourceType.KAKAO);
  }
}
