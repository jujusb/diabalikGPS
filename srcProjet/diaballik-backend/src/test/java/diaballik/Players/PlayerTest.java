package diaballik.Players;

import diaballik.Coordinates.ActionCoord;
import diaballik.Coordinates.Coordinate;
import diaballik.GameElements.Pawn;
import diaballik.Supervisors.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sound.midi.Soundbank;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    Player p;
    Player p1;
    Player p2;
    Game g;

    @BeforeEach
    void setUp() {
        p = new HumanPlayer("Charlie", true);
        p1 = new HumanPlayer("Bob", true);
        p2 = new HumanPlayer("Alice", false);
        g = new Game(p1, p2);
    }

    @Test
    void testToString() {
        System.out.println(p1);
    }

    @Test
    void getBall() {
        assertEquals(new Coordinate(3, 0), p1.getBall().getPosition());
        assertEquals(new Coordinate(3, 6), p2.getBall().getPosition());
    }

    @Test
    void setBall() {
        assertEquals(new Coordinate(3, 0), p1.getBall().getPosition());
        p1.setBall(p1.pawns.get(6));
        assertEquals(new Coordinate(6, 0), p1.getBall().getPosition());
    }

    @Test
    void hasHand() {
        assertFalse(p.hasHand());
    }

    @Test
    void setHasHand() {
        assertFalse(p.hasHand());
        p.setHasHand(true);
        assertTrue(p.hasHand());
    }

    @Test
    void addPawn() {
        Pawn pawn = new Pawn(new Coordinate(0, 0), p);
        assertEquals(p.pawns.get(0), pawn);
    }

    @Test
    void getName() {
        assertEquals("Charlie", p.getName());
    }

    @Test
    void getColor() {
        assertTrue(p.getColor());
        assertTrue(p1.getColor());
        assertFalse(p2.getColor());
    }

    @Test
    void testEquals() {
        assertEquals(p1, p1);
    }

    @Test
    void testEquals2() {
        assertNotEquals(p1, p2);
    }

    @Test
    void testHashCode2() {
        assertTrue(p1.hashCode() == p1.hashCode());

    }

    @Test
    void getPawns() {
        assertEquals(p.pawns, p.getPawns());
    }

    @Test
    void heightSum() {
        assertEquals(0, p.heightSum());
        assertEquals(42, p2.heightSum());
    }


    @Test
    void testSetCurrentAction() {
        assertNull(((HumanPlayer) p).currentAction);
        ((HumanPlayer) p).setCurrentAction(new ActionCoord(new Coordinate(0, 0), new Coordinate(1, 0)));
        assertEquals(new ActionCoord(new Coordinate(0, 0), new Coordinate(1, 0)), ((HumanPlayer) p).currentAction);
    }

    @Test
    void constructeur2() {
        Player a = new HumanPlayer();
        assertNull(a.getPawns());
        assertEquals(false, p.hasHand());
    }
}