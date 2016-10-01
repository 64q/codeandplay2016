package com.github._64q.codeandplay2016.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.github._64q.codeandplay2016.exception.CreationPartieException;
import com.github._64q.codeandplay2016.ia.IntelligenceArtificielle;
import com.github._64q.codeandplay2016.model.EtatMouvement;
import com.github._64q.codeandplay2016.model.EtatPartie;
import com.github._64q.codeandplay2016.model.Mouvement;
import com.github._64q.codeandplay2016.model.Plateau;
import com.github._64q.codeandplay2016.model.VariablesMoteur;

/**
 * Moteur de jeu
 * 
 * @author qlebourgeois &lt;contact@qlebourgeois.me&gt;
 */
@Service
public class MoteurJeu {

  /** Logger */
  private static final Logger LOG = LoggerFactory.getLogger(MoteurJeu.class);

  // --- Services

  /**
   * Client REST
   */
  @Autowired
  private RestClient client;

  /**
   * IA du jeu
   */
  @Autowired
  @Qualifier(value = "simpleIntelligenceArtificielle")
  private IntelligenceArtificielle intell;

  // --- Variables

  /**
   * EtatPartie du moteur de jeu
   */
  private VariablesMoteur variables;

  /**
   * Initialise le moteur de jeu
   * 
   * @param nom
   * @param motDePasse
   * @param mode
   * @return
   */
  public MoteurJeu initialize(String nom, String motDePasse) {
    variables = new VariablesMoteur();

    variables.setNom(nom);

    // récupération de notre identifiant de joueur
    String identifiant = client.getIdEquipe(nom, motDePasse);
    variables.setIdEquipe(identifiant);
    LOG.info("Identifiant de l'équipe [ {} ]", identifiant);

    return this;
  }

  /**
   * Lance la partie en practice
   * 
   * @param mode
   * @param level
   */
  public void runPractice(String level) {
    String idPartie = client.newPractice(level, variables.getIdEquipe());

    if ("NA".equals(idPartie)) {
      throw new CreationPartieException("Impossible de créer la partie");
    }

    variables.setIdPartie(idPartie);

    loop();
  }

  /**
   * Lance la partie en versus
   * 
   * @param mode
   * @param level
   */
  public void runVersus() {

  }

  // --- Méthodes internes propres au workflow

  /**
   * Boucle principale d'exécution du bot
   */
  private void loop() {
    EtatPartie etat = client.getStatus(variables.getIdPartie(), variables.getIdEquipe());

    switch (etat) {
      case CANCELLED:
        performCancelled();
        break;
      case DEFEAT:
        performDefeat();
        break;
      case VICTORY:
        performVictory();
        break;
      case CANPLAY:
        play();
        break;
      case CANTPLAY:
      default:
        LOG.info("En attente de pouvoir jouer");
        loop();
    }
  }

  private void performVictory() {
    LOG.info("Victoire de notre IA");
  }

  private void performDefeat() {
    LOG.info("Défaite de notre IA");
  }

  private void performCancelled() {
    LOG.info("Arrêt de la partie");
  }

  /**
   * Joue un coup
   */
  private void play() {
    Plateau plateau = client.getBoard(variables.getIdPartie());
    Mouvement dernierMouvement =
        client.getLastMovement(variables.getIdPartie(), variables.getIdEquipe());

    variables.setPlateau(plateau);
    variables.setDernierMouvement(dernierMouvement);

    printNouveauTour();
    printLastMove();
    printPlateau();

    Mouvement prochainMouvement = intell.makeMove(variables);

    EtatMouvement etat =
        client.makeMove(variables.getIdPartie(), variables.getIdEquipe(), prochainMouvement);

    switch (etat) {
      case OK:
        LOG.info(">>> Mouvement effectué:\t\t\t{}", prochainMouvement);
        loop();
        break;
      case FORBIDDEN:
        LOG.info("Mouvement {} interdit", prochainMouvement);
        performDefeat();
        break;
      case GAMEOVER:
        LOG.info("Défaite sur le mouvement {}", prochainMouvement);
        performDefeat();
        break;
      case NOTYET:
      default:
        LOG.info("Pas encore notre tour");
        loop();
    }

  }

  private void printNouveauTour() {
    LOG.info(" ------------------- Tour {} ------------------- ",
        30 - variables.getPlateau().getNbrActionLeft());
  }

  /**
   * Affiche le dernier mouvement (de l'adversaire)
   */
  private void printLastMove() {
    LOG.info(">>> Dernier mouvement adversaire:\t\t{}", variables.getDernierMouvement());
  }

  /**
   * Affiche le plateau
   */
  private void printPlateau() {
    Plateau plateau = variables.getPlateau();

    LOG.info("--- Plateau");
    LOG.info("Joueur1 {} : PV:{}, Balles:{}, Shields:{}, Focus:{}, Bomb:{}", plateau.getPlayer1().getName(),
        plateau.getPlayer1().getHealth(), plateau.getPlayer1().getBullet(),
        plateau.getPlayer1().getShield(), plateau.getPlayer1().isFocused(), plateau.getPlayer1().getBomb());
    LOG.info("Joueur2 {} : PV:{}, Balles:{}, Shields:{}, Focus:{}, Bomb:{}", plateau.getPlayer2().getName(),
        plateau.getPlayer2().getHealth(), plateau.getPlayer2().getBullet(),
        plateau.getPlayer2().getShield(), plateau.getPlayer2().isFocused(),plateau.getPlayer2().getBomb());
    LOG.info("Nombre de tours restants: {}", plateau.getNbrActionLeft());
    LOG.info("--- Fin Plateau");
  }
}
