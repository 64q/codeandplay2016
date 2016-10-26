package com.github._64q.codeandplay2016.app;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.github._64q.codeandplay2016.model.ModeJeu;
import com.github._64q.codeandplay2016.service.MoteurJeu;
import com.github._64q.codeandplay2016.service.RestClient;

/**
 * Application principale
 *
 * @author qlebourgeois &lt;contact@qlebourgeois.me&gt;
 */
public class Application {

  /** Logger */
  private static final Logger LOG = LoggerFactory.getLogger(Application.class);

  // --- Paramètres serveur

  public static final String API_URL = "http://evenement.codeandplay.date/epic-ws";

  // --- Paramètres de partie

  public static final String NOM_EQUIPE = "ok";

  public static final String MOT_DE_PASSE = "ok";

  public static final ModeJeu MODE = ModeJeu.VERSUS;

  public static final int LEVEL = 30;

  // --- Contexte spring

  private static AnnotationConfigApplicationContext context;

  /**
   * Point d'entrée du bot de la battle code
   *
   * @param args pas d'arguments à renseigner
   */
  public static void main(String[] args) {
    // etape 1 : chargement du contexte spring depuis les packages du soft
    loadSpring();

    // etape 2 : affichage du lancement
    printIntroduction();

    // etape 3 : vérification de la connectivité avec le serveur de jeu
    checkConnexion();

    // etape 4 : initialisation du moteur
    MoteurJeu moteur = initializeMoteur();

    // etape 5 : place au jeu !
    if (MODE == ModeJeu.PRACTICE) {
      LOG.info("Lancement d'une partie en practice de niveau [ {} ]", LEVEL);

      moteur.runPractice(LEVEL);
    } else if (MODE == ModeJeu.VERSUS) {
      LOG.info("Lancement d'une partie en versus");

      moteur.runVersus();
    } else {
      LOG.warn("Mode de jeu inconnu, arrêt du programme");

      System.exit(254);
    }

    // etape 6 : arrêt du bot
    LOG.info("Fin de l'exécution du bot");
    unloadSpring();
    System.exit(0);
  }

  /**
   * Initialise le moteur de jeu
   *
   * @return le {@link MoteurJeu}
   */
  private static MoteurJeu initializeMoteur() {
    MoteurJeu moteur = context.getBean(MoteurJeu.class);

    moteur.initialize(NOM_EQUIPE, MOT_DE_PASSE);

    return moteur;
  }

  /**
   * Charge le contexte spring
   */
  private static void loadSpring() {
    context = new AnnotationConfigApplicationContext(
        // serivces
        "com.github._64q.codeandplay2016.service",
        // intelligences artificielles
        "com.github._64q.codeandplay2016.intelligence");
  }

  /**
   * Décharge le contexte spring
   */
  private static void unloadSpring() {
    context.close();
  }

  /**
   * Vérifie que la connexion est OK
   */
  private static void checkConnexion() {
    String pong = context.getBean(RestClient.class).ping();

    if (!StringUtils.equals(pong, "pong")) {
      LOG.error("Erreur de ping, serveur non joignable");

      System.exit(255);
    }

    LOG.info(" --> Test de connectivité OK [ {} ]", pong);
  }

  /**
   * Affiche les paramètres du bot au lancement
   */
  private static void printIntroduction() {
    LOG.info("Battle Code IA de l'équipe [ {} ]", NOM_EQUIPE);
    LOG.info(" --> Nom équipe: {}", NOM_EQUIPE);
    LOG.info(" --> Mode: {}", MODE);
    LOG.info(" --> LEVEL: {}", LEVEL);
  }

}
