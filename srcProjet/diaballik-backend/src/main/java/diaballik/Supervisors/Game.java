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
     * The thread in which we execute the game
     */
    private Thread threadOfTheGame;

    /**
     * True if there is an undo request false otherwise
     */
    private boolean undo;

    /**
     * True if there is a redo request false otherwise
     */
    private boolean redo;


    /**
     * Builder of games, creates a game from a query
     *
     * @param game game parameters in JSON
     */
    public void initializeGame(final Map<String, String> game) {
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

        threadOfTheGame = new Thread(new Runnable() {
            @Override
            public void run() {
                runGame();
            }
        });
    }

    /**
     * game computing
     */
    private void runGame() {
        // while nobody has won

        // TODO transformer le code suivant en streams (bon courage :D) :
        /*
        while (!gameBoard.checkIfWon()) {
            // makes player1 play
            for (int i = 0; i < nbActions; i++) {
                // if the move that the player tries is not legal
                if (!gameBoard.move(player1, player1.getMove())) {
                    // does one iteration more to let the player try something else
                    i--;
                    if(undo) {gameBoard.undo(); i--; undo=false;}
                    if(redo) {gameBoard.redo(); i++; redo=false;}
                } else {
                    // if the player has won during his turn
                    if (gameBoard.checkIfWon()) {
                        return;
                    }
                }
                if(i==nbActions-1){
                    player1.waitEndOfTurn();
                    if(undo) {gameBoard.undo(); i--; undo=false;}
                    if(redo) {gameBoard.redo(); i++; redo=false;}
                }
            }

           currentPlayer=player2;

            // and then makes player2 play
            for (int i = 0; i < nbActions; i++) {
                // if the move that the player tries is not legal
                if (!gameBoard.move(player2, player2.getMove())) {
                    // does one iteration more to let the player try something else
                    i--;
                    if(undo) {gameBoard.undo(); i--; undo=false;}
                    if(redo) {gameBoard.redo(); i++; redo=false;}
                } else {
                    // if the player has won during his turn
                    if (gameBoard.checkIfWon()) {
                        return;
                    }
                }
                if(i==nbActions-1){
                    player2.waitEndOfTurn();
                    if(undo) {gameBoard.undo(); i--; undo=false;}
                    if(redo) {gameBoard.redo(); i++; redo=false;}
                }
            }
            currentPlayer=player1
        }*/
    }

    /**
     * Launches the game in a new thread
     */
    public void start() {
        threadOfTheGame.start();
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
        undo = true;
    }

    /**
     * Tells the game there is a redo request
     */
    public void redo() {
        redo = true;
    }

}
