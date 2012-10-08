package com.kblaney.ohl.website;

import com.google.common.base.Function;
import com.google.inject.Inject;
import com.kblaney.ohl.PlayerStreaks;
import org.apache.commons.lang.math.NumberUtils;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

final class PlayerStreaksSupplierImpl implements PlayerStreaksSupplier
{
  private final Function<String, NodeList> toGameRowNodeListFunction;

  @Inject
  public PlayerStreaksSupplierImpl(
        final Function<String, NodeList> toGameRowNodeListFunction)
  {
    this.toGameRowNodeListFunction = toGameRowNodeListFunction;
  }

  public PlayerStreaks get(final String gameByGameFilePath, final String position)
  {
    if (position.equals("G"))
    {
      return getGoalieStreaks();
    }
    else
    {
      return getSkaterStreaks(gameByGameFilePath);
    }
  }

  private PlayerStreaks getSkaterStreaks(final String gameByGameFilePath)
  {
    final NodeList gameRowNodeList =
          toGameRowNodeListFunction.apply(gameByGameFilePath);

    int goalStreak = 0;
    int assistStreak = 0;
    int pointStreak = 0;
    boolean onGoalStreak = true;
    boolean onAssistStreak = true;
    boolean onPointStreak = true;

    int i = gameRowNodeList.getLength() - 1;
    while (onPointStreak && i >= 0)
    {
      final Node gameRowNode = gameRowNodeList.item(i);
      final int numGoalsInGame = getNumGoalsInGame(gameRowNode);
      final int numAssistsInGame = getNumAssistsInGame(gameRowNode);
      final int numPointsInGame = getNumPointsInGame(gameRowNode);
      if (onGoalStreak && (numGoalsInGame > 0))
      {
        goalStreak++;
      }
      onGoalStreak = onGoalStreak && (numGoalsInGame > 0);

      if (onAssistStreak && (numAssistsInGame > 0))
      {
        assistStreak++;
      }
      onAssistStreak = onAssistStreak && (numAssistsInGame > 0);

      if (numPointsInGame > 0)
      {
        pointStreak++;
      }
      onPointStreak = onPointStreak && (numPointsInGame > 0);

      i--;
    }

    return new PlayerStreaks.Builder().setGoalStreak(goalStreak).
          setAssistStreak(assistStreak).
          setPointStreak(pointStreak).build();
  }

  private PlayerStreaks getGoalieStreaks()
  {
    return new PlayerStreaks.Builder().build();
  }

  private int getNumGoalsInGame(final Node gameRowNode)
  {
    final int goalIndex = 4;
    return NumberUtils.toInt(getGameRowText(gameRowNode, goalIndex), 0);
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

  private int getNumAssistsInGame(final Node gameRowNode)
  {
    final int assistIndex = 5;
    return NumberUtils.toInt(getGameRowText(gameRowNode, assistIndex), 0);
  }

  private int getNumPointsInGame(final Node gameRowNode)
  {
    final int pointsIndex = 6;
    return NumberUtils.toInt(getGameRowText(gameRowNode, pointsIndex), 0);
  }
}
