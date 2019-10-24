package diaballik.Coordinates;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActionCoordTest {

    ActionCoord a;

    @BeforeEach
    void init() {
        a = new ActionCoord(new Coordinate(2, 1), new Coordinate(4, 5));
    }

    @Test
    void testGetSource() {
        assertEquals(new Coordinate(2, 1), a.getSource());
    }

    @Test
    void testGetTarget() {
        assertEquals(new Coordinate(4, 5), a.getTarget());
    }

    @Test
    void testInvert() {
        ActionCoord b = new ActionCoord(a.getSource(),a.getTarget());
        b.invert();
        assertEquals(a.getSource(),b.getTarget());
        assertEquals(a.getTarget(),b.getSource());
    }
}