package com.kblaney.ohl.gamesheets.html;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.kblaney.ohl.Goalie;
import com.kblaney.ohl.Player;
import com.kblaney.ohl.PlayerPointsComparator;
import com.kblaney.ohl.StatsProvider;
import com.kblaney.ohl.Team;
import com.kblaney.ohl.gamesheets.Gamesheets;
import com.kblaney.ohl.gamesheets.GamesheetsGetter;
import com.kblaney.ohl.gamesheets.ProgressIndicator;
import java.io.IOException;
import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public final class HtmlGamesheetsGetter implements GamesheetsGetter
{
  private final StatsProvider statsProvider;
  private final Function<List<Player>, String> playersToHtmlTableFunction;
  private final Function<List<Goalie>, String> goaliesToHtmlTableFunction;

  @Inject
  public HtmlGamesheetsGetter(final StatsProvider statsProvider,
        final Function<List<Player>, String> playersToHtmlTableFunction,
        final Function<List<Goalie>, String> goaliesToHtmlTableFunction)
  {
    this.statsProvider = statsProvider;
    this.playersToHtmlTableFunction = playersToHtmlTableFunction;
    this.goaliesToHtmlTableFunction = goaliesToHtmlTableFunction;
  }

  public Gamesheets getGamesheets(final Team homeTeam, final Team roadTeam, final Calendar gameDate,
        final ProgressIndicator progressIndicator) throws IOException
  {
    final String roadTeamGamesheet = getRoadTeamGamesheet(roadTeam, progressIndicator);
    progressIndicator.setPlayerInProgress("Done road team");
    final String homeTeamGamesheet = getHomeTeamGamesheet(homeTeam, roadTeam, gameDate, progressIndicator);
    progressIndicator.setPlayerInProgress("Done");

    return new Gamesheets(homeTeamGamesheet, roadTeamGamesheet);
  }

  private String getRoadTeamGamesheet(final Team roadTeam, final ProgressIndicator progressIndicator)
        throws IOException
  {
    return getGamesheet(roadTeam, progressIndicator);
  }

  private String getGamesheet(final Team team, final ProgressIndicator progressIndicator) throws IOException
  {
    final List<Player> players = statsProvider.getPlayers(team, progressIndicator);
    final List<Player> activePlayers = removeInactivePlayers(players);
    Collections.sort(activePlayers, new PlayerPointsComparator());
    final List<Goalie> goalies = statsProvider.getGoalies(team, progressIndicator);

    final StringBuilder s = new StringBuilder(getTeamHeading(team));
    s.append(Systems.LINE_SEPARATOR);
    s.append(playersToHtmlTableFunction.apply(activePlayers));
    s.append(Systems.LINE_SEPARATOR);
    s.append(HtmlSpecialCharacters.LINE_BREAK);
    s.append(Systems.LINE_SEPARATOR);
    s.append(goaliesToHtmlTableFunction.apply(goalies));
    return s.toString();
  }

  private List<Player> removeInactivePlayers(final List<Player> players)
  {
    return Lists.newArrayList(Collections2.filter(players, new ActivePlayerPredicate()));
  }

  private String getTeamHeading(final Team team)
  {
    return HtmlHeadings.getH3Heading(team.getName());
  }

  private String getHomeTeamGamesheet(final Team homeTeam, final Team roadTeam, final Calendar gameDate,
        final ProgressIndicator progressIndicator) throws IOException
  {
    final StringBuilder s = new StringBuilder(getGameHeading(homeTeam, roadTeam, gameDate));
    s.append(Systems.LINE_SEPARATOR);
    s.append(getGamesheet(homeTeam, progressIndicator));

    return s.toString();
  }

  private String getGameHeading(final Team homeTeam, final Team roadTeam, final Calendar gameDate)
  {
    final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE., MMM. d, yyyy");
    final FieldPosition fieldPosition = new FieldPosition(0);
    final StringBuffer bufferToAppendTo = new StringBuffer();
    final StringBuffer formattedGameDate = simpleDateFormat.format(gameDate.getTime(), bufferToAppendTo, fieldPosition);

    return HtmlHeadings.getH3Heading(roadTeam.getName() + " @ " + homeTeam.getName() + " - " + formattedGameDate);
  }
}
