package com.kblaney.ohl.website;

import com.kblaney.commons.xml.UrlToDomDocumentFunction;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public final class GoalieTableRowNodeListSupplierTest
{
  private UrlToDomDocumentFunction urlToDomDocumentFunction;
  private GoalieTableRowNodeListSupplier supplier;

  @Before
  public void setUp()
  {
    urlToDomDocumentFunction = mock(UrlToDomDocumentFunction.class);
    supplier = new GoalieTableRowNodeListSupplier(urlToDomDocumentFunction);
  }

  @Test
  public void get_twoGoalies()
  {
    assertTrue(true);
  }
}
