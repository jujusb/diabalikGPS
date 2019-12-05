package diaballik.players.algorithms;

import diaballik.coordinates.ActionCoord;
import diaballik.players.Player;

import java.util.List;
import java.util.Random;

public class NoobAlgo extends Algo {
    /**
     * Constructor of the algorith
     *
     * @param p the IA player.
     */
    public NoobAlgo(final Player p) {
        super(p);
    }

    /**
     * Function which returns a move to execute
     *
     * @param nbActions number of actions already done by the AI
     * @return an ActionCoord's instance, which defines the movement of the Player
     */
    @Override
    public ActionCoord decideMove(final int nbActions) {
        // List of the moves that the player can perform
        final List<ActionCoord> possibleMoves = calculatePossibleMoves(player);
        // now that the list possibleMoves is full, we have to randomly select one of the moves to proceed
        final Random rdm = new Random();
        return possibleMoves.get(rdm.nextInt(possibleMoves.size()));
    }


}
