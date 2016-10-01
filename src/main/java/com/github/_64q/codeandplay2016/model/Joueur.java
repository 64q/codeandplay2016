package com.github._64q.codeandplay2016.model;

/**
 * Représente un joueur
 * 
 * @author qlebourgeois &lt;contact@qlebourgeois.me&gt;
 */
public class Joueur {

  private String name;
  private int health;
  private int bullet;
  private int shield;
  private int bomb;
  private boolean focused;
  private int cumulatedCovers;

  public String getName() {
    return name;
  }

  public void setName(String nom) {
    this.name = nom;
  }

  public int getHealth() {
    return health;
  }

  public void setHealth(int health) {
    this.health = health;
  }

  public int getBullet() {
    return bullet;
  }

  public void setBullet(int bullet) {
    this.bullet = bullet;
  }

  public int getShield() {
    return shield;
  }

  public void setShield(int shield) {
    this.shield = shield;
  }

  public boolean isFocused() {
    return focused;
  }

  public void setFocused(boolean focused) {
    this.focused = focused;
  }

  public int getBomb() {
    return this.bomb;
  }

  public void setBomb(int bomb) {
    this.bomb = bomb;
  }
  
  public int getCumulatedCovers() {
    return this.cumulatedCovers;
  }

  public void setCumulatedCovers(int cumulatedCovers) {
    this.cumulatedCovers = cumulatedCovers;
  }

  @Override
  public String toString() {
    return "Player [name=" + name + ", health=" + health + ", bullet=" + bullet + ", shield="
        + shield + ", focused=" + focused + "]";
  }
}
