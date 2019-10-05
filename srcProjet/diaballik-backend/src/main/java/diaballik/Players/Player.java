package diaballik.Players;

import diaballik.GameElements.Pawn;

import java.util.List;

/**
 * class Player
 * Class used to specify the parameters and functions of a Player
 */
public abstract class  Player {

    /**
     * The name of the player
     */
    private String name;

    /**
     * The colour of the Player. Since only two colors are allowed in the game, we
     * implement the colour with a boolean, true if the colour is white, and false if the color is black.
     */
    private boolean colour;

    /**
     * The different pieces a Player owns. The ball is included in this List.
     */
    private List<Pawn> pieces;

    /**
     * The Pawn which possesses the ball
     */
    private Pawn ball;

    /**
     * Getter of ball
     * @return the current Pawn who possesses the ball
     */
    public Pawn getBall() {
        return ball;
    }

    public void setBall(Pawn p){
        ball=p;
    }
}
