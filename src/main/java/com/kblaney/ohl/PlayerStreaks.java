package com.kblaney.ohl;

import com.kblaney.commons.lang.ArgChecker;

/**
 * One player's streaks.
 *
 * <p>
 * Note that this class does not have a public constructor.  Rather, instances
 * are created using the PlayerStreaks.Builder class.
 * </p>
 */
public final class PlayerStreaks
{
   /**
    * Builds PlayerStreaks instances.
    */
   public static final class Builder
   {
      private int goalStreak;
      private int assistStreak;
      private int pointStreak;

      /**
       * Constructs a new instance of Builder.
       */
      public Builder()
      {
      }

      /**
       * Builds a PlayerStreaks instance.
       *
       * @return the PlayerStreaks instance
       */
      public PlayerStreaks build()
      {
         final PlayerStreaks playerStreaks = new PlayerStreaks();
         playerStreaks.goalStreak = this.goalStreak;
         playerStreaks.assistStreak = this.assistStreak;
         playerStreaks.pointStreak = this.pointStreak;
         return playerStreaks;
      }

      /**
       * Sets the goal streak.
       *
       * @param goalStreak the goal streak, which can't be negative
       *
       * @return the builder instance
       */
      public Builder setGoalStreak( final int goalStreak )
      {
         ArgChecker.checkIfNegative( goalStreak, "goalStreak" );
         this.goalStreak = goalStreak;
         return this;
      }

      /**
       * Sets the assist streak.
       *
       * @param assistStreak the assist streak, which can't be negative
       *
       * @return the builder instance
       */
      public Builder setAssistStreak( final int assistStreak )
      {
         ArgChecker.checkIfNegative( assistStreak, "assistStreak" );
         this.assistStreak = assistStreak;
         return this;
      }

      /**
       * Sets the point streak.
       *
       * @param pointStreak the point streak, which can't be negative
       *
       * @return the builder instance
       */
      public Builder setPointStreak( final int pointStreak )
      {
         ArgChecker.checkIfNegative( pointStreak, "pointStreak" );
         this.pointStreak = pointStreak;
         return this;
      }
   }

   private int goalStreak;
   private int assistStreak;
   private int pointStreak;

   /**
    * Constructs a new instance of PlayerStreaks.
    */
   private PlayerStreaks()
   {
   }

   /**
    * Gets the goal streak.
    *
    * @return the goal streak
    */
   public int getGoalStreak()
   {
      return this.goalStreak;
   }

   /**
    * Gets the assist streak.
    *
    * @return the assist streak
    */
   public int getAssistStreak()
   {
      return this.assistStreak;
   }

   /**
    * Gets the point streak.
    *
    * @return the point streak
    */
   public int getPointStreak()
   {
      return this.pointStreak;
   }
}
