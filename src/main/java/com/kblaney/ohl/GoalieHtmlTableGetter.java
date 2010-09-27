package com.kblaney.ohl;

import com.kblaney.commons.html.HtmlUtil;
import com.kblaney.commons.lang.ArgAssert;
import com.kblaney.commons.lang.SystemUtil;
import java.text.DecimalFormat;
import java.util.List;

/**
 * Gets HTML tables for goalies.
 */
public final class GoalieHtmlTableGetter
{
  /**
   * Gets an HTML table using a specified list of goalies.
   *
   * @param goalies the list of goalies, which can't be null or empty
   *
   * @return the HTML table
   */
  public String getHtmlTable(final List<Goalie> goalies)
  {
    ArgAssert.notNull(goalies, "goalies");

    final StringBuilder htmlTable = new StringBuilder(HtmlUtil.TABLE_START);
    htmlTable.append(SystemUtil.LINE_SEPARATOR);
    htmlTable.append(getHtmlTableHeader());
    htmlTable.append(SystemUtil.LINE_SEPARATOR);
    for (Goalie goalie : goalies)
    {
      htmlTable.append(getHtmlTableRow(goalie));
      htmlTable.append(SystemUtil.LINE_SEPARATOR);
    }
    htmlTable.append(HtmlUtil.TABLE_END);

    return htmlTable.toString();
  }

  private String getHtmlTableHeader()
  {
    final StringBuffer s = new StringBuffer();
    s.append(HtmlUtil.TABLE_ROW_START);
    s.append(HtmlUtil.getTableElement("Name", false));
    s.append(HtmlUtil.getTableElement("GP", true));
    s.append(HtmlUtil.getTableElement("W", true));
    s.append(HtmlUtil.getTableElement("L", true));
    s.append(HtmlUtil.getTableElement("OTL", true));
    s.append(HtmlUtil.getTableElement("SOL", true));
    s.append(HtmlUtil.getTableElement("MIN", true));
    s.append(HtmlUtil.getTableElement("SO", true));
    s.append(HtmlUtil.getTableElement("SA", true));
    s.append(HtmlUtil.getTableElement("SV", true));
    s.append(HtmlUtil.getTableElement("GA", true));
    s.append(HtmlUtil.getTableElement("GAA", true));
    s.append(HtmlUtil.getTableElement("Sv%", true));
    s.append(HtmlUtil.TABLE_ROW_END);
    return s.toString();
  }

  private String getHtmlTableRow(final Goalie goalie)
  {
    ArgAssert.notNull(goalie, "goalie");

    final DecimalFormat gaaDecimalFormat = new DecimalFormat("0.00");
    final DecimalFormat savePercentageDecimalFormat =
          new DecimalFormat("0.000");

    final StringBuilder s = new StringBuilder(HtmlUtil.TABLE_ROW_START);
    s.append(HtmlUtil.getTableElement(goalie.getName(), false));
    s.append(HtmlUtil.getTableElement(Integer.toString(
          goalie.getStats().getNumGamesPlayed()), true));
    s.append(HtmlUtil.getTableElement(Integer.toString(
          goalie.getStats().getNumWins()), true));
    s.append(HtmlUtil.getTableElement(Integer.toString(
          goalie.getStats().getNumRegulationLosses()), true));
    s.append(HtmlUtil.getTableElement(Integer.toString(
          goalie.getStats().getNumOvertimeLosses()), true));
    s.append(HtmlUtil.getTableElement(Integer.toString(
          goalie.getStats().getNumShootoutLosses()), true));
    s.append(HtmlUtil.getTableElement(Integer.toString(
          goalie.getStats().getNumMinutesPlayed()), true));
    s.append(HtmlUtil.getTableElement(Integer.toString(
          goalie.getStats().getNumShutouts()), true));
    s.append(HtmlUtil.getTableElement(Integer.toString(
          goalie.getStats().getNumShotsAgainst()), true));
    s.append(HtmlUtil.getTableElement(Integer.toString(
          goalie.getStats().getNumSaves()), true));
    s.append(HtmlUtil.getTableElement(Integer.toString(
          goalie.getStats().getNumGoalsAgainst()), true));
    s.append(HtmlUtil.getTableElement(gaaDecimalFormat.format(
          goalie.getStats().getGoalsAgainstAverage()), true));
    s.append(HtmlUtil.getTableElement(
          savePercentageDecimalFormat.format(goalie.getStats().
          getSavePercentage()), true));
    s.append(HtmlUtil.TABLE_ROW_END);
    return s.toString();
  }
}
