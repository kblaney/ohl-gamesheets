package com.kblaney.ohl.website;

import java.io.IOException;
import org.w3c.dom.NodeList;

interface TeamNumToNodeListFunction
{
  NodeList apply(int teamNum) throws IOException;
}
