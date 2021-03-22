package com.junyoung.searchwheretogoapi.model.api;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PlaceData implements Serializable {
    private String name;
    private String address;
    private String phone;
    private String link;
}
