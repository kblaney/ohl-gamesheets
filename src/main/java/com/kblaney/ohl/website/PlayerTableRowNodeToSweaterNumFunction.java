package com.kblaney.ohl.website;

import org.apache.commons.lang.math.NumberUtils;
import org.w3c.dom.Node;

final class PlayerTableRowNodeToSweaterNumFunction
{
  public int apply(final Node tableRowNode)
  {
    final String sweaterNumString = Nodes.getChildNodeValue(tableRowNode, 1);
    final int sweaterNumIfParseError = 0;
    return NumberUtils.toInt(sweaterNumString, sweaterNumIfParseError);
  }
}
