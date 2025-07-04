package diaballik.players;

import diaballik.gameElements.GameBoard;
import diaballik.players.algorithms.EAiType;
import diaballik.players.algorithms.NoobAlgo;
import diaballik.players.algorithms.StartingAlgo;
import diaballik.supervisors.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AiPlayerTest {
    AiPlayer p;
    HumanPlayer p1;
    AiPlayer p2;
    AiPlayer p3;
    Game g;

    @BeforeEach
    void setUp() {
        p = new AiPlayer(EAiType.NOOB, "Alice", true);
        p1 = new HumanPlayer("Alice", true);
        p2 = new AiPlayer(EAiType.STARTING, "Bob", false);
        p3 = new AiPlayer(EAiType.PROGRESSIVE, "Charlie", true);
        g = new Game(p1, p2);
    }

    @Test
    void getTURNS_BEFORE_SWAP() {
        assertEquals(-1, p.getTURNS_BEFORE_SWAP());
        assertEquals(-1, p2.getTURNS_BEFORE_SWAP());
        assertEquals(3, p3.getTURNS_BEFORE_SWAP());
    }


    @Test
    void swap() {
        p3.setCurrentTurn(2);
        p3.swap();
        assertTrue(p3.getAlgo() instanceof NoobAlgo);
        p3.setCurrentTurn(3);
        p3.swap();
        assertTrue(p3.getAlgo() instanceof StartingAlgo);
    }

    @Test
    void swapAlreadyStarting() {
        p2.setCurrentTurn(2);
        p2.swap();
        assertTrue(p2.getAlgo() instanceof StartingAlgo);
        p2.setCurrentTurn(3);
        p2.swap();
        assertTrue(p2.getAlgo() instanceof StartingAlgo);
    }

    @Test
    void setBoard() {
        GameBoard board = new GameBoard(p1, p2);
        p2.setBoard(board);
    }

    @Test
    void setCurrentTurn() {
        assertEquals(0, p.currentTurn);
        p.setCurrentTurn(1);
        assertEquals(1, p.currentTurn);
    }

    @Test
    void getAlgo() {
        assertTrue(p.getAlgo() instanceof NoobAlgo);
        assertTrue(p2.getAlgo() instanceof StartingAlgo);
        assertTrue(p3.getAlgo() instanceof NoobAlgo);
    }
}