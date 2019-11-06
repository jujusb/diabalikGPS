package diaballik.GameElements;

import diaballik.Coordinates.ActionCoord;
import diaballik.Coordinates.Coordinate;
import diaballik.Players.Player;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
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
    @XmlTransient
    private int size_max;

    /**
     * List of pawn who represent the board
     */
    private List<Pawn> board;

    /**
     * Player 1
     */
    @XmlTransient
    private Player player1;

    /**
     * Player 2
     */
    @XmlTransient
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
                .limit(BOUNDARY * BOUNDARY)
                .forEach(n -> {
                    if (n < BOUNDARY) {
                        board.add(n, new Pawn(new Coordinate(n, 0), player1));
                    } else if (n > BOUNDARY * (BOUNDARY - 1) - 1) {
                        board.add(n, new Pawn(new Coordinate(n - (BOUNDARY * (BOUNDARY - 1)), n / (BOUNDARY)), player2));
                    } else {
                        board.add(n, null);
                    }
                });
        getPawn(new Coordinate(Math.floorDiv(BOUNDARY, 2), 0)).get().setBallOwner(true); // top-middle
        getPawn(new Coordinate(Math.floorDiv(BOUNDARY, 2), BOUNDARY - 1)).get().setBallOwner(true); // bottom-middle*/
    }

    public GameBoard() {
    }

    /**
     * Get Human Player
     */
    public Player getHumanPlayer() {
        return player1;
    }

    /**
     * Function which returns the pawn to the present coordinates. Returns null if the
     * present coordinates are false (in the sense of function checkCoord(Coordinate)), and if
     * there is no pawn at these coordinates on the board.
     *
     * @param c the chosen coordinates
     * @return the pawn if he found him else return null
     */
    public Optional<Pawn> getPawn(final Coordinate c) {
        //No check for out of bound ?And what if the pawn returned is null ?
        final Pawn p = checkCoord(c) ? board.get(c.getPosY() * BOUNDARY + c.getPosX()) : null;
        return Optional.ofNullable(p);
    }

    /**
     * Method which moves the specified pawn if the move is correct
     *
     * @param p      the current player
     * @param coords the coordinates of the source and the target
     * @return true if the move went well false otherwise
     */
    @Override
    public boolean move(final Player p, final ActionCoord coords) {
        if (canMove(p, coords)) {
            moveNoCheck(coords, true, true);
            return true;
        }
        return false;
    }

    /**
     * Execute the move represented by the ActionCoord and update the board.
     * The move to do must be valid because NO tests are made on this function.
     *
     * @param coords    an ActionCoord which represents the move to make
     * @param save      true if we have to save the move in the undo list, false otherwise
     * @param clearRedo true if we want to clear the redo list, in the case of a pawn or ball move (which is not a redo or a undo)
     */
    public void moveNoCheck(final ActionCoord coords, final boolean save, final boolean clearRedo) {
        final Pawn source = getPawn(coords.getSource()).get();

        // checks if the ball moves or if it is a pawn
        if (source.isBallOwner()) {
            // it is a ball move
            final Pawn dest = getPawn(coords.getTarget()).get();
            dest.setBallOwner(true);
            source.setBallOwner(false);
            //Update of the reference of ball for the current Player
            dest.getPlayer().setBall(dest);
            //No need to update the board
        } else {
            // it is a pawn move
            source.setPosition(coords.getTarget());
            //Update of board
            board.set(coords.getSource().getPosX() + coords.getSource().getPosY() * 7, null);
            board.set(coords.getTarget().getPosX() + coords.getTarget().getPosY() * 7, source);
        }
        if (save) {
            this.addUndo(coords, clearRedo);
        }
    }


    /**
     * Verify if the move to do is OK
     *
     * @param p      the current player
     * @param coords the coordinate of the source and the target
     * @return true if OK false otherwise
     */
    @Override
    public boolean canMove(final Player p, final ActionCoord coords) {

        final Optional<Pawn> optSource = getPawn(coords.getSource());
        final Pawn source;

        if (!checkCoord(coords.getSource()) || !checkCoord(coords.getTarget())) {
            throw new IndexOutOfBoundsException("The coordinates are not in the gameboard");
        }

        // checks that there is a pawn at source coordinates and that it is a "friendly" pawn
        if (optSource.isPresent() && optSource.get().getPlayer() == p) {
            source = optSource.get();

            // checks if the ball moves or if it is a pawn
            if (source.isBallOwner()) {
                // it is a ball move
                final Optional<Pawn> optDest = getPawn(coords.getTarget());
                // checks that there is a pawn at target coordinates and that it is a "friendly" pawn
                if (optDest.isPresent() && optDest.get().getPlayer().equals(p)) {
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
     * Checks if we can move the ball from source to dest. Be sure that source
     * and destination belongs to the same Player, because this condition is not
     * tested.
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
                            (destination.getPosition().absoluteDistance(source.getPosition()) / 2) - 1 :
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
     * @param u         the action we just done
     * @param clearRedo true if we want to clear the redo list (for example if we make a move)
     */
    public void addUndo(final ActionCoord u, final boolean clearRedo) {
        if (undoable_mode.size() == size_max) {
            undoable_mode.removeLast();
        }
        if (clearRedo) {
            redoable_mode.clear();
        }
        undoable_mode.push(u);
    }

    /**
     * Undoable a move
     */
    @Override
    public void undo() {
        if (!undoable_mode.isEmpty()) {
            final ActionCoord undoable = undoable_mode.pop();
            undoable.invert();
            moveNoCheck(undoable, false, false);
            redoable_mode.push(undoable);
        }
    }

    /**
     * Redo a move after the undo : Return to the target coordinate after the undo
     */
    @Override
    public void redo() {
        if (!redoable_mode.isEmpty()) {
            final ActionCoord redoable = redoable_mode.pop();
            redoable.invert();
            moveNoCheck(redoable, true, false);
        }
    }

    /**
     * Checks that the coordinates are within the board
     *
     * @param c the coordinates we have to check
     * @return true if the coordinate is ok and false otherwise
     */
    public boolean checkCoord(final Coordinate c) {
        return c.getPosY() < BOUNDARY && c.getPosY() >= 0 && c.getPosX() < BOUNDARY && c.getPosX() >= 0;
    }

    /**
     * Checks if a player has won
     *
     * @return true if someone has won false otherwise
     */
    public Boolean checkIfWon() {
        if (player1.getBall().getPosition().getPosY() == 6) {
            return true;
        }
        if (player2.getBall().getPosition().getPosY() == 0) {
            return true;
        }
        return false;
    }

    public String playerPawnCoordinates() {
        final String[] aux = {""};
        aux[0] += "Player 1\n";
        player1.getPawns().stream().forEach(p -> aux[0] += p.getPosition() + "\n");
        aux[0] += "Player 2\n";
        player2.getPawns().stream().forEach(p -> aux[0] += p.getPosition() + "\n");
        return aux[0];
    }

    public Deque<ActionCoord> getUndoable_mode() {
        return undoable_mode;
    }

    public Deque<ActionCoord> getRedoable_mode() {
        return redoable_mode;
    }
}
