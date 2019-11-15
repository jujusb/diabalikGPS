package diaballik.players.algorithms.AlphaPion;


import diaballik.coordinates.ActionCoord;
import diaballik.players.AiPlayer;
import diaballik.players.Player;
import diaballik.supervisors.Game;

import java.util.ArrayList;
import java.util.List;

public class State {
    private Game game;
    /**
     * represents the player that can lead to this game situation. It is not always related to the game player turn, for exemple if a players has 1 action left
     * and moves a pawn, this will lead to a new node with a new state in which it is the opponent's turn, but the value of player will be the one who did this move
     */
    private Player player;
    private int visitCount;
    private double winScore;

    /**
     * Creates an empty state, used when creating an empty node (e.g. for the tree root)
     */
    public State() {
        game = new Game();
        player = game.getCurrentPlayer();
    }

    /**
     * Creates a state from a new game possibility
     *
     * @param game   The game possibility
     * @param player The player who can lead to this possibility
     */
    private State(Game game, Player player) {
        this.game = game;
        visitCount = 0;
        winScore = 0;
        this.player = player;

    }

    /**
     * Copies an other state so that we can simulate it without modifying it
     *
     * @param state The state we want to copy
     */
    public State(State state) {
        this.game = (Game) state.getGame().clone();
        this.visitCount = state.getVisitCount();
        this.winScore = state.getWinScore();
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public int getVisitCount() {
        return visitCount;
    }

    public double getWinScore() {
        return winScore;
    }

    public void setWinScore(double winScore) {
        this.winScore = winScore;
    }

    /**
     * Get the states we can have after this one. Quite slow but only used to create new nodes, so it does not matter as the time is mainly spent in simulations (phase 3 of MCTS)
     *
     * @return A liste of all possibilities the current game player (and not the attribute "player" of this object) has
     */
    public List<State> getAllPossibleStates() {
        List<ActionCoord> listGame = ((AiPlayer) player).getAlgo().calculatePossibleMoves(game.getCurrentPlayer());
        List<State> listState = new ArrayList<>();
        for (ActionCoord action : listGame) {
            Game possibleGame = (Game) game.clone();
            game.moveOfPlayerNoCheck(action);

            listState.add(new State(possibleGame, game.getCurrentPlayer()));
        }
        return listState;
    }

    public void incrementVisit() {
        this.visitCount++;
    }

    public void addScore(double score) {
        this.winScore += score;
    }


    /**
     * Randomly plays, modifying the current game.
     * This method takes most of the MCTS computing time
     */
    public void randomPlay() {
        game.moveOfPlayerNoCheck(((AiPlayer)game.getCurrentPlayer()).getAlgo().decideMove());
    }
}
