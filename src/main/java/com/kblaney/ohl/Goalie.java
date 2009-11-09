package com.kblaney.ohl;

import com.kblaney.commons.lang.ArgAssert;

/**
 * A goalie.
 */
public class Goalie
{
   private final String name;
   private final GoalieStats stats;

   /**
    * Constructs a new instance of Goalie.
    *
    * @param name the goalie's name, which can't be null
    * @param stats the goalie's statistics, which can't be null
    */
   public Goalie( final String name, final GoalieStats stats )
   {
      ArgAssert.notNull( name, "name" );
      ArgAssert.notNull( stats, "stats" );

      this.name = name;
      this.stats = stats;
   }

   /**
    * Gets the goalie's name.
    *
    * @return the name
    */
   public String getName()
   {
      return this.name;
   }

   /**
    * Gets the goalie's statistics.
    *
    * @return the statistics
    */
   public GoalieStats getStats()
   {
      return this.stats;
   }
}
