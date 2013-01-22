package com.kblaney.ohl.website;

import com.google.common.base.Optional;
import org.junit.Test;
import static org.junit.Assert.*;

public final class PlayerTableRowNodeToSweaterNumFunctionTest
{
  @Test
  public void apply_sweaterNumIsPresent() throws Exception
  {
    final Optional<Integer> expected = Optional.of(27);
    final Optional<Integer> actual = new PlayerTableRowNodeToSweaterNumFunction().apply(new XmlToDomElementFunction()
          .apply("<tr><td/><td>27</td></tr>"));
    assertEquals(expected, actual);
  }

  @Test
  public void apply_sweaterNumIsZero() throws Exception
  {
    final Optional<Integer> expected = Optional.absent();
    final Optional<Integer> actual = new PlayerTableRowNodeToSweaterNumFunction().apply(new XmlToDomElementFunction()
          .apply("<tr><td/><td>0</td></tr>"));
    assertEquals(expected, actual);
  }

  @Test
  public void apply_noSweaterNum() throws Exception
  {
    final Optional<Integer> expected = Optional.absent();
    final Optional<Integer> actual = new PlayerTableRowNodeToSweaterNumFunction().apply(new XmlToDomElementFunction()
          .apply("<tr><td/><td></td></tr>"));
    assertEquals(expected, actual);
  }
}
