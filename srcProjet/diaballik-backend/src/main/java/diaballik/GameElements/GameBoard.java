package diaballik.GameElements;

import diaballik.Coordinates.ActionCoord;
import diaballik.Coordinates.Coordinate;
import diaballik.Players.Player;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Optional;

public class GameBoard extends Do {
    /**
     * The boundary of the board
     */
    private final int BOUNDARY = 7;

    /**
     * The queue of an undoable move in case the player want undo his move
     */
    private Deque<ActionCoord> undoable_mode;

    /**
     * The queue of a redoable moves in case the player want redo his move undo before
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
     * constructor of the game
     * @param p1 player 1
     * @param p2 player 2
     */
    public GameBoard(final Player p1, final Player p2) {
        super();
        board = new ArrayList<>(BOUNDARY * BOUNDARY);
        undoable_mode = new ArrayDeque<>();
        redoable_mode = new ArrayDeque<>();
        size_max = 3;
    }

    /**
     * to get the pawn to the present coordinate
     * @param c the coordinates selectionned
     * @return the pawn if he found him else return null
     */
    public Optional<Pawn> getPawn(final Coordinate c) {
        //TODO
        return null;
    }

    /**
     * The move to do
     * @param p the current player
     * @param coords the coordiate of the source and the target
     * @return true if OK false else
     */
    @Override
    public boolean move(final Player p, final ActionCoord coords) {
        //TODO
        return false;
    }

    /**
     * Verify if the move to do is OK
     * @param p the current player
     * @param coords the coordiate of the source and the target
     * @return true if OK false else
     */
    @Override
    public boolean canMove(final Player p, final ActionCoord coords) {
        //TODO
        return false;
    }

    /**
     * To add to the list of UNDOABLEs
     * @param u the action we just done
     */
    public void add(final ActionCoord u) {
        if(undoable_mode.size() == size_max) {
            undoable_mode.removeLast();
        }
        redoable_mode.clear();
        undoable_mode.push(u);
    }

    /**
     * Undo a move
     */
    @Override
    public void undo() {
        if(!undoable_mode.isEmpty()) {
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
        if(!redoable_mode.isEmpty()) {
            final ActionCoord redoable = redoable_mode.pop();
            redoable.redo();
            redoable_mode.push(redoable);
        }
    }
}
