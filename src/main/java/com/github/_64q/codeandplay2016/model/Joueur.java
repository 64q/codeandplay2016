package com.github._64q.codeandplay2016.model;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * Représente un joueur
 *
 * @author qlebourgeois &lt;contact@qlebourgeois.me&gt;
 */
public class Joueur {

  private String playerId;

  private String playerName;

  private List<Fighters> fighters = Lists.newArrayList();

  public String getPlayerId() {
    return playerId;
  }

  public void setPlayerId(String playerId) {
    this.playerId = playerId;
  }

  public String getPlayerName() {
    return playerName;
  }

  public void setPlayerName(String playerName) {
    this.playerName = playerName;
  }

  public List<Fighters> getFighters() {
    return fighters;
  }

  public void setFighters(List<Fighters> fighters) {
    this.fighters = fighters;
  }

  @Override
  public String toString() {
    return "[playerName=" + playerName + ", fighters=" + fighters + "]";
  }



}
