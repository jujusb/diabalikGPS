package diaballik.Players.Algorithms;

import diaballik.Coordinates.ActionCoord;
import diaballik.GameElements.GameBoard;
import diaballik.Players.Player;

public abstract class Algo {
    /**
     * The current board of the game
     */
    GameBoard board;

    /**
     * The player who is the IA
     */
    Player player;

    public Algo(final Player p) {
        board = null;
        player = p;
    }

    /**
     * to get the board from the IA
     * @return the current board
     */
    public GameBoard getBoard() {
        return board;
    }

    /**
     * to set the board to the IA
     * @param board the current board
     */
    public void setBoard(final GameBoard board) {
        this.board = board;
    }

    /**
     * Function which returns a move to execute
     * @return an ActionCoord's instance, which defines the movement of the Player
     */
    public abstract ActionCoord decideMove();

}
