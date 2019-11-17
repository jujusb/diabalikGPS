package diaballik.players.algorithms.AlphaPion;

import diaballik.coordinates.ActionCoord;
import diaballik.gameElements.GameBoard;
import diaballik.players.Player;
import diaballik.supervisors.Game;

import java.util.List;


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
        long start = System.currentTimeMillis();
        long end = start + 5000;

        this.opponent = opponent;
        this.player = player;

        Tree tree = new Tree();
        Node rootNode = tree.getRoot();

        GameBoard board = (GameBoard) currentBoard.clone();

        rootNode.getState().setGame(new Game(board, nbActions));
        rootNode.getState().setPlayer(board.getPlayer2());

        while (System.currentTimeMillis() < end) {
            // Phase 1 - Selection of the most interesting node
            Node promisingNode = selectPromisingNode(rootNode);
            // Phase 2 - Expansion of the node to get its children
            if (promisingNode.getState().getGame().getWinner() == null) // checks that the game isn't finished
                expandNode(promisingNode);

            // Phase 3 - Simulation : chooses a random child node and tries a game with it
            Node nodeToExplore = promisingNode;
            if (promisingNode.getChildArray().size() > 0) {
                nodeToExplore = promisingNode.getRandomChildNode();
            }
            Player playoutResult = simulateRandomPlayout(nodeToExplore);
            // Phase 4 - Update of the scores
            backPropogation(nodeToExplore, playoutResult);

        }

        /** We run out of time : we have to stop the exploration there */
        Node winnerNode = rootNode.getChildWithMaxScore();
        tree.setRoot(winnerNode);
        System.out.println("Chances to win : " + (winnerNode.getState().getWinScore() + 5 * winnerNode.getState().getVisitCount()) / (10 * winnerNode.getState().getVisitCount()));
        System.out.println("Number of simulations : " + nbSimulations); // ~ beetween 5000-15000
        return winnerNode.getState().getActionCoord();
    }

    /**
     * Selects the most interesting node using UCT values, the node has to be a leaf of the tree
     *
     * @param rootNode "father" of the nodes we want to order and to choose, it is the root of our tree here
     * @return the node with the highest UCT score
     */
    private Node selectPromisingNode(Node rootNode) {
        Node node = rootNode;
        while (node.getChildArray().size() != 0) {
            node = UCT.findBestNodeWithUCT(node, player);
        }
        return node;
    }

    /**
     * Expands the node : creates its childrens and add them to the tree
     *
     * @param node the node that is to be expanded
     */
    private void expandNode(Node node) {
        List<State> possibleStates = node.getState().getAllPossibleStates();
        possibleStates.forEach(state -> {
            Node newNode = new Node(state);
            newNode.setParent(node);
            node.getChildArray().add(newNode);
        });
    }

    /**
     * randomly simulates the game. each player has more chances to advance his pawns and to attack than to go back, so that the simulation will be shorter.
     *
     * @param node node where we have to do a simulation
     * @return the player that wins
     */
    private Player simulateRandomPlayout(Node node) {
        Node tempNode = new Node(node);
        State tempState = tempNode.getState();
        Player boardStatus = tempState.getGame().getWinner();

        if (boardStatus == opponent) {
            tempNode.getParent().getState().setWinScore(Integer.MIN_VALUE);
            return boardStatus;
        }

        int c = 0;
        while (boardStatus == null) { // while the game isn't finished
            tempState.setPlayer(tempState.getGame().getCurrentPlayer());
            tempState.randomPlay();
            c++;
            tempState.getGame().swapPlayer();
            boardStatus = tempState.getGame().getWinner();
        }
        System.out.println("c = " + c); // typically 500-1000
        nbSimulations++;

        return boardStatus;
    }

    /**
     * Once the simulation has been done, updates the score of all the nodes above the one we chose
     *
     * @param nodeToExplore the node that has been evaluated
     * @param winner        the winner of this evaluation
     */
    private void backPropogation(Node nodeToExplore, Player winner) {
        Node tempNode = nodeToExplore;
        while (tempNode != null) {
            tempNode.getState().incrementVisit();

            if (tempNode.getState().getPlayer().equals(winner)) {
                tempNode.getState().addScore(WIN_SCORE);
            } else {
                tempNode.getState().addScore(-WIN_SCORE);
            }
            tempNode = tempNode.getParent();
        }
    }


}
