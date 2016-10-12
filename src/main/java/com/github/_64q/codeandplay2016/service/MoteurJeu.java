package com.github._64q.codeandplay2016.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.github._64q.codeandplay2016.exception.CreationPartieException;
import com.github._64q.codeandplay2016.intelligence.IntelligenceArtificielle;
import com.github._64q.codeandplay2016.model.EtatMouvement;
import com.github._64q.codeandplay2016.model.EtatPartie;
import com.github._64q.codeandplay2016.model.Mouvement;
import com.github._64q.codeandplay2016.model.Plateau;
import com.github._64q.codeandplay2016.model.VariablesMoteur;
import com.github._64q.codeandplay2016.util.MoteurPrinter;

/**
 * Moteur de jeu
 * 
 * @author qlebourgeois &lt;contact@qlebourgeois.me&gt;
 */
@Service
public class MoteurJeu {

  /** Logger */
  private static final Logger LOG = LoggerFactory.getLogger(MoteurJeu.class);

  // --- Constantes du moteur

  /**
   * Nombre tours max du jeu
   */
  public static final int NB_TOURS_MAX = 30;

  /**
   * Temps d'attente dans un tour vide en ms
   */
  public static final int TEMPS_ENTRE_STATUT = 200;

  // --- Services

  /**
   * Client REST
   */
  @Autowired
  private RestClient client;

  /**
   * IA du jeu
   * 
   * <p>
   * Changer le qualifier pour faire jouer une autre IA enregistrée en tant que bean spring
   */
  @Autowired
  @Qualifier(value = "simpleIntelligenceArtificielle")
  private IntelligenceArtificielle intelligence;

  // --- Variables

  /**
   * EtatPartie du moteur de jeu
   */
  private VariablesMoteur variables;

  /**
   * Initialise le moteur de jeu
   * 
   * @param nomEquipe
   * @param motDePasse
   * @param mode
   * @return
   */
  public MoteurJeu initialize(String nomEquipe, String motDePasse) {
    Assert.notNull(nomEquipe, "Le nom d'équipe est obligatoire");
    Assert.notNull(motDePasse, "Le mot de passe est obligatoire");

    variables = new VariablesMoteur();

    variables.setNomEquipe(nomEquipe);

    // récupération de notre identifiant de joueur
    String identifiant = client.getIdEquipe(nomEquipe, motDePasse);
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
  public void runPractice(int level) {
    Assert.isTrue(level > 0 && level < 7, "Le niveau de jeu doit être compris entre 1 et 6");

    String idPartie = client.newPractice(String.valueOf(level), variables.getIdEquipe());

    if (idPartie == null || "NA".equals(idPartie)) {
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
    String idPartie = client.newVersus(variables.getIdEquipe());

    if (idPartie == null || "NA".equals(idPartie)) {
      throw new CreationPartieException("Impossible de créer la partie");
    }

    variables.setIdPartie(idPartie);

    loop();
  }

  // --- Méthodes internes propres au workflow

  /**
   * Boucle principale d'exécution du jeu
   */
  protected void loop() {
    EtatPartie etat;
    do {
      etat = client.getStatus(variables.getIdPartie(), variables.getIdEquipe());
  
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
          // On ignore le résultat, on tente de continuer (au pire il y aura DEFEAT)
          // if(!play()) etat = DEFEAT;
          break;
          
        case CANTPLAY:
          LOG.info("En attente de pouvoir jouer");
          break;
          
        default:
            throw new IllegalStateException("Unhandled status : " + etat.name());
      }
    } while(!etat.isFinal());
    updatePlateau();
  }

  /**
   * Joue un coup
   * @return false si on a perdu la partie sur ce coup
   */
  private boolean play() {
    Plateau plateau = client.getBoard(variables.getIdPartie());
    Mouvement dernierMouvement =
        client.getLastMovement(variables.getIdPartie(), variables.getIdEquipe());

    variables.setPlateau(plateau);
    variables.setMouvementAdversaire(dernierMouvement);

    MoteurPrinter.printVariablesMoteur(variables);

    Mouvement prochainMouvement = intelligence.makeMove(variables);
    EtatMouvement etat =
        client.makeMove(variables.getIdPartie(), variables.getIdEquipe(), prochainMouvement);
    variables.setMouvementNous(prochainMouvement);
    

    switch (etat) {
      case OK:
        return true;
        
      case NOTYET:
        LOG.info(" --> Pas encore notre tour");
        return true;
        
      case FORBIDDEN:
        LOG.info(" --> Mouvement interdit:\t\t{}", prochainMouvement);
        return false;
        
      case GAMEOVER:
        LOG.info(" --> Défaite sur mouvement:\t\t{}", prochainMouvement);
        return false;
    }
    
    //Retourne Ok par défaut, évite de planter le jeu si jamais il y a un bug ici
    return true;

  }
  private void updatePlateau() {
    Plateau plateau = client.getBoard(variables.getIdPartie());

    variables.setPlateau(plateau);
    
    Mouvement mouvementAdversaire =
        client.getLastMovement(variables.getIdPartie(), variables.getIdEquipe());
    variables.setMouvementAdversaire(mouvementAdversaire);

    MoteurPrinter.printVariablesMoteur(variables);
  }

  protected void performVictory() {
    LOG.info(" ----> Victoire de notre IA");
  }

  protected void performDefeat() {
    LOG.info(" ----> Défaite de notre IA");
  }

  protected void performCancelled() {
    LOG.info(" ----> Partie annulée");
  }
}
