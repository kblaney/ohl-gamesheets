package com.kblaney.ohl.website;

import com.kblaney.ohl.ProgressIndicator;
import com.kblaney.ohl.Goalie;
import org.w3c.dom.Node;

interface GoalieSupplier
{
  Goalie get(Node goalieTableRowNode, ProgressIndicator progressIndicator);
}
