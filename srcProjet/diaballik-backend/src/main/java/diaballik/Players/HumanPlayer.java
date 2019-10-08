package diaballik.Players;

import diaballik.Coordinates.ActionCoord;

public class HumanPlayer extends Player {

    /**
     * Constructor of HumanPlayer
     * @param n the name of the HumanPlayer
     * @param c the colour of the HumanPlayer
     */
    public HumanPlayer(final String n, final boolean c) {
        super(n, c);
    }


    /**
     * Function which wait and notify for the end of the turn
     * @return true if the turn of the player is finished, false otherwise
     */
    @Override
    public boolean waitEndOfTurn() {
        //TODO
        return false;
    }

    /**
     * Function which returns a move to execute
     * @return an ActionCoord's instance, which defines the movement of the Player
     */
    @Override
    public ActionCoord getMove() {
        //TODO
        return null;
    }
}
