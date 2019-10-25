package diaballik.Supervisors;

import diaballik.Coordinates.ActionCoord;
import diaballik.GameElements.GameBoard;
import diaballik.Players.AiPlayer;
import diaballik.Players.Player;
import diaballik.Players.PlayerFactory;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;


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
    private Player currentPlayer;

    /**
     * The thread in which we execute the game
     */
    private Thread threadOfTheGame;


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
        if (typePlayer2 == null) {
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

        currentPlayer = player1;
        threadOfTheGame = new Thread(() -> runGame());
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
        gameBoard.undo();
    }

    /**
     * Tells the game there is a redo request
     */
    public void redo() {
        gameBoard.redo();
    }

    /**
     * Kills the thread that computes the game
     */
    public void kill() {
        try {
            threadOfTheGame.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Tries to move a pawn or a ball of a human player
     *
     * @param move the move that is tried
     */
    public void moveOfPlayer(final ActionCoord move) {
        if (nbActions != nbActionsPerTurn && gameBoard.move(currentPlayer, move)) {
            nbActions++;
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
                // swaps the AI algorithm if we have a progressive AI
                ((AiPlayer) currentPlayer).swap();
                Stream.iterate(0, i -> i < nbActionsPerTurn, i -> i + 1)
                        .forEach(i -> gameBoard.moveNoCheck(((AiPlayer) currentPlayer).getMove(), true));
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
}
