package com.github._64q.codeandplay2016.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github._64q.codeandplay2016.model.Joueur;
import com.github._64q.codeandplay2016.model.Mouvement;
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
    printPlateau(variables);
  }

  public static void printNouveauTour(VariablesMoteur variables) {
    LOG.info("----------------------------------- Tour {} -----------------------------------",
        MoteurJeu.NB_TOURS_MAX - variables.getPlateau().getNbrTurnsLeft());
  }

  public static void printMouvementAdversaire(VariablesMoteur variables) {
    LOG.info(" --> Mouvement adversaire :\t{}", variables.getMouvementAdversaire());
  }

  public static void printPlateau(VariablesMoteur variables) {
    Plateau plateau = variables.getPlateau();

    printPlayer(variables.getAdversaire(), variables.getMouvementAdversaire());
    printPlayer(variables.getNous(), variables.getMouvementNous());

    LOG.info("| Nombre de tours restants:\t{}", plateau.getNbrTurnsLeft());
    LOG.info("+-----------------------------------------------------------------------------+");
  }

  private static void printPlayer(Joueur player, Mouvement mouvement) {
    LOG.info("| Joueur {}", player, mouvement);
  }
}
