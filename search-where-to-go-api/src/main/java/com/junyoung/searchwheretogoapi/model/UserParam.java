package com.junyoung.searchwheretogoapi.model;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserParam {
    @NotBlank(message = "should be exists.")
    private String username;
    @NotBlank(message = "should be exists.")
    private String password;
}
