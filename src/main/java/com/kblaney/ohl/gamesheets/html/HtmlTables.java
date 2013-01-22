package com.kblaney.ohl.gamesheets.html;

import com.kblaney.assertions.ArgAssert;

final class HtmlTables
{
  public static String getRightAlignedTdElement(final String value)
  {
    return getTableElement(value, /* isRightAligned= */true);
  }

  private static String getTableElement(final String value, final boolean isRightAligned)
  {
    ArgAssert.assertNotNull(value, "value");

    final StringBuilder s = new StringBuilder();
    if (isRightAligned)
    {
      s.append("<td align=\"right\">");
    }
    else
    {
      s.append("<td>");
    }

    if (value.equals(""))
    {
      final String nonBreakingSpace = "&nbsp;";
      s.append(nonBreakingSpace);
    }
    else
    {
      s.append(value);
    }

    s.append("</td>");

    return s.toString();
  }

  public static String getLeftAlignedTdElement(final String value)
  {
    return getTableElement(value, /* isRightAligned= */false);
  }

  private HtmlTables()
  {
  }
}
