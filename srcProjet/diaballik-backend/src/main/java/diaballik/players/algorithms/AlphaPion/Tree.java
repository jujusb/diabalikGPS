package diaballik.players.algorithms.AlphaPion;

class Tree {
    private Node root;

    Tree() {
        root = new Node();
    }


    public Node getRoot() {
        return root;
    }

    public void setRoot(final Node root) {
        this.root = root;
    }
}
