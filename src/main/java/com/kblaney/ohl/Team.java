package com.kblaney.ohl;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.builder.HashCodeBuilder;

public final class Team
{
  private String name;
  private int num;

  public Team(final String name, final int num)
  {
    this.name = name;
    this.num = num;
  }

  public String getName()
  {
    return name;
  }

  public int getNum()
  {
    return num;
  }

  @Override
  public boolean equals(final Object thatObject)
  {
    if (thatObject == null)
    {
      return false;
    }

    if (thatObject.getClass().equals(getClass()))
    {
      final Team that = (Team) thatObject;
      return ((ObjectUtils.equals(name, that.name)) && (num == that.num));
    }
    else
    {
      return false;
    }
  }

  @Override
  public int hashCode()
  {
    return new HashCodeBuilder().append(name).append(num).toHashCode();
  }

  @Override
  public String toString()
  {
    return name + ":" + num;
  }
}