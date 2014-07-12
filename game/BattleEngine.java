package game;

import pokemon.Pokemon;
import pokemon.moves.Move;
import trainers.Person;
import trainers.Trainer;

public class BattleEngine {
	public BattlePanel panel;
	public GameEngine engine;
	public Pokemon enemy, friend;
	public Trainer self, opponent; // opponent will be null if fighting wild pokemon

	// Start a wild encounter with a pokemon
	public BattleEngine(GameEngine e, Pokemon p) {
		engine = e;
		self = (Trainer) engine.board.player;
		friend = self.get_first_pokemon();
		enemy = p;
		panel = new BattlePanel(this);
		panel.animate_walkon_self();
		panel.animate_walkon_pkmn_left();
		panel.display_moves();
	}

	// Start a trainer battle
	public BattleEngine(GameEngine e, Person p) {
		engine = e;
		opponent = (Trainer) p;
		self = (Trainer) engine.board.player;
		friend = self.get_first_pokemon();
		enemy = opponent.get_first_pokemon();
		panel = new BattlePanel(this);
		panel = new BattlePanel(this);
		panel.animate_walkon_self();
		panel.animate_walkon_opponent();
		panel.display_moves();
	}

	// Called by BattlePanel when a player selects what move to use.
	public void selection_made(Move m) {

	}
}
