package com.kblaney.ohl.gamesheets;

import com.kblaney.commons.html.HtmlUtil;
import com.kblaney.commons.lang.SystemUtil;
import com.kblaney.ohl.Goalie;
import com.kblaney.ohl.Player;
import com.kblaney.ohl.PlayerPointsComparator;
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

  public HtmlGamesheets getGamesheets(final String homeTeamName,
        final String roadTeamName, final Calendar gameDate,
        final ProgressIndicator progressIndicator) throws IOException
  {
    final String roadTeamGamesheet = getRoadTeamGamesheet(roadTeamName,
          progressIndicator);
    progressIndicator.setPlayerInProgress("Done road team");
    final String homeTeamGamesheet = getHomeTeamGamesheet(homeTeamName,
          roadTeamName, gameDate, progressIndicator);
    progressIndicator.setPlayerInProgress("Done");

    return new HtmlGamesheets(homeTeamGamesheet, roadTeamGamesheet);
  }

  private String getHomeTeamGamesheet(final String homeTeamName,
        final String roadTeamName, final Calendar gameDate,
        final ProgressIndicator progressIndicator) throws IOException
  {
    final StringBuilder s = new StringBuilder(
          getGameHeading(homeTeamName, roadTeamName, gameDate));
    s.append(SystemUtil.LINE_SEPARATOR);
    s.append(getGamesheet(homeTeamName, progressIndicator));

    return s.toString();
  }

  private String getRoadTeamGamesheet(final String roadTeamName,
        final ProgressIndicator progressIndicator) throws IOException
  {
    return getGamesheet(roadTeamName, progressIndicator);
  }

  private String getGameHeading(final String homeTeamName,
        final String roadTeamName, final Calendar gameDate)
  {
    final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
          "EEE., MMM. d, yyyy");
    final FieldPosition fieldPosition = new FieldPosition(0);
    final StringBuffer bufferToAppendTo = new StringBuffer();
    final StringBuffer formattedGameDate = simpleDateFormat.format(
          gameDate.getTime(), bufferToAppendTo, fieldPosition);

    return HtmlUtil.getH3Heading(roadTeamName + " @ " + homeTeamName + " - " +
          formattedGameDate);
  }

  private String getGamesheet(final String teamName,
        final ProgressIndicator progressIndicator) throws IOException
  {
    final List<Player> players = statsProvider.getPlayers(teamName,
          progressIndicator);
    Collections.sort(players, new PlayerPointsComparator());
    final List<Goalie> goalies = statsProvider.getGoalies(teamName);

    final StringBuilder s = new StringBuilder(getTeamHeading(teamName));
    s.append(SystemUtil.LINE_SEPARATOR);
    s.append(new PlayerHtmlTableGetter().getHtmlTable(players));
    s.append(SystemUtil.LINE_SEPARATOR);
    s.append(HtmlUtil.LINE_BREAK);
    s.append(SystemUtil.LINE_SEPARATOR);
    s.append(new GoalieHtmlTableGetter().getHtmlTable(goalies));

    return s.toString();
  }

  private String getTeamHeading(final String teamName)
  {
    return HtmlUtil.getH3Heading(teamName);
  }
}
