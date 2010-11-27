package com.kblaney.ohl.website;

import com.kblaney.commons.xml.XmlUtil;
import com.kblaney.ohl.Player;
import com.kblaney.ohl.PlayerBio;
import com.kblaney.ohl.PlayerStats;
import com.kblaney.ohl.PlayerStreaks;
import com.kblaney.ohl.PlayerType;
import com.kblaney.ohl.gamesheets.ProgressIndicator;
import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.transform.TransformerException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

final class PlayerSupplier
{
  public Player getPlayer(final Node tableRowNode,
        final ProgressIndicator progressIndicator) throws IOException
  {
    if ((tableRowNode.getChildNodes() != null) &&
          (tableRowNode.getChildNodes().getLength() == 14))
    {
      final String playerId = getPlayerId(tableRowNode);
      final String playerName = getPlayerName(tableRowNode);

      progressIndicator.setPlayerInProgress(playerName);

      final PlayerType playerType = getPlayerType(tableRowNode);
      final int sweaterNum = getSweaterNum(tableRowNode);
      final PlayerStats playerStats = getPlayerStats(tableRowNode);
      final PlayerBio playerBio = getPlayerBio(playerId);
      final PlayerStreaks playerStreaks = getPlayerStreaks(playerId,
            playerBio.getPosition());

      return new Player(playerName, playerType, sweaterNum, playerStats,
            playerBio, playerStreaks);
    }
    else
    {
      throw new IllegalArgumentException("Wrong number of child nodes");
    }
  }
  private String getPlayerId(final Node tableRowNode)
  {
    return new PlayerTableRowNodeToIdFunction().apply(tableRowNode);
  }

  private String getPlayerName(final Node tableRowNode)
  {
    return new PlayerTableRowNodeToNameFunction().apply(tableRowNode);
  }

  private PlayerType getPlayerType(final Node tableRowNode)
  {
    final String isRookie = "*";
    // Veterans have a non-breaking space in the player type column.
    final String isVeteran = "\u00A0";

    final String nodeValue = Nodes.getChildNodeValue(tableRowNode, 0);
    if (isRookie.equals(nodeValue))
    {
      return PlayerType.ROOKIE;
    }
    else if (isVeteran.equals(nodeValue))
    {
      return PlayerType.VETERAN;
    }
    else
    {
      return PlayerType.NOT_ACTIVE;
    }
  }

  private int getSweaterNum(final Node tableRowNode)
  {
    final String sweaterNumString = Nodes.getChildNodeValue(tableRowNode, 1);
    final int sweaterNumIfParseError = 0;
    return NumberUtils.toInt(sweaterNumString, sweaterNumIfParseError);
  }

  private PlayerStats getPlayerStats(final Node tableRowNode)
  {
    final int numChildNodes = tableRowNode.getChildNodes().getLength();
    if (numChildNodes >= 11)
    {
      final String numGamesPlayedString = Nodes.getChildNodeValue(
            tableRowNode, 4);
      final String numGoalsString = Nodes.getChildNodeValue(
            tableRowNode, 5);
      final String numAssistsString = Nodes.getChildNodeValue(
            tableRowNode, 6);
      final String numPointsString = Nodes.getChildNodeValue(
            tableRowNode, 7);
      final String plusMinusString = StringUtils.remove(
            Nodes.getChildNodeValue(tableRowNode, 8), '+');
      final String numPenaltyMinutesString = Nodes.getChildNodeValue(
            tableRowNode, 9);
      final String numPowerplayGoalsString = Nodes.getChildNodeValue(
            tableRowNode, 10);
      final String numShorthandedGoalsString = Nodes.getChildNodeValue(
            tableRowNode, 11);

      final int numGamesPlayed = Integer.parseInt(numGamesPlayedString);
      final int numGoals = Integer.parseInt(numGoalsString);
      final int numAssists = Integer.parseInt(numAssistsString);
      final int numPoints = Integer.parseInt(numPointsString);
      final int plusMinus = Integer.parseInt(plusMinusString);
      final int numPenaltyMinutes = Integer.parseInt(
            numPenaltyMinutesString);
      final int numPowerplayGoals = Integer.parseInt(
            numPowerplayGoalsString);
      final int numShorthandedGoals = Integer.parseInt(
            numShorthandedGoalsString);

      return new PlayerStats.Builder().setNumGamesPlayed(numGamesPlayed).
            setNumGoals(numGoals).
            setNumAssists(numAssists).
            setNumPoints(numPoints).
            setPlusMinus(plusMinus).
            setNumPenaltyMinutes(numPenaltyMinutes).
            setNumPowerPlayGoals(numPowerplayGoals).
            setNumShorthandedGoals(numShorthandedGoals).build();
    }
    else
    {
      throw new IllegalArgumentException(
            "Not enough child nodes to get player stats: " + numChildNodes);
    }
  }

