package diaballik.coordinates;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoordinateTest {

    Coordinate c1;
    Coordinate c2;
    Coordinate c3;
    Coordinate c4;

    @BeforeEach
    void init(){
        c1 = new Coordinate(0,1);
        c2 = new Coordinate(5,6);
        c3 = new Coordinate(5,6);
        c4 = new Coordinate(0,0);
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
        assertTrue(c2.equals(c2));
    }

    @Test
    void testEqualsFalse() {
        assertFalse(c2.equals(c1));
        assertFalse(c2.equals(null));
        assertFalse(c2.equals(5));
    }
    
    @Test
    void testAbsoluteDistance(){
        assertEquals(10,c2.absoluteDistance(c1));
        assertEquals(10,c1.absoluteDistance(c2)); //to check the symmetry of the function
        assertEquals(0,c2.absoluteDistance(c3));
    }

    @Test
    void testSameDiagonal() {
        assertTrue(c1.sameDiagonal(c2));
        assertTrue(c2.sameDiagonal(c1));
        assertTrue(c1.sameDiagonal(c1));
    }

    @Test
    void testSameDiagonalFalse(){
        assertFalse(c1.sameDiagonal(c4));
        assertFalse(c4.sameDiagonal(c1));
    }

    @Test
    void testSameHorizontal() {
        c2.moveTo(new Coordinate(0,6));
        assertTrue(c2.sameHorizontal(c3));
        assertTrue(c3.sameHorizontal(c2));
        assertTrue(c2.sameHorizontal(c2));
    }

    @Test
    void testSameHorizontalFalse(){
        assertFalse(c1.sameHorizontal(c2));
        assertFalse(c2.sameHorizontal(c1));
        assertFalse(c1.sameHorizontal(c4)); //c1 and c4 are on the same column
    }

    @Test
    void testSameVertical() {
        assertTrue(c1.sameVertical(c4));
        assertTrue(c4.sameVertical(c1));
        assertTrue(c2.sameVertical(c2));
    }

    @Test
    void testSameVerticalFalse() {
        c3.moveTo(new Coordinate(6,6));
        assertFalse(c1.sameVertical(c2));
        assertFalse(c2.sameVertical(c1));
        assertFalse(c2.sameVertical(c3));   //c2 and c3 are on the same row
    }

    @Test
    void testMoveOf() {
        c1.moveOf(0,-1);
        assertEquals(c4,c1);
    }

    @Test
    void testClone() {
        Coordinate aux = (Coordinate) c1.clone();
        assertFalse(aux==c1);
        assertEquals(c1,aux);
    }
}