package com.kblaney.ohl.website;

import org.junit.Test;
import static org.junit.Assert.*;

public final class PlayerTableRowNodeToNameFunctionTest
{
  @Test
  public void apply() throws Exception
  {
    assertEquals("Stephen Silas", new PlayerTableRowNodeToNameFunction().apply(new XmlToDomElementFunction()
          .apply("<tr><td/><td/><td><a>Stephen Silas</a></td></tr>")));
  }
}
