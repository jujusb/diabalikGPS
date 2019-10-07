package diaballik.Players;

import diaballik.Coordinates.ActionCoord;

public class HumanPlayer extends Player {

    /**
     * Constructor of HumanPlayer
     * @param n the name of the HumanPlayer
     * @param c the colour of the HumanPlayer
     */
    public HumanPlayer(String n, boolean c){
        super(n,c);
    }

    @Override
    public boolean waitEndOfTurn() {
        //TODO
        return false;
    }

    @Override
    public ActionCoord getMove() {
        //TODO
        return null;
    }
}
