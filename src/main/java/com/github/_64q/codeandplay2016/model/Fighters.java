package com.github._64q.codeandplay2016.model;

import java.util.List;

import com.google.common.collect.Lists;

public class Fighters {
  private FighterClass fighterClass;

  private int orderNumberInTeam;

  private boolean isDead;

  private int maxAvailableMana;
  private int maxAvailableLife;
  private int currentMana;
  private int currentLife;
  private List<FighterState> states = Lists.newArrayList();
  private String action;
  private String fighterID;
  private int receivedAttacks;
  private int diffMana;
  private int diffLife;

  public FighterClass getFighterClass() {
    return fighterClass;
  }

  public void setFighterClass(FighterClass fighterClass) {
    this.fighterClass = fighterClass;
  }

  public int getOrderNumberInTeam() {
    return orderNumberInTeam;
  }

  public void setOrderNumberInTeam(int orderNumberInTeam) {
    this.orderNumberInTeam = orderNumberInTeam;
  }

  public boolean getIsDead() {
    return isDead;
  }

  public void setDead(boolean isDead) {
    this.isDead = isDead;
  }

  public int getMaxAvailableMana() {
    return maxAvailableMana;
  }

  public void setMaxAvailableMana(int maxAvailableMana) {
    this.maxAvailableMana = maxAvailableMana;
  }

  public int getMaxAvailableLife() {
    return maxAvailableLife;
  }

  public void setMaxAvailableLife(int maxAvailableLife) {
    this.maxAvailableLife = maxAvailableLife;
  }

  public int getCurrentMana() {
    return currentMana;
  }

  public void setCurrentMana(int currentMana) {
    this.currentMana = currentMana;
  }

  public int getCurrentLife() {
    return currentLife;
  }

  public void setCurrentLife(int currentLife) {
    this.currentLife = currentLife;
  }

  public List<FighterState> getStates() {
    return states;
  }

  public void setStates(List<FighterState> states) {
    this.states = states;
  }

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public String getFighterID() {
    return fighterID;
  }

  public void setFighterID(String fighterID) {
    this.fighterID = fighterID;
  }

  public int getReceivedAttacks() {
    return receivedAttacks;
  }

  public void setReceivedAttacks(int receivedAttacks) {
    this.receivedAttacks = receivedAttacks;
  }

  public int getDiffMana() {
    return diffMana;
  }

  public void setDiffMana(int diffMana) {
    this.diffMana = diffMana;
  }

  public int getDiffLife() {
    return diffLife;
  }

  public void setDiffLife(int diffLife) {
    this.diffLife = diffLife;
  }

  @Override
  public String toString() {
    return "[fighterClass=" + fighterClass + ", isDead=" + isDead + ", currentMana=" + currentMana
        + ", currentLife=" + currentLife + ", states=" + states + "]";
  }


}
