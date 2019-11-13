package diaballik.players;

import diaballik.coordinates.ActionCoord;
import diaballik.gameElements.GameBoard;
import diaballik.players.algorithms.AiAlgoAdaptor;
import diaballik.players.algorithms.Algo;
import diaballik.players.algorithms.EAiType;
import diaballik.players.algorithms.NoobAlgo;
import diaballik.players.algorithms.StartingAlgo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Objects;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class AiPlayer extends Player {
    /**
     * The algorithm used to determine a move
     */
    @XmlJavaTypeAdapter(AiAlgoAdaptor.class)
    private Algo algo;

    /**
     * Symbolizes the number of turns played
     */
    @XmlTransient
    int currentTurn = 0;

    /**
     * Indicates at which turn the algorithm should swap
     */
    @XmlTransient
    private int TURNS_BEFORE_SWAP;

    /**
     * Getter of the turn the algorithm should swap
     */
    public int getTURNS_BEFORE_SWAP() {
        return TURNS_BEFORE_SWAP;
    }

    /**
     * Constructor of AiPlayer
     *
     * @param t the type of the AiPlayer with the EAiTyoe format
     * @param n the name of the AiPlayer
     * @param c the colour of the AiPlayer
     */
    public AiPlayer(final EAiType t, final String n, final boolean c) {
        super(n, c);
        currentTurn = 0;
        switch (t) {
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


    /**
     * Setter of the algorithm
     *
     * @param a the algorithm we want to set
     */
    public void setAlgo(final Algo a) {
        this.algo = a;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        final AiPlayer aiPlayer = (AiPlayer) o;
        return currentTurn == aiPlayer.currentTurn &&
                TURNS_BEFORE_SWAP == aiPlayer.TURNS_BEFORE_SWAP; //aucun test sur algo
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), algo, currentTurn, TURNS_BEFORE_SWAP);
    }
}
