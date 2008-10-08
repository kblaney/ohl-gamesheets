package com.kblaney.ohl;

import com.kblaney.commons.lang.ArgChecker;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * An OHL team.
 *
 * This class is immutable.
 */
public final class Team
{
   private final String name;
   private final String id;

   /**
    * Constructs a new instance of Team with a specified name and ID.
    *
    * @param name the team's name, which can't be null
    * @param id the team's ID, which can't be null
    */
   public Team( final String name, final String id )
   {
      ArgChecker.checkIfNull( name, "name" );
      ArgChecker.checkIfNull( id, "id" );

      this.name = name;
      this.id = id;
   }

   /**
    * Gets this team's name.
    *
    * @return this team's name
    */
   public String getName()
   {
      return this.name;
   }
   
   /**
    * Gets this team's ID.
    *
    * @return this team's ID
    */
   public String getId()
   {
      return this.id;
   }

   /** {@inheritDoc} */
   @Override
   public String toString()
   {
      return this.name;
   }

   /** {@inheritDoc} */
   @Override
   public boolean equals( final Object otherObject )
   {
      final boolean areEqual;

      if (otherObject instanceof Team)
      {
         if (otherObject != this)
         {
            final Team otherTeam = (Team) otherObject;
            areEqual = new EqualsBuilder().
                  append( this.id, otherTeam.id ).
                  append( this.name, otherTeam.name ).isEquals();
         }
         else
         {
            // The other object is the same exact object.
            //
            areEqual = true;
         }
      }
      else
      {
         // The other object is not a Team.
         //
         areEqual = false;
      }

      return areEqual;
   }

   /** {@inheritDoc} */
   @Override
   public int hashCode()
   {
      return new HashCodeBuilder().
            append( this.id ).
            append( this.name ).hashCode();
   }
}
