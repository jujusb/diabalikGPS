package diaballik.GameElements;

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
    }

    @Test
    void constructor(){
    }
    
    @Test
    void move() {
        //board.move(p1,);
    }

    @Test
    void canMove() {
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