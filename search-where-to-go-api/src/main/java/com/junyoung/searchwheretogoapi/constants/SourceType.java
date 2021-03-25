package com.junyoung.searchwheretogoapi.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SourceType {
  KAKAO(1),
  NAVER(2);

  private final Integer order;
}
