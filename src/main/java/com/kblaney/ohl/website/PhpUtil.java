package com.kblaney.ohl.website;

final class PhpUtil
{
  private static final String KEY_VALUE_SEPARATOR = "=";
  public static final String PAIRS_SEPARATOR = "?";

  private PhpUtil() {}

  public static String getKeyValueString(final String key, final String value)
  {
    return key + KEY_VALUE_SEPARATOR + value;
  }
}
