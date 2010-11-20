package com.kblaney.ohl.website;

import org.junit.Test;
import static org.junit.Assert.*;

public final class PhpUtilTest
{
  @Test
  public void getKeyValueString()
  {
    assertEquals("key=value", PhpUtil.getKeyValueString("key", "value"));
  }
}
