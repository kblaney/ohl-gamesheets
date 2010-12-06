package com.kblaney.ohl.website;

import org.junit.Test;
import static org.junit.Assert.*;

public final class PlayerTableRowNodeToSweaterNumFunctionTest
{
  @Test
  public void apply() throws Exception
  {
    final int expected = 27;
    final int actual = new PlayerTableRowNodeToSweaterNumFunction().apply(
          new XmlToDomElementFunction().apply("<tr><td/><td>27</td></tr>"));
    assertEquals(expected, actual);
  }
}
