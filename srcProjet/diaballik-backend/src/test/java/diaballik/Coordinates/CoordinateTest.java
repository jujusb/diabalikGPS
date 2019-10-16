package diaballik.Coordinates;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoordinateTest {

    Coordinate c1;
    Coordinate c2;
    Coordinate c3;

    @BeforeEach
    void init(){
        c1 = new Coordinate(0,1);
        c2 = new Coordinate(5,6);
        c3 = new Coordinate(5,6);
    }

    @Test
    void testGetPosX() {
        assertEquals(0,c1.getPosX());
    }

    @Test
    void testGetPosY() {
        assertEquals(1,c1.getPosY());
    }

    @Test
    void testMoveTo() {
        c1.moveTo(c2);
        assertEquals(c2,c1);
    }

    @Test
    void testEqualsTrue() {
        assertTrue(c2.equals(c3));
    }

    @Test
    void testEqualsFalse() {
        assertFalse(c2.equals(c1));;
    }

    @Test
    void testAbsoluteDistance(){
        assertEquals(10,c2.absoluteDistance(c1));
        //ajouter d'autres cas ?
    }
}