package edu.rcos.pkmnrpi.main.pokemon.ai;

import java.util.List;

import edu.rcos.pkmnrpi.main.pokemon.AI;
import edu.rcos.pkmnrpi.main.pokemon.Move;
import edu.rcos.pkmnrpi.main.pokemon.Pokemon;

/**
 * Select the move that does the most damage taking hit/critical
 * chances into account.
 * @author Austin Gulati
 */
public class MostDamageAndChanceAI implements AI {

	public Move decide(Pokemon me, Pokemon enemy) {
		List<Move> moves = me.knownMoves;
		Move mostDamageMove = moves.get(0);
		for (Move move : moves) {
			if (getDamage(move, me, enemy)
					> getDamage(mostDamageMove, me, enemy)) {
				mostDamageMove = move;
			}
		}
		return mostDamageMove;
	}
	
	private int getDamage(Move move, Pokemon me, Pokemon enemy) {
		return (int) (move.damage * (1 + move.crit_chance)
				* (move.hit_chance / 100));
	}
	
}
