package com.kblaney.ohl.website;

import com.google.common.base.Function;
import com.kblaney.ohl.PlayerType;
import org.w3c.dom.Node;

final class PlayerTableRowNodeToPlayerTypeFunction
      implements Function<Node, PlayerType>
{
  public PlayerType apply(final Node tableRowNode)
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
}
