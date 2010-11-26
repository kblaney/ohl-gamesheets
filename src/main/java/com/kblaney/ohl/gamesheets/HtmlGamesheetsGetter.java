package com.kblaney.ohl.gamesheets;

import com.kblaney.commons.html.HtmlUtil;
import com.kblaney.commons.lang.SystemUtil;
import com.kblaney.ohl.Goalie;
import com.kblaney.ohl.Player;
import com.kblaney.ohl.PlayerPointsComparator;
import com.kblaney.ohl.Team;
import com.kblaney.ohl.website.Website;
import java.io.IOException;
import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

final class HtmlGamesheetsGetter
{
  private final StatsProvider statsProvider = new Website();

  public HtmlGamesheets getGamesheets(final Team homeTeam, final Team roadTeam,
          final Calendar gameDate, final ProgressIndicator progressIndicator)
          throws IOException
  {
    final String roadTeamGamesheet = getRoadTeamGamesheet(roadTeam,
          progressIndicator);
    progressIndicator.setPlayerInProgress("Done road team");
    final String homeTeamGamesheet = getHomeTeamGamesheet(homeTeam, roadTeam,
            gameDate, progressIndicator);
    progressIndicator.setPlayerInProgress("Done");

    return new HtmlGamesheets(homeTeamGamesheet, roadTeamGamesheet);
  }

  private String getHomeTeamGamesheet(final Team homeTeam, final Team roadTeam,
        final Calendar gameDate, final ProgressIndicator progressIndicator)
        throws IOException
  {
    final StringBuilder s = new StringBuilder(
          getGameHeading(homeTeam, roadTeam, gameDate));
    s.append(SystemUtil.LINE_SEPARATOR);
    s.append(getGamesheet(homeTeam, progressIndicator));

    return s.toString();
  }

  private String getRoadTeamGamesheet(final Team roadTeam,
        final ProgressIndicator progressIndicator) throws IOException
  {
    return getGamesheet(roadTeam, progressIndicator);
  }

  private String getGameHeading(final Team homeTeam, final Team roadTeam,
        final Calendar gameDate)
  {
    final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
          "EEE., MMM. d, yyyy");
    final FieldPosition fieldPosition = new FieldPosition(0);
    final StringBuffer bufferToAppendTo = new StringBuffer();
    final StringBuffer formattedGameDate = simpleDateFormat.format(
          gameDate.getTime(), bufferToAppendTo, fieldPosition);

    return HtmlUtil.getH3Heading(roadTeam.getName() + " @ " +
          homeTeam.getName() + " - " + formattedGameDate);
  }

  private String getGamesheet(final Team team,
        final ProgressIndicator progressIndicator) throws IOException
  {
    final List<Player> players = statsProvider.getPlayers(team,
          progressIndicator);
    Collections.sort(players, new PlayerPointsComparator());
    final List<Goalie> goalies = statsProvider.getGoalies(team);

    final StringBuilder s = new StringBuilder(getTeamHeading(team));
    s.append(SystemUtil.LINE_SEPARATOR);
    s.append(new PlayerHtmlTableGetter().getHtmlTable(players));
    s.append(SystemUtil.LINE_SEPARATOR);
    s.append(HtmlUtil.LINE_BREAK);
    s.append(SystemUtil.LINE_SEPARATOR);
    s.append(new GoalieHtmlTableGetter().getHtmlTable(goalies));

    return s.toString();
  }

  private String getTeamHeading(final Team team)
  {
    return HtmlUtil.getH3Heading(team.getName());
  }
}
