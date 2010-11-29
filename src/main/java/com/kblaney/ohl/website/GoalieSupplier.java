package com.kblaney.ohl.website;

import com.kblaney.ohl.Goalie;
import com.kblaney.ohl.gamesheets.ProgressIndicator;
import org.w3c.dom.Node;

interface GoalieSupplier
{
  Goalie get(Node goalieTableRowNode, ProgressIndicator progressIndicator);
}
