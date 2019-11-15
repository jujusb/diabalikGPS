package diaballik.players;

import diaballik.gameElements.BallAdapter;
import diaballik.gameElements.Pawn;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@XmlRootElement
// Should consider the attributes during the marshalling
@XmlAccessorType(XmlAccessType.FIELD)
// We have to identify the sub-classes to help the marshalling
@XmlSeeAlso({AiPlayer.class, HumanPlayer.class})
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
    @XmlTransient
    List<Pawn> pawns; // ne peut pas être mis protected pour MAVEN à cause de l'erreur URF_UNREAD_PUBLIC OR PROTECTED FIELDS

    /**
     * The Pawn which possesses the ball
     */
    @XmlJavaTypeAdapter(BallAdapter.class)
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

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", colour=" + colour +
                ", pawns=" + pawns +
                ", ball=" + ball +
                '}';
    }

    /**
     * Constructor of Player. The marshalling library we use require a default constructor (no arg).
     */
    Player() {
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
     * Setter of if it's his turn
     *
     * @param hashand : true if it's turn, false anyway
     */
    public void setHasHand(final boolean hashand) {
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
     * Getter of the list of pawns of the player
     *
     * @return the list of pawns of the player
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

    /**
     * Setter of the list of pawns of the player
     *
     * @param pawns the new list of pawns
     */
    public void setPawns(List<Pawn> pawns) {
        this.pawns = pawns;
    }
}
