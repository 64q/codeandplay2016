package com.github._64q.codeandplay2016.model;

public class VariablesMoteur {
  private String nom;
  private String idEquipe;
  private String idPartie;

  private Plateau plateau;

  private Mouvement dernierMouvement;

  public String getNom() {
    return this.nom;
  }

  public void setNom(String nom) {
    this.nom = nom;
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

  public Mouvement getDernierMouvement() {
    return this.dernierMouvement;
  }

  public void setDernierMouvement(Mouvement dernierMouvement) {
    this.dernierMouvement = dernierMouvement;
  }
}
