package com.kblaney.ohl.gamesheets.html;

import com.google.common.base.Predicate;
import com.kblaney.ohl.Player;
import com.kblaney.ohl.PlayerType;

final class ActivePlayerPredicate implements Predicate<Player>
{
  @Override
  public boolean apply(final Player player)
  {
    return ((player.getType() != PlayerType.NOT_ACTIVE) &&
          (player.getStats().getNumGamesPlayed() != 0));
  }
}
