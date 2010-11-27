package com.kblaney.ohl.website;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.tidy.Tidy;
import static org.junit.Assert.*;

public final class PlayerTableRowNodeToIdFunctionTest
{
  @Test
  public void apply()
  {
    final Document doc = Tidy.createEmptyDocument();
    final Element rootElement = doc.createElement("root");
    final Element trElement = doc.createElement("tr");
    final Element firstTdElement = doc.createElement("td");
    final Element secondTdElement = doc.createElement("td");
    final Element playerTdElement = doc.createElement("td");
    final Element linkElement = doc.createElement("a");
    linkElement.setAttribute("href", "blah blah blah?1234");
    rootElement.appendChild(trElement);
    trElement.appendChild(firstTdElement);
    trElement.appendChild(secondTdElement);
    trElement.appendChild(playerTdElement);
    playerTdElement.appendChild(linkElement);
    assertEquals("1234", new PlayerTableRowNodeToIdFunction().apply(trElement));
  }
}
