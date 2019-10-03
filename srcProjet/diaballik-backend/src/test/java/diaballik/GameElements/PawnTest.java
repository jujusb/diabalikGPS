package diaballik.GameElements;

import diaballik.Coordinates.Coordinate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PawnTest {

    Pawn a;

    @BeforeEach
    void init(){
        a = new Pawn(new Coordinate(1,2), null);
    }

    @Test
    void getPosition() {
    }

    @Test
    void setPosition() {
    }

    @Test
    void isBallOwner() {
    }

    @Test
    void setBallOwner() {
    }

    @Test
    void getPlayer() {
    }
}