package com.kblaney.ohl.website;

import com.kblaney.ohl.PlayerStreaks;

interface PlayerStreaksSupplier
{
  PlayerStreaks get(String playerId, String position);
}
