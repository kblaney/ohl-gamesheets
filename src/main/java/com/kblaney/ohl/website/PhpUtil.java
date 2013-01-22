package com.kblaney.ohl.website;

final class PhpUtil
{
  public static final String FILE_PAIRS_SEPARATOR = "?";
  public static final String PAIR_SEPARATOR = "&";

  private PhpUtil()
  {
  }

  public static String getPair(final String key, final String value)
  {
    final String separator = "=";
    return key + separator + value;
  }
}