  private PlayerBio getPlayerBio(final String playerId) throws IOException
  {
    try
    {
      final URL url = Urls.getPlayerBioUrl(playerId);
      final Document document = XmlUtil.getXmlDocument(url);
      final Node bioDivNode = XPathAPI.selectSingleNode(
            document.getDocumentElement(),
            "//div[@class='profile']/div[@class='details']/table");
      if (bioDivNode == null)
      {
        throw new IOException("Can't find player bio table node: " + playerId);
      }
      else
      {
        return new PlayerBio.Builder().
              setBirthYear(getBirthYear(bioDivNode)).
              setPosition(getPosition(bioDivNode)).
              setHeight(getHeight(bioDivNode)).
              setWeight(getWeight(bioDivNode)).
              setHomeTown(getHomeTown(bioDivNode)).build();
      }
    }
    catch (TransformerException e)
    {
      throw new IOException(e);
    }
  }

  private PlayerStreaks getPlayerStreaks(final String playerId,
        final String position)
  {
    int goalStreak = 0;
    int assistStreak = 0;
    int pointStreak = 0;

    if (!position.equals("G"))
    {
      final URL gameByGameUrl = Urls.getPlayerGameByGameUrl(playerId);
      final Document gameByGameDocument = XmlUtil.getXmlDocument(gameByGameUrl);
      if (gameByGameDocument == null)
      {
        throw new IllegalStateException(
              "<html>Game-by-game URL not found: " + gameByGameUrl);
      }

      try
      {
        final int goalIndex = 4;
        final int assistIndex = 5;
        final int pointIndex = 6;
        final NodeList nodeList = XPathAPI.selectNodeList(
              gameByGameDocument.getDocumentElement(),
              "//div[@id='gamebygameBlock']/table[@class='statsTable']/tr[td[a]]");
        if (nodeList.getLength() > 0)
        {
          boolean onGoalStreak = true;
          boolean onAssistStreak = true;
          boolean onPointStreak = true;

          int i = nodeList.getLength() - 1;
          while (onPointStreak && i >= 0)
          {
            final Node gameRowNode = nodeList.item(i);
            final int numGoals = NumberUtils.toInt(getGameRowText(
                  gameRowNode, goalIndex), 0);
            final int numAssists = NumberUtils.toInt(getGameRowText(
                  gameRowNode, assistIndex), 0);
            final int numPoints = NumberUtils.toInt(getGameRowText(
                  gameRowNode, pointIndex), 0);
            if (numGoals > 0)
            {
              if (onGoalStreak)
              {
                goalStreak++;
              }
            }
            else
            {
              onGoalStreak = false;
            }
            if (numAssists > 0)
            {
              if (onAssistStreak)
              {
                assistStreak++;
              }
            }
            else
            {
              onAssistStreak = false;
            }
            if (numPoints > 0)
            {
              if (onPointStreak)
              {
                pointStreak++;
              }
            }
            else
            {
              onPointStreak = false;
            }

            i--;
          }

        }
      }
      catch (final TransformerException e)
      {
        throw new IllegalStateException(e);
      }
    }

    return new PlayerStreaks.Builder().setGoalStreak(goalStreak).
          setAssistStreak(assistStreak).
          setPointStreak(pointStreak).build();
  }

