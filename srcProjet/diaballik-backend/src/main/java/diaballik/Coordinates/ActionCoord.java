package diaballik.Coordinates;

/**
 * class ActionCoord
 * A pair of Coordinate representing a move on the GameBoard.
 * @see Coordinate
 */
public class ActionCoord {

    /**
     *  The Coordinate of the Pawn to move
     */
    private Coordinate source;

    /**
     * The Coordinate where we want the Pawn to go
     */
    private Coordinate target;

    /**
     * Constructor of the class
     * @param s the Coordinate of the Pawn to mover
     * @param t the Coordinate where we want the Pawn to go
     */
    public ActionCoord(final Coordinate s, final Coordinate t) {
        source = s;
        target = t;
    }

    /**
     * Getter of source
     * @return  source as a Coordinate
     * @link ActionCoord.source
     */
    public Coordinate getSource() {
        return source;
    }

    /**
     * Getter of target
     * @return  target as a Coordinate
     * @link ActionCoord.target
     */
    public Coordinate getTarget() {
        return target;
    }

    @Override
    public String toString() {
        return "ActionCoord {" +
                "source =" + source +
                " ; target=" + target +
                '}';
    }


    /**
     * Return to the source coordinate
     */
    public void undo() {
        //TODO
    }

    /**
     * Return to the target coordinate after the undo
     */
    public void redo() {
        //TODO
    }
}
