package com.kblaney.ohl.gamesheets;

import com.kblaney.commons.html.HtmlUtil;
import com.kblaney.commons.lang.SystemUtil;
import com.kblaney.ohl.Player;
import com.kblaney.ohl.PlayerType;
import java.util.List;

/**
 * Gets HTML tables for players.
 */
final class PlayerHtmlTableGetter
{
  /**
   * Gets an HTML table using a specified list of players.
   *
   * @param players the list of players, which can't be null or empty
   *
   * @return the HTML table
   */
  public String getHtmlTable(final List<Player> players)
  {
    final StringBuilder s = new StringBuilder(HtmlUtil.TABLE_START);
    s.append(SystemUtil.LINE_SEPARATOR);
    s.append(getHtmlTableHeader());
    s.append(SystemUtil.LINE_SEPARATOR);
    for (final Player player : players)
    {
      s.append(getHtmlTableRow(player));
      s.append(SystemUtil.LINE_SEPARATOR);
    }
    s.append(HtmlUtil.TABLE_END);

    return s.toString();
  }

  private String getHtmlTableHeader()
  {
    final StringBuilder s = new StringBuilder();
    s.append(HtmlUtil.TABLE_ROW_START);
    s.append(HtmlUtil.getTableElement("R", false));
    s.append(HtmlUtil.getTableElement("#", true));
    s.append(HtmlUtil.getTableElement("Name", false));
    s.append(HtmlUtil.getTableElement("GP", true));
    s.append(HtmlUtil.getTableElement("G", true));
    s.append(HtmlUtil.getTableElement("A", true));
    s.append(HtmlUtil.getTableElement("PT", true));
    s.append(HtmlUtil.getTableElement("+/-", true));
    s.append(HtmlUtil.getTableElement("PIM", true));
    s.append(HtmlUtil.getTableElement("PP", true));
    s.append(HtmlUtil.getTableElement("SH", true));
    s.append(HtmlUtil.getTableElement("G", true));
    s.append(HtmlUtil.getTableElement("A", true));
    s.append(HtmlUtil.getTableElement("P", true));
    s.append(HtmlUtil.getTableElement("Birth", true));
    s.append(HtmlUtil.getTableElement("P", true));
    s.append(HtmlUtil.getTableElement("H", true));
    s.append(HtmlUtil.getTableElement("W", true));
    s.append(HtmlUtil.getTableElement("Birthplace", false));
    s.append(HtmlUtil.TABLE_ROW_END);
    return s.toString();
  }

  private String getHtmlTableRow(final Player player)
  {
    final StringBuilder s = new StringBuilder(HtmlUtil.TABLE_ROW_START);
    s.append(HtmlUtil.getTableElement(getRookieString(
          player.getPlayerType()), false));
    s.append(HtmlUtil.getTableElement(Integer.toString(
          player.getSweaterNum()), true));
    s.append(HtmlUtil.getTableElement(
          player.getName(), false));
    s.append(HtmlUtil.getTableElement(Integer.toString(
          player.getStats().getNumGamesPlayed()), true));
    s.append(HtmlUtil.getTableElement(Integer.toString(
          player.getStats().getNumGoals()), true));
    s.append(HtmlUtil.getTableElement(Integer.toString(
          player.getStats().getNumAssists()), true));
    s.append(HtmlUtil.getTableElement(Integer.toString(
          player.getStats().getNumPoints()), true));
    s.append(HtmlUtil.getTableElement(Integer.toString(
          player.getStats().getPlusMinus()), true));
    s.append(HtmlUtil.getTableElement(Integer.toString(
          player.getStats().getNumPenaltyMinutes()), true));
    s.append(HtmlUtil.getTableElement(Integer.toString(
          player.getStats().getNumPowerPlayGoals()), true));
    s.append(HtmlUtil.getTableElement(Integer.toString(
          player.getStats().getNumShorthandedGoals()), true));
    s.append(HtmlUtil.getTableElement(Integer.toString(
          player.getStreaks().getGoalStreak()), true));
    s.append(HtmlUtil.getTableElement(Integer.toString(
          player.getStreaks().getAssistStreak()), true));
    s.append(HtmlUtil.getTableElement(Integer.toString(
          player.getStreaks().getPointStreak()), true));
    s.append(HtmlUtil.getTableElement(
          player.getBio().getBirthYear(), true));
    s.append(HtmlUtil.getTableElement(
          player.getBio().getPosition(), true));
    s.append(HtmlUtil.getTableElement(
          player.getBio().getHeight(), true));
    s.append(HtmlUtil.getTableElement(
          player.getBio().getWeight(), true));
    s.append(HtmlUtil.getTableElement(
          player.getBio().getBirthplace(), false));
    s.append(HtmlUtil.TABLE_ROW_END);
    return s.toString();
  }

  private static String getRookieString(final PlayerType playerType)
  {
    if (playerType == PlayerType.ROOKIE)
    {
      return "*";
    }
    else
    {
      return "&nbsp;";
    }
  }
}
