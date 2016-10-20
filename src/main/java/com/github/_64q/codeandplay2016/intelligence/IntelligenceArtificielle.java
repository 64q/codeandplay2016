package com.github._64q.codeandplay2016.intelligence;

import org.springframework.stereotype.Component;

import com.github._64q.codeandplay2016.model.Mouvement;
import com.github._64q.codeandplay2016.model.VariablesMoteur;

/**
 * Interface d'intelligence artificelle à implémenter
 *
 * @author qlebourgeois &lt;contact@qlebourgeois.me&gt;
 */
@Component
public interface IntelligenceArtificielle {
  /**
   * Joue le prochain coup
   *
   * @param variables etat du moteur
   * @return le mouvement à jouer
   */
  public Mouvement makeMove(VariablesMoteur variables);
}
