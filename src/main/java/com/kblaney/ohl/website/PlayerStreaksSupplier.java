package com.kblaney.ohl.website;

import com.kblaney.commons.xml.XmlUtil;
import com.kblaney.ohl.PlayerStreaks;
import java.net.URL;
import javax.xml.transform.TransformerException;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

final class PlayerStreaksSupplier
{
  public PlayerStreaks get(final String playerId, final String position)
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
}
