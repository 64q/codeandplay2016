package com.github._64q.codeandplay2016.model;

public class Plateau {
  private int nbrActionLeft;

  private Joueur player1;

  private Joueur player2;

  public int getNbrActionLeft() {
    return nbrActionLeft;
  }

  public void setNbrActionLeft(int nbrActionLeft) {
    this.nbrActionLeft = nbrActionLeft;
  }

  public Joueur getPlayer1() {
    return player1;
  }

  public void setPlayer1(Joueur player1) {
    this.player1 = player1;
  }

  public Joueur getPlayer2() {
    return player2;
  }

  public void setPlayer2(Joueur player2) {
    this.player2 = player2;
  }
}
