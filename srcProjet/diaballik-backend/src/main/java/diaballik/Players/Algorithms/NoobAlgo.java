package diaballik.Players.Algorithms;

import diaballik.Coordinates.ActionCoord;
import diaballik.Coordinates.Coordinate;
import diaballik.GameElements.Pawn;
import diaballik.Players.Player;

import java.util.ArrayList;
import java.util.Collections;
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
     * @return an ActionCoord's instance, which defines the movement of the Player
     */
    @Override
    public ActionCoord decideMove() {
        // List of the moves that the player can perform
        final List<ActionCoord> possibleMoves = new ArrayList<>();
        calculatePossiblePawnMoves(player,possibleMoves);// add the pawn moves
        calculatePossibleBallMoves(player,possibleMoves);// add the ball moves
        Collections.shuffle(possibleMoves);// shuffle the list

        // now that the list possibleMoves is full, we have to randomly select one of the moves to proceed
        final Random rdm = new Random();
        return possibleMoves.get(rdm.nextInt(possibleMoves.size()));
    }


}
