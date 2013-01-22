package com.kblaney.ohl.gamesheets.html;

import static org.junit.Assert.*;
import org.junit.Test;

public final class HtmlTablesTest
{
  @Test
  public void getRightAlignedTdElement_EmptyValue()
  {
    assertEquals("<td align=\"right\">&nbsp;</td>", HtmlTables.getRightAlignedTdElement(""));
  }

  @Test
  public void getRightAlignedTdElement_NonEmptyValue()
  {
    assertEquals("<td align=\"right\">Kyle</td>", HtmlTables.getRightAlignedTdElement("Kyle"));
  }

  @Test
  public void getLeftAlignedTdElement_EmptyValue()
  {
    assertEquals("<td>&nbsp;</td>", HtmlTables.getLeftAlignedTdElement(""));
  }

  @Test
  public void getLeftAlignedTdElement_NonEmptyValue()
  {
    assertEquals("<td>Kyle</td>", HtmlTables.getLeftAlignedTdElement("Kyle"));
  }
}
