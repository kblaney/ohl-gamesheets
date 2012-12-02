package com.kblaney.ohl.website;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import org.w3c.dom.Node;

final class PlayerTableRowNodeToSweaterNumFunction
      implements Function<Node, Optional<Integer>>
{
  public Optional<Integer> apply(final Node tableRowNode)
  {
    try
    {
      final String sweaterNumString = Nodes.getChildNodeValue(tableRowNode, 1);
      return Optional.of(Integer.parseInt(sweaterNumString));
    }
    catch (final NumberFormatException e)
    {
      return Optional.absent();
    }
  }
}
