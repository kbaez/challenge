package com.kbaez.challange.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ConsUtil {

  public static final String BEGIN_METHOD = "inside method";
  public static final String END_METHOD = "end method";

  // exception message
  public static final String POSITION_NOT_DETERMINED = "position not determined";
  public static final String MESSAGE_NOT_DETERMINED = "message not determined";
  public static final String MISSING_INFORMATION = "there is not enough information";

  public static final String MESSAGE_OK = "ok";
}
