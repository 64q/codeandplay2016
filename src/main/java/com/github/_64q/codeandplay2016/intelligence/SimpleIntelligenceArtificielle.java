package com.github._64q.codeandplay2016.intelligence;

import java.util.Collections;
import java.util.Comparator;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.github._64q.codeandplay2016.model.FighterClass;
import com.github._64q.codeandplay2016.model.FighterMouvement;
import com.github._64q.codeandplay2016.model.FighterState;
import com.github._64q.codeandplay2016.model.Fighters;
import com.github._64q.codeandplay2016.model.Mouvement;
import com.github._64q.codeandplay2016.model.Status;
import com.github._64q.codeandplay2016.model.TypeAction;
import com.github._64q.codeandplay2016.model.TypeMouvement;
import com.github._64q.codeandplay2016.model.VariablesMoteur;

@Component
public class SimpleIntelligenceArtificielle implements IntelligenceArtificielle {

  /** Logger */
  private static final Logger LOG = LoggerFactory.getLogger(SimpleIntelligenceArtificielle.class);

  private Mouvement dernierMouvement;
  private Mouvement avantDernierMouvement;

  @Override
  public Mouvement makeMove(VariablesMoteur variables) {
    Mouvement mouvement = new Mouvement();
    int tour = variables.getPlateau().getNbrTurnsLeft();

    dernierMouvement = variables.getMouvementAdversaire();

    LOG.trace("Avant dernier: {}, dernier: {}", avantDernierMouvement, dernierMouvement);

    if (tour > 50) {
      mouvement = pick(variables);
    } else {
      mouvement = jouer(variables);
    }

    variables.setOldPlateau(variables.getPlateau());

    return mouvement;
  }

  private Mouvement pick(VariablesMoteur variables) {
    Mouvement mouvement = new Mouvement();

    mouvement.setTypeAction(TypeAction.CHOIX);

    if (variables.getTour() == 1) {
      mouvement.setSelection(FighterClass.CHAMAN);
    } else if (variables.getTour() == 2) {
      mouvement.setSelection(FighterClass.PALADIN);
    } else if (variables.getTour() == 3) {
      mouvement.setSelection(FighterClass.ORC);
    }

    return mouvement;
  }

  private Mouvement jouer(VariablesMoteur variables) {
    Mouvement mouvement = new Mouvement();

    mouvement.getMouvements()
        .add(getMouvement(variables, variables.getNous().getFighters().get(0)));
    mouvement.getMouvements()
        .add(getMouvement(variables, variables.getNous().getFighters().get(1)));
    mouvement.getMouvements()
        .add(getMouvement(variables, variables.getNous().getFighters().get(2)));

    return mouvement;
  }

  private FighterMouvement getMouvement(VariablesMoteur variables, Fighters fighters) {

    if (fighters.getIsDead() || fighters.getCurrentMana() == 0) {
      return new FighterMouvement("A" + fighters.getOrderNumberInTeam(), TypeMouvement.REST,
          "A" + fighters.getOrderNumberInTeam());
    }

    for (FighterState state : CollectionUtils.emptyIfNull(fighters.getStates())) {
      if (state.getType().equals(Status.SCARED) && state.getRemainingDuration() > 0) {
        return new FighterMouvement("A" + fighters.getOrderNumberInTeam(), TypeMouvement.DEFEND,
            "A" + fighters.getOrderNumberInTeam());
      }
    }

    if (fighters.getCurrentLife() < 10 || wasFocused(variables, fighters)) {
      return new FighterMouvement("A" + fighters.getOrderNumberInTeam(), TypeMouvement.DEFEND,
          "A" + fighters.getOrderNumberInTeam());
    }

    if (fighters.getFighterClass().equals(FighterClass.PALADIN)) {
      return mvtPaladin(variables, fighters);
    } else if (fighters.getFighterClass().equals(FighterClass.ORC)) {
      return mvtOrc(variables, fighters);
    } else if (fighters.getFighterClass().equals(FighterClass.CHAMAN)) {
      return mvtChaman(variables, fighters);
    } else {
      return new FighterMouvement("A" + fighters.getOrderNumberInTeam(), TypeMouvement.ATTACK,
          getCible(variables));
    }
  }

  private boolean wasFocused(VariablesMoteur variables, Fighters fighters) {
    int i = 0;

    for (FighterMouvement f : variables.getMouvementAdversaire().getMouvements()) {
      if (f.getCible().equals("E" + fighters.getOrderNumberInTeam())) {
        i++;
        LOG.info("focused {}", fighters.getOrderNumberInTeam());
      }
    }

    return i > 1;
  }

  private FighterMouvement mvtChaman(VariablesMoteur variables, Fighters fighters) {
    return new FighterMouvement("A" + fighters.getOrderNumberInTeam(), TypeMouvement.ATTACK,
        getCible(variables));

  }

  private FighterMouvement mvtOrc(VariablesMoteur variables, Fighters fighters) {
    return new FighterMouvement("A" + fighters.getOrderNumberInTeam(), TypeMouvement.ATTACK,
        getCible(variables));
  }

  private FighterMouvement mvtPaladin(VariablesMoteur variables, Fighters fighters) {
    if (fighters.getCurrentMana() >= 2) {
      return new FighterMouvement("A" + fighters.getOrderNumberInTeam(), TypeMouvement.CHARGE,
          getCible(variables));
    } else {
      return new FighterMouvement("A" + fighters.getOrderNumberInTeam(), TypeMouvement.REST,
          "A" + fighters.getOrderNumberInTeam());
    }

  }

  private String getCible(VariablesMoteur variables) {
    Collections.sort(variables.getAdversaire().getFighters(), new Comparator<Fighters>() {

      @Override
      public int compare(Fighters o1, Fighters o2) {
        if (o1.getCurrentLife() < o2.getCurrentLife()) {
          return -1;
        } else if (o1.getCurrentLife() == o2.getCurrentLife()) {
          return 0;
        } else {
          return 1;
        }
      }

    });

    for (Fighters f : variables.getAdversaire().getFighters()) {
      if (!f.getIsDead()) {
        LOG.info(" adv {}, Atk {}", variables.getAdversaire().getPlayerName(),
            f.getOrderNumberInTeam());
        return "E" + f.getOrderNumberInTeam();
      }
    }

    return "E1";
  }
}
