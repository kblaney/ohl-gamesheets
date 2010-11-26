package com.kblaney.ohl.website;

import com.kblaney.ohl.Team;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.builder.HashCodeBuilder;

final class NumberedTeam implements Team
{
  private String name;
  private int num;

  public NumberedTeam(final String name, final int num)
  {
    this.name = name;
    this.num = num;
  }

  @Override
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

    if (thatObject instanceof Team)
    {
      final Team that = (Team) thatObject;
      return ObjectUtils.equals(name, that.getName());
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
