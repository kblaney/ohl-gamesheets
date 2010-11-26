package com.kblaney.ohl.website;

import com.google.common.base.Function;
import java.util.regex.Matcher;

final class TeamOptionHtmlToTeamFunction
      implements Function<String, NumberedTeam>
{
  public NumberedTeam apply(final String teamOptionHtml)
  {
    final Matcher matcher = TeamOptionHtml.PATTERN.matcher(teamOptionHtml);
    if (matcher.find())
    {
      final String teamName =
              matcher.group(TeamOptionHtml.TEAM_NAME_GROUP_NUM).trim();
      final int teamNum = Integer.parseInt(
              matcher.group(TeamOptionHtml.TEAM_NUM_GROUP_NUM));
      return new NumberedTeam(teamName, teamNum);
    }
    throw new IllegalArgumentException(
          "Can't find team in option HTML: " + teamOptionHtml);
  }
}
