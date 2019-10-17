package diaballik.GameElements;

import diaballik.Coordinates.ActionCoord;
import diaballik.Coordinates.Coordinate;
import diaballik.Players.Player;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class GameBoard extends Do {
    /**
     * The boundary of the board
     */
    private static final int BOUNDARY = 7;

    /**
     * The queue of an undoable move in case the player want to undo his move
     */
    private Deque<ActionCoord> undoable_mode;

    /**
     * The queue of a redoable moves in case the player want to redo his move undo before
     */
    private Deque<ActionCoord> redoable_mode;

    /**
     * The max size of the 2 queues
     */
    private int size_max;

    /**
     * List of pawn who represent the board
     */
    private List<Pawn> board;

    /**
     * Player 1
     */
    private Player player1;

    /**
     * Player 2
     */
    private Player player2;

    /**
     * constructor of the game
     *
     * @param p1 player 1
     * @param p2 player 2
     */
    public GameBoard(final Player p1, final Player p2) {
        super();
        undoable_mode = new ArrayDeque<>();
        redoable_mode = new ArrayDeque<>();
        size_max = 3;
        player1 = p1;
        player2 = p2;

        board = new ArrayList<>(BOUNDARY * BOUNDARY);
        // initialisation of the board
        //for raw values with BOUNDARY = 7 : 7,3,7,6,45
        Stream.iterate(0, n -> n + 1)
                .limit(BOUNDARY)
                .forEach(n -> board.add(n, new Pawn(new Coordinate(n, 0), player1)));
        board.get(BOUNDARY / 2).setBallOwner(true); // top-middle
        Stream.iterate(0, n -> n + 1)
                .limit(BOUNDARY)
                .forEach(n -> board.add(n, new Pawn(new Coordinate(n, BOUNDARY - 1), player2)));
        board.get(BOUNDARY * (BOUNDARY - 1) + BOUNDARY / 2).setBallOwner(true); // bottom-middle
    }

    /**
     * to get the pawn to the present coordinate
     *
     * @param c the coordinates selectionned
     * @return the pawn if he found him else return null
     */
    public Optional<Pawn> getPawn(final Coordinate c) {
        //No check for out of bound ?And what if the pawn returned is null ?
        final Pawn p = board.get(c.getPosY() * 7 + c.getPosX());
        return Optional.ofNullable(p);
    }

    /**
     * The move to do
     *
     * @param p      the current player
     * @param coords the coordinates of the source and the target
     * @return true if the move went well false otherwise
     */
    @Override
    public boolean move(final Player p, final ActionCoord coords) {
        if (canMove(p, coords)) {
            final Pawn source = getPawn(coords.getSource()).get();

            // checks if the ball moves or if it is a pawn
            if (source.isBallOwner()) {
                // it is a ball move
                final Pawn dest = getPawn(coords.getTarget()).get();
                dest.setBallOwner(true);
                source.setBallOwner(false);
            } else {
                // it is a pawn move
                source.setPosition(coords.getTarget());
            }
            return true;
        }
        return false;
    }

    /**
     * Verifies if the move to do is OK
     *
     * @param p      the current player
     * @param coords the coordiate of the source and the target
     * @return true if OK false otherwise
     */
    @Override
    public boolean canMove(final Player p, final ActionCoord coords) {

        final Optional<Pawn> optSource = getPawn(coords.getSource());
        final Pawn source;

        // checks that both coordinates are different, that there is a pawn at source coordinates and that it is a "friendly" pawn
        if (!coords.getSource().equals(coords.getTarget()) &&
                optSource.isPresent() && optSource.get().getPlayer() == p) {
            source = optSource.get();

            // checks if the ball moves or if it is a pawn
            if (source.isBallOwner()) {
                // it is a ball move
                final Optional<Pawn> optDest = getPawn(coords.getTarget());
                // checks that there is a pawn at target coordinates and that it is a "friendly" pawn
                if (optDest.isPresent() && optDest.get().getPlayer() == p) {
                    final Pawn dest = optDest.get();
                    return canMoveBall(source, dest);
                }
            } else {
                // it is a pawn move
                if (getPawn(coords.getTarget()).isEmpty()) {
                    // checks that the source and target are at an absolute distance of 1 (i.e. they are neighbors)
                    if (coords.getTarget().absoluteDistance(coords.getSource()) == 1) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * Checks if we can move the ball from source to dest
     *
     * @param source      the source pawn, it is the pawn who carries the ball
     * @param destination the ball catcher
     * @return true if we can move the ball or false otherwise
     */
    public boolean canMoveBall(final Pawn source, final Pawn destination) {
        if (destination.getPosition().sameDiagonal(source.getPosition())
                || destination.getPosition().sameHorizontal(source.getPosition())
                || destination.getPosition().sameVertical(source.getPosition())) {

            final Coordinate sourceCo = (Coordinate) source.getPosition().clone();


            // checks that no object can be found between the two pawns
            return Stream.iterate(0, n -> n + 1)
                    // defines the quantity of moves that are necessary to check the line/diagonal. These two cases are different
                    .limit(destination.getPosition().sameDiagonal(source.getPosition()) ?
                            destination.getPosition().absoluteDistance(source.getPosition()) / 2 - 1 :
                            destination.getPosition().absoluteDistance(source.getPosition()) - 1)
                    // checks that no object can be found on the line between source and target, that can whether be a diagonal or a "simple line"
                    .noneMatch(n -> {
                        final int deltaY = Integer.compare(destination.getPosition().getPosY(), source.getPosition().getPosY());
                        final int deltaX = Integer.compare(destination.getPosition().getPosX(), source.getPosition().getPosX());
                        sourceCo.moveOf(deltaX, deltaY);
                        return getPawn(sourceCo).isPresent();
                    });
        }
        return false;
    }

    /**
     * To add to the list of UNDOABLEs
     *
     * @param u the action we just done
     */
    public void add(final ActionCoord u) {
        if (undoable_mode.size() == size_max) {
            undoable_mode.removeLast();
        }
        redoable_mode.clear();
        undoable_mode.push(u);
    }

    /**
     * Undoable a move
     */
    @Override
    public void undo() {
        if (!undoable_mode.isEmpty()) {
            final ActionCoord undoable = undoable_mode.pop();
            undoable.undo();
            undoable_mode.push(undoable);
        }
    }

    /**
     * Redo a move after the undo
     */
    @Override
    public void redo() {
        if (!redoable_mode.isEmpty()) {
            final ActionCoord redoable = redoable_mode.pop();
            redoable.redo();
            redoable_mode.push(redoable);
        }
    }
}
