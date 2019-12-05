package diaballik.players.algorithms.AlphaPion;

import diaballik.coordinates.ActionCoord;
import diaballik.gameElements.GameBoard;
import diaballik.players.Player;
import diaballik.supervisors.Game;

import java.util.List;
import java.util.stream.IntStream;


public class MonteCarloTreeSearch {
    /**
     * score that a node gets when it wins, or looses when it looses
     **/
    private static final int WIN_SCORE = 1;
    /**
     * player is the player for who the MCTS works
     **/
    private Player player;
    private Player opponent;

    int nbSimulations = 0;

    public ActionCoord findNextMove(final GameBoard currentBoard, final Player player, final Player opponent, final int nbActions) {
        final long start = System.currentTimeMillis();
        final long end = start + 5000;

        this.opponent = opponent;
        this.player = player;

        final Tree tree = new Tree();
        final Node rootNode = tree.getRoot();

        final GameBoard board = (GameBoard) currentBoard.clone();

        rootNode.getState().setGame(new Game(board, nbActions));
        rootNode.getState().setPlayer(board.getPlayer2());

        // stream that explores nodes until the end of the allocated time
        IntStream
                .iterate(1, n -> n + 1)
                .takeWhile(n -> System.currentTimeMillis() < end)
                .forEach(n -> {
                    // Phase 1 - Selection of the most interesting node
                    final Node promisingNode = selectPromisingNode(rootNode);
                    // Phase 2 - Expansion of the node to get its children
                    if (promisingNode.getState().getGame().getWinner() == null) { // checks that the game isn't finished
                        expandNode(promisingNode);
                    }
                    // Phase 3 - Simulation : chooses a random child node and tries a game with it
                    Node nodeToExplore = promisingNode;
                    if (promisingNode.getChildArray().size() > 0) {
                        nodeToExplore = promisingNode.getRandomChildNode();
                    }
                    final Player playoutResult = simulateRandomPlayout(nodeToExplore);
                    // Phase 4 - Update of the scores
                    backPropogation(nodeToExplore, playoutResult);
                });


        /**
         * We run out of time : we have to stop the exploration there
         */
        final Node winnerNode = rootNode.getChildWithMaxScore();
        tree.setRoot(winnerNode);
        System.out.println("Chances to win : " + (winnerNode.getState().

                getWinScore() + 5 * winnerNode.getState().

                getVisitCount()) / (10 * winnerNode.getState().

                getVisitCount()));
        System.out.println("Number of simulations : " + nbSimulations); // ~ beetween 5000-15000
        return winnerNode.getState().

                getActionCoord();

    }

    /**
     * Selects the most interesting node using UCT values, the node has to be a leaf of the tree
     *
     * @param rootNode "father" of the nodes we want to order and to choose, it is the root of our tree here
     * @return the node with the highest UCT score
     */
    private Node selectPromisingNode(final Node rootNode) {
        Node node = rootNode;

        // recursively looks for the best leaf of the tree
        if (node.getChildArray().size() != 0) {
            node = selectPromisingNode(UCT.findBestNodeWithUCT(node, player));
        }

        return node;
    }

    /**
     * Expands the node : creates its childrens and add them to the tree
     *
     * @param node the node that is to be expanded
     */
    private void expandNode(final Node node) {
        final List<State> possibleStates = node.getState().getAllPossibleStates();
        possibleStates.forEach(state -> {
            final Node newNode = new Node(state);
            newNode.setParent(node);
            node.getChildArray().add(newNode);
        });
    }

    /**
     * randomly simulates the game, updating node and state
     *
     * @param node node where we have to do a simulation
     * @return the player that wins
     */
    private Player simulateRandomPlayout(final Node node) {
        final Node tempNode = new Node(node);
        final State tempState = tempNode.getState();
        final Player boardStatus = tempState.getGame().getWinner();

        if (boardStatus == opponent) {
            tempNode.getParent().getState().setWinScore(Integer.MIN_VALUE);
            return boardStatus;
        }

        // we randomly play this game to its end and cound the number of iterations needed
        //int c = 0;
        randomlyPlayUntilEnd(tempState, 0);

        //System.out.println("c = " + c); // typically 500-1000
        nbSimulations++;

        return boardStatus;
    }

    /**
     * Randomly and recursively plays a game to its end
     *
     * @param s the state of the game at which we are
     * @param c the number of actions already simulated
     * @return the number of actions simulated for the whole game
     */
    int randomlyPlayUntilEnd(final State s, final int c) {
        // there is a winner, we do not need to play
        if (s.getGame().getWinner() != null) {
            return c;
        } else {
            // there is no winner, we have to keep on playing
            s.randomPlay();
            s.getGame().swapPlayer();
            return randomlyPlayUntilEnd(s, c + 1);
        }
    }

    /**
     * Once the simulation has been done, updates the score of all the nodes above the one we chose
     *
     * @param node   the node that has been evaluated
     * @param winner the winner of this evaluation
     */
    private void backPropogation(final Node node, final Player winner) {
        if (node != null) {
            node.getState().incrementVisit();
            if (node.getState().getPlayer().equals(winner)) {
                node.getState().addScore(WIN_SCORE);
            } else {
                node.getState().addScore(-WIN_SCORE);
            }
            backPropogation(node.getParent(), winner);
        }
    }


}
