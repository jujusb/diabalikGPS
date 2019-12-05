package diaballik.players.algorithms;

import diaballik.players.Player;
import diaballik.coordinates.ActionCoord;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.HashMap;

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
     * Coefficient for the proportion of victorious ball moves over the opponent possible ball moves
     */
    private final double k6;

    /**
     * Coefficient for the height of the adversary's ball
     */
    private final double k7;

    /**
     * Coefficient for the height of our ball
     */
    private final double k8;


    /**
     * Constructor of the algorithm
     *
     * @param p the IA player.
     */
    public StartingAlgo(final Player p) {
        super(p);
        k1 = createCoeff(-1.5, 0.5);
        k2 = createCoeff(-1.5, 0.5);
        k3 = createCoeff(-2.0, 0.7);
        k4 = createCoeff(2.0, 0.7);
        k5 = createCoeff(60.0, 20.0);
        k6 = createCoeff(-60.0, 20.0);
        k7 = createCoeff(-10.0, 3.4);
        k8 = createCoeff(-10.0, 3.4);
    }

    /**
     * Determinist constructor of the algorithm (its heuristic coef are not random)
     *
     * @param p the IA player
     */
    public StartingAlgo(final Player p, final double k1, final double k2, final double k3, final double k4, final double k5, final double k6, final double k7, final double k8) {
        super(p);
        this.k1 = k1;
        this.k2 = k2;
        this.k3 = k3;
        this.k4 = k4;
        this.k5 = k5;
        this.k6 = k6;
        this.k7 = k7;
        this.k8 = k8;
    }

    /**
     * Create a coefficent randomnly by picking a number in a Gauss law distribution
     * with parameters mean and standard deviation
     *
     * @param mean           the mean of your Gaussian Law
     * @param std_derivation the standard deviation of your Gaussian Law
     * @return a random coefficient
     */
    private double createCoeff(final double mean, final double std_derivation) {
        final Random ra = new Random();
        return ra.nextGaussian() * std_derivation + mean;
    }

    /**
     * Function which returns a move to execute
     *
     * @param nbActions number of actions already done by the AI
     * @return an instance of ActionCoord, which defines the movement of the Player
     */
    @Override
    public ActionCoord decideMove(final int nbActions) {
        final Player adversary = board.getPlayer1();
        final List<ActionCoord> moves;
        final List<ActionCoord> ballMoves;

        final Map<ActionCoord, Double> heuristics = new HashMap<>();
        //we calculate all the possible moves
        moves = calculatePossiblePawnMoves(player);
        ballMoves = calculatePossibleBallMoves(player);
        moves.addAll(ballMoves);
        //for each move, we're looking at the heuristic if the move is made, and add it to a list
        moves.forEach(m -> heuristics.put(m, computeHeuristic(m, adversary)));

        // sorts the move list by the heuristics
        moves.sort(Comparator.comparingDouble(m -> -1 * heuristics.get(m)));

        //after, we're taking the best move given our heuristic
        //Check if it's a ball move. If it is, return a random ball move previously calculated
        if (board.getPawn(moves.get(0).getSource()).get().isBallOwner()) {
            final Random rdm = new Random();
            return ballMoves.get(rdm.nextInt(ballMoves.size()));
        }
        //Else, return the best move
        return moves.get(0);
    }

    /**
     * Computes the heuristic of a move
     * heuristic for a move =
     * k1 * sum(height of opponent's pawns)      k1<0
     * + k2 * sum(height of our pawns)       k2<0||k2>0 if we play in a defensive way
     * + k3 * sum(opponent's possible moves)   k3<0
     * + k4 * sum(our possible moves)        k4>0
     * + k5 * proportion of winning ball moves over our possible ball moves  k5>>0
     * + k6 * proportion of winning ball moves over the oponent's possible ball moves k6<<0
     * + k7 * height of the ball of the opponent          k6<0
     * + k8 * height of our ball     k7<0
     *
     * @param m         the move we want to examine
     * @param adversary the adversary of the AI
     * @return the heuristic of the move according to the previously given rules
     */
    public Double computeHeuristic(final ActionCoord m, final Player adversary) {
        board.moveNoCheck(m, true, true);

        // we have to calculate the move possibilities of the player now to avoid computing it several times
        final List<ActionCoord> ballMoves = calculatePossibleBallMoves(player);
        final List<ActionCoord> adversaryBallMoves = calculatePossibleBallMoves(adversary);
        final List<ActionCoord> moves = calculatePossiblePawnMoves(player);
        moves.addAll(ballMoves);


        final double heuristic = k1 * adversary.heightSum() +
                k2 * player.heightSum() +
                k3 * calculatePossibleMoves(adversary).size() +
                k4 * moves.size() +
                k5 * ballMoves.stream().filter(a -> a.getTarget().getPosY() == 0).count() / (ballMoves.size() + 1) + //the addition is to avoid division by zero
                k6 * adversaryBallMoves.stream().filter(a -> a.getTarget().getPosY() == 6).count() / (adversaryBallMoves.size() + 1) +
                k7 * adversary.getBall().getPosition().getPosY() +
                k8 * player.getBall().getPosition().getPosY();

        board.undo();

        return heuristic;
    }

}
