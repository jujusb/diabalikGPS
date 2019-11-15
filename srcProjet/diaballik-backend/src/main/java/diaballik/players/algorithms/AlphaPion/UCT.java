package diaballik.players.algorithms.AlphaPion;

import Jeu.model.Player;

import java.util.Collections;
import java.util.Comparator;

class UCT {

    /**
     * Computes the UCT (upper confidence bound) values of each node. The higher it is, the more chances we have to explore this tree
     *
     * @param totalVisit   Number of visits the parent of the node has had
     * @param nodeWinScore The total score of the node (0 is we had as many looses as victories, negative if we loose more and positive if we win more)
     * @param nodeVisit    Number of visit the node has had
     * @param playerOfNode The player leading to this node
     * @param playerGoal   The player we want to make win. If the opponent plays, we suppose it takes harmful decisions
     * @return The UCT value of the node
     */
    private static double uctValue(int totalVisit, double nodeWinScore, int nodeVisit, Player playerOfNode, Player playerGoal) {
        if (nodeVisit == 0) {
            return Integer.MAX_VALUE;
        }
        if (playerOfNode.equals(playerGoal))
            return (nodeWinScore / (double) nodeVisit) + 1.41 * Math.sqrt(Math.log(totalVisit) / (double) nodeVisit);
        else
            return (-nodeWinScore / (double) nodeVisit) + 1.41 * Math.sqrt(Math.log(totalVisit) / (double) nodeVisit);
    }

    /**
     * Given a node, gives the child with the highest UCT, i.e. the child we have to explore
     *
     * @param node       The parent root from where we want to choose a child
     * @param playerGoal The player we want to make win
     * @return The child we need to explore
     */
    static Node findBestNodeWithUCT(Node node, Player playerGoal) {
        int parentVisit = node.getState().getVisitCount();
        return Collections.max(
                node.getChildArray(),
                Comparator.comparing(c -> uctValue(parentVisit, c.getState().getWinScore(), c.getState().getVisitCount(), c.getState().getPlayer(), playerGoal)));
    }

}
