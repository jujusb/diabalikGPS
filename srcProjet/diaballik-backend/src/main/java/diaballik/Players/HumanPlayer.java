package diaballik.Players;

import diaballik.Coordinates.ActionCoord;

public class HumanPlayer extends Player {
    private ActionCoord currentAction;
    private Object sema = new Object();

    /**
     * Constructor of HumanPlayer
     *
     * @param n the name of the HumanPlayer
     * @param c the colour of the HumanPlayer
     */
    public HumanPlayer(final String n, final boolean c) {
        super(n, c);
    }

    /**
     * set the action that the player will announce in the getMove
     *
     * @param currentAction
     */
    public void setCurrentAction(final ActionCoord currentAction) {
        this.currentAction = currentAction;
    }
}
