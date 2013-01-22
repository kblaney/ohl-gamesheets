package com.kblaney.ohl.gamesheets.html;

import com.google.common.base.Function;
import com.kblaney.ohl.Goalie;
import java.text.DecimalFormat;
import java.util.List;

final class GoalieHtmlTableGetter implements Function<List<Goalie>, String>
{
  public String apply(final List<Goalie> goalies)
  {
    final StringBuilder s = new StringBuilder();
    s.append(HtmlTables.TABLE_START);
    s.append(Systems.LINE_SEPARATOR);
    s.append(getTableHeader());
    s.append(Systems.LINE_SEPARATOR);
    for (final Goalie goalie : goalies)
    {
      s.append(getTableRow(goalie));
      s.append(Systems.LINE_SEPARATOR);
    }
    s.append(HtmlTables.TABLE_END);

    return s.toString();
  }

  private String getTableHeader()
  {
    final StringBuilder s = new StringBuilder();
    s.append(HtmlTables.TABLE_ROW_START);
    s.append(HtmlTables.getLeftAlignedTdElement("Name"));
    s.append(HtmlTables.getRightAlignedTdElement("GP"));
    s.append(HtmlTables.getRightAlignedTdElement("W"));
    s.append(HtmlTables.getRightAlignedTdElement("L"));
    s.append(HtmlTables.getRightAlignedTdElement("OTL"));
    s.append(HtmlTables.getRightAlignedTdElement("SOL"));
    s.append(HtmlTables.getRightAlignedTdElement("MIN"));
    s.append(HtmlTables.getRightAlignedTdElement("SO"));
    s.append(HtmlTables.getRightAlignedTdElement("SA"));
    s.append(HtmlTables.getRightAlignedTdElement("SV"));
    s.append(HtmlTables.getRightAlignedTdElement("GA"));
    s.append(HtmlTables.getRightAlignedTdElement("GAA"));
    s.append(HtmlTables.getRightAlignedTdElement("Sv%"));
    s.append(HtmlTables.TABLE_ROW_END);
    return s.toString();
  }

  private String getTableRow(final Goalie g)
  {
    final StringBuilder s = new StringBuilder(HtmlTables.TABLE_ROW_START);
    s.append(HtmlTables.getLeftAlignedTdElement(g.getName()));
    s.append(HtmlTables.getRightAlignedTdElement(g.getStats().getNumGamesPlayed()));
    s.append(HtmlTables.getRightAlignedTdElement(g.getStats().getNumWins()));
    s.append(HtmlTables.getRightAlignedTdElement(g.getStats().getNumRegulationLosses()));
    s.append(HtmlTables.getRightAlignedTdElement(g.getStats().getNumOvertimeLosses()));
    s.append(HtmlTables.getRightAlignedTdElement(g.getStats().getNumShootoutLosses()));
    s.append(HtmlTables.getRightAlignedTdElement(g.getStats().getNumMinutesPlayed()));
    s.append(HtmlTables.getRightAlignedTdElement(g.getStats().getNumShutouts()));
    s.append(HtmlTables.getRightAlignedTdElement(g.getStats().getNumShotsAgainst()));
    s.append(HtmlTables.getRightAlignedTdElement(g.getStats().getNumSaves()));
    s.append(HtmlTables.getRightAlignedTdElement(g.getStats().getNumGoalsAgainst()));
    final DecimalFormat gaaDecimalFormat = new DecimalFormat("0.00");
    s.append(HtmlTables.getRightAlignedTdElement(gaaDecimalFormat.format(g.getStats().getGoalsAgainstAverage())));
    final DecimalFormat savePercentageDecimalFormat = new DecimalFormat("0.000");
    s.append(HtmlTables.getRightAlignedTdElement(savePercentageDecimalFormat.format(g.getStats().getSavePercentage())));
    s.append(HtmlTables.TABLE_ROW_END);
    return s.toString();
  }
}
