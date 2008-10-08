package com.kblaney.ohl;

import com.kblaney.commons.lang.ArgChecker;

/**
 * Stores the streaks for one player.
 * <p>
 * Note that streaks can not be negative. Any time a negative number is
 * passed into any method accepting a streak, an
 * <code>IllegalArgumentException</code> is thrown.
 */
public class PlayerStreaks
{
   private int goalStreak;
   private int assistStreak;
   private int pointStreak;

   /**
    * Constructs a <code>PlayerStreaks</code> object with all fields
    * zeroed.
    */
   public PlayerStreaks()
   {
      final int initialStreak = 0;
      setGoalStreak( initialStreak );
      setAssistStreak( initialStreak );
      setPointStreak( initialStreak );
   }

   /**
    * Constructs a <code>PlayerStreaks</code> object based on specified
    * values.
    * @param goalStreak the goal streak
    * @param assistStreak the assist streak
    * @param pointStreak the point streak
    */
   public PlayerStreaks( final int goalStreak, final int assistStreak,
         final int pointStreak )
   {
      setGoalStreak( goalStreak );
      setAssistStreak( assistStreak );
      setPointStreak( pointStreak );
   }

   /**
    * Constructs a <code>PlayerStreaks</code> based on another
    * <code>PlayerStreaks</code>.
    * @param playerStreaks the other <code>PlayerStreaks</code>
    */
   public PlayerStreaks( final PlayerStreaks playerStreaks )
   {
      setGoalStreak( playerStreaks.goalStreak );
      setAssistStreak( playerStreaks.assistStreak );
      setPointStreak( playerStreaks.pointStreak );
   }

   /**
    * Gets the goal streak.
    * @return the goal streak
    */
   public int getGoalStreak()
   {
      return this.goalStreak;
   }

   /**
    * Sets the goal streak
    * @param goalStreak the goal streak
    */
   public void setGoalStreak( final int goalStreak )
   {
      ArgChecker.checkIfNegative( goalStreak, "goalStreak" );

      this.goalStreak = goalStreak;
   }

   /**
    * Gets the assist streak.
    * @return the assist streak
    */
   public int getAssistStreak()
   {
      return this.assistStreak;
   }

   /**
    * Sets the assist streak
    * @param assistStreak the assist streak
    */
   public void setAssistStreak( final int assistStreak )
   {
      ArgChecker.checkIfNegative( assistStreak, "assistStreak" );

      this.assistStreak = assistStreak;
   }

   /**
    * Gets the point streak.
    * @return the point streak
    */
   public int getPointStreak()
   {
      return this.pointStreak;
   }

   /**
    * Sets the point streak
    * @param pointStreak the point streak
    */
   public void setPointStreak( final int pointStreak )
   {
      ArgChecker.checkIfNegative( pointStreak, "pointStreak" );

      this.pointStreak = pointStreak;
   }
}
