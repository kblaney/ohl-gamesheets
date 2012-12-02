package com.kblaney.ohl.gamesheets.html;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.kblaney.commons.html.HtmlUtil;
import com.kblaney.commons.lang.SystemUtil;
import com.kblaney.ohl.Player;
import com.kblaney.ohl.PlayerType;
import java.util.List;

final class PlayerHtmlTableGetter implements Function<List<Player>, String>
{
  static final String EMPTY_TABLE_CELL = "&nbsp;";
  static final String ROOKIE_TABLE_CELL = "*";

  public String apply(final List<Player> players)
  {
    final StringBuilder s = new StringBuilder();
    s.append(HtmlUtil.TABLE_START);
    s.append(SystemUtil.LINE_SEPARATOR);
    s.append(getTableHeader());
    s.append(SystemUtil.LINE_SEPARATOR);
    for (final Player player : players)
    {
      s.append(getTableRow(player));
      s.append(SystemUtil.LINE_SEPARATOR);
    }
    s.append(HtmlUtil.TABLE_END);

    return s.toString();
  }

  private String getTableHeader()
  {
    final StringBuilder s = new StringBuilder();
    s.append(HtmlUtil.TABLE_ROW_START);
    s.append(HtmlUtil.getLeftAlignedTdElement("R"));
    s.append(HtmlUtil.getRightAlignedTdElement("#"));
    s.append(HtmlUtil.getLeftAlignedTdElement("Name"));
    s.append(HtmlUtil.getRightAlignedTdElement("GP"));
    s.append(HtmlUtil.getRightAlignedTdElement("G"));
    s.append(HtmlUtil.getRightAlignedTdElement("A"));
    s.append(HtmlUtil.getRightAlignedTdElement("PT"));
    s.append(HtmlUtil.getRightAlignedTdElement("+/-"));
    s.append(HtmlUtil.getRightAlignedTdElement("PIM"));
    s.append(HtmlUtil.getRightAlignedTdElement("PP"));
    s.append(HtmlUtil.getRightAlignedTdElement("SH"));
    s.append(HtmlUtil.getRightAlignedTdElement("G"));
    s.append(HtmlUtil.getRightAlignedTdElement("A"));
    s.append(HtmlUtil.getRightAlignedTdElement("P"));
    s.append(HtmlUtil.getRightAlignedTdElement("Birth"));
    s.append(HtmlUtil.getRightAlignedTdElement("P"));
    s.append(HtmlUtil.getRightAlignedTdElement("H"));
    s.append(HtmlUtil.getRightAlignedTdElement("W"));
    s.append(HtmlUtil.getLeftAlignedTdElement("Hometown"));
    s.append(HtmlUtil.TABLE_ROW_END);
    return s.toString();
  }

  private String getTableRow(final Player p)
  {
    final StringBuilder s = new StringBuilder(HtmlUtil.TABLE_ROW_START);
    s.append(HtmlUtil.getLeftAlignedTdElement(getRookieString(p.getType())));
    s.append(HtmlUtil.getRightAlignedTdElement(getSweaterNumString(p.getSweaterNum())));
    s.append(HtmlUtil.getLeftAlignedTdElement(p.getName()));
    s.append(HtmlUtil.getRightAlignedTdElement(p.getStats().getNumGamesPlayed()));
    s.append(HtmlUtil.getRightAlignedTdElement(p.getStats().getNumGoals()));
    s.append(HtmlUtil.getRightAlignedTdElement(p.getStats().getNumAssists()));
    s.append(HtmlUtil.getRightAlignedTdElement(p.getStats().getNumPoints()));
    s.append(HtmlUtil.getRightAlignedTdElement(p.getStats().getPlusMinus()));
    s.append(HtmlUtil.getRightAlignedTdElement(p.getStats().getNumPenaltyMinutes()));
    s.append(HtmlUtil.getRightAlignedTdElement(p.getStats().getNumPowerPlayGoals()));
    s.append(HtmlUtil.getRightAlignedTdElement(p.getStats().getNumShorthandedGoals()));
    s.append(HtmlUtil.getRightAlignedTdElement(p.getStreaks().getGoalStreak()));
    s.append(HtmlUtil.getRightAlignedTdElement(p.getStreaks().getAssistStreak()));
    s.append(HtmlUtil.getRightAlignedTdElement(p.getStreaks().getPointStreak()));
    s.append(HtmlUtil.getRightAlignedTdElement(p.getBio().getBirthYear()));
    s.append(HtmlUtil.getRightAlignedTdElement(p.getBio().getPosition()));
    s.append(HtmlUtil.getRightAlignedTdElement(p.getBio().getHeight()));
    s.append(HtmlUtil.getRightAlignedTdElement(p.getBio().getWeight()));
    s.append(HtmlUtil.getLeftAlignedTdElement(p.getBio().getHometown()));
    s.append(HtmlUtil.TABLE_ROW_END);
    return s.toString();
  }

  private String getRookieString(final PlayerType playerType)
  {
    if (playerType == PlayerType.ROOKIE)
    {
      return ROOKIE_TABLE_CELL;
    }
    else
    {
      return EMPTY_TABLE_CELL;
    }
  }

  private String getSweaterNumString(final Optional<Integer> sweaterNum)
  {
    if (sweaterNum.isPresent())
    {
      return Integer.toString(sweaterNum.get());
    }
    else
    {
      return EMPTY_TABLE_CELL;
    }
  }
}
