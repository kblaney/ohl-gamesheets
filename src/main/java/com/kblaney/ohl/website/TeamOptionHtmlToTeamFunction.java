package com.kblaney.ohl.website;

import com.google.common.base.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class TeamOptionHtmlToTeamFunction implements Function<String, Team>
{
  private static final Pattern PATTERN = Pattern.compile(
        "OPTION value.*?subType=(\\d+).*?>(.*?)<", Pattern.DOTALL);
  private static final int TEAM_NUM_GROUP_NUM = 1;
  private static final int TEAM_NAME_GROUP_NUM = 2;

  public Team apply(final String teamOptionHtml)
  {
    final Matcher matcher = PATTERN.matcher(teamOptionHtml);
    if (matcher.find())
    {
      final String teamName = matcher.group(TEAM_NAME_GROUP_NUM).trim();
      final int teamNum = Integer.parseInt(matcher.group(TEAM_NUM_GROUP_NUM));
      return new Team(teamName, teamNum);
    }
    throw new IllegalArgumentException(
          "Can't find team in option HTML: " + teamOptionHtml);
  }
}
