package com.kblaney.ohl.gamesheets.html;

import com.kblaney.assertions.ArgAssert;

final class HtmlTables
{
  public static final String TABLE_START =
        "<table border=\"1\" cellspacing=\"0\" cellpadding=\"1\">";
  public static final String TABLE_END = "</table>";
  public static final String TABLE_ROW_START = "<tr>";
  public static final String TABLE_ROW_END = "</tr>";

  public static String getRightAlignedTdElement(final int value)
  {
    return getRightAlignedTdElement(Integer.toString(value));
  }

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
      s.append(HtmlSpecialCharacters.NON_BREAKING_SPACE);
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
