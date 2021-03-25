package com.junyoung.searchwheretogoapi.model.api;

import javax.validation.constraints.NotBlank;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
public class QueryParam {
  @NotBlank(message = "should be exists.")
  private String query;

  public String getQuery() {
    return query.trim();
  }
}