  private String getGameRowText(final Node gameRowNode, final int itemIndex)
  {
    if (gameRowNode.getChildNodes().getLength() >= itemIndex)
    {
      final Node node = gameRowNode.getChildNodes().item(itemIndex);
      if (node.getFirstChild() != null)
      {
        return node.getFirstChild().getNodeValue();
      }
    }

    return null;
  }

  private String getBirthYear(final Node bioDivNode)
  {
    try
    {
      final Node birthdateRowNode = XPathAPI.selectSingleNode(
            bioDivNode, "//tr[td='Birthdate']");
      final String birthDate = Nodes.getFirstChildNodeValueOrEmpty(
            birthdateRowNode.getLastChild());
      final Pattern p = Pattern.compile("\\d\\d\\d\\d");
      final Matcher m = p.matcher(birthDate);
      if (m.find())
      {
        return m.group();
      }
      else
      {
        return StringUtils.EMPTY;
      }
    }
    catch (final TransformerException e)
    {
      throw new IllegalStateException("Can't get birth year", e);
    }
  }

  private String getPosition(final Node bioDivNode)
  {
    try
    {
      final Node rowNode = XPathAPI.selectSingleNode(bioDivNode,
            "//tr[td='Position']");
      final String positionLongForm = Nodes.getFirstChildNodeValueOrEmpty(
            rowNode.getLastChild());
      if (positionLongForm.contains("Defence"))
      {
        return "D";
      }
      else if ("Centre".equals(positionLongForm))
      {
        return "C";
      }
      else if ("Left Wing".equals(positionLongForm))
      {
        return "LW";
      }
      else if ("Right Wing".equals(positionLongForm))
      {
        return "RW";
      }
      else if ("Goaltender".equals(positionLongForm))
      {
        return "G";
      }
      else
      {
        return positionLongForm;
      }
    }
    catch (final TransformerException e)
    {
      throw new IllegalStateException("Can't get position", e);
    }
  }

  private String getHeight(final Node bioDivNode)
  {
    try
    {
      final Node rowNode = XPathAPI.selectSingleNode(bioDivNode,
            "//tr[td='Height']");
      final String height = Nodes.getFirstChildNodeValueOrEmpty(
            rowNode.getLastChild());
      return height.replace('\'', '.');
    }
    catch (final TransformerException e)
    {
      throw new IllegalStateException("Can't get height", e);
    }
  }

  private String getWeight(final Node bioDivNode)
  {
    try
    {
      final Node rowNode = XPathAPI.selectSingleNode(bioDivNode,
            "//tr[td='Weight']");
      return Nodes.getFirstChildNodeValueOrEmpty(rowNode.getLastChild());
    }
    catch (final TransformerException e)
    {
      throw new IllegalStateException("Can't get weight", e);
    }
  }

  private String getHomeTown(final Node bioDivNode)
  {
    try
    {
      final Node rowNode = XPathAPI.selectSingleNode(bioDivNode,
            "//tr[td='Birthplace']");

      if (rowNode.getLastChild().hasChildNodes())
      {
        final String nodeValue = rowNode.getLastChild().
              getFirstChild().getNodeValue();
        final Pattern pattern = Pattern.compile("^[^,]+, [^,]+");
        final Matcher matcher = pattern.matcher(nodeValue);
        if (matcher.find())
        {
          return matcher.group();
        }
        else
        {
          return nodeValue;
        }
      }
      else
      {
        return StringUtils.EMPTY;
      }
    }
    catch (final TransformerException e)
    {
      throw new IllegalStateException("Can't get hometown", e);
    }
  }
}
