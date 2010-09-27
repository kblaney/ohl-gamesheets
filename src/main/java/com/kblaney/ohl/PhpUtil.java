package com.kblaney.ohl;

import com.kblaney.commons.lang.ArgAssert;

/**
 * PHP utilities.
 */
public class PhpUtil
{
  private static final String KEY_VALUE_SEPARATOR = "=";
  public static final String PAIRS_SEPARATOR = "?";

  /**
   * Constructor made private to restrict instantiation.
   */
  private PhpUtil()
  {
  }

  /**
   * Gets a key and value string to use in a PHP query.
   *
   * @param key the key, which can't be null
   * @param value the value, which can't be null
   *
   * @return the key and value string
   */
  public static String getKeyValueString(final String key, final String value)
  {
    ArgAssert.notNull(key, "key");
    ArgAssert.notNull(value, "value");

    return key + KEY_VALUE_SEPARATOR + value;
  }
}
