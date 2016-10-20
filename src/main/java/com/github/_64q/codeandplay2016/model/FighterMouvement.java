package com.github._64q.codeandplay2016.model;

public class FighterMouvement {
  private String attaquant;

  private TypeMouvement type;

  private String cible;

  public FighterMouvement(String atk, TypeMouvement type, String cib) {
    this.attaquant = atk;
    this.type = type;
    this.cible = cib;
  }

  public FighterMouvement() {}

  public String getAttaquant() {
    return attaquant;
  }

  public void setAttaquant(String attaquant) {
    this.attaquant = attaquant;
  }

  public TypeMouvement getType() {
    return type;
  }

  public void setType(TypeMouvement type) {
    this.type = type;
  }

  public String getCible() {
    return cible;
  }

  public void setCible(String cible) {
    this.cible = cible;
  }

  @Override
  public String toString() {
    return "FighterMouvement [attaquant=" + attaquant + ", type=" + type + ", cible=" + cible + "]";
  }
}
