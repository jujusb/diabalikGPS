package diaballik.players.algorithms;

import diaballik.coordinates.ActionCoord;
import diaballik.coordinates.Coordinate;
import diaballik.gameElements.GameBoard;
import diaballik.players.AiPlayer;
import diaballik.players.HumanPlayer;
import diaballik.players.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AlgoTest {

    Player p1;
    AiPlayer p2;
    AiPlayer p3;
    GameBoard b;

    @BeforeEach
    void setUp() {
        p1 = new HumanPlayer("Bob", true);
        p2 = new AiPlayer(EAiType.STARTING, "Caroline", false);
        p3 = new AiPlayer(EAiType.NOOB, "John", false);
        b = new GameBoard(p1, p2);
        p2.setBoard(b);
    }

    @Test
    void setBoard() {
        GameBoard board2 = new GameBoard(p3, p2);
        board2.moveNoCheck(new ActionCoord(new Coordinate(0, 0), new Coordinate(0, 1)), false, false);

        p2.getAlgo().setBoard(board2);
        assertEquals(board2, p2.getAlgo().getBoard());
    }

    @Test
    void computeHeuristicStarting() {
        p2.setAlgo(new StartingAlgo(p2, -1, -1, -1, 1, 1, -1, -1, -1));
        p2.setBoard(b);

        final ActionCoord action = new ActionCoord(new Coordinate(3, 6), new Coordinate(4, 6));
        final double h = ((StartingAlgo) p2.getAlgo()).computeHeuristic(action, p1);

        assertEquals(0 - 7 * 6 - 8 + 8 + 0 - 0 - 0 - 6, h, 0.1);
    }

    @Test
    void moveAndCheck() {
        final Coordinate source = new Coordinate(1, 0);
        final Coordinate dest = new Coordinate(1, 0);

        List<ActionCoord> list = new ArrayList<>();

        // fails because there is a pawn on (2,0)
        p2.getAlgo().moveAndCheck(1, 0, list, dest, source);
        assertTrue(list.isEmpty());

        // succeeds because there is no pawn on (1,1)
        p2.getAlgo().moveAndCheck(-1, 1, list, dest, source);
        assertFalse(list.isEmpty());
        assertEquals(1, list.size());
        assertEquals(new ActionCoord(new Coordinate(1, 0), new Coordinate(1, 1)), list.get(0));

        // fails because (9,1) is not valid as it is out of bounds
        list.clear();
        p2.getAlgo().moveAndCheck(8, 0, list, dest, source);
        assertTrue(list.isEmpty());
    }

    @Test
    void calculatePossiblePawnMoves() {
        List<ActionCoord> list = p2.getAlgo().calculatePossiblePawnMoves(p1);

        assertFalse(list.isEmpty());
        assertEquals(6,list.size());
        assertTrue(list.contains(new ActionCoord(new Coordinate(0,0),new Coordinate(0,1))));
        assertTrue(list.contains(new ActionCoord(new Coordinate(1,0),new Coordinate(1,1))));
        assertTrue(list.contains(new ActionCoord(new Coordinate(2,0),new Coordinate(2,1))));
        assertTrue(list.contains(new ActionCoord(new Coordinate(4,0),new Coordinate(4,1))));
        assertTrue(list.contains(new ActionCoord(new Coordinate(5,0),new Coordinate(5,1))));
        assertTrue(list.contains(new ActionCoord(new Coordinate(6,0),new Coordinate(6,1))));
    }

    @Test
    void calculatePossibleBallMoves() {
        List<ActionCoord> list = p2.getAlgo().calculatePossibleBallMoves(p1);

        assertFalse(list.isEmpty());
        assertEquals(2,list.size());
        assertTrue(list.contains(new ActionCoord(new Coordinate(3,0),new Coordinate(2,0))));
        assertTrue(list.contains(new ActionCoord(new Coordinate(3,0),new Coordinate(4,0))));
    }

    @Test
    void calculatePossibleMoves() {
        List<ActionCoord> list = p2.getAlgo().calculatePossibleMoves(p1);

        assertFalse(list.isEmpty());
        assertEquals(8,list.size());
        assertTrue(list.contains(new ActionCoord(new Coordinate(0,0),new Coordinate(0,1))));
        assertTrue(list.contains(new ActionCoord(new Coordinate(1,0),new Coordinate(1,1))));
        assertTrue(list.contains(new ActionCoord(new Coordinate(2,0),new Coordinate(2,1))));
        assertTrue(list.contains(new ActionCoord(new Coordinate(4,0),new Coordinate(4,1))));
        assertTrue(list.contains(new ActionCoord(new Coordinate(5,0),new Coordinate(5,1))));
        assertTrue(list.contains(new ActionCoord(new Coordinate(6,0),new Coordinate(6,1))));
        assertTrue(list.contains(new ActionCoord(new Coordinate(3,0),new Coordinate(2,0))));
        assertTrue(list.contains(new ActionCoord(new Coordinate(3,0),new Coordinate(4,0))));
    }
}