package diaballik.players.algorithms.AlphaPion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Node {
    /**
     * State represents the state of the game corresponding to the node
     **/
    private State state;
    private Node parent;
    private List<Node> childArray;

    /**
     * Creates an empty node, used for the root of the tree
     */
    public Node() {
        this.state = new State();
        childArray = new ArrayList<>();
    }

    /**
     * Creates a node corresponding to a possible game state
     *
     * @param state State of the game related to the new node we create
     */
    public Node(final State state) {
        this.state = state;
        childArray = new ArrayList<>();
    }

    /**
     * Creates a node copying another node. This is used to make a simulation for a given node without modifying it
     *
     * @param node The node to copy
     */
    public Node(final Node node) {
        this.childArray = new ArrayList<>();
        this.state = new State(node.getState());
        if (node.getParent() != null) {
            this.parent = node.getParent();
        }
        final List<Node> childArray = node.getChildArray();
        childArray.forEach(child -> this.childArray.add(new Node(child)));
    }

    public State getState() {
        return state;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(final Node parent) {
        this.parent = parent;
    }

    public List<Node> getChildArray() {
        return childArray;
    }


    /**
     * Selects a random node from the children of the node
     *
     * @return A random children node
     */
    public Node getRandomChildNode() {
        final int noOfPossibleMoves = this.childArray.size();
        final int selectRandom = (int) (Math.random() * noOfPossibleMoves);
        return this.childArray.get(selectRandom);
    }

    /**
     * Gets the child wich has the best score. It is used at the end of the MCTS iterations
     *
     * @return The best child node. In our case, the best decision the MCTS could take for the next move (a childnode of the root)
     */
    public Node getChildWithMaxScore() {
        return Collections.max(this.childArray, Comparator.comparing(c -> c.getState().getVisitCount()));
    }

}
