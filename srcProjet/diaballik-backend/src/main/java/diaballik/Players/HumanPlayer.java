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
     * Function which wait and notify for the end of the turn
     *
     * @return true if the turn of the player is finished, false otherwise
     */
    @Override
    public void waitEndOfTurn() {
        try {
            sema.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Function which returns a move to execute
     *
     * @return an ActionCoord's instance, which defines the movement of the Player
     */
    @Override
    public ActionCoord getMove() {
        try {
            sema.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return currentAction;
    }

    /**
     * set the action that the player will announce in the getMove
     *
     * @param currentAction
     */
    public void setCurrentAction(final ActionCoord currentAction) {
        this.currentAction = currentAction;
    }

    /**
     * enables the player class to carry on what it was doing, meaning that the human player has done something
     */
    public void free() {
        sema.notifyAll();
    }

}
