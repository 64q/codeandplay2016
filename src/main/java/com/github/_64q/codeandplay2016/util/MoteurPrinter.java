package com.github._64q.codeandplay2016.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github._64q.codeandplay2016.model.Joueur;
import com.github._64q.codeandplay2016.model.Plateau;
import com.github._64q.codeandplay2016.model.VariablesMoteur;
import com.github._64q.codeandplay2016.service.MoteurJeu;

/**
 * Utilitaire d'affichage pour le moteur de jeu
 * 
 * @author qlebourgeois &lt;contact@qlebourgeois.me&gt;
 */
public class MoteurPrinter {

  /** Logger */
  private static final Logger LOG = LoggerFactory.getLogger(MoteurPrinter.class);

  public static void printVariablesMoteur(VariablesMoteur variables) {
    printNouveauTour(variables);
    printDernierMouvement(variables);
    printPlateau(variables);
  }

  public static void printNouveauTour(VariablesMoteur variables) {
    LOG.info("----------------------------------- Tour {} -----------------------------------",
        MoteurJeu.NB_TOURS_MAX - variables.getPlateau().getNbrActionLeft());
  }

  public static void printDernierMouvement(VariablesMoteur variables) {
    LOG.info(" --> Mouvement adversaire :\t{}", variables.getDernierMouvement());
  }

  public static void printPlateau(VariablesMoteur variables) {
    Plateau plateau = variables.getPlateau();

    LOG.info("+------------------------------ Plateau --------------------------------------+");
    printPlayer(plateau.getPlayer1());
    printPlayer(plateau.getPlayer2());
    LOG.info("| Nombre de tours restants:\t{}", plateau.getNbrActionLeft());
    LOG.info("+---------------------------- Fin Plateau ------------------------------------+");
  }

  private static void printPlayer(Joueur player) {
    LOG.info("| Joueur1 {} :\tPV:{},\tBalles:{},\tShields:{},\tFocus:{},\tBomb:{}",
        StringUtils.substring(player.getName(), 0, 4), player.getHealth(), player.getBullet(),
        player.getShield(), player.isFocused(), player.getBomb());
  }
}
