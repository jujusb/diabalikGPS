package diaballik.players.algorithms;

import diaballik.coordinates.ActionCoord;
import diaballik.players.Player;
import diaballik.players.algorithms.AlphaPion.MonteCarloTreeSearch;

public class MonteCarloAlgo extends Algo {
    MonteCarloTreeSearch mcts = new MonteCarloTreeSearch();

    public MonteCarloAlgo(final Player p) {
        super(p);
    }

    @Override
    public ActionCoord decideMove(int nbActions) {
        return mcts.findNextMove(board, player, board.getPlayer1(),nbActions);
    }
}
