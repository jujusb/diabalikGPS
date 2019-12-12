package diaballik.gameElements;

import diaballik.coordinates.ActionCoord;
import diaballik.coordinates.Coordinate;
import diaballik.players.AiPlayer;
import diaballik.players.Player;
import diaballik.players.algorithms.EAiType;

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
public class GameBoard extends Do implements Cloneable {

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
    public Player getPlayer1() {
        return player1;
    }

    /**
     * Get the other Player
     */
    public Player getPlayer2() {
        return player2;
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

        // System.out.println("coords = " + coords);
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
            //Update of board
            board.set(coords.getSource().getPosX() + coords.getSource().getPosY() * 7, null);
            board.set(coords.getTarget().getPosX() + coords.getTarget().getPosY() * 7, source);

            // it is a pawn move
            source.setPosition(coords.getTarget());
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
            return checkCanMove(p, coords, source);
        }
        return false;
    }

    /**
     * Check if the move to do is OK
     *
     * @param p      the current player
     * @param coords the coordinate of the source and the target
     * @param source the source Pawn
     * @return true if OK false otherwise
     */
    public boolean checkCanMove(final Player p, final ActionCoord coords, final Pawn source) {
        // checks if the ball moves or if it is a pawn
        if (source.isBallOwner()) {
            // it is a ball move
            final Optional<Pawn> optDest = getPawn(coords.getTarget());
            // checks that there is a pawn at target coordinates and that it is a "friendly" pawn
            if (optDest.isPresent() && optDest.get().getPlayer().equals(p) && optDest.get() != (source)) {
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
        // makes a copy of ActionCoord to avoid breaking the moves that the algorithms study
        final ActionCoord v = (ActionCoord) u.clone();

        if (undoable_mode.size() == size_max) {
            undoable_mode.removeLast();
        }
        if (clearRedo) {
            redoable_mode.clear();
        }
        undoable_mode.push(v);
    }

    /**
     * Undoable a move
     */
    @Override
    public boolean undo() {
        if (!undoable_mode.isEmpty()) {
            final ActionCoord undoable = undoable_mode.pop();
            undoable.invert();
            moveNoCheck(undoable, false, false);
            redoable_mode.push(undoable);
            return true;
        }
        return false;
    }

    /**
     * Redo a move after the undo : Return to the target coordinate after the undo
     */
    @Override
    public boolean redo() {
        if (!redoable_mode.isEmpty()) {
            final ActionCoord redoable = redoable_mode.pop();
            redoable.invert();
            moveNoCheck(redoable, true, false);
            return true;
        }
        return false;
    }

    /**
     * End Of Turn. Clears the redos and undos.
     */
    public boolean endOfTurn() {
        if (undoable_mode.size() == 3) {
            redoable_mode.clear();
            undoable_mode.clear();
            return true;
        }
        return false;
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
            player1.setWinner(true);
            return true;
        }
        if (player2.getBall().getPosition().getPosY() == 0) {
            player2.setWinner(true);
            return true;
        }
        return false;
    }

    /**
     * Returns the pawns of the game, classified by the player.
     *
     * @return a String with format "Player 1" followed by the list of pawn of the player,
     * and the same for the second player.
     */
    public String playerPawnCoordinates() {
        final String[] aux = {""};
        aux[0] += "Player 1\n";
        player1.getPawns().forEach(p -> aux[0] += p.getPosition() + "\n");
        aux[0] += "Player 2\n";
        player2.getPawns().forEach(p -> aux[0] += p.getPosition() + "\n");
        return aux[0];
    }


    /**
     * Returns a list of the possible moves for a given pawn (if it doesn't carry the ball)
     *
     * @param p The pawn we want to check the moves
     * @return the list of possible moves
     */
    public List<ActionCoord> getPossiblePawnMoves(final Pawn p) {
        final List<ActionCoord> possibleMoves = new ArrayList<>();
        if (!p.isBallOwner()) {
            final Coordinate source = (Coordinate) p.getPosition().clone();
            final Coordinate c1 = (Coordinate) p.getPosition().clone();
            final Coordinate c2 = (Coordinate) p.getPosition().clone();
            final Coordinate c3 = (Coordinate) p.getPosition().clone();
            final Coordinate c4 = (Coordinate) p.getPosition().clone();

            moveAndCheck(1, 0, possibleMoves, c1, source);
            moveAndCheck(-1, 0, possibleMoves, c2, source);
            moveAndCheck(0, 1, possibleMoves, c3, source);
            moveAndCheck(0, -1, possibleMoves, c4, source);
        }
        return possibleMoves;
    }

    /**
     * Returns a list of the possible moves for a given pawn with ball
     *
     * @param ball The baall owner we want to check the moves
     * @return the list of possible moves
     */
    public List<ActionCoord> getPossibleBallMoves(final Pawn ball) {
        final List<ActionCoord> possibleMoves = new ArrayList<>();
        if (ball.isBallOwner()) {
            final List<Pawn> pawns = ball.getPlayer().getPawns();
            // gathers all the possible moves of balls in the list possibleMoves
            pawns.forEach(p -> {
                if (!p.isBallOwner()) {
                    if (this.canMoveBall(ball, p)) {
                        possibleMoves.add(new ActionCoord((Coordinate) ball.getPosition().clone(), (Coordinate) p.getPosition().clone()));
                    }
                }
            });
        }
        return possibleMoves;
    }

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
        if (this.checkCoord(dest) && this.getPawn(dest).isEmpty()) {
            possibleMoves.add(new ActionCoord(source, dest));
        }
    }

    /**
     * Getter of undoable_mode
     *
     * @return a reference on undoable_mode
     */
    public Deque<ActionCoord> getUndoable_mode() {
        return undoable_mode;
    }

    /**
     * Getter of redoable_mode
     *
     * @return a reference on redoable_mode
     */
    public Deque<ActionCoord> getRedoable_mode() {
        return redoable_mode;
    }

    /**
     * Returns a view of the GameBoard
     *
     * @return a String view of the GameBoard,
     * by default the first player is in the top of the view
     */
    @Override
    public String toString() {
        final String[] s = {""};
        s[0] = "._._._._._._._.\n";
        final int[] i = {1};
        i[0] = 0;
        board.forEach(p -> {
            s[0] += "|";
            if (p != null) {
                final String aux = p.getPlayer().getColor() ? "o" : "x";
                s[0] += p.isBallOwner() ? aux.toUpperCase() : aux;
            } else {
                s[0] += " ";
            }
            if ((i[0]++) % 7 == 6) {
                s[0] += "|\n";
            }
        });
        s[0] += "*-*-*-*-*-*-*-*";
        return s[0];
    }

    /**
     * Clones a board but makes a copy of players with cloning its pawns only (the other non-primitive attributes of the player will not be cloned)
     *
     * @return a new board with the same characteristics
     */
    @Override
    public Object clone() {
        try {
            final GameBoard res = (GameBoard) super.clone();

            // resets the list of the board pawns
            res.board = new ArrayList<>(49);

            // makes new players with noob algorithms
            res.player1 = new AiPlayer(EAiType.NOOB, "A", player1.getColor());
            res.player2 = new AiPlayer(EAiType.NOOB, "B", player2.getColor());

            // sets the gameboard of the noob algorithms
            ((AiPlayer) res.player1).setBoard(res);
            ((AiPlayer) res.player2).setBoard(res);

            // resets the lists of pawns of the players
            res.player1.setPawns(new ArrayList<>());
            res.player2.setPawns(new ArrayList<>());

            // updates all the pawns lists
            Stream.iterate(0, n -> n + 1)
                    .limit(board.size())
                    .forEachOrdered(n -> {
                        if (board.get(n) != null) {
                            final Pawn p = board.get(n);
                            final Pawn p2;
                            // let's add the pawn to the players' lists
                            if (p.getPlayer().getColor() == player1.getColor()) {
                                p2 = new Pawn((Coordinate) p.getPosition().clone(), res.player1);
                                if (p.isBallOwner()) {
                                    p2.setBallOwner(true);
                                }
                            } else {
                                p2 = new Pawn((Coordinate) p.getPosition().clone(), res.player2);
                                if (p.isBallOwner()) {
                                    p2.setBallOwner(true);
                                }
                            }

                            // let's add the pawn to the board's list
                            res.board.add(p2);
                        } else {
                            res.board.add(null);
                        }
                    });
            return res;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return new GameBoard();
        }
    }
}
