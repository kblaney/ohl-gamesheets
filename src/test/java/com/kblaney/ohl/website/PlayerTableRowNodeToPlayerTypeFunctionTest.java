package com.kblaney.ohl.website;

import com.google.common.base.Function;
import com.kblaney.ohl.PlayerType;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Node;
import static org.junit.Assert.*;

public final class PlayerTableRowNodeToPlayerTypeFunctionTest
{
  private Function<Node, PlayerType> function;

  @Before
  public void setUp()
  {
    function = new PlayerTableRowNodeToPlayerTypeFunction();
  }

  @Test
  public void apply_rookie() throws Exception
  {
    assertEquals(PlayerType.ROOKIE, function.apply(new XmlToDomElementFunction().apply("<tr><td>*</td></tr>")));
  }

  @Test
  public void apply_veteran() throws Exception
  {
    assertEquals(PlayerType.VETERAN, function.apply(new XmlToDomElementFunction().apply("<tr><td>\u00A0</td></tr>")));
  }

  @Test
  public void apply_notActive() throws Exception
  {
    assertEquals(PlayerType.NOT_ACTIVE, function.apply(new XmlToDomElementFunction().apply("<tr><td><img/></td></tr>")));
  }
}
