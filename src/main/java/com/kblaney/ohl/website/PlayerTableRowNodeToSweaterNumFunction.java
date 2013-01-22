package com.kblaney.ohl.website;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import org.w3c.dom.Node;

final class PlayerTableRowNodeToSweaterNumFunction implements Function<Node, Optional<Integer>>
{
  public Optional<Integer> apply(final Node tableRowNode)
  {
    try
    {
      final String sweaterNumString = Nodes.getChildNodeValue(tableRowNode, 1);
      final int sweaterNum = Integer.parseInt(sweaterNumString);
      final int minSweaterNum = 1;
      if (sweaterNum >= minSweaterNum)
      {
        return Optional.of(sweaterNum);
      }
      return Optional.absent();
    }
    catch (final NumberFormatException e)
    {
      return Optional.absent();
    }
  }
}
