package diaballik.gameElements;

import diaballik.coordinates.Coordinate;
import diaballik.players.Player;
import diaballik.players.PlayerAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * class Pawn
 * Class used to modelize a pawn on the GameBoard
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
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
    @XmlJavaTypeAdapter(PlayerAdapter.class)
    private Player player;

    /**
     * Constructor of Pawn. By default set ballOwner to false.
     *
     * @param position the position of Pawn in Coordinate format
     * @param player   the player who owns the pawn
     */
    public Pawn(final Coordinate position, final Player player) {
        this.position = position;
        this.player = player;
        this.ballOwner = false;

        player.addPawn(this);
    }

    public Pawn() {
    }
    /**
     * Getter of position
     *
     * @return position in Coordinate format
     */
    public Coordinate getPosition() {
        return position;
    }

    /**
     * Setter of position
     *
     * @param target the Coordinate we want the pawn to go
     */
    public void setPosition(final Coordinate target) {
        this.position.moveTo(target);
    }

    /**
     * Getter of ballOwner
     *
     * @return true if the current Pawn has the ball, false otherwise
     */
    public boolean isBallOwner() {
        return ballOwner;
    }

    /**
     * Setter of ballOwner
     *
     * @param b a boolean
     */
    public void setBallOwner(final boolean b) {
        this.ballOwner = b;

        // if it now owns the ball, says it to the player
        if (b) {
            player.setBall(this);
        }
    }

    /**
     * Getter of player
     *
     * @return the player who owns the Pawn
     */
    public Player getPlayer() {
        return player;
    }


}
