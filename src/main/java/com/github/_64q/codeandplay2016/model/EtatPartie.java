package com.github._64q.codeandplay2016.model;

public enum EtatPartie {
  CANPLAY(false), CANTPLAY(false), VICTORY(true), DEFEAT(true), DRAW(true), CANCELLED(true);

  private boolean etatFinal;

  public boolean isFinal() {
    return etatFinal;
  }

  EtatPartie(boolean etatFinal) {
    this.etatFinal = etatFinal;
  }
}
