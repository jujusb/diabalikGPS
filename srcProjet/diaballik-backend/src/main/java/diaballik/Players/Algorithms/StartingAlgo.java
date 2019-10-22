package diaballik.Players.Algorithms;

import diaballik.Coordinates.ActionCoord;
import diaballik.GameElements.Pawn;
import diaballik.Players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StartingAlgo extends Algo {

    /**
     * Coefficient for the sum of the height of the pawns of the adversary
     */
    private final double k1;

    /**
     * Coefficient for the sum of the height of our pawns
     */
    private final double k2;

    /**
     * Coefficient for the number of the possible moves of the adversary
     */
    private final double k3;

    /**
     * Coefficient for the number of our possible moves
     */
    private final double k4;

    /**
     * Coefficient for the proportion of victorious ball moves over our possible ball moves
     */
    private final double k5;

    /**
     * Coefficient for the height of the adversary's ball
     */
    private final double k6;

    /**
     * Coefficient for the height of our ball
     */
    private final double k7;

    /**
     * Constructor of the algorithm
     *
     * @param p the IA player.
     */
    public StartingAlgo(final Player p) {
        super(p);
        k1 = createCoeff(1.5,0.5);
        k2 = createCoeff(1.5,0.5);
        k3 = createCoeff(2.0,0.7);
        k4 = createCoeff(2.0,0.7);
        k5 = createCoeff(60.0,20.0);
        k6 = createCoeff(10.0,3.4);
        k7 = createCoeff(10.0,3.4);
    }

    /**
     * Create a coefficent randomnly by picking a number in a Gauss law distribution
     * with parameters mean and standard deviation
     * @param mean the mean of your Gaussian Law
     * @param std_derivation the standard deviation of your Gaussian Law
     * @return a random coefficient
     */
    private double createCoeff(final double mean, final double std_derivation){
        Random ra = new Random();
        return ra.nextGaussian()*std_derivation + mean;
    }

    /**
     * Function which returns a move to execute
     *
     * @return an instance of ActionCoord, which defines the movement of the Player
     */
    @Override
    public ActionCoord decideMove() {
        double heuristique = 0;
        board.getHumanPlayer().getPieces().stream()
                .forEach(n -> {
                    heuristique+=
                });

        //TODO
        /*heuristique pour le mouvement des pions =
         * k1 * somme(pion_adverse_hauteur)      k1<0
         * + k2 * somme(pion_soi_hauteur)       k2<0||k2>0 si on joue défensif
         * + k3 * somme(mvt_adverse_possible)   k3<0
         * + k4 * somme(mvt_soi_hauteur)        k4>0
         * + k5 * proportion de mouvement de balles victorieux sur les mouvement de balles possibles  k5>>0
         * + k6 * hauteur_adversaire_balle             k6<0
         * + k7 * hauteur_soi_balle      k7<0
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
        // now that the list possibleMoves is full, we have to randomly select one of the moves to proceed
        final Random rdm = new Random();
        return possibleMoves.get(rdm.nextInt(possibleMoves.size()));
    }
}
