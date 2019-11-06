package diaballik.Supervisors;

import diaballik.Coordinates.ActionCoord;
import diaballik.GameElements.GameBoard;
import diaballik.Players.AiPlayer;
import diaballik.Players.Player;
import diaballik.Players.PlayerAdapter;
import diaballik.Players.PlayerFactory;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Game {
    /**
     * The number of actions that a player can do during a turn
     */
    private int nbActionsPerTurn = 3;

    /**
     * The number of actions that have been played, from 0 to nbActionsPerTurn
     */
    private int nbActions;

    /**
     * The number of turns that have been achieved
     */
    private int currentTurn = 0;

    /**
     * The current gameboard
     */
    private GameBoard gameBoard;

    /**
     * The first player (white)
     */
    private Player player1;

    /**
     * The second player (black)
     */
    private Player player2;

    /**
     * The player that currently plays
     */
    @XmlJavaTypeAdapter(PlayerAdapter.class)
    private Player currentPlayer;

    public Game() {
    }

    public Game(final Player p1, final Player p2) {
        player1 = p1;
        player2 = p2;
        gameBoard = new GameBoard(player1, player2);

        if (player2 instanceof AiPlayer) {
            ((AiPlayer) player2).setBoard(gameBoard);
        }

        currentPlayer = player1;
    }

    /**
     * Builder of games, creates a game from a query
     *
     * @param game game parameters in JSON
     */
    public void initializeGame(final Map<String, String> game) {
        final PlayerFactory pf = new PlayerFactory();

        // the player1 is always human
        String name = game.get("namePlayer1");
        final boolean colour = Boolean.getBoolean(game.get("colourPlayer1"));
        player1 = pf.createHuman(name, colour);

        final String typePlayer2 = game.getOrDefault("aiLevel", null);

        // for the second player we have to check whether he is human or not
        if (typePlayer2 == null) {
            // we have a human
            name = game.get("namePlayer2");
            player2 = pf.createHuman(name, !colour);
        } else {
            // we have an AI
            name = game.get("namePlayer2");
            player2 = pf.createAi(typePlayer2, !colour, Optional.ofNullable(name));
        }

        gameBoard = new GameBoard(player1, player2);

        if (player2 instanceof AiPlayer) {
            ((AiPlayer) player2).setBoard(gameBoard);
        }

        currentPlayer = player1;
    }


    /**
     * Getter of the current Player
     *
     * @return the current player
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Tells the game there is an undo request
     */
    public void undo() {
        gameBoard.undo();
    }

    /**
     * Tells the game there is a redo request
     */
    public void redo() {
        gameBoard.redo();
    }

    /**
     * Tries to move a pawn or a ball of a human player
     *
     * @param move the move that is tried
     */
    public void moveOfPlayer(final ActionCoord move) {
        // if the player still has moves and if his choice is "legal"
        if (nbActions < nbActionsPerTurn && gameBoard.move(currentPlayer, move)) {
            nbActions++;
            System.out.println(gameBoard);
        }
    }

    /**
     * Notifies the player wants to end his turn
     */
    public void endOfTurn() {
        // if it is really the turn of the other player
        if (nbActions == nbActionsPerTurn) {
            // updates the current player
            swapPlayer();

            // updates the nb of actions
            nbActions = 0;
            // increases the number of turns only if we came back to player1
            if (currentPlayer == player1) {
                currentTurn++;
                // in case of AI we have to notice it of the current turns
                if (player2 instanceof AiPlayer) {
                    ((AiPlayer) player2).setCurrentTurn(currentTurn);
                }
            }

            // if it is an AI we make it play instantly
            if (currentPlayer instanceof AiPlayer) {
                // eventually swaps the AI algorithm if we have a progressive AI
                ((AiPlayer) currentPlayer).swap();
                Stream.iterate(0, i -> i < nbActionsPerTurn, i -> i + 1)
                        .forEach(i -> {
                            gameBoard.moveNoCheck(((AiPlayer) currentPlayer).getMove(), true, true);
                            System.out.println(gameBoard);
                        });
                swapPlayer();
            }
        }
    }

    /**
     * Simply swaps the player that currently plays
     */
    public void swapPlayer() {
        if (currentPlayer == player1) {
            currentPlayer = player2;
        } else {
            currentPlayer = player1;
        }
    }

    /**
     * Gets the board
     *
     * @return the gameboard
     */
    public GameBoard getGameBoard() {
        return gameBoard;
    }
}
