package com.kblaney.ohl.website;

import com.google.common.base.Function;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Node;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Element;
import static org.junit.Assert.*;

public final class PlayerBioDivNodeToWeightFunctionTest
{
  private Function<Node, String> function;

  @Before
  public void setUp()
  {
    function = new PlayerBioDivNodeToWeightFunction();
  }

  @Test
  public void apply_noWeightPresent() throws Exception
  {
    final Element element = new XmlToElementFunction().apply(
          "<div><table><tr><td>Weight</td><td/></tr></table></div>");
    assertEquals(StringUtils.EMPTY, function.apply(element));
  }

  @Test
  public void apply_weightPresent() throws Exception
  {
    final Element element = new XmlToElementFunction().apply(
          "<div><table><tr><td>Weight</td><td>218</td></tr></table></div>");
    assertEquals("218", function.apply(element));
  }
}
