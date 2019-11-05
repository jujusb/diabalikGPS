package diaballik.Players;

import diaballik.Coordinates.ActionCoord;
import diaballik.GameElements.GameBoard;
import diaballik.Players.Algorithms.Algo;
import diaballik.Players.Algorithms.EAiType;
import diaballik.Players.Algorithms.NoobAlgo;
import diaballik.Players.Algorithms.StartingAlgo;


import javax.xml.bind.annotation.XmlTransient;

public class AiPlayer extends Player {
    /**
     * The algorithm used to determine a move
     */
    @XmlTransient
    private Algo algo;

    /**
     * Symbolizes the number of turns played
     */
    private int currentTurn = 0;

    /**
     * Indicates at which turn the algorithm should swap
     */
    private int TURNS_BEFORE_SWAP;

    /**
     * Constructor of AiPlayer
     *
     * @param type the type of the AiPlayer with the EAiTyoe format
     * @param n    the name of the AiPlayer
     * @param c    the colour of the AiPlayer
     */
    public AiPlayer(final EAiType type, final String n, final boolean c) {
        super(n, c);
        currentTurn = 0;
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

    public AiPlayer() {
        super();
    }

    /**
     * Method which swaps the type of Algo under a certain condition
     */
    public void swap() {
        if (currentTurn == TURNS_BEFORE_SWAP) {
            final Algo temp = new StartingAlgo(this);
            temp.setBoard(algo.getBoard());
            algo = temp;
        }
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
     *
     * @param board the current gameboard
     */
    public void setBoard(final GameBoard board) {
        algo.setBoard(board);
    }

    /**
     * Setter of the current turn
     *
     * @param currentTurn the
     */
    public void setCurrentTurn(final int currentTurn) {
        this.currentTurn = currentTurn;
    }

    /**
     * Getter of the algorithm
     *
     * @return the current algorithm
     */
    public Algo getAlgo() {
        return algo;
    }
}
