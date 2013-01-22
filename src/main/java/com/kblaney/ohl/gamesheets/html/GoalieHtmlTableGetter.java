package com.kblaney.ohl.gamesheets.html;

import com.google.common.base.Function;
import com.kblaney.commons.html.HtmlUtil;
import com.kblaney.commons.lang.SystemUtil;
import com.kblaney.ohl.Goalie;
import java.text.DecimalFormat;
import java.util.List;

final class GoalieHtmlTableGetter implements Function<List<Goalie>, String>
{
  public String apply(final List<Goalie> goalies)
  {
    final StringBuilder s = new StringBuilder();
    s.append(HtmlUtil.TABLE_START);
    s.append(SystemUtil.LINE_SEPARATOR);
    s.append(getTableHeader());
    s.append(SystemUtil.LINE_SEPARATOR);
    for (final Goalie goalie : goalies)
    {
      s.append(getTableRow(goalie));
      s.append(SystemUtil.LINE_SEPARATOR);
    }
    s.append(HtmlUtil.TABLE_END);

    return s.toString();
  }

  private String getTableHeader()
  {
    final StringBuilder s = new StringBuilder();
    s.append(HtmlUtil.TABLE_ROW_START);
    s.append(HtmlUtil.getLeftAlignedTdElement("Name"));
    s.append(HtmlUtil.getRightAlignedTdElement("GP"));
    s.append(HtmlUtil.getRightAlignedTdElement("W"));
    s.append(HtmlUtil.getRightAlignedTdElement("L"));
    s.append(HtmlUtil.getRightAlignedTdElement("OTL"));
    s.append(HtmlUtil.getRightAlignedTdElement("SOL"));
    s.append(HtmlUtil.getRightAlignedTdElement("MIN"));
    s.append(HtmlUtil.getRightAlignedTdElement("SO"));
    s.append(HtmlUtil.getRightAlignedTdElement("SA"));
    s.append(HtmlUtil.getRightAlignedTdElement("SV"));
    s.append(HtmlUtil.getRightAlignedTdElement("GA"));
    s.append(HtmlUtil.getRightAlignedTdElement("GAA"));
    s.append(HtmlUtil.getRightAlignedTdElement("Sv%"));
    s.append(HtmlUtil.TABLE_ROW_END);
    return s.toString();
  }

  private String getTableRow(final Goalie g)
  {
    final StringBuilder s = new StringBuilder(HtmlUtil.TABLE_ROW_START);
    s.append(HtmlUtil.getLeftAlignedTdElement(g.getName()));
    s.append(HtmlUtil.getRightAlignedTdElement(g.getStats().getNumGamesPlayed()));
    s.append(HtmlUtil.getRightAlignedTdElement(g.getStats().getNumWins()));
    s.append(HtmlUtil.getRightAlignedTdElement(g.getStats().getNumRegulationLosses()));
    s.append(HtmlUtil.getRightAlignedTdElement(g.getStats().getNumOvertimeLosses()));
    s.append(HtmlUtil.getRightAlignedTdElement(g.getStats().getNumShootoutLosses()));
    s.append(HtmlUtil.getRightAlignedTdElement(g.getStats().getNumMinutesPlayed()));
    s.append(HtmlUtil.getRightAlignedTdElement(g.getStats().getNumShutouts()));
    s.append(HtmlUtil.getRightAlignedTdElement(g.getStats().getNumShotsAgainst()));
    s.append(HtmlUtil.getRightAlignedTdElement(g.getStats().getNumSaves()));
    s.append(HtmlUtil.getRightAlignedTdElement(g.getStats().getNumGoalsAgainst()));
    final DecimalFormat gaaDecimalFormat = new DecimalFormat("0.00");
    s.append(HtmlUtil.getRightAlignedTdElement(gaaDecimalFormat.format(g.getStats().getGoalsAgainstAverage())));
    final DecimalFormat savePercentageDecimalFormat = new DecimalFormat("0.000");
    s.append(HtmlUtil.getRightAlignedTdElement(savePercentageDecimalFormat.format(g.getStats().getSavePercentage())));
    s.append(HtmlUtil.TABLE_ROW_END);
    return s.toString();
  }
}
