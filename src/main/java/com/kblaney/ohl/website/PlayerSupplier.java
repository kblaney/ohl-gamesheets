package com.kblaney.ohl.website;

import com.kblaney.ohl.ProgressIndicator;
import com.kblaney.ohl.Player;
import java.io.IOException;
import org.w3c.dom.Node;

interface PlayerSupplier
{
  Player getPlayer(Node tableRowNode, ProgressIndicator progressIndicator) throws IOException;
}
