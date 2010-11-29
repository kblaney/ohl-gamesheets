package com.kblaney.ohl.website;

import com.google.common.base.Function;
import org.apache.commons.lang.math.NumberUtils;
import org.w3c.dom.Node;

final class PlayerTableRowNodeToSweaterNumFunction
      implements Function<Node, Integer>
{
  public Integer apply(final Node tableRowNode)
  {
    final String sweaterNumString = Nodes.getChildNodeValue(tableRowNode, 1);
    final int sweaterNumIfParseError = 0;
    return NumberUtils.toInt(sweaterNumString, sweaterNumIfParseError);
  }
}
