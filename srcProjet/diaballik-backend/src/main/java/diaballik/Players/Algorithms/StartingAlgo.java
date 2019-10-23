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
        Player adversary = board.getHumanPlayer();
        List<ActionCoord> moves = new ArrayList<>();
        List<ActionCoord> ballMoves = new ArrayList<>();
        List<Float> heuristic = new ArrayList<>();
        //we calculate all the possible moves
        calculatePossiblePawnMoves(player,moves);
        calculatePossibleBallMoves(player,moves);
        calculatePossibleBallMoves(player,ballMoves);
        //for each move, we're looking at the heuristic if the move is made, and add it to a list
        moves.stream().forEachOrdered(m -> testHeuristic(m,adversary,heuristic));
        //after, we're taking the best move given our heuristic
        //TODO
        //Check if it's a ball move. If it is, return a random ball move previously calculated
        //Else, return the best move
        //TODO

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

    public void testHeuristic(final ActionCoord m, final Player adversary, final List<Float> heuristic) {

    }

}
