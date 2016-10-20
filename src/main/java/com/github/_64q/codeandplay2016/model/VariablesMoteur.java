package com.github._64q.codeandplay2016.model;

/**
 * Variables du moteur de jeu
 *
 * @author qlebourgeois &lt;contact@qlebourgeois.me&gt;
 */
public class VariablesMoteur {
  private String nomEquipe;
  private String idEquipe;
  private String idPartie;

  /**
   * Plateau de jeu, refresh a chaque fois que l'on doit jouer
   */
  private Plateau plateau;

  /**
   * Dernier mouvement renvoyé par l'API (celui de l'adversaire)
   */
  private Mouvement mouvementAdversaire;

  private Mouvement mouvementNous;

  public String getNomEquipe() {
    return this.nomEquipe;
  }

  public void setNomEquipe(String nom) {
    this.nomEquipe = nom;
  }

  public String getIdEquipe() {
    return this.idEquipe;
  }

  public void setIdEquipe(String identifiant) {
    this.idEquipe = identifiant;
  }

  public Plateau getPlateau() {
    return this.plateau;
  }

  public void setPlateau(Plateau plateau) {
    this.plateau = plateau;
  }

  public String getIdPartie() {
    return this.idPartie;
  }

  public void setIdPartie(String idPartie) {
    this.idPartie = idPartie;
  }

  public Mouvement getMouvementAdversaire() {
    return this.mouvementAdversaire;
  }

  public void setMouvementAdversaire(Mouvement mouvementAdversaire2) {
    this.mouvementAdversaire = mouvementAdversaire2;
  }

  public Mouvement getMouvementNous() {
    return this.mouvementNous;
  }

  public void setMouvementNous(Mouvement mouvementNous) {
    this.mouvementNous = mouvementNous;
  }


  public Joueur getNous() {
    if (getPlateau().getPlayerBoards().get(0).getPlayerId().equals("0")) {
      return getPlateau().getPlayerBoards().get(0);
    }

    return getPlateau().getPlayerBoards().get(1);
  }

  public Joueur getAdversaire() {
    if (!getPlateau().getPlayerBoards().get(0).getPlayerId().equals("0")) {
      return getPlateau().getPlayerBoards().get(0);
    }

    return getPlateau().getPlayerBoards().get(1);
  }

  public int getTour() {
    return 53 - getPlateau().getNbrTurnsLeft() + 1;
  }
}
