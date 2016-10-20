package com.github._64q.codeandplay2016.model;

import java.util.List;

public class Plateau {
  private int nbrTurnsLeft;

  private String playerMoves;

  private List<Joueur> playerBoards;

  public int getNbrTurnsLeft() {
    return nbrTurnsLeft;
  }

  public void setNbrTurnsLeft(int nbrActionLeft) {
    this.nbrTurnsLeft = nbrActionLeft;
  }

  public String getPlayerMoves() {
    return playerMoves;
  }

  public void setPlayerMoves(String playerMoves) {
    this.playerMoves = playerMoves;
  }

  public List<Joueur> getPlayerBoards() {
    return playerBoards;
  }

  public void setPlayerBoards(List<Joueur> playerBoards) {
    this.playerBoards = playerBoards;
  }


}
