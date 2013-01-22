package com.kblaney.ohl.website;

import com.google.common.base.Function;
import com.kblaney.ohl.PlayerType;
import org.w3c.dom.Node;

final class PlayerTableRowNodeToPlayerTypeFunction implements Function<Node, PlayerType>
{
  public PlayerType apply(final Node tableRowNode)
  {
    final String nonBreakingSpace = "\u00A0";
    final String playerTypeCellValue = Nodes.getChildNodeValue(tableRowNode, 0);
    if ("*".equals(playerTypeCellValue))
    {
      return PlayerType.ROOKIE;
    }
    else if (nonBreakingSpace.equals(playerTypeCellValue))
    {
      return PlayerType.VETERAN;
    }
    else
    {
      return PlayerType.NOT_ACTIVE;
    }
  }
}
