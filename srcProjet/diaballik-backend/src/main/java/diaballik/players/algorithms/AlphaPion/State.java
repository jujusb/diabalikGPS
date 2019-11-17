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
     * The actionCoord that has led to this situation
     */
    private ActionCoord actionCoord;
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
    private State(Game game, Player player, ActionCoord action) {
        this.game = game;
        visitCount = 0;
        winScore = 0;
        this.player = player;
        this.actionCoord = action;
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

        // defines the right player to the state
        if(state.getPlayer() == state.getGame().getPlayer1()){
            this.player = game.getPlayer1();
        }else{
            this.player = game.getPlayer2();
        }
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

    public ActionCoord getActionCoord() {
        return actionCoord;
    }

    public void setGame(Game game) {
        this.game = game;
        this.player = game.getCurrentPlayer();
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
            //System.out.println("New possible node : ");
            possibleGame.moveOfPlayerNoCheck(action);
            possibleGame.swapPlayer();

            listState.add(new State(possibleGame, game.getCurrentPlayer(), action));
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
        //System.out.println("Random play : ");
        game.moveOfPlayerNoCheck(((AiPlayer)game.getCurrentPlayer()).getAlgo().decideMove(0));
    }
}
