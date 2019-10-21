package diaballik.Players.Algorithms;

import diaballik.Coordinates.ActionCoord;
import diaballik.Coordinates.Coordinate;
import diaballik.GameElements.Pawn;
import diaballik.Players.Player;

import java.util.ArrayList;
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

        final Pawn ball = player.getBall();
        final List<Pawn> pawns = player.getPieces();

        // gathers all the possible moves of pawns and balls in the list possibleMoves
        pawns.stream()
                .filter(p -> !p.isBallOwner())
                .forEach(p -> {
                    if (board.canMoveBall(ball, p)) {
                        possibleMoves.add(new ActionCoord(ball.getPosition(), p.getPosition()));
                    }
                    final Coordinate c = (Coordinate) p.getPosition().clone();
                    moveAndCheck(1, 0, possibleMoves, c, p.getPosition());
                    moveAndCheck(-2, 0, possibleMoves, c, p.getPosition());
                    moveAndCheck(1, 1, possibleMoves, c, p.getPosition());
                    moveAndCheck(0, -2, possibleMoves, c, p.getPosition());
                });

        // now that the list possibleMoves is full, we have to randomly select on of the moves to proceed
        final Random rdm = new Random();
        return possibleMoves.get(rdm.nextInt(possibleMoves.size()));
    }


}
