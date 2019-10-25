package diaballik.Players;

import diaballik.Coordinates.ActionCoord;
import diaballik.GameElements.GameBoard;
import diaballik.Players.Algorithms.Algo;
import diaballik.Players.Algorithms.EAiType;
import diaballik.Players.Algorithms.NoobAlgo;
import diaballik.Players.Algorithms.StartingAlgo;

public class AiPlayer extends Player {

    /**
     * The algorithm used to determine a move
     */
    private Algo algo;

    /**
     * Symbolizes the number of turns played
     */
    private int current_turns;

    /**
     * Indicates at which turn the algorithm should swap
     */
    private final int TURNS_BEFORE_SWAP;

    /**
     * Constructor of AiPlayer
     *
     * @param type the type of the AiPlayer with the EAiTyoe format
     * @param n    the name of the AiPlayer
     * @param c    the colour of the AiPlayer
     */
    public AiPlayer(final EAiType type, final String n, final boolean c) {
        super(n, c);
        current_turns = 0;
        switch (type) {
            case NOOB:
                algo = new NoobAlgo(this);
                TURNS_BEFORE_SWAP = -1;
                break;
            case STARTING:
                algo = new StartingAlgo(this);
                TURNS_BEFORE_SWAP = -1;
                break;
            default:
                algo = new NoobAlgo(this);
                TURNS_BEFORE_SWAP = 3;
        }
    }

    /**
     * Method which swaps the type of Algo under a certain condition
     */
    public void swap() {
        if (current_turns == TURNS_BEFORE_SWAP) {
            final Algo temp = new StartingAlgo(this);
            temp.setBoard(algo.getBoard());
            algo = temp;
        }
    }

    /**
     * Function that finish the turn of the AiPlayer. It increments current_turns by 1
     * and calls the function swap() before returning true.
     *
     * @return true
     */
    public void endOfTurn() {
        current_turns++;
        swap();
        /*An AiPlayer never fails*/
    }

    /**
     * Function which returns a move to execute
     *
     * @return an ActionCoord's instance, which defines the movement of the Player
     */
    public ActionCoord getMove() {
        return algo.decideMove();
    }

    /**
     * Defines the board for the algorithm which needs it
      * @param board the current gameboard
     */
    public void setBoard(final GameBoard board) {
        algo.setBoard(board);
    }
}
