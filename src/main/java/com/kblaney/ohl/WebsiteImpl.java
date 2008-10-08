package com.kblaney.ohl;

import com.kblaney.commons.lang.ArgChecker;
import com.kblaney.commons.xml.XmlUtil;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.DOMReader;

/**
 * The implementation of the OHL website.
 */
public class WebsiteImpl implements Website
{
   private static final String PROTOCOL = "http";
   private static final String HOST = "www.ontariohockeyleague.com";
   private static final String STATS = "/stats/";
   private static final String ROSTER_PHP = "roster.php";
   private static final String TEAM_STATS_DISPLAY_PHP =
         STATS + "statdisplay.php";
   private static final String PLAYER_STATS_PHP = STATS + "player.php";
   private static final String PLAYER_GAME_BY_GAME_PHP =
         STATS + "gamebygame.php";
   private static final String SEASON_ID = "26";
   private static final String SCORING_TYPE = "skaters";
   private static final String GOALIES_TYPE = "goalies";
   private static final String TYPE = "type";
   private static final String TEAM_NUM = "subType";
   private static final String HOST_FILE_SEPARATOR = "/";
   private static final String PHP_PAIRS_SEPARATOR = "?";
   private static final String KEY_VALUE_SEPARATOR = "=";
   private static final String PAIR_SEPARATOR = "&";

   private final URL mainRosterUrl;

   public WebsiteImpl()
   {
      try
      {
         this.mainRosterUrl = new URL( PROTOCOL, HOST, STATS + ROSTER_PHP );
      }
      catch (MalformedURLException malformedUrlException)
      {
         throw new RuntimeException( malformedUrlException );
      }
   }

   /** {@inheritDoc} */
   public Set<Team> getTeams()
   {
      try
      {
         final Set<Team> teams = new HashSet<Team>();

         final org.w3c.dom.Document domDocument = XmlUtil.getXmlDocument(
               this.mainRosterUrl );
         final Document document = new DOMReader().read( domDocument );
         final List bellevilleBullsObjects = document.selectNodes(
               "//li[a='Belleville Bulls']" );
         final Object bellevilleBullsObject = bellevilleBullsObjects.get( 0 );
         final Node bellevilleBullsElement = (Element) bellevilleBullsObject;
         final Element teamListElement = bellevilleBullsElement.getParent();
         final List teamObjects = teamListElement.selectNodes( "li/a" );
         final Pattern teamIdPattern = Pattern.compile( "team_id=(\\d+)" );
         for (Object teamObject : teamObjects)
         {
            final Element teamElement = (Element) teamObject;

            final String teamName = teamElement.getText();

            final String hrefAttrValue = (String) teamElement.
                  attribute( "href" ).getValue();
            final Matcher teamIdMatcher = teamIdPattern.
                  matcher( hrefAttrValue );
            if (teamIdMatcher.find())
            {
               final String teamId = teamIdMatcher.group( 1 );
               teams.add( new Team( teamName, teamId ) );
            }
            else
            {
               throw new RuntimeException( "Can't find team ID" );
            }
         }

         return teams;
      }
      catch (Exception exception)
      {
         // TODO:  Throw my own exception
         throw new RuntimeException( exception );
      }
   }

   /**
    * Gets a key and value string to use in a PHP query.
    *
    * @param key the key, which can't be null
    * @param value the value, which can't be null
    *
    * @return the key and value string
    */
   private String getKeyValueString( final String key, final String value )
   {
      ArgChecker.checkIfNull( key, "key" );
      ArgChecker.checkIfNull( value, "value" );

      return key + KEY_VALUE_SEPARATOR + value;
   }
}
