package com.github._64q.codeandplay2016.service;

import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github._64q.codeandplay2016.app.Application;
import com.github._64q.codeandplay2016.exception.ReponseInvalideException;
import com.github._64q.codeandplay2016.model.EtatPartie;
import com.github._64q.codeandplay2016.model.EtatMouvement;
import com.github._64q.codeandplay2016.model.Mouvement;
import com.github._64q.codeandplay2016.model.Plateau;
import com.github._64q.codeandplay2016.model.Erreur;
import com.github._64q.codeandplay2016.rest_interface.DuelResource;
import com.github._64q.codeandplay2016.util.JsonUtils;

/**
 * Client REST
 * 
 * @author qlebourgeois &lt;contact@qlebourgeois.me&gt;
 */
@Service
public class RestClient {

  /** Logger */
  private static final Logger LOG = LoggerFactory.getLogger(RestClient.class);

  /**
   * Client HTTP
   */
  private static final DuelResource RESOURCE =
      JAXRSClientFactory.create(Application.API_URL, DuelResource.class);

  /**
   * Déclenche une pause volontaire sur tous les appels
   */
  private void pause() {
    try {
      Thread.sleep(200);
    } catch (InterruptedException e) {
      LOG.trace("Impossible de mettre en pause", e);
    }
  }

  private Response analyze(Response response) {
    if (response.getStatus() == 406) {
      Erreur objet =
          JsonUtils.fromJson(response.readEntity(String.class), Erreur.class);

      LOG.error("ErreurCode: {}, Message: {}", objet.getClasse(), objet.getMessage());

      throw new ReponseInvalideException(
          "Réponse en erreur du serveur [ message = " + objet.getMessage() + " ]");
    }

    pause();

    return response;
  }

  /**
   * Ping API server
   * 
   * @return
   */
  public String ping() {
    return analyze(RESOURCE.ping()).readEntity(String.class);
  }

  /**
   * Retourne l'identifiant de l'équipe
   * 
   * @param nomEquipe
   * @param motDePasse
   * @return
   */
  public String getIdEquipe(String nomEquipe, String motDePasse) {
    return analyze(RESOURCE.getIdEquipe(nomEquipe, motDePasse)).readEntity(String.class);
  }

  /**
   * Retourne l'identifiant de la prochaine partie contre un bot
   * 
   * @param level
   * @param idEquipe
   * @return
   */
  public String newPractice(String level, String idEquipe) {
    return analyze(RESOURCE.createPractice(level, idEquipe)).readEntity(String.class);
  }

  /**
   * Retourne l'état courant de la partie
   * 
   * @param idPartie
   * @param idEquipe
   * @return
   */
  public EtatPartie getStatus(String idPartie, String idEquipe) {
    return EtatPartie.valueOf(analyze(RESOURCE.getStatus(idPartie, idEquipe)).readEntity(String.class));
  }
  
  /**
   * Récupère le plateau de jeu
   * 
   * @param idPartie
   * @return
   */
  public Plateau getBoard(String idPartie) {
    return JsonUtils.fromJson(analyze(RESOURCE.getBoard(idPartie, "JSON")).readEntity(String.class), Plateau.class);
  }
  
  /**
   * Récupère le dernier mouvement effectué
   * 
   * @param idPartie
   * @param idEquipe
   * @return
   */
  public Mouvement getLastMovement(String idPartie, String idEquipe) {
    return Mouvement
        .valueOf(analyze(RESOURCE.getLastMove(idPartie, idEquipe)).readEntity(String.class));
  }
  
  /**
   * Effectue un mouvement
   * 
   * @param idPartie
   * @param idEquipe
   * @param mouvement
   * @return
   */
  public EtatMouvement makeMove(String idPartie, String idEquipe, Mouvement mouvement) {
    return EtatMouvement.valueOf(analyze(RESOURCE.makeMove(idPartie, mouvement.name(), idEquipe)).readEntity(String.class));
  }
}
