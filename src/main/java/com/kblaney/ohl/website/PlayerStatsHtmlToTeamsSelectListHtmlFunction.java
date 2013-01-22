package com.kblaney.ohl.website;

import com.google.common.base.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class PlayerStatsHtmlToTeamsSelectListHtmlFunction implements Function<String, String>
{
  public String apply(final String playerStatsHtml)
  {
    final Pattern pattern = Pattern.compile("<SELECT name=\"subStat\".*?</SELECT>", Pattern.DOTALL);
    final Matcher matcher = pattern.matcher(playerStatsHtml);
    if (matcher.find())
    {
      return matcher.group();
    }
    else
    {
      throw new IllegalArgumentException("Can't find teams select list:" + playerStatsHtml);
    }
  }
}
