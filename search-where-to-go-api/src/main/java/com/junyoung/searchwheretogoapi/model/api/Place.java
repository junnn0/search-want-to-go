package com.junyoung.searchwheretogoapi.model.api;

import java.io.Serializable;

public interface Place extends Serializable {
    PlaceData toPlaceData();
}
