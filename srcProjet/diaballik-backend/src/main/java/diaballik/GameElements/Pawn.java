package diaballik.GameElements;

import diaballik.Coordinates.Coordinate;
import diaballik.Players.Player;

/**
 * class Pawn
 * Class used to modelize a pawn on the GameBoard
 */
public class Pawn {

    /**
     * The current position of the pawn
     */
    private Coordinate position;

    /**
     * boolean that shows if the pawn have the ball
     */
    private boolean ballOwner;

    /**
     * The player who owns the pawn
     */
    private Player player;

    /**
     * Constructor of Pawn. By default set ballOwner to false.
     * @param position  the position of Pawn in Coordinate format
     * @param player    the player who owns the pawn
     */
    public Pawn(Coordinate position, Player player) {
        this.position = position;
        this.player = player;
        this.ballOwner = false;
    }

    /**
     * Getter of position
     * @return  position in Coordinate format
     */
    public Coordinate getPosition(){
        return position;
    }

    /**
     * Setter of position
     * @param target    the Coordinate we want the pawn to go
     */
    public void setPosition(Coordinate target) {
        this.position.moveTo(target);
    }

    /**
     * Getter of ballOwner
     * @return  true if the current Pawn has the ball, false otherwise
     */
    public boolean isBallOwner() {
        return ballOwner;
    }

    /**
     * Setter of ballOwner
     * @param b a boolean
     */
    public void setBallOwner(boolean b) {
        this.ballOwner = b;
    }

    /**
     * Getter of player
     * @return  the player who owns the Pawn
     */
    public Player getPlayer() {
        return player;
    }


}
