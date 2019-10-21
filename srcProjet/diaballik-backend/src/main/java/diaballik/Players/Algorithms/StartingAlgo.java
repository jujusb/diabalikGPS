package diaballik.Players.Algorithms;

import diaballik.Coordinates.ActionCoord;
import diaballik.GameElements.Pawn;
import diaballik.Players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StartingAlgo extends Algo {
    /**
     * Constructor of the algorith
     *
     * @param p the IA player.
     */
    public StartingAlgo(final Player p) {
        super(p);
    }

    /**
     * Function which returns a move to execute
     *
     * @return an ActionCoord's instance, which defines the movement of the Player
     */
    @Override
    public ActionCoord decideMove() {
        //TODO
        /*heuristique pour le mouvement des pions =
        * k1 * somme(pion_adverse_hauteur)      k1<0
         * + k2 * somme(pion_soi_hauteur)       k2<0||k2>0 si on joue définitif
         * + k3 * somme(mvt_adverse_possible)   k3<0
         * + k4 * somme(mvt_soi_hauteur)        k4>0
         * + k5 * victory_proportion            k5>>0
         * + k6 * hauteur_soi_balle             k6<0
         * + k7 * hauteur_adversaire_balle      k7<0
         */
        return null;
    }

    /**
     * Function which returns a ball move to execute
     *
     * @return an ActionCoord's instance, which defines the movement of the ball of the AI Player
     */
    public ActionCoord moveBall() {
        // List of the moves that the player can perform
        final List<ActionCoord> possibleMoves = new ArrayList<>();

        final Pawn ball = player.getBall();
        final List<Pawn> pawns = player.getPieces();

        // gathers all the possible moves of balls in the list possibleMoves
        pawns.stream()
                .filter(p -> !p.isBallOwner())
                .forEach(p -> {
                    if (board.canMoveBall(ball, p)) {
                        possibleMoves.add(new ActionCoord(ball.getPosition(), p.getPosition()));
                    }
                });
        // now that the list possibleMoves is full, we have to randomly select on of the moves to proceed
        final Random rdm = new Random();
        return possibleMoves.get(rdm.nextInt(possibleMoves.size()));
    }
}
