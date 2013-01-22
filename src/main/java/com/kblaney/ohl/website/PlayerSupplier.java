package com.kblaney.ohl.website;

import com.kblaney.ohl.Player;
import com.kblaney.ohl.gamesheets.ProgressIndicator;
import java.io.IOException;
import org.w3c.dom.Node;

interface PlayerSupplier
{
  Player getPlayer(Node tableRowNode, ProgressIndicator progressIndicator) throws IOException;
}
