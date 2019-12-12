package diaballik.players.algorithms;

import diaballik.coordinates.ActionCoord;
import diaballik.coordinates.Coordinate;
import diaballik.gameElements.GameBoard;
import diaballik.gameElements.Pawn;
import diaballik.players.Player;

import java.util.ArrayList;
import java.util.List;

public abstract class Algo {
    /**
     * The current board of the game
     */
    GameBoard board;

    /**
     * The player who is the IA
     */
    Player player;

    public Algo(final Player p) {
        board = null;
        player = p;
    }

    /**
     * to get the board from the IA
     *
     * @return the current board
     */
    public GameBoard getBoard() {
        return board;
    }

    /**
     * to set the board to the IA
     *
     * @param board the current board
     */
    public void setBoard(final GameBoard board) {
        this.board = board;
    }

    /**
     * Function which returns a move to execute
     *
     * @param nbActions number of actions already played by AI
     * @return an ActionCoord's instance, which defines the movement of the Player
     */
    public abstract ActionCoord decideMove(int nbActions);


    /**
     * Method which adds in a list all the possible pawn moves for a given player
     *
     * @param one a Player for which we want to know all the possible pawn moves
     * @return a list containing all the possible pawn moves
     */
    public List<ActionCoord> calculatePossiblePawnMoves(final Player one) {
        final List<ActionCoord> possibleMoves = new ArrayList<>();
        final List<Pawn> pawns = one.getPawns();
        // gathers all the possible moves of pawns and balls in the list possibleMoves
        pawns.forEach(p -> {
            possibleMoves.addAll(board.getPossiblePawnMoves(p));
        });


        return possibleMoves;
    }

    /**
     * Method which adds in a list all the possible ball moves for a given player
     *
     * @param one a Player for which we want to know all the possible ball moves
     * @return a list containing all the possible ball moves
     */
    public List<ActionCoord> calculatePossibleBallMoves(final Player one) {
        final Pawn ball = one.getBall();
        return board.getPossibleBallMoves(ball);
    }

    /**
     * Method which adds in a list all the possible moves for a given player. Uses the two other possibilities computation functions
     *
     * @param one a Player for which we want to know all the possible  moves
     * @return a list containing all the possible moves
     */
    public List<ActionCoord> calculatePossibleMoves(final Player one) {
        final List<ActionCoord> possibleMoves;
        possibleMoves = calculatePossiblePawnMoves(one); // add the pawn moves
        possibleMoves.addAll(calculatePossibleBallMoves(one)); //add the ball moves
        return possibleMoves;
    }
}
