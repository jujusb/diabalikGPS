package diaballik.Players.Algorithms;

import diaballik.Coordinates.ActionCoord;
import diaballik.Coordinates.Coordinate;
import diaballik.GameElements.GameBoard;
import diaballik.Players.AiPlayer;
import diaballik.Players.HumanPlayer;
import diaballik.Players.Player;
import diaballik.Supervisors.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AlgoTest {

    Player p1;
    AiPlayer p2;
    AiPlayer p3;

    @BeforeEach
    void setUp() {
        p1 = new HumanPlayer("Bob", true);
        p2 = new AiPlayer(EAiType.STARTING, "Caroline", false);
        p3 = new AiPlayer(EAiType.NOOB, "John", false);
        p2.setBoard(new GameBoard(p1, p2));
    }

    @Test
    void getBoard() {
        GameBoard board = p2.getAlgo().getBoard();
        assertNotNull(board);
        assertTrue(board.getPawn(new Coordinate(3, 0)).get().isBallOwner()); //check the ball for player 1
        assertTrue(board.getPawn(new Coordinate(3, 6)).get().isBallOwner()); //check the ball for player 2
        assertFalse(board.getPawn(new Coordinate(2, 6)).get().isBallOwner()); //check if another pawn does not have the ball

        System.out.println(board);

        assertTrue(board.getPawn(new Coordinate(0, 0)).isPresent());    //check all the pawns of player1
        assertTrue(board.getPawn(new Coordinate(1, 0)).isPresent());
        assertTrue(board.getPawn(new Coordinate(2, 0)).isPresent());
        assertTrue(board.getPawn(new Coordinate(3, 0)).isPresent());
        assertTrue(board.getPawn(new Coordinate(4, 0)).isPresent());
        assertTrue(board.getPawn(new Coordinate(5, 0)).isPresent());
        assertTrue(board.getPawn(new Coordinate(6, 0)).isPresent());

        assertTrue(board.getPawn(new Coordinate(0, 6)).isPresent());    //check all the pawns of player 2
        assertTrue(board.getPawn(new Coordinate(1, 6)).isPresent());
        assertTrue(board.getPawn(new Coordinate(2, 6)).isPresent());
        assertTrue(board.getPawn(new Coordinate(3, 6)).isPresent());
        assertTrue(board.getPawn(new Coordinate(4, 6)).isPresent());
        assertTrue(board.getPawn(new Coordinate(5, 6)).isPresent());
        assertTrue(board.getPawn(new Coordinate(6, 6)).isPresent());

        System.out.println(board.playerPawnCoordinates());

        assertTrue(board.getPawn(new Coordinate(3, 3)).isEmpty()); // check an empty position
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
        p2.setBoard(new GameBoard(p1, p2));
        //TODO

        final ActionCoord action = new ActionCoord(new Coordinate(3, 6), new Coordinate(4, 6));
        //final double h = ((StartingAlgo) p2.getAlgo()).computeHeuristic(action, p1);

        //assertEquals(0 - 7*6 - 8 + 8 + 0 - 0 - 6, h);
    }

    @Test
    void moveAndCheck() {
        //TODO
    }

    @Test
    void calculatePossiblePawnMoves() {
        //TODO
    }

    @Test
    void calculatePossibleBallMoves() {
        //TODO
    }

    @Test
    void calculatePossibleMoves() {
        //TODO
    }
}