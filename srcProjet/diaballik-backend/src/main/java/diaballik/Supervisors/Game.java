package diaballik.Supervisors;

import diaballik.GameElements.GameBoard;
import diaballik.Players.AiPlayer;
import diaballik.Players.Player;
import diaballik.Players.PlayerFactory;

import java.util.Map;
import java.util.Optional;

public class Game {
    /**
     * The number of actions that a player can do during a turn
     */
    private int nbActions;

    /**
     * The number of turns that have been achieved
     */
    private int nbTurns;

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
    private Player currentPlayer;

    /**
     * Constructor of games, creates a game from a query
     *
     * @param game game parameters in JSON
     */
    public void Game(final Map<String, String> game) {
        final PlayerFactory pf = new PlayerFactory();

        // the player1 is always human
        String name = game.get("namePlayer1");
        boolean colour = Boolean.getBoolean(game.get("colourPlayer1"));
        player1 = pf.createHuman(name, colour);

        colour = !colour; // the other player has the opposite color of the first one

        final String typePlayer2 = game.getOrDefault("aiLevel", null);

        // for the second player we have to check whether he is human or not
        if (typePlayer2.equals(null)) {
            // we have a human
            name = game.get("namePlayer2");
            player2 = pf.createHuman(name, colour);
        } else {
            // we have an AI
            name = game.get("namePlayer2");
            player2 = pf.createAi(typePlayer2, colour, Optional.ofNullable(name));
        }

        gameBoard = new GameBoard(player1, player2);

        if (player2 instanceof AiPlayer) {
            ((AiPlayer) player2).setBoard(gameBoard);
        }
    }

}
