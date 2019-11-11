package diaballik.Players;

import diaballik.GameElements.GameBoard;
import diaballik.Players.Algorithms.EAiType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AiPlayerTest {
    AiPlayer p ;
    AiPlayer p2 ;
    AiPlayer p3 ;
    @BeforeEach
    void setUp() {
        p = new AiPlayer(EAiType.NOOB, "Alice", true);
        p2 = new AiPlayer(EAiType.STARTING, "Bob", false);
        p3 = new AiPlayer(EAiType.PROGRESSIVE, "Charlie", true);
    }

    @Test
    void getTURNS_BEFORE_SWAP() {
        assertEquals(-1, p.getTURNS_BEFORE_SWAP());
        assertEquals(-1, p2.getTURNS_BEFORE_SWAP());
        assertEquals(3, p3.getTURNS_BEFORE_SWAP());
    }

    @Test
    void swap() {
        //TODO
    }

    @Test
    void getMove() {
        //TODO
    }

    @Test
    void setBoard() {
        GameBoard board = new GameBoard(p, p2);
        p.setBoard(board);
    }

    @Test
    void setCurrentTurn() {
        //TODO
    }

    @Test
    void getAlgo() {
        //TODO
    }
}