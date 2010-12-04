package com.kblaney.ohl.website;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

final class Urls
{
  private static final String PROTOCOL = "http";
  private static final String HOST = "www.ontariohockeyleague.com";
  private static final String STATS = "/stats/";
  private static final String TEAM_STATS_DISPLAY_PHP =
        STATS + "statdisplay.php";
  private static final String PLAYER_STATS_PHP = STATS + "player.php";
  private static final String PLAYER_GAME_BY_GAME_PHP =
        STATS + "gamebygame.php";
  private static final String SKATERS_TYPE = "skaters";
  private static final String TYPE = "type";
  private static final String TEAM_NUM = "subType";
  private static final String PAIR_SEPARATOR = "&";
  private static final String SEASON_ID_KEY = "season_id";
  private static final String SEASON_ID = "42";
  private static final String PLAYER_STATS_FILE =
        TEAM_STATS_DISPLAY_PHP + PhpUtil.PAIRS_SEPARATOR +
        PhpUtil.getKeyValueString(TYPE, SKATERS_TYPE) + PAIR_SEPARATOR +
        PhpUtil.getKeyValueString(SEASON_ID_KEY, SEASON_ID);

  private Urls() {}

  public static URL getPlayerStatsUrl()
  {
    try
    {
      return new URL(PROTOCOL, HOST, PLAYER_STATS_FILE);
    }
    catch (final MalformedURLException e)
    {
      throw new IllegalStateException(e);
    }
  }

  public static URL getSkaterStatsUrl(final int teamNum) throws IOException
  {
    return getStatsUrl(teamNum, /*isForSkaters=*/true);
  }

  private static URL getStatsUrl(final int teamNum, final boolean isForSkaters)
        throws IOException
  {
    final String file = TEAM_STATS_DISPLAY_PHP + PhpUtil.PAIRS_SEPARATOR +
          PhpUtil.getKeyValueString(TYPE, getType(isForSkaters)) +
          PAIR_SEPARATOR +
          PhpUtil.getKeyValueString(TEAM_NUM, Integer.toString(teamNum)) +
          PAIR_SEPARATOR +
          PhpUtil.getKeyValueString(SEASON_ID_KEY, SEASON_ID);

    try
    {
      return new URL(PROTOCOL, HOST, file);
    }
    catch (final MalformedURLException e)
    {
      throw new IllegalStateException(e);
    }
  }

  public static URL getGoalieStatsUrl(final int teamNum) throws IOException
  {
    return getStatsUrl(teamNum, /*isForSkaters=*/false);
  }

  private static String getType(final boolean isForSkaters)
  {
    if (isForSkaters)
    {
      return SKATERS_TYPE;
    }
    else
    {
      return "goalies";
    }
  }

  public static URL getPlayerBioUrl(final String playerId)
  {
    try
    {
      return new URL(PROTOCOL, HOST,
            PLAYER_STATS_PHP + PhpUtil.PAIRS_SEPARATOR + playerId);
    }
    catch (final MalformedURLException e)
    {
      throw new IllegalStateException(e);
    }
  }

  public static URL getPlayerGameByGameUrl(final String playerId)
  {
    try
    {
      return new URL(PROTOCOL, HOST,
            PLAYER_GAME_BY_GAME_PHP + PhpUtil.PAIRS_SEPARATOR + playerId);
    }
    catch (final MalformedURLException e)
    {
      throw new IllegalStateException(e);
    }
  }
}
