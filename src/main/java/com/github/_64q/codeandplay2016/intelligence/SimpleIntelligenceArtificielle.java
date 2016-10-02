package com.github._64q.codeandplay2016.intelligence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.github._64q.codeandplay2016.model.Joueur;
import com.github._64q.codeandplay2016.model.Mouvement;
import com.github._64q.codeandplay2016.model.VariablesMoteur;

@Component
public class SimpleIntelligenceArtificielle implements IntelligenceArtificielle {

  /** Logger */
  private static final Logger LOG = LoggerFactory.getLogger(SimpleIntelligenceArtificielle.class);

  private Mouvement dernierMouvement;
  private Mouvement avantDernierMouvement;

  private Joueur getNous(VariablesMoteur variables) {
    if (variables.getNomEquipe().equals(variables.getPlateau().getPlayer1().getName())) {
      return variables.getPlateau().getPlayer1();
    }

    return variables.getPlateau().getPlayer2();
  }

  private Joueur getAdversaire(VariablesMoteur variables) {
    if (!variables.getNomEquipe().equals(variables.getPlateau().getPlayer1().getName())) {
      return variables.getPlateau().getPlayer1();
    }

    return variables.getPlateau().getPlayer2();
  }

  @Override
  public Mouvement makeMove(VariablesMoteur variables) {
    Mouvement mouvement = Mouvement.SHOOT;

    Joueur nous = getNous(variables);
    Joueur adversaire = getAdversaire(variables);

    dernierMouvement = variables.getDernierMouvement();

    LOG.trace("Avant dernier: {}, dernier: {}", avantDernierMouvement, dernierMouvement);

    if (avantDernierMouvement == Mouvement.BOMB && nous.getShield() > 0) {
      mouvement = Mouvement.COVER;
    } else if (nous.getHealth() > 5 && nous.getBullet() > 0 && !nous.isFocused()
        && adversaire.getBullet() < 1) {
      mouvement = Mouvement.AIM;
    } else if (nous.getBomb() > 0 && adversaire.getBullet() < 1) {
      mouvement = Mouvement.BOMB;
    } else if (dernierMouvement == Mouvement.AIM && nous.getShield() > 1) {
      mouvement = Mouvement.COVER;
    } else if (nous.getBullet() < 1) {
      mouvement = Mouvement.RELOAD;
    } else if (nous.getBullet() > 0 && adversaire.getBomb() > 0 && adversaire.getShield() < 1) {
      mouvement = Mouvement.SHOOT;
    } else {
      if (nous.isFocused()) {
        mouvement = Mouvement.SHOOT;
      } else {
        mouvement = Mouvement.AIM;
      }
    }

    avantDernierMouvement = dernierMouvement;

    return mouvement;
  }
}
