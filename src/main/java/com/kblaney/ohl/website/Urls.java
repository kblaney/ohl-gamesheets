package com.kblaney.ohl.website;

import java.net.MalformedURLException;
import java.net.URL;

final class Urls
{
  private static final String SKATERS_TYPE = "skaters";
  private static final String TYPE_KEY = "type";

  private Urls()
  {
  }

  public static URL getPlayerStatsUrl()
  {
    final String file = getTeamStatsPhpFile() + PhpUtil.FILE_PAIRS_SEPARATOR + PhpUtil.getPair(TYPE_KEY, SKATERS_TYPE) +
          PhpUtil.PAIR_SEPARATOR + getSeasonPair();
    return getUrl(file);
  }

  private static String getTeamStatsPhpFile()
  {
    return getPhpFile("statdisplay.php");
  }

  private static String getPhpFile(final String phpFile)
  {
    return "/stats/" + phpFile;
  }

  private static String getSeasonPair()
  {
    final String key = "season_id";
    final String seasonValue = "51";
    return PhpUtil.getPair(key, seasonValue);
  }

  private static URL getUrl(final String file)
  {
    try
    {
      final String protocol = "http";
      final String host = "www.ontariohockeyleague.com";
      return new URL(protocol, host, file);
    }
    catch (final MalformedURLException e)
    {
      throw new IllegalStateException(e);
    }
  }

  public static URL getSkaterStatsUrl(final int teamNum)
  {
    return getStatsUrl(teamNum, /* isForSkaters= */true);
  }

  private static URL getStatsUrl(final int teamNum, final boolean isForSkaters)
  {
    final String teamNumKey = "team_id";
    final String file = getTeamStatsPhpFile() + PhpUtil.FILE_PAIRS_SEPARATOR +
          PhpUtil.getPair(TYPE_KEY, getType(isForSkaters)) + PhpUtil.PAIR_SEPARATOR +
          PhpUtil.getPair(teamNumKey, Integer.toString(teamNum)) + PhpUtil.PAIR_SEPARATOR + getSeasonPair();
    return getUrl(file);
  }

  public static URL getGoalieStatsUrl(final int teamNum)
  {
    return getStatsUrl(teamNum, /* isForSkaters= */false);
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
    final String phpFile = getPhpFile("player.php");
    return getUrl(phpFile + PhpUtil.FILE_PAIRS_SEPARATOR + playerId);
  }

  public static URL getPlayerGameByGameUrl(final String gameByGameFilePath)
  {
    return getUrl(gameByGameFilePath);
  }
}
