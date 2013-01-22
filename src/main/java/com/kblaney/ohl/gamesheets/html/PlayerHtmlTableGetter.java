package com.kblaney.ohl.gamesheets.html;

import com.google.common.base.Function;
import com.google.common.base.Optional;
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
    s.append(HtmlTables.TABLE_START);
    s.append(Systems.LINE_SEPARATOR);
    s.append(getTableHeader());
    s.append(Systems.LINE_SEPARATOR);
    for (final Player player : players)
    {
      s.append(getTableRow(player));
      s.append(Systems.LINE_SEPARATOR);
    }
    s.append(HtmlTables.TABLE_END);

    return s.toString();
  }

  private String getTableHeader()
  {
    final StringBuilder s = new StringBuilder();
    s.append(HtmlTables.TABLE_ROW_START);
    s.append(HtmlTables.getLeftAlignedTdElement("R"));
    s.append(HtmlTables.getRightAlignedTdElement("#"));
    s.append(HtmlTables.getLeftAlignedTdElement("Name"));
    s.append(HtmlTables.getRightAlignedTdElement("GP"));
    s.append(HtmlTables.getRightAlignedTdElement("G"));
    s.append(HtmlTables.getRightAlignedTdElement("A"));
    s.append(HtmlTables.getRightAlignedTdElement("PT"));
    s.append(HtmlTables.getRightAlignedTdElement("+/-"));
    s.append(HtmlTables.getRightAlignedTdElement("PIM"));
    s.append(HtmlTables.getRightAlignedTdElement("PP"));
    s.append(HtmlTables.getRightAlignedTdElement("SH"));
    s.append(HtmlTables.getRightAlignedTdElement("G"));
    s.append(HtmlTables.getRightAlignedTdElement("A"));
    s.append(HtmlTables.getRightAlignedTdElement("P"));
    s.append(HtmlTables.getRightAlignedTdElement("Birth"));
    s.append(HtmlTables.getRightAlignedTdElement("P"));
    s.append(HtmlTables.getRightAlignedTdElement("H"));
    s.append(HtmlTables.getRightAlignedTdElement("W"));
    s.append(HtmlTables.getLeftAlignedTdElement("Hometown"));
    s.append(HtmlTables.TABLE_ROW_END);
    return s.toString();
  }

  private String getTableRow(final Player p)
  {
    final StringBuilder s = new StringBuilder(HtmlTables.TABLE_ROW_START);
    s.append(HtmlTables.getLeftAlignedTdElement(getRookieString(p.getType())));
    s.append(HtmlTables.getRightAlignedTdElement(getSweaterNumString(p.getSweaterNum())));
    s.append(HtmlTables.getLeftAlignedTdElement(p.getName()));
    s.append(HtmlTables.getRightAlignedTdElement(p.getStats().getNumGamesPlayed()));
    s.append(HtmlTables.getRightAlignedTdElement(p.getStats().getNumGoals()));
    s.append(HtmlTables.getRightAlignedTdElement(p.getStats().getNumAssists()));
    s.append(HtmlTables.getRightAlignedTdElement(p.getStats().getNumPoints()));
    s.append(HtmlTables.getRightAlignedTdElement(p.getStats().getPlusMinus()));
    s.append(HtmlTables.getRightAlignedTdElement(p.getStats().getNumPenaltyMinutes()));
    s.append(HtmlTables.getRightAlignedTdElement(p.getStats().getNumPowerPlayGoals()));
    s.append(HtmlTables.getRightAlignedTdElement(p.getStats().getNumShorthandedGoals()));
    s.append(HtmlTables.getRightAlignedTdElement(p.getStreaks().getGoalStreak()));
    s.append(HtmlTables.getRightAlignedTdElement(p.getStreaks().getAssistStreak()));
    s.append(HtmlTables.getRightAlignedTdElement(p.getStreaks().getPointStreak()));
    s.append(HtmlTables.getRightAlignedTdElement(p.getBio().getBirthYear()));
    s.append(HtmlTables.getRightAlignedTdElement(p.getBio().getPosition()));
    s.append(HtmlTables.getRightAlignedTdElement(p.getBio().getHeight()));
    s.append(HtmlTables.getRightAlignedTdElement(p.getBio().getWeight()));
    s.append(HtmlTables.getLeftAlignedTdElement(p.getBio().getHometown()));
    s.append(HtmlTables.TABLE_ROW_END);
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
