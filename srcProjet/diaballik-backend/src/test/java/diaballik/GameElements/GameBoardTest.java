package diaballik.GameElements;

import diaballik.Coordinates.ActionCoord;
import diaballik.Coordinates.Coordinate;
import diaballik.Players.HumanPlayer;
import diaballik.Players.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

class GameBoardTest {
    GameBoard board;
    Player p1 = new HumanPlayer("Toto", false);
    Player p2 = new HumanPlayer("Tata", true);

    @BeforeEach
    void init() {
        board = new GameBoard(p1, p2);
        board.move(p1, new ActionCoord(new Coordinate(4, 0), new Coordinate(4, 1)));
    }

    @Test
    void getPawn() {
        assertEquals(board.getHumanPlayer().getPawns().get(0),board.getPawn(board.getHumanPlayer().getPawns().get(0).getPosition()).get());
    }

    @Test
    void constructor() {
        assertTrue(board.getPawn(new Coordinate(3, 0)).get().isBallOwner());
        assertTrue(board.getPawn(new Coordinate(3, 6)).get().isBallOwner());
        assertFalse(board.getPawn(new Coordinate(2, 6)).get().isBallOwner());
        assertTrue(board.getPawn(new Coordinate(3, 3)).isEmpty());
    }

    @Test
    void canMove() {
        assertTrue(board.canMove(p1, new ActionCoord(new Coordinate(0, 0), new Coordinate(0, 1))));
    }

    @Test
    void movePawnVertical() {
        assertTrue(board.move(p1, new ActionCoord(new Coordinate(0, 0), new Coordinate(0, 1))));
        assertTrue(board.getPawn(new Coordinate(0, 1)).isPresent());
        assertTrue(board.getPawn(new Coordinate(0, 0)).isEmpty());
    }

    @Test
    void moveBallHorizontal() {
        assertTrue(board.getPawn(new Coordinate(3, 0)).get().isBallOwner());
        assertTrue(board.move(p1, new ActionCoord(new Coordinate(3, 0), new Coordinate(2, 0))));
        assertEquals(board.getPawn(new Coordinate(2, 0)).get(),board.getHumanPlayer().getBall());
        assertTrue(board.getPawn(new Coordinate(2, 0)).get().isBallOwner());
        assertFalse(board.getPawn(new Coordinate(3, 0)).get().isBallOwner());
    }

    @Test
    void moveBallVertical() {
        assertTrue(board.getPawn(new Coordinate(3, 0)).get().isBallOwner());
        assertTrue(board.move(p1, new ActionCoord(new Coordinate(3, 0), new Coordinate(2, 0))));
        assertEquals(board.getPawn(new Coordinate(2, 0)).get(),board.getHumanPlayer().getBall());
        assertTrue(board.getPawn(new Coordinate(2, 0)).get().isBallOwner());
        assertFalse(board.getPawn(new Coordinate(3, 0)).get().isBallOwner());
    }

    @Test
    void undo() {
        assertTrue(board.getPawn(new Coordinate(0, 1)).isEmpty());
        assertTrue(board.getPawn(new Coordinate(0, 0)).isPresent());
        assertTrue(board.move(p1, new ActionCoord(new Coordinate(0, 0), new Coordinate(0, 1))));
        assertTrue(board.getPawn(new Coordinate(0, 1)).isPresent());
        assertTrue(board.getPawn(new Coordinate(0, 0)).isEmpty());
        board.undo();
        assertTrue(board.getPawn(new Coordinate(0, 1)).isEmpty());
        assertTrue(board.getPawn(new Coordinate(0, 0)).isPresent());
    }

    @Test
    void redo() {
        assertTrue(board.getPawn(new Coordinate(0, 1)).isEmpty());
        assertTrue(board.getPawn(new Coordinate(0, 0)).isPresent());
        assertTrue(board.move(p1, new ActionCoord(new Coordinate(0, 0), new Coordinate(0, 1))));
        assertTrue(board.getPawn(new Coordinate(0, 1)).isPresent());
        assertTrue(board.getPawn(new Coordinate(0, 0)).isEmpty());
        board.undo();
        assertTrue(board.getPawn(new Coordinate(0, 1)).isEmpty());
        assertTrue(board.getPawn(new Coordinate(0, 0)).isPresent());
        board.redo();
        assertTrue(board.getPawn(new Coordinate(0, 1)).isPresent());
        assertTrue(board.getPawn(new Coordinate(0, 0)).isEmpty());
    }

    @Test
    void undoafterRedo() {
        assertTrue(board.getPawn(new Coordinate(0, 1)).isEmpty());
        assertTrue(board.getPawn(new Coordinate(0, 0)).isPresent());
        assertTrue(board.move(p1, new ActionCoord(new Coordinate(0, 0), new Coordinate(0, 1))));
        assertTrue(board.getPawn(new Coordinate(0, 1)).isPresent());
        assertTrue(board.getPawn(new Coordinate(0, 0)).isEmpty());
        board.undo();
        assertTrue(board.getPawn(new Coordinate(0, 1)).isEmpty());
        assertTrue(board.getPawn(new Coordinate(0, 0)).isPresent());
        board.redo();
        assertTrue(board.getPawn(new Coordinate(0, 1)).isPresent());
        assertTrue(board.getPawn(new Coordinate(0, 0)).isEmpty());
        board.undo();
        assertTrue(board.getPawn(new Coordinate(0, 1)).isEmpty());
        assertTrue(board.getPawn(new Coordinate(0, 0)).isPresent());
    }

    @Test
    void checkIfWon() {

    }
}