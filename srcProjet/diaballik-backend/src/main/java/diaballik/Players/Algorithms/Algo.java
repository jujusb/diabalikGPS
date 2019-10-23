package diaballik.Players.Algorithms;

import diaballik.Coordinates.ActionCoord;
import diaballik.Coordinates.Coordinate;
import diaballik.GameElements.GameBoard;
import diaballik.GameElements.Pawn;
import diaballik.Players.Player;

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
     * @return an ActionCoord's instance, which defines the movement of the Player
     */
    public abstract ActionCoord decideMove();

    /**
     * Moves a coordinate and, if it is free, adds a move there in an ActionCoord list for a given pawn
     *
     * @param dx            the x displacement
     * @param dy            the y displacement
     * @param possibleMoves the list of currently possible moves
     * @param dest          the coordinates we want to check
     * @param source        the coordinates of the pawn that could move to c
     */
    void moveAndCheck(final int dx, final int dy, final List<ActionCoord> possibleMoves, final Coordinate dest, final Coordinate source) {
        dest.moveOf(dx, dy);
        if (board.checkCoord(dest) && board.getPawn(dest).isEmpty()) {
            possibleMoves.add(new ActionCoord(source, dest));
        }
    }

    /**
     * Method which adds in a list all the possible pawn moves for a given player
     * @param one a Player for which we want to know all the possible pawn moves
     * @param possibleMoves the list in which we want to add elements
     */
    public void calculatePossiblePawnMoves(final Player one, final List<ActionCoord> possibleMoves){
        final List<Pawn> pawns = one.getPieces();
        // gathers all the possible moves of pawns and balls in the list possibleMoves
        pawns.stream()
                .filter(p -> !p.isBallOwner())
                .forEach(p -> {
                    final Coordinate c = (Coordinate) p.getPosition().clone();
                    moveAndCheck(1, 0, possibleMoves, c, p.getPosition());
                    moveAndCheck(-2, 0, possibleMoves, c, p.getPosition());
                    moveAndCheck(1, 1, possibleMoves, c, p.getPosition());
                    moveAndCheck(0, -2, possibleMoves, c, p.getPosition());
                });
    }

    /**
     * Method which adds in a list all the possible ball moves for a given player
     * @param one a Player for which we want to know all the possible ball moves
     * @param possibleMoves the list in which we want to add elements
     */
    public void calculatePossibleBallMoves(final Player one, final List<ActionCoord> possibleMoves){
        final Pawn ball = one.getBall();
        final List<Pawn> pawns = one.getPieces();
        // gathers all the possible moves of balls in the list possibleMoves
        pawns.stream()
                .filter(p -> !p.isBallOwner())
                .forEach(p -> {
                    if (board.canMoveBall(ball, p)) {
                        possibleMoves.add(new ActionCoord(ball.getPosition(), p.getPosition()));
                    }
                });
    }
}
