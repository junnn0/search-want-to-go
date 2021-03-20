package com.junyoung.searchwheretogoapi.model.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponseHeader {
  private Boolean isSuccessful;
  private int resultCode;
  private String resultMessage;
}
