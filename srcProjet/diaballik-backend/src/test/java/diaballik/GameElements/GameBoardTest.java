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
    Player p1 = new HumanPlayer("Toto",false);
    Player p2 = new HumanPlayer("Tata",true);

    @BeforeEach
    void init() {
        board = new GameBoard(p1, p2);
    }

    @Test
    void constructor(){
        assertTrue(board.getPawn(new Coordinate(3,0)).get().isBallOwner());
        assertTrue(board.getPawn(new Coordinate(3,6)).get().isBallOwner());
        assertFalse(board.getPawn(new Coordinate(2,6)).get().isBallOwner());
        assertTrue(board.getPawn(new Coordinate(3,3)).isEmpty());
    }
    
    @Test
    void canMove() {
        assertTrue(board.canMove(p1,new ActionCoord(new Coordinate(0,0),new Coordinate(0,1))));
    }

    @Test
    void move() {
    }

    @Test
    void addUndo() {
    }

    @Test
    void getPawn() {

    }

    @Test
    void undo() {
    }

    @Test
    void redo() {
    }




}