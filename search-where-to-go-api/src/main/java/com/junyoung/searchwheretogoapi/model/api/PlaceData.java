package com.junyoung.searchwheretogoapi.model.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PlaceData {
    private String name;
    private String address;
    private String phone;
    private String link;
}
