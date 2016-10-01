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

  public static final String API_URL = "http://www.codeandplay.date/battle-ws";

  // --- Paramètres de partie

  public static final String NOM = "O(k)";

  public static final String MOT_DE_PASSE = "TEsOk!TEsBath!TEsIN!";

  public static final ModeJeu MODE = ModeJeu.PRACTICE;

  public static final String LEVEL = "5";

  // --- Ctx

  private static AnnotationConfigApplicationContext context;

  /**
   * @param args
   */
  public static void main(String[] args) {
    context = new AnnotationConfigApplicationContext("com.github._64q.codeandplay2016");
    
    intro();
    checkConnexion();

    MoteurJeu moteur = context.getBean(MoteurJeu.class);

    moteur.initialize(NOM, MOT_DE_PASSE);

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
    
    LOG.info(" --> Fin de l'exécution du bot");
    
    context.close();

    System.exit(0);
  }

  private static void intro() {
    LOG.info(" --> Nom équipe: {}", NOM);
    LOG.info(" --> Mode: {}", MODE);
    LOG.info(" --> LEVEL: {}", LEVEL);
  }

  private static void checkConnexion() {
    String pong = context.getBean(RestClient.class).ping();

    if (!StringUtils.equals(pong, "pong")) {
      LOG.error("Erreur de ping, serveur non joignable");

      System.exit(255);
    }

    LOG.info(" --> Test de connectivité OK [ {} ]", pong);
  }

}
