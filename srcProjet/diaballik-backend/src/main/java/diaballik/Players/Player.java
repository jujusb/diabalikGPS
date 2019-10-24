package diaballik.Players;

import diaballik.Coordinates.ActionCoord;
import diaballik.GameElements.Pawn;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * class Player
 * Class used to specify the parameters and functions of a Player
 */
public abstract class Player {

    /**
     * The name of the player
     */
    private String name; // ne peut pas être mis protected pour MAVEN à cause de l'erreur URF_UNREAD_PUBLIC OR PROTECTED FIELDS

    /**
     * The colour of the Player. Since only two colors are allowed in the game, we
     * implement the colour with a boolean, true if the colour is white, and false if the color is black.
     */
    private boolean colour; // ne peut pas être mis protected pour MAVEN à cause de l'erreur URF_UNREAD_PUBLIC OR PROTECTED FIELDS

    /**
     * The different pawns a Player owns. The ball is included in this List.
     */
    List<Pawn> pawns; // ne peut pas être mis protected pour MAVEN à cause de l'erreur URF_UNREAD_PUBLIC OR PROTECTED FIELDS

    /**
     * The Pawn which possesses the ball
     */
    Pawn ball; // ne peut pas être mis protected pour MAVEN à cause de l'erreur URF_UNREAD_PUBLIC OR PROTECTED FIELDS

    /**
     * Consturctor of Player. Initializes also pawns to an empty ArrayList and ball to null;
     *
     * @param n the name of the Player
     * @param c the colour of the Player
     */
    public Player(final String n, final boolean c) {
        name = n;
        colour = c;
        pawns = new ArrayList<Pawn>();
        ball = null;
    }

    /**
     * Getter of ball
     *
     * @return the current Pawn who possesses the ball
     */
    public Pawn getBall() {
        return ball;
    }

    /**
     * Setter of ball
     *
     * @param p the Pawn who will receives the ball
     */
    public void setBall(final Pawn p) {
        ball = p;
    }

    /**
     * Method which adds a Pawn to the List pawns
     *
     * @param p a Pawn to add
     */
    public void addPawn(final Pawn p) {
        pawns.add(p);
    }

    /**
     * Function which wait and notify for the end of the turn
     *
     * @return true if the turn of the player is finished, false otherwise
     */
    public abstract boolean waitEndOfTurn();

    /**
     * Function which returns a move to execute
     *
     * @return an ActionCoord's instance, which defines the movement of the Player
     */
    public abstract ActionCoord getMove();

    /**
     * Getter of the name of the Player
     *
     * @return name of the player
     */
    public String getName() {
        return name;
    }


    /**
     * Getter of the color of the Player
     *
     * @return true if the colour is white, and false if the color is black.
     */
    public boolean getColor() {
        return colour;
    }


    /**
     * Equals of two players
     *
     * @return true if they are the same player
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Player)) {
            return false;
        }
        final Player player = (Player) o;
        return getColor() == player.getColor() &&
                Objects.equals(getName(), player.getName());
    }

    @Override
    public int hashCode() {
        return 0;
    }

    /**
     * Getter of the list of pawn of the player
     *
     * @return the list of pawn of the player
     */
    public List<Pawn> getPawns() {
        return pawns;
    }

    /**
     * Function returning the sum of the height of the pawns, including the one carrying the ball
     *
     * @return the sum of the height of the pawns
     */
    public int heightSum() {
        return pawns.stream().mapToInt(p -> p.getPosition().getPosY()).sum();
    }
}
