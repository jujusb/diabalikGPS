package diaballik.gameElements;

import diaballik.coordinates.Coordinate;
import diaballik.players.HumanPlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PawnTest {

    Pawn a;

    @BeforeEach
    void init() {
        a = new Pawn(new Coordinate(1, 2), new HumanPlayer("Toto",false));
    }

    @Test
    void testGetPosition() {
        assertEquals(new Coordinate(1, 2), a.getPosition());
    }

    @Test
    void testSetPosition() {
        a.setPosition(new Coordinate(5, 7));
        assertEquals(new Coordinate(5, 7), a.getPosition());
    }

    @Test
    void testIsBallOwner() {
        assertFalse(a.isBallOwner());
    }

    @Test
    void testSetBallOwner() {
        a.setBallOwner(true);
        assertTrue(a.isBallOwner());
    }

    @Test
    void testGetPlayer() {
        assertEquals(new HumanPlayer("Toto",false),a.getPlayer());
        assertEquals("Toto", a.getPlayer().getName());
        assertFalse(a.getPlayer().getColor());
    }
}