package diaballik.Players;

import diaballik.Coordinates.ActionCoord;
import diaballik.GameElements.Pawn;

import java.util.ArrayList;
import java.util.List;

/**
 * class Player
 * Class used to specify the parameters and functions of a Player
 */
public abstract class  Player {

    /**
     * The name of the player
     */
    protected String name;

    /**
     * The colour of the Player. Since only two colors are allowed in the game, we
     * implement the colour with a boolean, true if the colour is white, and false if the color is black.
     */
    protected boolean colour;

    /**
     * The different pieces a Player owns. The ball is included in this List.
     */
    protected List<Pawn> pieces;

    /**
     * The Pawn which possesses the ball
     */
    protected Pawn ball;

    /**
     * Consturctor of Player. Initializes also pieces to an empty ArrayList and ball to null;
     * @param n the name of the Player
     * @param c the colour of the Player
     */
    public Player(final String n, final boolean c) {
        name = n;
        colour = c;
        pieces = new ArrayList<Pawn>();
        ball = null;
    }

    /**
     * Getter of ball
     * @return the current Pawn who possesses the ball
     */
    public Pawn getBall() {
        return ball;
    }

    /**
     * Setter of ball
     * @param p the Pawn who will receives the ball
     */
    public void setBall(final Pawn p) {
        ball = p;
    }

    /**
     * Method which adds a Pawn to the List pieces
     * @param p a Pawn to add
     */
    public void addPawn(final Pawn p) {
        pieces.add(p);
    }

    /**
     * Function which wait and notify for the end of the turn
     * @return true if the turn of the player is finished, false otherwise
     */
    public abstract boolean waitEndOfTurn();

    /**
     * Function which returns a move to execute
     * @return an ActionCoord's instance, which defines the movement of the Player
     */
    public abstract ActionCoord getMove();

}
