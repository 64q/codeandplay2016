package com.github._64q.codeandplay2016.model;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;

public class Mouvement {
  List<FighterMouvement> mouvements = Lists.newArrayList();

  private FighterClass selection;
  private TypeAction typeAction;

  public Mouvement(String readEntity, TypeAction action) {
    if (TypeAction.CHOIX.equals(action)) {
      selection = FighterClass.valueOf(readEntity);
    } else if (TypeAction.NA.equals(action)) {

      System.err.println("ttt");
    } else {
      String[] fighters = readEntity.split("$");

      for (String fighter : fighters) {
        FighterMouvement mouve = new FighterMouvement();

        String[] str = fighter.split(",");

        mouve.setAttaquant(str[0]);
        mouve.setType(TypeMouvement.valueOf(str[1]));
        mouve.setCible(str[2]);

        mouvements.add(mouve);
      }
    }

    this.typeAction = action;
  }

  public Mouvement() {
    // yolo
  }

  public List<FighterMouvement> getMouvements() {
    return mouvements;
  }

  public void setMouvements(List<FighterMouvement> mouvements) {
    this.mouvements = mouvements;
  }

  public String serialize() {
    if (typeAction == TypeAction.CHOIX) {
      return selection.name();
    }

    StringBuilder builder = new StringBuilder();

    for (FighterMouvement m : mouvements) {
      builder.append(m.getAttaquant() + "," + m.getType().name() + "," + m.getCible() + "$");
    }

    return StringUtils.substring(builder.toString(), 0, builder.toString().length() - 1);
  }

  public TypeAction getTypeAction() {
    return typeAction;
  }

  public void setTypeAction(TypeAction typeAction) {
    this.typeAction = typeAction;
  }

  public FighterClass getSelection() {
    return selection;
  }

  public void setSelection(FighterClass selection) {
    this.selection = selection;
  }

  @Override
  public String toString() {
    return "Mouvement [mouvements=" + mouvements + ", selection=" + selection + ", typeAction="
        + typeAction + "]";
  }


}
