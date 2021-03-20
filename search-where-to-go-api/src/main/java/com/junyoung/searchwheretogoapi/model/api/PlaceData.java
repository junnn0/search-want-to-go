package com.junyoung.searchwheretogoapi.model.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PlaceData{
    private String name;
    private String address;
    private String phone;
    private String link;
}